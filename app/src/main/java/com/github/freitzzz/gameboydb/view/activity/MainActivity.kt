package com.github.freitzzz.gameboydb.view.activity

import android.os.Bundle
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.fragment.FavoritesFragment
import com.github.freitzzz.gameboydb.view.fragment.FeedFragment
import com.github.freitzzz.gameboydb.view.fragment.SettingsFragment
import com.github.freitzzz.gameboydb.view.navigateTo
import com.github.freitzzz.gameboydb.view.view
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppActivity(R.layout.activity_main) {
    private val virtualPages = mutableMapOf(
        R.id.menu_item_feed_page to FeedFragment(),
        R.id.menu_item_favorites_page to FavoritesFragment(),
        R.id.menu_item_settings_page to SettingsFragment()
    )

    private var selectedItemId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view(R.id.top_bar_search_icon).setOnClickListener {
            navigateTo<SearchGamesActivity>()
        }

        virtualPages.keys.onEach {
            val fragment = supportFragmentManager.findFragmentByTag(it.toString())
            if (fragment != null) {
                virtualPages[it] = fragment
            }
        }

        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_bar)
        this.selectedItemId = bottomBar.selectedItemId

        val currentFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_virtual_page
        )!!

        when (currentFragment) {
            is FeedFragment -> this.selectedItemId = R.id.menu_item_feed_page
            is FavoritesFragment -> this.selectedItemId = R.id.menu_item_favorites_page
            is SettingsFragment -> this.selectedItemId = R.id.menu_item_settings_page
        }

        virtualPages[this.selectedItemId] = currentFragment
        bottomBar.setOnItemSelectedListener {
            swapVirtualPage(it.itemId)
            selectedItemId = it.itemId
            true
        }
    }

    private fun swapVirtualPage(itemId: Int) {
        if (itemId == selectedItemId) {
            return
        }

        val fragment = virtualPages[itemId] ?: return
        supportFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)

            if (fragment.id == 0) {
                add(R.id.fragment_virtual_page, fragment, itemId.toString())
            }

            virtualPages.onEach {
                when (itemId) {
                    it.key -> show(it.value)
                    else -> hide(it.value)
                }
            }

            commit()
        }
    }
}