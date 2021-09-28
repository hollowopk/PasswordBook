package com.example.passwordbook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mychat.showLog
import com.example.passwordbook.database.Account

class AccountAdapter(private val accountList: List<Account>,
                     private val itemListener: ItemListener) :
    RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    private lateinit var context: Context
    private val tag = "AccountAdapter"

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val accountName: TextView = view.findViewById(R.id.accountName)
        val username: TextView = view.findViewById(R.id.username)
        val password: TextView = view.findViewById(R.id.password)

    }

    interface ItemListener {
        fun onItemClick(position: Int, account: Account)
        fun onItemLongClick(position: Int, account: Account)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accountList[position]
        "get".showLog(tag)
        holder.accountName.text = account.accountName
        holder.username.text = account.username
        holder.password.text = "******"
        holder.itemView.setOnClickListener {
            itemListener.onItemClick(holder.adapterPosition, account)
        }
        holder.itemView.setOnLongClickListener {
            itemListener.onItemLongClick(holder.adapterPosition, account)
            true
        }
    }

    override fun getItemCount() = accountList.size

}