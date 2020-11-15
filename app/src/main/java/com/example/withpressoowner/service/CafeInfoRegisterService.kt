package com.example.withpressoowner.service

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CafeInfoRegisterService {
    @FormUrlEncoded
    @POST("/cafe_info/")
    fun requestCafeInfoRegister(
        /* 카페 기본 정보 */
        @Field("cafe_name") cafe_name: String,
        @Field("cafe_addr") cafe_addr: String,
        @Field("cafe_hour") cafe_hour: String,
        @Field("cafe_tel") cafe_tel: String,
        @Field("cafe_menu") cafe_menu: String,
        /* 카페 세부 정보 */
        /* 1.책상 */
        @Field("table_info") table_info: String,
        @Field("table_size_info") table_size_info: String,
        /* 2.의자 */
        @Field("chair_back_info") chair_back_info: Boolean,
        @Field("chair_cushion_info") chair_cushion_info: String,
        /* 3.플러그 */
        @Field("num_plug") num_plug: Int,
        /* 4.음악 */
        @Field("bgm_info") bgm_info: String,
        /* 5.화장실 정보 */
        @Field("toilet_info") toilet_info: Boolean,
        @Field("toilet_gender_info") toilet_gender_info: Boolean,
        /* 6.방역 날짜 */
        @Field("sterilization_info") sterilization_info: String,
        /* 7.흡연실 유무 */
        @Field("smoking_room") smoking_room: Boolean,
        /* 8.매장 및 화장실 청결 *//*
        @Field("user_clean_info") user_clean_info: Float,
        @Field("user_toilet_clean_info") user_toilet_clean_info: Float,
        *//* 9.주변 소리 *//*
        @Field("user_noisy_info") user_noisy_info: Float,
        *//* 10.공부 잘 됨 지수 *//*
        @Field("user_good_study_info") user_good_study_info: Float,*/
        /* 11.할인율 */
        @Field("discount") discount: Int
        ): Call<Int>
}