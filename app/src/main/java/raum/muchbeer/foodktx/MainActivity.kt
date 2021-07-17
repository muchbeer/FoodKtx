package raum.muchbeer.foodktx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import raum.muchbeer.foodktx.adapter.FoodAdapter
import raum.muchbeer.foodktx.databinding.ActivityMainBinding
import raum.muchbeer.foodktx.model.FoodState
import raum.muchbeer.foodktx.viemodel.FoodViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FoodAdapter
    private val viewModel: FoodViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding
    private lateinit var searchView: SearchView

    // private lateinit var liveNetStat: NetworkStatusLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
  /*      liveNetStat = NetworkStatusLiveData(this)
        liveNetStat.observe(this, Observer {
            if (it) {
                networkStatus.visibility = View.GONE
                Log.d("MainActivity", "NetworkAvailable")
            } else {
                networkStatus.visibility = View.VISIBLE
                networkStatus.text = "No internet connection"
                Log.d("MainActivity", "No internet connnectio")
            }
        })*/

        setupUI()
        setupObservers()
    }


    private fun setupUI() {
        //recyleView.addItemDecoration(DefaultItemDecorator(4, 6))
        binding.recyleView.layoutManager = GridLayoutManager(this@MainActivity, 2)
        binding.recyleView.setHasFixedSize(true)
        adapter = FoodAdapter()
        binding.recyleView.adapter = adapter
    }


    private fun setupObservers() {

        viewModel.retrieveFoods.observe(this, { result ->
            adapter.submitList(result.data)
            binding.progressBar.isVisible = result is FoodState.Loading && result.data.isNullOrEmpty()
            binding.networkStatus.isVisible = result is FoodState.Error
            binding.networkStatus.text = result.message?.localizedMessage
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setSubmitButtonEnabled(true)
        searchView.setQueryHint("Search Foods")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
               /* viewModel.query.observe(this@MainActivity, {searchItem ->
                    searchItem == newText
                })*/
                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)

        return true
    }
}