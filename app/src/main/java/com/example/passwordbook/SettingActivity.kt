package com.example.passwordbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.passwordbook.databinding.ActivitySettingBinding
import com.csvreader.*
import com.example.mychat.showToast
import com.example.passwordbook.database.AppDatabase
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import kotlin.concurrent.thread

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = AppDatabase.getDatabase(this)

        val changePwdBtn = binding.changePwdBtn
        val exportBtn = binding.exportBtn

        changePwdBtn.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        exportBtn.setOnClickListener {
            val file = File(externalCacheDir, "account.csv")
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            thread {
                val csvWriter = CsvWriter(file.absolutePath, ',', Charset.forName("UTF-8"))
                val headers = arrayOf("accountName", "username", "password", "remark")
                csvWriter.writeRecord(headers)
                val accountDao = database.accountDao()
                val accounts = accountDao.loadAllAccount()
                for (account in accounts) {
                    val accountInfo = arrayOf(account.accountName, account.username,
                        account.password, account.remark)
                    csvWriter.writeRecord(accountInfo)
                }
                csvWriter.close()
                runOnUiThread {
                    "文件已保存至${file.absolutePath}".showToast(this)
                }
                finish()
            }
        }

    }
}