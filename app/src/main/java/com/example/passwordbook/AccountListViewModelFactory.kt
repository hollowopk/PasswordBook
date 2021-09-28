package com.example.passwordbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordbook.database.AccountDao

class AccountListViewModelFactory(private val accountDao: AccountDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AccountListViewModel(accountDao) as T
    }

}