package ml.wonwoo.springkotlincoroutineexample.web

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import ml.wonwoo.springkotlincoroutineexample.product.Book
import ml.wonwoo.springkotlincoroutineexample.product.BookRepository
import ml.wonwoo.springkotlincoroutineexample.product.History
import ml.wonwoo.springkotlincoroutineexample.product.ProductType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody


@RestController
@RequestMapping("/books")
class BookController(private val bookRepository: BookRepository, builder: WebClient.Builder) {

    private val client = builder.baseUrl("http://localhost:8080/histories")
        .build()

    @GetMapping
    @ExperimentalCoroutinesApi
    fun books(name: String): Flow<BookDto> =
        bookRepository.findByName(name).asFlow()
            .map { it.dto() }
            .onCompletion { exception ->
                if (exception == null) {
                    client.post()
                        .bodyValue(HistoryRequest(name = name, productType = ProductType.BOOK))
                        .retrieve()
                        .awaitBody<History>()

                }
            }

    @GetMapping("/{id}")
    suspend fun book(@PathVariable id: String): BookDto? =
        bookRepository.findById(id).awaitFirstOrNull()?.also {
            client.post()
                .bodyValue(HistoryRequest(name = it.name, productType = ProductType.BOOK))
                .retrieve()
                .awaitBody<History>()
        }?.dto()
}

data class BookDto(

    val id: String? = null,

    val name: String,

    val image: String,

    val author: String
)

fun Book.dto() = BookDto(id, name, image, author)
