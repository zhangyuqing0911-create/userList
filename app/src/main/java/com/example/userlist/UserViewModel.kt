package com.example.userlist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel  : ViewModel() {
    var users by mutableStateOf<List<User>>(emptyList())
        private set
    var userDetail by mutableStateOf<UserDetail?>(null)
        private set

    var followList by mutableStateOf<List<User>>(emptyList())
        private set
    var followerList by mutableStateOf<List<User>>(emptyList())
        private set
    var eventsList by mutableStateOf<List<Event>>(emptyList())
        private set
    var receivedEventsList by mutableStateOf<List<Event>>(emptyList())
        private set
    var createRepositoryList by mutableStateOf<List<ReposDetail>>(emptyList())
        private set

    fun loadUsers() {
        viewModelScope.launch {
            try {
                val result = ApiClient.retrofit.getUsers()
                Log.d("API", "size = ${result.size}")
                users = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    //詳細ページロード時実行　プロフィールとイベントを取得
    fun loadUserPage(user: String) {
        viewModelScope.launch {
            try {
                val result = ApiClient.retrofit.getUserDetail(user)
                val result2 = ApiClient.retrofit.getUserEvents(user)
                val result3 = ApiClient.retrofit.getUserReceivedEvents(user)
                val result4 = ApiClient.retrofit.getUserCreateRepository(user)
                userDetail = result
                eventsList = result2
                receivedEventsList = result3
                createRepositoryList = result4
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    //フォローリストとフォロワーリストを取得
    fun getFollowerOrFollow(user: String) {
        viewModelScope.launch {
            try {
                val result = ApiClient.retrofit.getFollowingList(user)
                followList = result
                val result1 = ApiClient.retrofit.getFollowerList(user)
                followerList = result1
                Log.d("TAG", followList.toString())
                Log.d("TAG", followerList.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}