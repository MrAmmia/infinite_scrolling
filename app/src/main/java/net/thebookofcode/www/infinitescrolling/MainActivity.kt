package net.thebookofcode.www.infinitescrolling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.thebookofcode.www.infinitescrolling.adapter.PixaBayViewAdapter
import net.thebookofcode.www.infinitescrolling.databinding.ActivityMainBinding
import net.thebookofcode.www.infinitescrolling.util.DataState

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Set up your RecyclerView
        val adapter = PixaBayViewAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        //val modelState by model.uiState.flowWithLifecycle()

        lifecycleScope.launchWhenCreated {
            model.uiState.collect() { uiState ->
                when (uiState) {
                    is HomeUiState.HasPhotos -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.loadingLayout.visibility = View.GONE
                        binding.errorLayout.visibility = View.GONE
                        adapter.submitData(uiState.photos)
                    /*if (uiState.isLoading){
                            binding.recyclerView.visibility = View.GONE
                            binding.loadingLayout.visibility = View.VISIBLE
                            binding.errorLayout.visibility = View.GONE
                        }else{
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.loadingLayout.visibility = View.GONE
                            binding.errorLayout.visibility = View.GONE
                            adapter.submitData(uiState.photos)
                        }*/

                    }
                    is HomeUiState.NoPhotos -> {
                        if (uiState.isLoading) {
                            binding.recyclerView.visibility = View.GONE
                            binding.loadingLayout.visibility = View.VISIBLE
                            binding.errorLayout.visibility = View.GONE
                        } else {
                            binding.recyclerView.visibility = View.GONE
                            binding.loadingLayout.visibility = View.GONE
                            binding.errorLayout.visibility = View.VISIBLE
                            binding.errorText.text = uiState.errorMessage
                        }
                    }
                }
            }
        }
       /* model.dataState.observe(this@MainActivity, Observer { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.loadingLayout.visibility = View.GONE
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.errorText.text = dataState.throwable.toString()
                }
                is DataState.Success -> {
                    val scope = CoroutineScope(Dispatchers.Default)
                    scope.launch {
                        adapter.submitData(dataState.data)
                        binding.recyclerView.visibility = View.VISIBLE
                    }
                    binding.loadingLayout.visibility = View.GONE
                    binding.errorLayout.visibility = View.GONE
                }
                is DataState.Loading -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.loadingLayout.visibility = View.VISIBLE
                    binding.errorLayout.visibility = View.GONE
                }
            }
        })*/


        // Populate your RecyclerView with data, if needed
        //
    }
}