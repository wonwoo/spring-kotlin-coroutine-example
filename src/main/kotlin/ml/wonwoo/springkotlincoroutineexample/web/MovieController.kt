package ml.wonwoo.springkotlincoroutineexample.web

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import ml.wonwoo.springkotlincoroutineexample.product.History
import ml.wonwoo.springkotlincoroutineexample.product.Movie
import ml.wonwoo.springkotlincoroutineexample.product.MovieRepository
import ml.wonwoo.springkotlincoroutineexample.product.ProductType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@RestController
@RequestMapping("/movies")
class MovieController(private val movieRepository: MovieRepository, builder: WebClient.Builder) {

    private val client = builder.baseUrl("http://localhost:8080/histories")
        .build()

    @ExperimentalCoroutinesApi
    @GetMapping
    fun movies(name: String): Flow<MovieDto> =
        movieRepository.findByName(name)
            .map { it.dto() }
            .onCompletion { exception ->
                if (exception == null) {
                    client.post()
                        .bodyValue(HistoryRequest(name = name, productType = ProductType.MOVIE))
                        .retrieve()
                        .awaitBody<History>()
                }
            }

    @GetMapping("/{id}")
    suspend fun book(@PathVariable id: String): MovieDto? =
        movieRepository.findById(id)?.also {
            client.post()
                .bodyValue(HistoryRequest(name = it.name, productType = ProductType.MOVIE))
                .retrieve()
                .awaitBody<History>()
        }?.dto()

}

data class MovieDto(

    val id: String? = null,

    val name: String,

    val image: String,

    val genre: String
)

fun Movie.dto() = MovieDto(id, name, image, genre)
