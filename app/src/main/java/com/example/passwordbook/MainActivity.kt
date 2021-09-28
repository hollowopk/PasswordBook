package com.example.passwordbook

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.mychat.showLog
import com.example.mychat.showToast
import com.example.passwordbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginPassword: TextView
    private var curString = ""
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.hide()

        val zero = binding.zero
        val one = binding.one
        val two = binding.two
        val three = binding.three
        val four = binding.four
        val five = binding.five
        val six = binding.six
        val seven = binding.seven
        val eight = binding.eight
        val nine = binding.nine
        val delete = binding.delete
        val confirm = binding.confirm
        loginPassword = binding.loginPassword

        val sp = getSharedPreferences("data", Context.MODE_PRIVATE)
        password = sp.getString("password","").toString()
        if (password == "") {
            confirm.visibility = View.VISIBLE
            val ss = SpannableString("初次登陆，请输入密码并点击确定")
            val ass = AbsoluteSizeSpan(80)
            ss.setSpan(ass,0,ss.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            loginPassword.hint = ss
        }

        zero.setOnClickListener {
            onClickAction("0",)
        }
        one.setOnClickListener {
            onClickAction("1",)
        }
        two.setOnClickListener {
            onClickAction("2",)
        }
        three.setOnClickListener {
            onClickAction("3",)
        }
        four.setOnClickListener {
            onClickAction("4",)
        }
        five.setOnClickListener {
            onClickAction("5",)
        }
        six.setOnClickListener {
            onClickAction("6",)
        }
        seven.setOnClickListener {
            onClickAction("7")
        }
        eight.setOnClickListener {
            onClickAction("8")
        }
        nine.setOnClickListener {
            onClickAction("9")
        }
        confirm.setOnClickListener {
            val newPassword = curString
            if (newPassword.isNotEmpty()) {
                val editor = sp.edit()
                editor.putString("password", newPassword)
                editor.apply()
                login()
            }
            else {
                "密码不能为空！".showToast(this)
            }
        }
        delete.setOnClickListener {
            onClickAction("delete")
        }



    }

    private fun onClickAction(key: String) {
        var curPassword = loginPassword.text.toString()
        if (key != "delete") {
            curString += key
            curPassword += "*"
            if (curString == password) {
                login()
            }
        }
        else {
            if (curString.isNotEmpty()) {
                curString = curString.substring(0,curString.length-1)
                curPassword = curPassword.substring(0,curPassword.length-1)
            }
        }
        loginPassword.setText(curPassword)
    }

    private fun login() {
        val intent = Intent(this,AccountList::class.java)
        startActivity(intent)
        finish()
    }

}