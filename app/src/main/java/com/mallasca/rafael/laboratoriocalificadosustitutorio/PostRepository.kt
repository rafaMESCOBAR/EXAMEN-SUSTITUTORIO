package com.mallasca.rafael.laboratoriocalificadosustitutorio

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getPosts(): Result<List<Post>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getPosts()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body is List<*>) {
                    try {
                        val posts = body.filterIsInstance<Post>()
                        Result.success(posts)
                    } catch (e: Exception) {
                        Result.failure(Exception("Error parsing response"))
                    }
                } else {
                    Result.failure(Exception("Unexpected response format"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}