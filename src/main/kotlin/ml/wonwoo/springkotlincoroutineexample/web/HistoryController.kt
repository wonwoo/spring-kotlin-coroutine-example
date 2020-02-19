package ml.wonwoo.springkotlincoroutineexample.web

import kotlinx.coroutines.flow.Flow
import ml.wonwoo.springkotlincoroutineexample.product.History
import ml.wonwoo.springkotlincoroutineexample.product.HistoryRepository
import ml.wonwoo.springkotlincoroutineexample.product.ProductType
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime


@RestController
@RequestMapping("/histories")
class HistoryController(private val historyRepository: HistoryRepository) {

    @GetMapping(produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun historiesEvent(): Flow<History> = historyRepository.findBy()

    @PostMapping
    suspend fun save(@RequestBody historyRequest: HistoryRequest): History =
        historyRepository.save(historyRequest.toDto())

}

data class HistoryRequest(
    val name: String,

    val productType: ProductType
)

fun HistoryRequest.toDto(): History =
    History(name = this.name, productType = this.productType, registerTime = LocalDateTime.now())