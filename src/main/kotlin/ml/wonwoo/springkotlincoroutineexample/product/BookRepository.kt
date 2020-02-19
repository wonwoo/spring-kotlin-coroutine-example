package ml.wonwoo.springkotlincoroutineexample.product

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface BookRepository : ReactiveMongoRepository<Book, String> {

    fun findByName(name: String): Flux<Book>
}