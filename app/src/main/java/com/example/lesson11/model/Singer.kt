package com.example.lesson11.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Singer(
    var id: Long,
    var name: String?,
    var singerPhoto: Int,
    var birthCountry: String?
): Parcelable

