package com.example.withpressoowner.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.withpressoowner.R
import kotlinx.android.synthetic.main.fragment_basic_info.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.jetbrains.anko.support.v4.toast

class BasicInfoFragment : Fragment() {
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* init */
        bundle = Bundle()

        /* event listener */
        basic_info_layout.setOnClickListener {  }
        basic_info_linear.setOnClickListener {  }
        basic_info_cafe_name_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        basic_info_hour_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        basic_info_menu_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        basic_info_tel_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }
        basic_info_cafe_addr_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }


        basic_info_prev_image.setOnClickListener {
            findNavController().navigate(R.id.action_basicInfoFragment_to_signUpFragment)
        }
        basic_info_next_image.setOnClickListener {
            if (basic_info_cafe_name_edit.text.toString().isNullOrBlank())
                toast("카페 이름을 입력해주세요")
            else if (basic_info_cafe_addr_edit.text.toString().isNullOrBlank())
                toast("카페 주소를 입력해주세요")
            else if (basic_info_hour_edit.text.toString().isNullOrBlank())
                toast("카페 운영 시간을 입력해주세요")
            else if (basic_info_tel_edit.text.toString().isNullOrBlank())
                toast("카페 전화 번호를 입력해주세요")
            else if (basic_info_menu_edit.text.toString().isNullOrBlank())
                toast("카페 메뉴를 입력해주세요")
            else {
                val bundle = Bundle().apply {
                    putString("cafe_name", basic_info_cafe_name_edit.text.toString())
                    putString("cafe_addr", basic_info_cafe_addr_edit.text.toString())
                    putString("cafe_hour", basic_info_hour_edit.text.toString())
                    putString("cafe_tel", basic_info_tel_edit.text.toString())
                    putString("cafe_menu", basic_info_menu_edit.text.toString())
                }
                findNavController().navigate(
                    R.id.action_basicInfoFragment_to_detailInfoFragment,
                    bundle
                )
            }
        }
    }

    private fun hideKeypad(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}