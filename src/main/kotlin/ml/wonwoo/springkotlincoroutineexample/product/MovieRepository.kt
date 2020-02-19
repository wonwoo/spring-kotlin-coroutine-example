package ml.wonwoo.springkotlincoroutineexample.product

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface MovieRepository : CoroutineCrudRepository<Movie, String> {

    fun findByName(name: String): Flow<Movie>

}