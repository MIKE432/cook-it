package com.apusart.cookit.ui.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.cookit.RecipeListQuery
import com.apusart.cookit.RecipeScopedListQuery
import com.apusart.cookit.databinding.RecipeListItemBinding
import com.bumptech.glide.Glide

class RecipeListAdapter : ListAdapter<RecipeScopedListQuery.ScopedRecipe, RecipeViewHolder>(diffCallback) {

    object diffCallback : DiffUtil.ItemCallback<RecipeScopedListQuery.ScopedRecipe>() {
        override fun areItemsTheSame(
            oldItem: RecipeScopedListQuery.ScopedRecipe,
            newItem: RecipeScopedListQuery.ScopedRecipe
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecipeScopedListQuery.ScopedRecipe,
            newItem: RecipeScopedListQuery.ScopedRecipe
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val container = RecipeListItemBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(container)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RecipeViewHolder(private val binding: RecipeListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(recipe: RecipeScopedListQuery.ScopedRecipe) {
        binding.name = recipe.name
        Glide
            .with(binding.root)
            .load(recipe.thumbnailUrl)
            .into(binding.image)

        binding.description = recipe.description
        binding.estTime = if (recipe.cookTimeInMinutes != null)
            "Est:" + recipe.cookTimeInMinutes
        else
            ""
    }

}