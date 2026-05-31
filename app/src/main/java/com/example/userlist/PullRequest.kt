package com.example.userlist

import kotlinx.serialization.Serializable

@Serializable
data class PullRequest( //プルリクエスト詳細
    //実行結果
    val url: String?,
    //内部キー
    val id: String?,
    //リクエストID
    val number: String?,
    //送り元ブランチ
    val head: PullRequestBranch?,
    //送り先ブランチ
    val base: PullRequestBranch?,
)
