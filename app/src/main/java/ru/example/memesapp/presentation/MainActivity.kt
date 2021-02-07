package ru.example.memesapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.example.memesapp.R
import ru.example.memesapp.databinding.ActivityMainBinding
import ru.example.memesapp.presentation.scenes.adapters.ViewPagerAdapter
import ru.example.memesapp.presentation.utils.addSystemTopPadding
import ru.example.memesapp.presentation.utils.viewbindingdelegate.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val vb by viewBinding(ActivityMainBinding::bind, R.id.root_container)

    @Inject
    lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb.rootContainer.addSystemTopPadding()
        with(vb) {
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = adapter.getPageTitle(position)
            }.attach()
        }
    }
}