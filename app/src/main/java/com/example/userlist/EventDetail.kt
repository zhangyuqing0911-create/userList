package com.example.userlist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDetail(
    //実行結果
    val action: String?,
    //イベントID
    val number: String?,
    //プルリクエスト
    @SerialName("pull_request")
    val pullRequest: PullRequest?,
)
