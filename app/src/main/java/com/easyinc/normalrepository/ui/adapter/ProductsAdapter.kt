package com.easyinc.normalrepository.ui.adapter

import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.easyinc.normalrepository.data.model.ProductEntity
import javax.inject.Inject

class ProductsAdapter @Inject constructor(@Nullable val requestManager: RequestManager): PagedListAdapter<ProductEntity, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    internal var clickListener: (String) -> Unit = {_ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder.create(parent,requestManager)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as ProductViewHolder).bind(repoItem,clickListener)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ProductEntity>() {
            override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean =
                oldItem.warehouseId == newItem.warehouseId

            override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean =
                oldItem == newItem
        }
    }
}