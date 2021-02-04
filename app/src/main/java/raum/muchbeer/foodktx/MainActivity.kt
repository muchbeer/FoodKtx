package raum.muchbeer.foodktx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import raum.muchbeer.foodktx.adapter.FoodAdapter
import raum.muchbeer.foodktx.api.ApiHelper
import raum.muchbeer.foodktx.api.FoodInstance
import raum.muchbeer.foodktx.api.FoodService
import raum.muchbeer.foodktx.model.Food
import raum.muchbeer.foodktx.repository.FoodViewModelFactory
import raum.muchbeer.foodktx.utility.Status
import raum.muchbeer.foodktx.viemodel.FoodViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FoodAdapter
    private lateinit var viewModel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, FoodViewModelFactory(ApiHelper(FoodInstance.foodInstance()))).get(FoodViewModel::class.java)

        setupUI()
        setupObservers()
    }



    private fun setupUI() {
        //recyleView.addItemDecoration(DefaultItemDecorator(4, 6))
        recyleView.layoutManager = GridLayoutManager(this@MainActivity, 2)
        recyleView.setHasFixedSize(true)
        adapter = FoodAdapter(arrayListOf())
        recyleView.adapter = adapter
    }


    private fun setupObservers() {
       viewModel.getAllFoods().observe(this, {
           it?.let {
               when(it.status) {
                   Status.SUCCESS -> {
                       recyleView.visibility = View.VISIBLE
                       progressBar.visibility = View.GONE
                       it.data?.let { foodList
                           -> retrieveList(foodList) }
                   }

                   Status.LOADING -> {
                       recyleView.visibility = View.GONE
                       progressBar.visibility = View.VISIBLE

                   }

                   Status.ERROR -> {
                       recyleView.visibility=View.GONE
                       progressBar.visibility=View.GONE
                       Log.i("MainActivity", "The error is : ${it.message}")
                       Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                   }
               }
           }
       })
    }

    private fun retrieveList(foodList: List<Food>) {
        adapter.apply {
            setFood(foodList)
            notifyDataSetChanged()
        }
    }

}