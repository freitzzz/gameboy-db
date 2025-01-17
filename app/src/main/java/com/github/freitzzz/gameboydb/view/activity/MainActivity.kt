package com.github.freitzzz.gameboydb.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.fragment.SettingsFragment
import com.github.freitzzz.gameboydb.view.fragment.FeedFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val virtualPages = mutableMapOf(
        R.id.menu_item_feed_page to FeedFragment(),
        R.id.menu_item_settings_page to SettingsFragment()
    )

    private var selectedItemId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_bar)
        this.selectedItemId = bottomBar.selectedItemId

        virtualPages[this.selectedItemId] = supportFragmentManager.findFragmentById(
            R.id.fragment_virtual_page
        )!!

        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_item_feed_page -> swapVirtualPage(it.itemId)
                R.id.menu_item_favorites_page -> swapVirtualPage(it.itemId)
                R.id.menu_item_settings_page -> swapVirtualPage(it.itemId)
            }

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
            } else {
                virtualPages.onEach {
                    when (itemId) {
                        it.key -> show(it.value)
                        else -> hide(it.value)
                    }
                }
            }

            commit()
        }
    }
}