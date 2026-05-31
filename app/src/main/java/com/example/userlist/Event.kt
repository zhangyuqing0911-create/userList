package com.example.userlist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Event(
    //イベントID
    val id: String,
    //イベントタイプ
    val type: String,
    //実行者
    val actor: User,
    //対象リポジトリ
    val repo: Repository,
    //実行内容
    val payload: JsonElement,
    //公開ステータス
    val public: Boolean,
    //実行日時
    @SerialName("created_at")
    val createdAt: String
)
