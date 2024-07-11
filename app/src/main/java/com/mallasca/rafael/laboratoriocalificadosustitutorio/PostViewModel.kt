package com.mallasca.rafael.laboratoriocalificadosustitutorio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class PostViewModel : ViewModel() {
    private val repository = PostRepository()

    private val _postsState = MutableStateFlow<PostsState>(PostsState.Loading)
    val postsState: StateFlow<PostsState> = _postsState

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _postsState.value = PostsState.Loading
            val result = repository.getPosts()
            _postsState.value = when {
                result.isSuccess -> {
                    val posts = result.getOrNull() ?: emptyList()
                    if (posts.isEmpty()) PostsState.Error(R.string.error_empty_list)
                    else PostsState.Success(posts)
                }
                result.isFailure -> {
                    val error = result.exceptionOrNull()
                    when (error) {
                        is UnknownHostException -> PostsState.Error(R.string.error_no_internet)
                        is Exception -> {
                            if (error.message == "Unexpected response format") {
                                PostsState.UnexpectedError
                            } else {
                                PostsState.Error(R.string.error_unknown)
                            }
                        }
                        else -> PostsState.Error(R.string.error_unexpected)
                    }
                }
                else -> PostsState.Error(R.string.error_unexpected)
            }
        }
    }
}

sealed class PostsState {
    object Loading : PostsState()
    data class Success(val posts: List<Post>) : PostsState()
    data class Error(val messageResId: Int) : PostsState()
    object UnexpectedError : PostsState()
}