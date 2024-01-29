package com.example.media_player.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Singer(
    var id: Long,
    var name: String?,
    var singerPhoto: Int,
    var birthCountry: String?
): Parcelable

