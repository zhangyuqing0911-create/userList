package com.example.userlist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReposDetail(
    //イベントID
    val id: Int,
    //リポジトリ名
    val name: String,
    //URL
    val url: String,
    //作成者
    val owner: User,
    //説明
    val description: String?,
    //作成日
    @SerialName("created_at")
    val createdAt: String?,
    //更新日
    @SerialName("updated_at")
    val updatedAt: String?,
    val language: String?,
)
