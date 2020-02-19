package ml.wonwoo.springkotlincoroutineexample.product

import java.time.LocalDateTime

data class History(

    val id: String? = null,

    val name: String,

    val productType: ProductType,

    val registerTime: LocalDateTime

)

enum class ProductType {

    BOOK,

    MOVIE

}