package com.example.passwordbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mychat.showLog
import com.example.passwordbook.database.Account
import com.example.passwordbook.database.AppDatabase
import com.example.passwordbook.databinding.ActivityAccountListBinding

class AccountList : AppCompatActivity() {

    private lateinit var binding: ActivityAccountListBinding
    private lateinit var viewModel: AccountListViewModel
    private val requestCode = 1
    private val tag = "AccountList"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val accountDao = AppDatabase.getDatabase(this).accountDao()
        viewModel = ViewModelProvider(this,AccountListViewModelFactory(accountDao))
            .get(AccountListViewModel::class.java)

        val recyclerView = binding.recyclerView
        val addAccountButton = binding.addAccountButton
        val settingBtn = binding.settingBtn
        val accountLiveData = viewModel.getLiveData()

        accountLiveData.observe(this, Observer { res ->
            if (res != null) {
                val layoutManager = LinearLayoutManager(this)
                recyclerView.layoutManager = layoutManager
                val activity = this
                val adapter = AccountAdapter(res, object : AccountAdapter.ItemListener {
                    override fun onItemClick(position: Int, account: Account) {
                        val intent = Intent(activity, AccountInfo::class.java)
                        intent.putExtra("account",account)
                        startActivityForResult(intent, requestCode)
                    }

                    override fun onItemLongClick(position: Int, account: Account) {
                        confirmDelete(account.id)
                    }

                })
                recyclerView.adapter = adapter
            }
        })

        addAccountButton.setOnClickListener {

            val intent = Intent(this, AccountInfo::class.java)
            intent.putExtra("account",
                Account("","","",
                    "", System.currentTimeMillis()))
            startActivityForResult(intent,requestCode)

        }

        settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (reqCode) {
            requestCode -> {
                if (data != null) {
                    val isUpdate = data.getBooleanExtra("isUpdate", false)
                    val accountId = data.getLongExtra("accountId", -1)
                    val account = data.getSerializableExtra("newAccount") as Account
                    if (isUpdate && accountId >= 0) {
                        viewModel.updateListById(accountId,account)
                    }
                    else {
                        viewModel.addAccount(account)
                    }
                }
            }
        }
    }

    private fun confirmDelete(accountId: Long) {
        AlertDialog.Builder(this)
            .setTitle("??????????????????")
            .setMessage("???????????????")
            .setPositiveButton("??????") { _,_ ->
                viewModel.deleteById(accountId)
            }
            .setNegativeButton("??????") { _,_ ->

            }
            .show()
    }

}