package io.intheloup.apptest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(val id: Int,
                 val name: String,
                 val image: String,
                 val category: String) : Parcelable