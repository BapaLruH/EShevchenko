package ru.example.memesapp.presentation.scenes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.example.memesapp.R
import ru.example.memesapp.databinding.FragmentImageBinding
import ru.example.memesapp.databinding.ItemCellBinding
import ru.example.memesapp.presentation.models.ImageItem
import ru.example.memesapp.presentation.models.PageState
import ru.example.memesapp.presentation.scenes.adapters.BaseAdapter
import ru.example.memesapp.presentation.utils.dpToPxInt
import ru.example.memesapp.presentation.utils.viewbindingdelegate.viewBinding
import ru.example.memesapp.presentation.view_models.ImageViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ImageFragment @Inject constructor() : Fragment(R.layout.fragment_image) {
    companion object {
        const val ARG_PAGE = "ARG_PAGE"
        fun newInstance(page: String): ImageFragment {
            return ImageFragment().apply {
                arguments =
                    Bundle().apply {
                        putString(ARG_PAGE, page)
                    }
            }
        }
    }

    private val viewBinding by viewBinding(FragmentImageBinding::bind)
    private val viewModel by viewModels<ImageViewModel>()

    private lateinit var adapter: BaseAdapter<ItemCellBinding, ImageItem>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setupViews()
        initObservers()
    }

    private fun initAdapter() {
        adapter = BaseAdapter(
            viewInflater = { layoutInflater, parent, attachToParent ->
                ItemCellBinding.inflate(layoutInflater, parent, attachToParent)
            },
            bindFunction = { holder, item ->
                with(holder) {
                    Glide.with(root).asGif().load(item.image).placeholder(R.drawable.ic_loading)
                        .apply(
                            RequestOptions().transform(
                                CenterCrop(),
                                RoundedCorners(requireContext().dpToPxInt(6))
                            )
                        )
                        .into(ivImage)
                    tvDescription.text = item.description
                }
            }
        )
    }

    private fun setupViews() {
        with(viewBinding) {
            groupLoadingError.isVisible = false
            rvMemes.adapter = adapter
            rvMemes.setHasFixedSize(true)
            rvMemes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val manager = recyclerView.layoutManager ?: return
                    val adapter = recyclerView.adapter ?: return

                    if (manager is LinearLayoutManager) {
                        val currentPosition = manager.findLastCompletelyVisibleItemPosition()
                        val itemCount = adapter.itemCount - 1
                        lifecycleScope.launch {
                            delay(50)
                            btnPrevious.isVisible = buttonVisibility(currentPosition, itemCount)
                            btnNext.isVisible = buttonVisibility(currentPosition, itemCount, true)
                        }
                        if (currentPosition >= itemCount) {
                            viewModel.loadData()
                        }
                    }
                }
            })
            tvRetry.setOnClickListener { viewModel.loadData() }
            btnPrevious.setOnClickListener { onNextOrPreviousButtonClick() }
            btnNext.setOnClickListener { onNextOrPreviousButtonClick(true) }
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.pageState.collect { state ->
                with(viewBinding) {
                    progressBar.isVisible = state is PageState.Loading
                    groupLoadingError.isVisible = state is PageState.Error
                    rvMemes.isVisible = state is PageState.Success
                    btnPrevious.isVisible = state is PageState.Success
                    btnNext.isVisible = state is PageState.Success
                }
                if (state is PageState.Success) {
                    adapter.submitList(state.data)
                }
            }
        }
    }

    private fun onNextOrPreviousButtonClick(isNext: Boolean = false) {
        with(viewBinding.rvMemes) {
            layoutManager?.let { manager ->
                if (manager is LinearLayoutManager) {
                    val currentItem = manager.findLastVisibleItemPosition()
                    val position = if (isNext) currentItem.inc() else currentItem.dec()
                    this.post { scrollToPosition(position) }
                }
            }
        }
    }

    private fun buttonVisibility(
        currentPosition: Int,
        itemCount: Int,
        isNext: Boolean = false
    ): Boolean {
        return if (isNext) currentPosition < itemCount else currentPosition > 0
    }
}