package raum.muchbeer.foodktx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cheese_item.view.*
import raum.muchbeer.foodktx.R
import raum.muchbeer.foodktx.model.Cheese

class CheeseAdapter  : PagedListAdapter<Cheese, CheeseAdapter.CheeseViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.cheese_item, parent,false)

        return CheeseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CheeseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val name = itemView.name
        var cheese : Cheese? = null
        fun bind(cheese: Cheese?) {
            this.cheese = cheese
            name.text = cheese?.name
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Cheese>() {
            override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                oldItem == newItem
        }
    }
    }
