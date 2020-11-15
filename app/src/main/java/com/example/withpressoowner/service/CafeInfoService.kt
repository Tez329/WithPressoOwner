package com.example.withpressoowner.service

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.Serializable

data class CafeInfo (
    /* basic */
    @SerializedName("cafe_name") var cafe_name: String,
    @SerializedName("cafe_addr") var cafe_addr: String,
    @SerializedName("cafe_hour") var cafe_hour: String,
    @SerializedName("cafe_tel") var cafe_tel:String,
    @SerializedName("cafe_menu") var cafe_menu:String,
    /* table*/
    @SerializedName("table_info") var table_struct: String,
    @SerializedName("table_size_info") var table_size: String,
    /* chair */
    @SerializedName("chair_back_info") var chair_back: Int,
    @SerializedName("chair_cushion_info") var chair_cushion: String,
    /* # of plug */
    @SerializedName("num_plug") var num_plug: Int,
    /* music*/
    @SerializedName("bgm_info") var music_genre: String,
    /* restroom */
    @SerializedName("toilet_info") var rest_in: Int,
    @SerializedName("toilet_gender_info") var rest_gen_sep: Int,
    /*anti-corona */
    @SerializedName("sterilization_info") var anco_data: String,
    /*smoking room*/
    @SerializedName("smoking_room") var smoking_room: Int,
    /* user review */
    @SerializedName("user_clean_info") var cafe_clean: Float,
    @SerializedName("user_toilet_clean_info") var rest_clean: Float,
    @SerializedName("user_noisy_info") var noise: Float,
    @SerializedName("user_good_study_info") var study_well: Float
): Serializable

interface CafeInfoService {
    @GET("/${"cafe_asin"}/cafe_info/")
    suspend fun requestCafeInfo(
        @Path("cafe_asin") cafe_asin: Int
    ): CafeInfo
}