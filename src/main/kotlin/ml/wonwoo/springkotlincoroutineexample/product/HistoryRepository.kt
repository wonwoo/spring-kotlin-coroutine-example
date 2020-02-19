package ml.wonwoo.springkotlincoroutineexample.product

import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Flux

interface HistoryRepository : CoroutineCrudRepository<History, String> {

    @Tailable
    fun findBy(): Flow<History>

}