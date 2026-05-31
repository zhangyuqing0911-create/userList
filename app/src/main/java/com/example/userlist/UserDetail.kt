package com.example.userlist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetail(
    //ログインID
    val login: String,
    //内部キー
    val id: Int,
    //GitHub内部キー
    @SerialName("node_id")
    val nodeId: String,
    //画像URL
    @SerialName("avatar_url")
    val avatarUrl: String,
    //作成したリポジトリ一覧
    @SerialName("gists_url")
    val gistsUrl: String,
//    //お気に入り登録したリポジトリ一覧 ※詳細ページに不必要
//    @SerialName("starred_url")
//    val starredUrl: String,
//    //更新通知受け取り設定しているリポジトリ一覧 ※詳細ページに不必要
//    @SerialName("subscriptions_url")
//    val subscriptionsUrl: String,
    //所属組織一覧
    @SerialName("organizations_url")
    val organizationsUrl: String,
//    //フォークしたリポジトリ ※詳細ページに不必要
//    @SerialName("repos_url")
//    val reposUrl: String,

//    //イベント ※APIメソッドを作成
//    @SerialName("events_url")
//    val eventsUrl: String,

//    //受け取りイベント ※APIメソッドを作成
//    @SerialName("received_events_url")
//    val receivedEventsUrl: String,

    //ユーザタイプ
    val type: String,

    //公開設定
    @SerialName("user_view_type")
    val userViewType: String,

    //管理者権限
    @SerialName("site_admin")
    val siteAdmin: Boolean,

    //名前
    val name: String?,
    //社名
    val company: String?,
    //ブログURL
    val blog: String?,
    //居住地
    val location: String?,
    //メール
    val email: String?,
    //フォロワー数
    val followers: Int,
    //フォロー数
    val following: Int,
    //登録日
    @SerialName("created_at")
    val createdAt: String,
    //最終更新日
    @SerialName("updated_at")
    val updatedAt: String
)
