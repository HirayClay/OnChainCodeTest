package com.onchain.test.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.onchain.test.R
import com.onchain.test.data.model.AssetBalanceItem
import com.onchain.test.databinding.ListItemWalletAssetBinding

class WalletBalanceAdapter : RecyclerView.Adapter<WalletBalanceAdapter.BalanceViewHolder>() {
    private val assetList = ArrayList<AssetBalanceItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        return BalanceViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_wallet_asset, parent, false
            )
        )
    }

    override fun getItemCount() = assetList.size

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.bind()
    }

    fun update(balanceList: List<AssetBalanceItem>) {
        this.assetList.clear()
        this.assetList.addAll(balanceList)
        notifyDataSetChanged()
    }

    inner class BalanceViewHolder(val viewBinding: ListItemWalletAssetBinding) :
        ViewHolder(viewBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val itemData = assetList[adapterPosition]
            Glide.with(itemView.context)
                .load(itemData.currencyInfo?.imageUrl ?: "")
                .transform(RoundedCorners(24))
                .into(viewBinding.tokenLogo)

            val charSequence = itemData.currencyInfo?.tokenFullName ?: itemData.currencyName
            viewBinding.tokenFullName.text = charSequence
            viewBinding.tokenAmount.text = itemData.amount.toPlainString()
            viewBinding.tokenValue.text =
                itemData.tokenValue.toPlainString() + " " + itemData.valueSymbol
            viewBinding.tokenName.text = itemData.currencyName

        }

    }
}