package com.example.passwordbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mychat.showLog
import com.example.passwordbook.database.Account
import com.example.passwordbook.database.AccountDao
import com.example.passwordbook.database.AppDatabase
import kotlin.concurrent.thread

class AccountListViewModel(private val accountDao: AccountDao) : ViewModel() {

    private val accountListLiveData = MutableLiveData<ArrayList<Account>>()
    val tag = "AccountListViewModel"

    init {
        accountListLiveData.value = ArrayList()
        loadAllAccount()
    }

    fun getLiveData() = accountListLiveData

    private fun loadAllAccount() {
        val accountList = accountDao.loadAllAccount()
        for (account in accountList) {
            accountListLiveData.value?.add(account)
        }
    }

    fun addAccount(account: Account) {
        val list = accountListLiveData.value
        list?.add(0,account)
        accountListLiveData.value = list
    }

    fun updateListById(id: Long, account: Account) {
        val list = accountListLiveData.value
        val newList = ArrayList<Account>()
        if (list != null) {
            for (accountItem in list) {
                if (accountItem.id == id) {
                    newList.add(0,account)
                }
                else {
                    newList.add(accountItem)
                }
            }
            accountListLiveData.value = newList
        }
    }

    fun deleteById(id: Long) {
        accountDao.deleteAccountById(id)
        val list = accountListLiveData.value
        val newList = ArrayList<Account>()
        if (list != null) {
            for (accountItem in list) {
                if (accountItem.id != id) {
                    newList.add(accountItem)
                }
            }
            accountListLiveData.value = newList
        }
    }

}