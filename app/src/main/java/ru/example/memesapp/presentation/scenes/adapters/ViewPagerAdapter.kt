package ru.example.memesapp.presentation.scenes.adapters

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dagger.hilt.android.qualifiers.ActivityContext
import ru.example.memesapp.di.TabCategories
import ru.example.memesapp.di.TabTitles
import ru.example.memesapp.presentation.scenes.ImageFragment
import javax.inject.Inject

class ViewPagerAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    @TabTitles private val tabTitles: List<String>,
    @TabCategories val tabCategory: List<String>
) : FragmentStateAdapter(context as FragmentActivity) {
    companion object {
        const val PAGE_COUNT = 3
    }

    override fun getItemCount(): Int = PAGE_COUNT

    override fun createFragment(position: Int) = ImageFragment.newInstance(tabCategory[position])

    fun getPageTitle(position: Int) = tabTitles[position]
}