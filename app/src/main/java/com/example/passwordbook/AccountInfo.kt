package com.example.passwordbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mychat.showLog
import com.example.mychat.showToast
import com.example.passwordbook.database.Account
import com.example.passwordbook.database.AppDatabase
import com.example.passwordbook.databinding.ActivityAccountInfoBinding
import kotlin.concurrent.thread

class AccountInfo : AppCompatActivity() {

    private lateinit var binding: ActivityAccountInfoBinding
    private val tag = "AccountInfo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newAccountName = binding.newAccountName
        val newUsername = binding.newUsername
        val newPassword = binding.newPassword
        val newRemark = binding.newRemark
        val confirmButton = binding.confirmButton
        val accountDao = AppDatabase.getDatabase(this).accountDao()
        var isUpdate = false

        val curAccount = intent.getSerializableExtra("account") as Account
        val accountName = curAccount.accountName
        val username = curAccount.username
        val password = curAccount.password
        val remark = curAccount.remark
        val id = curAccount.id
        if (accountName != "") {
            isUpdate = true
        }
        newAccountName.setText(accountName)
        newUsername.setText(username)
        newPassword.setText(password)
        newRemark.setText(remark)

        confirmButton.setOnClickListener {
            val accountText = newAccountName.text.toString()
            val usernameText = newUsername.text.toString()
            val passwordText = newPassword.text.toString()
            val remarkText = newRemark.text.toString()
            if (accountText.isEmpty()) {
                "账号名不能为空".showToast(this)
            }
            else if (usernameText.isEmpty()) {
                "用户名不能为空".showToast(this)
            }
            else if (passwordText.isEmpty()) {
                "密码不能为空".showToast(this)
            }
            else {
                val newAccount = Account(accountText, usernameText,
                    passwordText, remarkText, System.currentTimeMillis())
                if (!isUpdate) {
                    val newId = accountDao.insertAccount(newAccount)
                    newAccount.id = newId
                }
                else {
                    accountDao.updateAccountById(id, accountText, usernameText,
                        passwordText, remarkText, System.currentTimeMillis())
                }
                val intent = Intent()
                intent.putExtra("isUpdate", isUpdate)
                intent.putExtra("accountId", id)
                intent.putExtra("newAccount", newAccount)
                setResult(RESULT_OK,intent)
                finish()
            }
        }

    }
}