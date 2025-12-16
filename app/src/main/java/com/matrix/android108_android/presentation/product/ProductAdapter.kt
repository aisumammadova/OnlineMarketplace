package com.matrix.android108_android.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matrix.android108_android.Dto.ProductDto
import com.matrix.android108_android.R
import com.matrix.android108_android.databinding.ProductItemBinding

class ProductAdapter(
    private var products: List<ProductDto>?, val itemClick:(ProductDto)->Unit): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = products?.get(position)
        holder.bind(product)


        if (product?.isLiked ==true) {
            holder.binding.likeBtn.setImageResource(R.drawable.red_heart_icon)
        } else {
            holder.binding.likeBtn.setImageResource(R.drawable.like_icon)
        }


        holder.binding.likeBtn.setOnClickListener {
            product?.isLiked = !product.isLiked
            product?.let {
                itemClick(it)
            }
            if (product?.isLiked ==true) {
                holder.binding.likeBtn.setImageResource(R.drawable.red_heart_icon)
            } else {
                holder.binding.likeBtn.setImageResource(R.drawable.like_icon)
            }
        }
    }

    override fun getItemCount(): Int {
        return products?.size ?:0
    }

    fun updateData(newList: List<ProductDto>) {
        products = newList
        notifyDataSetChanged()
    }


    inner class ProductViewHolder(val binding : ProductItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: ProductDto?){


            binding.productName.text = product?.title
            binding.productPrice.text = "$${product?.price}"

            Glide.with(binding.productPic)
                .load(product?.thumbnail)
                .into(binding.productPic)
        }

    }
}