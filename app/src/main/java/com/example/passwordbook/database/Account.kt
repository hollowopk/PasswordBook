package com.example.passwordbook.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Account(val accountName: String, val username: String,
                   val password: String, val remark: String,
                    val updateTime: Long) : Serializable{

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}