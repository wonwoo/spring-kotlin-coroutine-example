package ml.wonwoo.springkotlincoroutineexample.web

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import ml.wonwoo.springkotlincoroutineexample.product.Book
import ml.wonwoo.springkotlincoroutineexample.product.BookRepository
import ml.wonwoo.springkotlincoroutineexample.product.Movie
import ml.wonwoo.springkotlincoroutineexample.product.MovieRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(private val bookRepository: BookRepository,
                        private val movieRepository: MovieRepository) {

    @GetMapping
    suspend fun products() = coroutineScope {
        val books = async { bookRepository.findAll().asFlow().toList() }
        val movies = async { movieRepository.findAll().toList() }
        Product(books = books.await(), movies = movies.await())
    }


}

data class Product(

    val books: List<Book>,
    val movies: List<Movie>
)