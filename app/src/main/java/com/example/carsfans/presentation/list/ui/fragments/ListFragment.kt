package com.example.carsfans.presentation.list.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.carsfans.data.network.dto.Car
import com.example.carsfans.databinding.FragmentListBinding
import com.example.carsfans.domain.models.CarInfo
import com.example.carsfans.presentation.list.ui.adapters.ListAdapter
import com.example.carsfans.presentation.list.view_model.ListVIewModel
import com.example.carsfans.presentation.list.view_model.models.ApiStatus
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListVIewModel by viewModel()
    private lateinit var adapter: ListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var itemPosition = -1
    private val handler = Handler(Looper.getMainLooper())

    private var loading = true
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private lateinit var listOfCars: List<CarInfo>
    private lateinit var promptListOfCars: List<CarInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemPosition = it.getInt(POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createRecyclerView()

        viewModel.getCarsList()

        viewModel.listOfCars.observe(viewLifecycleOwner) { listOfCars ->
            scrollToNeededItem(listOfCars)
        }

        viewModel.apiStatus.observe(viewLifecycleOwner) { apiStatus ->
            when (apiStatus) {
                ApiStatus.Done -> showContent()
                ApiStatus.Error -> showError()
                ApiStatus.Loading -> showLoading()
            }
        }

        makeInfiniteScroll()
    }

    private fun createRecyclerView() {
        adapter = ListAdapter(
            requireContext(),
            binding.recyclerView,
            object : ListAdapter.ListActionListener {
                override fun onItemClick(carInfo: CarInfo, position: Int) {
                    val action =
                        ListFragmentDirections.actionListFragmentToInfoFragment(carInfo.id, position)
                    findNavController().navigate(action)
                }
            })
        binding.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

    }

    private fun makeInfiniteScroll() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                            loading = false
                            viewModel.showLoading()
                            increaseList()
                            loading = true

                        }
                    }
                }
            }
        })
    }

    private fun increaseList() {
        val tempList = mutableListOf<CarInfo>()
        tempList.addAll(listOfCars)
        tempList.addAll(promptListOfCars)
        listOfCars = tempList
        adapter.submitList(tempList)
    }

    private fun showContent() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.recyclerView.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
    }

    private fun scrollToNeededItem(list: List<CarInfo>){
        promptListOfCars = list
        this.listOfCars = list

        if (itemPosition > 10) {
            val tempList = mutableListOf<CarInfo>()

            repeat(itemPosition / 10 + 1) {
                tempList.addAll(promptListOfCars)
            }
            this.listOfCars = tempList.toList()
            adapter.submitList(tempList.toList())

            handler.postDelayed(
                { binding.recyclerView.smoothScrollToPosition(itemPosition)},
                SCROLL_DELAY
            )
        } else {
            adapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val POSITION = "position"
        const val SCROLL_DELAY = 300L
    }
}