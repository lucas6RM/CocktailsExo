package com.mercierlucas.cocktailsexo.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mercierlucas.cocktailsexo.R
import com.mercierlucas.cocktailsexo.data.local.model.DrinkLiteModel
import com.mercierlucas.cocktailsexo.databinding.ItemViewCocktailBinding



class DrinkAdapter(val onDrinkClicked : (Long, Boolean) -> Unit) : ListAdapter<DrinkLiteModel, DrinkViewHolder>(DrinkDiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_cocktail,parent,false)

        return DrinkViewHolder(itemView)
    }


    @SuppressLint("UseCompatLoadingForDrawables", "PrivateResource")
    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val article = getItem(position)



        holder.binding.apply {


        }
    }
}

object DrinkDiffCallback : DiffUtil.ItemCallback<DrinkLiteModel>() {
    override fun areItemsTheSame(oldItem: DrinkLiteModel, newItem: DrinkLiteModel): Boolean {
        return oldItem.idDrink == newItem.idDrink && oldItem.isMine == newItem.isMine
    }

    override fun areContentsTheSame(oldItem: DrinkLiteModel, newItem: DrinkLiteModel): Boolean {
        return oldItem == newItem
    }
}

class DrinkViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val binding = ItemViewCocktailBinding.bind(itemView)
}
