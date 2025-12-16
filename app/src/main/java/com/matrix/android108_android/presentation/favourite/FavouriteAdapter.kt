package com.matrix.android108_android.presentation.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matrix.android108_android.R
import com.matrix.android108_android.databinding.ProductItemBinding
import com.matrix.android108_android.roomdb.ProductEntity

class FavouriteAdapter(
    private val products: MutableList<ProductEntity>, val itemClick:(ProductEntity)->Unit
) : RecyclerView.Adapter<FavouriteAdapter.ProductViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {

        val product = products[holder.adapterPosition]
        holder.bind(product)

        if(product.isLiked){
            holder.binding.likeBtn.setImageResource(R.drawable.red_heart_icon)
        } else{
            holder.binding.likeBtn.setImageResource(R.drawable.like_icon)
        }


        holder.binding.likeBtn.setOnClickListener {
            product.isLiked = !product.isLiked

            if (product.isLiked) {
                holder.binding.likeBtn.setImageResource(R.drawable.red_heart_icon)
            } else {
                holder.binding.likeBtn.setImageResource(R.drawable.like_icon)
                itemClick(product)
                products.removeAt(holder.adapterPosition)
                notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }




    inner class ProductViewHolder(val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(product: ProductEntity){

            binding.productName.text = product.name
            binding.productPrice.text = "$${product.price}"

            Glide.with(binding.productPic)
                .load(product.imageUrl)
                .into(binding.productPic)




        }

    }
}