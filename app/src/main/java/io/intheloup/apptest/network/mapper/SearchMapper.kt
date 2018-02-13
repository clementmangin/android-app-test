package io.intheloup.apptest.network.mapper

import io.intheloup.apptest.model.Place
import io.intheloup.apptest.network.json.SearchJson

object SearchMapper {

    fun map(json: SearchJson): List<Place> {
        return json.places?.map {
            Place(
                    it.id ?: -1,
                    it.name ?: "",
                    it.images_url?.firstOrNull() ?: "",
                     "category"
            )
        } ?: emptyList()
    }
}