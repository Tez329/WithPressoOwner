package com.example.withpressoowner.service

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IdDupConfirmService{
    @FormUrlEncoded
    @POST("/id_dup_check/")
    fun requestIdDupConfirm(
        @Field("owner_id") owner_id:String
    ):Call<String>
}