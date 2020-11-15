package com.example.withpressoowner

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.withpressoowner.service.CafeInfo
import com.example.withpressoowner.service.LogInService
import com.example.withpressoowner.service.Login
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* init */
        pref = getSharedPreferences("owner_info", 0)
        retrofit = Retrofit.Builder()
            .baseUrl("https://withpresso.gq")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        /* 자동 로그인 */
        autoLogIn()

        /* set Listener */
        log_in_layout.setOnClickListener{}
        log_in_id_edit.setOnClickListener { showKeypad(it) }
        log_in_id_edit.setOnFocusChangeListener { v, hasFocus ->
                if(!hasFocus)
                    hideKeypad(v)
        }
        log_in_pw_edit.setOnClickListener { showKeypad(it) }
        log_in_pw_edit.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
                hideKeypad(v)
        }

        log_in_button.setOnClickListener {
            /* 서버로부터 데이터 받아와서 pref에 저장하기 */
            val id = log_in_id_edit.text.toString()
            val pw = log_in_pw_edit.text.toString()
            val logInService = retrofit.create(LogInService::class.java)
            logInService.requestLogin(id, pw).enqueue(object :Callback<Login> {
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    val logInResult = response.body()

                    logInResult?.let{
                        if (logInResult.cafe_asin == -1)
                            toast("로그인 실패")
                        else {
                            val edit = pref.edit()
                            edit.putString("id", log_in_id_edit.text.toString())
                            edit.putString("pw", log_in_pw_edit.text.toString())
                            edit.putInt("cafe_asin", logInResult.cafe_asin)
                            edit.putString("owner_name", logInResult.owner_name)
                            edit.apply()

                            startActivity<CafeManageActivity>(
                                "cafe_asin" to pref.getInt("cafe_asin", 0)
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<Login>, t: Throwable) {
                    val log = AnkoLogger(this::class.java)
                    log.error("Log in Network error")

                    toast("로그인 실패")
                }
            })
        }

        log_in_sign_up_button.setOnClickListener {
            startActivity<SignUpActivity>()
        }
    }

    private fun showKeypad(view: View) {
        view.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    private fun hideKeypad(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun autoLogIn() {
        if (pref.contains("id") && pref.contains("pw") &&
            pref.contains("owner_name") && pref.contains("cafe_asin"))
            startActivity<CafeManageActivity>(
                "cafe_asin" to pref.getInt("cafe_asin", 0)
            )
    }
}