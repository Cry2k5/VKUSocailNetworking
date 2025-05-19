package com.dacs3.socialnetworkingvku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.socialnetworkingvku.data.user.UserFollowingDto
import com.dacs3.socialnetworkingvku.repository.FollowerRepository
import kotlinx.coroutines.launch

class FollowerViewModel(private val repository: FollowerRepository) : ViewModel() {

    private val _followingList = MutableLiveData<List<UserFollowingDto>>()
    val followingList: LiveData<List<UserFollowingDto>> = _followingList

    private val _peopleList = MutableLiveData<List<UserFollowingDto>>()
    val peopleList: LiveData<List<UserFollowingDto>> = _peopleList

    fun getFollowing() {
        viewModelScope.launch {
            val result = repository.getFollowing()
            if (result.isSuccess) {
                _followingList.value = result.getOrThrow()
            }
        }
    }

    fun getPeople() {
        viewModelScope.launch {
            val result = repository.getPeople()
            if (result.isSuccess) {
                _peopleList.value = result.getOrThrow()
            }
        }
    }

    fun follow(userId: Long) {
        viewModelScope.launch {
            val result = repository.follow(userId)
            if (result.isSuccess) {

                val user = _peopleList.value?.find { it.userId == userId }
                user?.let {
                    _followingList.value = (_followingList.value ?: emptyList()) + it
                    _peopleList.value = _peopleList.value?.filterNot { it.userId == userId }
                }
            }
        }
    }

    fun unfollow(userId: Long) {
        viewModelScope.launch {
            val result = repository.unfollow(userId)
            Log.d("FollowerViewModel", "Unfollow result: $result")
            if (result.isSuccess) {
               Log.d("FollowerViewModel", "Unfollow successful")
                val user = _followingList.value?.find { it.userId == userId }
                user?.let {
                    _followingList.value = _followingList.value?.filterNot { it.userId == userId }
                    _peopleList.value = (_peopleList.value ?: emptyList()) + it
                }
            }
        }
    }
}
