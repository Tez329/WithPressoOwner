package com.example.withpressoowner.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.example.withpressoowner.R
import kotlinx.android.synthetic.main.fragment_detail_info.*
import org.jetbrains.anko.support.v4.toast

class DetailInfoFragment : Fragment() {
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bundle = Bundle()

        detail_info_layout.setOnClickListener {  }
        detail_info_linear.setOnClickListener {  }
        detail_info_table_size_layout.setOnClickListener {  }
        detail_info_num_outlet_layout.setOnClickListener {  }
        detail_info_genre_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        detail_info_seat1_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        detail_info_seat2_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        detail_info_seat4_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        detail_info_chair_cushion_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        detail_info_multi_seat_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        detail_info_num_outlet_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        detail_info_table_size_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }

        detail_info_chair_back_group.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.detail_info_chair_back_yes_radio -> bundle.putBoolean("chair_back_info", true)
                R.id.detail_info_chair_back_no_radio -> bundle.putBoolean("chair_back_info", false)
            }
        }

        detail_info_next_image.setOnClickListener {
            if (detail_info_seat1_edit.text.isNullOrBlank() ||
                    detail_info_seat2_edit.text.isNullOrBlank() ||
                    detail_info_seat4_edit.text.isNullOrBlank() ||
                    detail_info_multi_seat_edit.text.isNullOrBlank())
                toast("좌석 정보를 모두 입력해주세요")
            else if(detail_info_table_size_edit.text.isNullOrBlank())
                toast("책상 크기를 입력해주세요")
            else if(detail_info_chair_cushion_edit.text.isNullOrBlank())
                toast("의자 쿠션감 정보를 입력해주세요")
            else if(!detail_info_chair_back_group.isSelected)
                toast("의자 등받이 유무를 선택해주세요")
            else if(detail_info_num_outlet_edit.text.isNullOrBlank())
                toast("콘센트 개수를 입력해주세요")
            else if(detail_info_genre_edit.text.isNullOrBlank())
                toast("음악 장르를 입력해주세요")
            else {
                bundle.apply {
                    putString("cafe_name", arguments?.getString("cafe_name"))
                    putString("cafe_addr", arguments?.getString("cafe_addr"))
                    putString("cafe_hour", arguments?.getString("cafe_hour"))
                    putString("cafe_tel", arguments?.getString("cafe_tel"))
                    putString("cafe_menu", arguments?.getString("cafe_menu"))
                    putInt("table_info_seat1", detail_info_seat1_edit.text.toString().toInt())
                    putInt("table_info_seat2", detail_info_seat2_edit.text.toString().toInt())
                    putInt("table_info_seat4", detail_info_seat4_edit.text.toString().toInt())
                    putInt("table_info_multi_seat", detail_info_multi_seat_edit.text.toString().toInt())
                    putInt("table_size_info", detail_info_table_size_edit.text.toString().toInt())
                    putString("chair_cushion_info", detail_info_chair_cushion_edit.text.toString())
                    putInt("num_plug", detail_info_num_outlet_edit.text.toString().toInt())
                    putString("bgm_info", detail_info_genre_edit.text.toString())
                }
                findNavController().navigate(
                    R.id.action_detailInfoFragment_to_detailInfoSecFragment,
                    bundle
                )
            }
        }
        detail_info_prev_image.setOnClickListener {
            findNavController().navigate(R.id.action_detailInfoFragment_to_basicInfoFragment)
        }
    }

    private fun hideKeypad(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}