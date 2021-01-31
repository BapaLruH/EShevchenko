package ru.example.memesapp.presentation.scenes.adapters

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.example.memesapp.R
import ru.example.memesapp.presentation.scenes.ImageFragment

class ViewPagerAdapter(private val context: Context) :
    FragmentStateAdapter(context as FragmentActivity) {
    companion object {
        const val PAGE_COUNT = 3
    }

    private val tabTitles: Array<String> by lazy {
        arrayOf(
            context.getString(R.string.latest_page_title),
            context.getString(R.string.best_page_title),
            context.getString(R.string.hot_page_title)
        )
    }

    private val tabCategory: Array<String> by lazy {
        arrayOf(
            context.getString(R.string.latest_page_category),
            context.getString(R.string.best_page_category),
            context.getString(R.string.hot_page_category)
        )
    }

    override fun getItemCount(): Int = PAGE_COUNT

    override fun createFragment(position: Int) = ImageFragment.newInstance(tabCategory[position])

    fun getPageTitle(position: Int) = tabTitles[position]
}