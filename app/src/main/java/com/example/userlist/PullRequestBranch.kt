package com.example.userlist

import kotlinx.serialization.Serializable

@Serializable
data class PullRequestBranch(
    //ブランチ名
    val ref: String?,
    //内部キー
    val sha: String?,
    //リポジトリ情報
    val repo: Repository?,

)
