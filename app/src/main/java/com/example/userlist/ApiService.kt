package com.example.userlist

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
    //ユーザ詳細取得
    @GET("users/{user}")
    suspend fun getUserDetail(
        @Path("user") user: String
    ): UserDetail
    //フォロワーリスト取得
    @GET("users/{user}/followers")
    suspend fun getFollowerList(
        @Path("user") user: String
    ): List<User>
    //フォローリスト取得
    @GET("users/{user}/following")
    suspend fun getFollowingList(
        @Path("user") user: String
    ): List<User>
    //ユーザイベント取得
    @GET("users/{user}/events")
    suspend fun getUserEvents(
        @Path("user") user: String
    ): List<Event>
    //ユーザ受け取りイベント取得
    @GET("users/{user}/received_events")
    suspend fun getUserReceivedEvents(
        @Path("user") user: String
    ): List<Event>
    //ユーザ作成リポジトリ取得
    @GET("users/{user}/repos")
    suspend fun getUserCreateRepository(
        @Path("user") user: String
    ): List<ReposDetail>
}