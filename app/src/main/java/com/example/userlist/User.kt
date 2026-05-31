package com.example.userlist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val login: String, //詳細APIキー
    val id: Int, //内部キー

    @SerialName("avatar_url")
    val avatarUrl: String //画像URL
)
