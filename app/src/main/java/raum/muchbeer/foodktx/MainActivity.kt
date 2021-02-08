package raum.muchbeer.foodktx

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
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
import raum.muchbeer.foodktx.utility.NetworkStatusLiveData
import raum.muchbeer.foodktx.utility.Status
import raum.muchbeer.foodktx.viemodel.FoodViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FoodAdapter
    private lateinit var viewModel: FoodViewModel
    private lateinit var liveNetStat: NetworkStatusLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        liveNetStat = NetworkStatusLiveData(this)
        liveNetStat.observe(this, Observer {
            if (it) {
                networkStatus.visibility = View.GONE
                Log.d("MainActivity", "NetworkAvailable")
            } else {
                networkStatus.visibility = View.VISIBLE
                networkStatus.text = "No internet connection"
                Log.d("MainActivity", "No internet connnectio")
            }
        })
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

    private fun sharedPreference() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString(getString(R.string.app_name), "George").apply()
            }

      /*  val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)*/

        //Read from preference
      /*  val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getInteger(R.integer.saved_high_score_default_key)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue)*/
    }
}