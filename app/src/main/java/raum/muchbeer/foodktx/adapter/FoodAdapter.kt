package raum.muchbeer.foodktx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.food_item.view.*
import raum.muchbeer.foodktx.R
import raum.muchbeer.foodktx.databinding.FoodItemBinding
import raum.muchbeer.foodktx.model.Food

class FoodAdapter() : ListAdapter<Food, FoodAdapter.FoodViewHolder>(foodDiffUtil) {

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
          val view = FoodItemBinding.inflate(layoutInflater)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }

    class FoodViewHolder(val binding: FoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
           fun bind(food:Food) {
               binding.food = food
               binding.executePendingBindings()
               Glide.with(binding.image.context)
                   .load(food.image)
                   .into(binding.image)
            }
    }

    companion object foodDiffUtil : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
          return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id
        }

    }
}