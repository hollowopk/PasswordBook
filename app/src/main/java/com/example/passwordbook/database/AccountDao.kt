package com.example.passwordbook.database

import androidx.room.*

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(account: Account): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAccount(account: Account)

    @Query("select * from Account order by updateTime desc")
    fun loadAllAccount(): List<Account>

    @Query("delete from Account where id = :id")
    fun deleteAccountById(id: Long)

    @Query("update Account set accountName=:accountName, " +
            "username=:username, password=:password, " +
            "remark=:remark, updateTime=:updateTime where id=:id")
    fun updateAccountById(id: Long, accountName: String,
                            username: String, password: String,
                            remark: String, updateTime: Long)

}