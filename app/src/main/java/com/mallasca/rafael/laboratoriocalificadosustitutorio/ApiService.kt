package com.mallasca.rafael.laboratoriocalificadosustitutorio

import retrofit2.http.GET
import retrofit2.Response

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}