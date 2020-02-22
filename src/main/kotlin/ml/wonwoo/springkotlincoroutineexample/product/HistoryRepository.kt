package ml.wonwoo.springkotlincoroutineexample.product

import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface HistoryRepository : CoroutineCrudRepository<History, String> {

    @Tailable
    fun findBy(): Flow<History>

}