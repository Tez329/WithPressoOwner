package com.example.withpressoowner.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.withpressoowner.CafeManageActivity
import com.example.withpressoowner.R
import com.example.withpressoowner.service.CafeInfoRegisterService
import kotlinx.android.synthetic.main.fragment_basic_info.*
import kotlinx.android.synthetic.main.fragment_detail_info.*
import kotlinx.android.synthetic.main.fragment_detail_info_sec.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field

class DetailInfoSecFragment : Fragment() {
    private lateinit var pref: SharedPreferences
    private lateinit var retrofit: Retrofit
    private lateinit var scope: CoroutineScope

    private var rest_in = true
    private var rest_gen_sep = true
    private var smoking = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_info_sec, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* init */
        pref = requireActivity().getSharedPreferences("owner_info", 0)
        retrofit = Retrofit.Builder()
            .baseUrl("https://withpresso.gq")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        scope = CoroutineScope(Dispatchers.IO)

        detail_info2_layout.setOnClickListener {  }
        detail_info2_linear.setOnClickListener {  }
        detail_info2_anti_corona_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        detail_info2_discount_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }

        detail_info2_rest_loc_group.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.detail_info2_rest_in_radio -> rest_in = true
                R.id.detail_info2_rest_out_radio -> rest_in = false
            }
        }
        detail_info2_rest_gen_sep_group.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.detail_info2_gen_sep_yes_radio -> rest_gen_sep = true
                R.id.detail_info2_gen_sep_no_radio -> rest_gen_sep = false
            }
        }
        detail_info2_smoking_room_group.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.detail_info2_smoking_room_in_radio -> smoking = true
                R.id.detail_info2_smoking_room_out_radio -> smoking = false
            }
        }

        detail_info2_prev_image.setOnClickListener {
            findNavController().navigate(R.id.action_detailInfoSecFragment_to_detailInfoFragment)
        }
        detail_info2_next_image.setOnClickListener {
            if(!detail_info2_rest_loc_group.isSelected)
                toast("화장실 위치 정보를 선택해주세요")
            else if(!detail_info2_rest_gen_sep_group.isSelected)
                toast("화장실 성별 분리 여부를 선택해주세요")
            else if(!detail_info2_smoking_room_group.isSelected)
                toast("매장 내 흡연실 유무를 선택해주세요")
            else if(detail_info2_anti_corona_edit.text.isNullOrBlank())
                toast("최근 방역 날짜를 입력해주세요")
            else if(detail_info2_discount_edit.text.isNullOrBlank())
                toast("재주문 시 할인율을 입력해주세요")
            else {
                /* 카페 기본 정보 */
                val cafe_name = arguments?.getString("cafe_name")
                val cafe_hour = arguments?.getString("cafe_hour")
                val cafe_addr = arguments?.getString("cafe_addr")
                val cafe_tel = arguments?.getString("cafe_tel")
                val cafe_menu = arguments?.getString("cafe_menu")
                /* 카페 세부 정보 */
                /* 1.책상 */
                val table_info =
                        "${arguments?.getInt("table_info_seat1")}/" +
                        "${arguments?.getInt("table_info_seat2")}/" +
                        "${arguments?.getInt("table_info_seat4")}/" +
                        "${arguments?.getInt("table_info_multi_seat")}"
                val table_size_info = "${arguments?.getInt("table_size_info")}"
                /* 2.의자 */
                val chair_back_info = arguments?.getBoolean("chair_back_info")
                val chair_cushion_info = arguments?.getString("chair_cushion_info")
                /* 3.플러그 */
                val num_plug = arguments?.getInt("num_plug")
                /* 4.음악 */
                val bgm_info = arguments?.getString("bgm_info")
                /* 5.화장실 정보 */
                val toilet_info = rest_in
                val toilet_gender_info = rest_gen_sep
                /* 6.방역 날짜 */
                val sterilization_info = detail_info2_anti_corona_edit.text.toString()
                /* 7.흡연실 유무 */
                val smoking_room = smoking
                /* 11.할인율 */
                val discount = detail_info2_discount_edit.text.toString().toInt()
                val cafeInfoRegisterService = retrofit.create(CafeInfoRegisterService::class.java)
                cafeInfoRegisterService.requestCafeInfoRegister(
                    cafe_name!!, cafe_addr!!, cafe_hour!!, cafe_tel!!, cafe_menu!!,
                    table_info, table_size_info,
                    chair_back_info!!, chair_cushion_info!!,
                    num_plug!!,
                    bgm_info!!,
                    toilet_info, toilet_gender_info,
                    sterilization_info,
                    smoking_room,
                    discount
                ).enqueue(object: Callback<Int> {
                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        val cafe_asin = response.body()

                        cafe_asin?.let {
                            if (cafe_asin == -1)
                                toast("카페 정보 등록을 실패했습니다")
                            else {
                                val edit = pref.edit()
                                edit.putInt("cafe_asin", cafe_asin).apply()

                                longToast("카페 정보를 성공적으로 등록했습니다")

                                startActivity<CafeManageActivity>()
                            }
                        }
                    }
                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        val log = AnkoLogger(requireContext()::class.java)
                        log.error("Network error")

                        alert(title = "카페 정보 등록 실패", message = "통신 오류")
                    }
                })
            }
        }
    }

    private fun hideKeypad(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}