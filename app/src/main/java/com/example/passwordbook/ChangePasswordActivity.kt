package com.example.passwordbook

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mychat.showToast
import com.example.passwordbook.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp = getSharedPreferences("data", Context.MODE_PRIVATE)
        val oldPassword = sp.getString("password","").toString()

        val oldLoginPwd = binding.oldLoginPwd
        val newLoginPwd = binding.newLoginPwd
        val repeatNewLoginPwd = binding.repeatNewLoginPwd
        val confirmChangeBtn = binding.confirmChangeButton

        confirmChangeBtn.setOnClickListener {
            if (oldLoginPwd.text.toString() == oldPassword) {
                val new = newLoginPwd.text.toString()
                val repeat = repeatNewLoginPwd.text.toString()
                if (new.isEmpty()) {
                    "新密码不能为空！".showToast(this)
                }
                else if (repeat != new) {
                    "两次输入密码不一致！".showToast(this)
                }
                else {
                    val editor = sp.edit()
                    editor.putString("password", new)
                    editor.apply()
                    "修改密码成功！".showToast(this)
                    finish()
                }
            }
            else {
                "原密码输入错误！".showToast(this)
            }
        }

    }
}