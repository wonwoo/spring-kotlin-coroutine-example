package ml.wonwoo.springkotlincoroutineexample

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import ml.wonwoo.springkotlincoroutineexample.product.Book
import ml.wonwoo.springkotlincoroutineexample.product.BookRepository
import ml.wonwoo.springkotlincoroutineexample.product.Movie
import ml.wonwoo.springkotlincoroutineexample.product.MovieRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.data.mongodb.core.CollectionOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Component

@Component
class InitializerData(

    private val bookRepository: BookRepository,
    private val movieRepository: MovieRepository,
    private val reactiveMongoTemplate: ReactiveMongoTemplate

) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        runBlocking {
            reactiveMongoTemplate.createCollection("history",
                CollectionOptions.empty().size(1024).maxDocuments(5).capped()).awaitSingle()
            movieRepository.save(Movie(name = "Back to the Future", image = "http://localhost:8080", genre = "SF"))
            bookRepository.save(Book(name = "Effective Java", image = "http://localhost:8080", author = "Joshua Bloch")).awaitSingle()
            bookRepository.save(Book(name = "Back to the Future", image = "http://localhost:8080", author = "Michael Klastorin")).awaitSingle()
            bookRepository.save(Book(name = "Back to the Future", image = "http://localhost:8080", author = "Michael Klastorin")).awaitSingle()
        }
    }
}