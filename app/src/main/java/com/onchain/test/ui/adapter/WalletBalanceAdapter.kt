package com.onchain.test.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.onchain.test.data.model.AssetBalanceItem
import com.onchain.test.databinding.ListItemWalletAssetBinding

class WalletBalanceAdapter : RecyclerView.Adapter<WalletBalanceAdapter.BalanceViewHolder>() {
    private val tokenList = ArrayList<AssetBalanceItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        return BalanceViewHolder(ListItemWalletAssetBinding.inflate(LayoutInflater.from(parent.context),parent,false).root)
    }

    override fun getItemCount() = tokenList.size

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.bind()
    }

    fun update(balanceList: List<AssetBalanceItem>) {
        this.tokenList.clear()
        this.tokenList.addAll(balanceList)
        notifyDataSetChanged()
    }

    inner class BalanceViewHolder(itemView: View) : ViewHolder(itemView) {
        val viewBinding = ListItemWalletAssetBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind() {

            val itemData = tokenList.get(adapterPosition)

            Log.i("FELo", "bind: ${itemData.currencyInfo?.imageUrl}")
            Glide.with(itemView.context)
                .load(itemData.currencyInfo?.imageUrl ?: "")
                .transform(RoundedCorners(24))
                .into(viewBinding.tokenLogo)

            viewBinding.tokenFullName.text =
                itemData.currencyInfo?.tokenFullName ?: itemData.currencyName

            viewBinding.tokenValue.text = itemData.tokenValue.toPlainString() + ""

            viewBinding.tokenName.text = itemData.currencyName

        }

    }
}