package io.intheloup.apptest.network.json

class SearchJson(
        val places: List<PlaceJson>? = null
) {

    class PlaceJson(
            val id: Int? = null,
            val name: String? = null,
            val images_url: List<String>? = null
//            val category: List<CategoryJson>? = null
    ) {
        class CategoryJson(val name: String? = null)
    }
}