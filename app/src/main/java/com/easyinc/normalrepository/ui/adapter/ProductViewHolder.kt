package com.easyinc.normalrepository.ui.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.product_layout.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.easyinc.normalrepository.R
import com.easyinc.normalrepository.data.model.ProductEntity
import kotlinx.android.synthetic.main.product_layout.view.*
import javax.inject.Inject

class ProductViewHolder(private val view: View,val requestManager: RequestManager) : RecyclerView.ViewHolder(view) {

    private var product: ProductEntity? = null


    fun bind(product: ProductEntity?,clickListener: (String) -> Unit) {
        if (product == null) {
            view.name.text = "Empty"
            view.price.text = "Empty"

        } else {
            showRepoData(product)

            view.setOnClickListener { clickListener(product.id) }

        }
    }

    private fun showRepoData(product: ProductEntity) {
        this.product = product
        view.name.text = product.name
        view.price.text = "${product.price}$"

        requestManager
            .load(product.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view.imageView)


    }

    companion object {
        fun create(parent: ViewGroup, requestManager: RequestManager): ProductViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_layout, parent, false)

            return ProductViewHolder(view,requestManager)
        }
    }

}