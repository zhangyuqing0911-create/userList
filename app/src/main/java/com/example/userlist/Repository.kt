package com.example.userlist

import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    //イベントID
    val id: Int,
    //リポジトリ名
    val name: String,
    //URL
    val url: String
) {
}
