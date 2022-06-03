package com.nhlynn.paging_hilt.model

import com.google.gson.annotations.SerializedName

data class MovieVO(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("species")
    var species: String? = null,
    @SerializedName("image")
    var image: String? = null
)
