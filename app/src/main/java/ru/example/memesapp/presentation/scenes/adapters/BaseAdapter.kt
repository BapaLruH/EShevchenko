package ru.example.memesapp.presentation.scenes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.example.memesapp.presentation.models.ItemModel

class BaseAdapter<VB : ViewBinding, T : ItemModel>(
    private val viewInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val bindFunction: (VB, T) -> Unit
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB, T>>() {

    private val items: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB, T> {
        val binding = viewInflater(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding, bindFunction)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB, T>, position: Int) =
        holder.run { bind(items[position]) }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<T>) {
        items += list
        notifyDataSetChanged()
    }

    class BaseViewHolder<VB : ViewBinding, T : ItemModel>(
        private val binding: VB,
        private val bindFunction: (VB, T) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemModel: T) {
            bindFunction(binding, itemModel)
        }
    }
}

