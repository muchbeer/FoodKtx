package raum.muchbeer.foodktx

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_paging.*
import raum.muchbeer.foodktx.adapter.CheeseAdapter
import raum.muchbeer.foodktx.viemodel.CheeseViewModel

class CheeseActivity : AppCompatActivity() {

  //  private val viewModel by viewModels<CheeseViewModel>()
  val viewModel: CheeseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)



        // Create adapter for the RecyclerView
        val adapter = CheeseAdapter()
        viewModel.retrieveCheese.observe(this, Observer(adapter::submitList))
        cheeseList.layoutManager = LinearLayoutManager(this)
        cheeseList.adapter = adapter

      //  cheeseList.layoutManager = RecyclerView.LayoutManager

        initAddButtonListener()
        initSwipeToDelete()
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CheeseAdapter.CheeseViewHolder).cheese?.let {
                    viewModel.remove(it)
                }
            }
        }).attachToRecyclerView(cheeseList)
    }

    private fun initAddButtonListener() {
        addButton.setOnClickListener {
            addCheese()
        }

        // when the user taps the "Done" button in the on screen keyboard, save the item.
        inputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addCheese()
                return@setOnEditorActionListener true
            }
            false // action that isn't DONE occurred - ignore
        }
        // When the user clicks on the button, or presses enter, save the item.
        inputText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addCheese()
                return@setOnKeyListener true
            }
            false // event that isn't DOWN or ENTER occurred - ignore
        }
    }

    private fun addCheese() {
        val newCheese = inputText.text.trim()
        if (newCheese.isNotEmpty()) {
            viewModel.insert(newCheese)
            inputText.setText("")
          //  inputText.text = null
        }
    }


}