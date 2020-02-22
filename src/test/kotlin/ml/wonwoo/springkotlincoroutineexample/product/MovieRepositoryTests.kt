package ml.wonwoo.springkotlincoroutineexample.product

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.TestConstructor

@DataMongoTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class MovieRepositoryTests(private val movieRepository: MovieRepository) {

    //TODO TEST
    @Test
    fun `find by username test`() {
        runBlocking {
            movieRepository.save(Movie(name = "foo", image = "http://localhost:8080", genre = "SF"))
            val movies = movieRepository.findByName("foo").toList()
            assertThat(movies).hasSize(1)
            assertThat(movies[0].name).isEqualTo("foo")
            assertThat(movies[0].image).isEqualTo("http://localhost:8080")
            assertThat(movies[0].genre).isEqualTo("SF")
        }
    }
}