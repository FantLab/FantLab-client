package ru.fantlab.android.data.dao.response

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.JsonParser
import ru.fantlab.android.data.dao.Pageable
import ru.fantlab.android.data.dao.model.BookcaseEdition
import ru.fantlab.android.provider.rest.DataManager

data class BookcaseEditionsResponse(
        val editions: Pageable<BookcaseEdition>
) {
    class Deserializer(private val perPage: Int) : ResponseDeserializable<BookcaseEditionsResponse> {

        override fun deserialize(content: String): BookcaseEditionsResponse {
            val jsonObject = JsonParser().parse(content).asJsonObject
            val items: ArrayList<BookcaseEdition> = arrayListOf()
            val array = jsonObject.getAsJsonArray("bookcase_items")
            array.map {
                items.add(DataManager.gson.fromJson(it, BookcaseEdition::class.java))
            }
            val totalCount = jsonObject.getAsJsonPrimitive("count").asInt
            val lastPage = (totalCount - 1) / perPage + 1
            val editions = Pageable(lastPage, totalCount, items)
            return BookcaseEditionsResponse(editions)
        }
    }
}