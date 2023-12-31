package com.powerusertech.moviesverse.data.models.moviedetails

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("name")
    val name:String,
    @SerializedName("profile_path")
    val  profileUrl:String,
)
