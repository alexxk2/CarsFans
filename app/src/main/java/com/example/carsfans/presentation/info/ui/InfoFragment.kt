package com.example.carsfans.presentation.info.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carsfans.R
import com.example.carsfans.databinding.FragmentInfoBinding
import com.example.carsfans.domain.models.PostInfo
import com.example.carsfans.domain.models.SingleCar
import com.example.carsfans.presentation.info.adapters.PostsAdapter
import com.example.carsfans.presentation.info.view_model.InfoViewModel
import com.example.carsfans.presentation.list.view_model.models.ApiStatus
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoViewModel by viewModel()
    private lateinit var adapter: PostsAdapter
    private var carId = 0
    private var position = -1
    private lateinit var layoutManager: GridLayoutManager

    private var loading = true
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private lateinit var postsList: List<PostInfo>
    private lateinit var promptPostsList: List<PostInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            carId = it.getInt(ID)
            position = it.getInt(POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getSingleCarInfo(carId)
        viewModel.getPosts(carId)

        viewModel.singleCarInfo.observe(viewLifecycleOwner) { singleCar ->
            bindViews(singleCar)
        }

        viewModel.postsList.observe(viewLifecycleOwner) {
            loadRecyclerView(it)
        }

        binding.backButton.setOnClickListener {
            val action = InfoFragmentDirections.actionInfoFragmentToListFragment(position)
            findNavController().navigate(action)
        }

        viewModel.singleCarApiStatus.observe(viewLifecycleOwner) { apiStatus ->

            when (apiStatus) {
                ApiStatus.Done -> showInfoContent()
                ApiStatus.Error -> showError()
                ApiStatus.Loading -> showInfoLoading()
            }
        }

        viewModel.postApiStatus.observe(viewLifecycleOwner) { apiStatus ->

            when (apiStatus) {
                ApiStatus.Done -> showPostsContent()
                ApiStatus.Error -> showError()
                ApiStatus.Loading -> showPostsLoading()
            }
        }

        makeInfiniteScroll()

        binding.refreshButton.setOnClickListener {
            viewModel.getSingleCarInfo(carId)
            viewModel.getPosts(carId)
        }

    }

    private fun bindViews(singleCar: SingleCar) {

        with(binding) {
            Glide.with(requireContext())
                .load(singleCar.imageSrc)
                .placeholder(R.drawable.ic_car)
                .circleCrop()
                .into(carImage)

            Glide.with(requireContext())
                .load(singleCar.avatarImageSrc)
                .placeholder(R.drawable.ic_acc_image)
                .centerCrop()
                .into(avatarImage)

            brandName.text = singleCar.brandName
            modelName.text = singleCar.modelName
            yearText.text = getString(R.string.year, singleCar.year)
            engineText.text = getString(R.string.engine, singleCar.engineName)
            transmissionText.text = getString(R.string.transmission, singleCar.transmissionName)
            accountName.text = singleCar.accountName
        }
    }

    private fun showInfoContent() {
        with(binding) {
            carImage.visibility = View.VISIBLE
            brandName.visibility = View.VISIBLE
            modelName.visibility = View.VISIBLE
            yearText.visibility = View.VISIBLE
            engineText.visibility = View.VISIBLE
            transmissionText.visibility = View.VISIBLE
            avatarImage.visibility = View.VISIBLE
            accountName.visibility = View.VISIBLE
            infoProgressBar.visibility = View.INVISIBLE
            refreshButton.visibility = View.INVISIBLE
            errorMessage.visibility = View.INVISIBLE
        }
    }

    private fun showPostsContent() {
        with(binding) {
            postsRecyclerView.visibility = View.VISIBLE
            postsProgressBar.visibility = View.INVISIBLE
            refreshButton.visibility = View.INVISIBLE
            errorMessage.visibility = View.INVISIBLE
        }
    }

    private fun showInfoLoading() {
        with(binding) {
            carImage.visibility = View.INVISIBLE
            brandName.visibility = View.INVISIBLE
            modelName.visibility = View.INVISIBLE
            yearText.visibility = View.INVISIBLE
            engineText.visibility = View.INVISIBLE
            transmissionText.visibility = View.INVISIBLE
            avatarImage.visibility = View.INVISIBLE
            accountName.visibility = View.INVISIBLE
            infoProgressBar.visibility = View.VISIBLE
            refreshButton.visibility = View.INVISIBLE
            errorMessage.visibility = View.INVISIBLE
        }
    }

    private fun showPostsLoading() {
        with(binding) {
            postsRecyclerView.visibility = View.INVISIBLE
            postsProgressBar.visibility = View.VISIBLE
            refreshButton.visibility = View.INVISIBLE
            errorMessage.visibility = View.INVISIBLE
        }
    }

    private fun showError() {

        with(binding) {
            carImage.visibility = View.INVISIBLE
            brandName.visibility = View.INVISIBLE
            modelName.visibility = View.INVISIBLE
            yearText.visibility = View.INVISIBLE
            engineText.visibility = View.INVISIBLE
            transmissionText.visibility = View.INVISIBLE
            avatarImage.visibility = View.INVISIBLE
            accountName.visibility = View.INVISIBLE
            infoProgressBar.visibility = View.INVISIBLE
            postsRecyclerView.visibility = View.INVISIBLE
            postsProgressBar.visibility = View.INVISIBLE
            errorMessage.visibility = View.VISIBLE
            refreshButton.visibility = View.VISIBLE
        }

    }

    private fun loadRecyclerView(list: List<PostInfo>) {
        adapter = PostsAdapter(requireContext())
        binding.postsRecyclerView.adapter = adapter
        layoutManager = GridLayoutManager(requireContext(),2)
        binding.postsRecyclerView.layoutManager = layoutManager
        binding.postsRecyclerView.setHasFixedSize(true)
        adapter.submitList(list)

        postsList = list
        promptPostsList = list
    }

    private fun makeInfiniteScroll() {
        binding.postsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                            loading = false
                            viewModel.showLoading(carId)

                            increaseList()
                            loading = true

                        }
                    }
                }
            }
        })
    }

    private fun increaseList() {
        val tempList = mutableListOf<PostInfo>()
        tempList.addAll(postsList)
        tempList.addAll(promptPostsList)
        postsList = tempList
        adapter.submitList(tempList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ID = "carId"
        private const val POSITION = "position"
    }
}