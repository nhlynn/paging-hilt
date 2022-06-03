package com.nhlynn.paging_hilt.network

import com.google.gson.annotations.SerializedName
import com.nhlynn.paging_hilt.model.MovieVO

class CharacterResponse {
    @SerializedName("results")
    var character: List<MovieVO>? = null
}