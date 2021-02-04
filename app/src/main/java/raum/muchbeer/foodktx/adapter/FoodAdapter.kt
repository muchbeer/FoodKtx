package raum.muchbeer.foodktx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.food_item.view.*
import raum.muchbeer.foodktx.R
import raum.muchbeer.foodktx.model.Food

class FoodAdapter(private val foodList: ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
          val view = layoutInflater.inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int = foodList.size

    fun setFood(food: List<Food>) {
        this.foodList.apply {
            clear()
            addAll(food)
        }
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
           fun bind(food:Food) {
               itemView.apply {
                   food_name.text = food.title
                   Glide.with(image.context)
                       .load(food.image)
                       .into(image)
               }


            }
    }
}