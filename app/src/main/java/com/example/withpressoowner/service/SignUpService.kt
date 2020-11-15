package com.example.withpressoowner.service

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignUpService {
    @FormUrlEncoded
    @POST("/sign_up/")
    suspend fun requestSignUp(
        @Field("owner_id") owner_id: String,
        @Field("owner_pw") owner_pw: String,
        @Field("owner_name") owner_name: String,
        @Field("busi_num") busi_num: String
    ): Int
}