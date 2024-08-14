package ru.itis.kpfu.cellularworld.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.kpfu.cellularworld.R
import ru.itis.kpfu.cellularworld.databinding.ActivityMainBinding
import ru.itis.kpfu.cellularworld.presentation.fragments.cells.CellsFragment

/**
 * Активити приложения
 * */
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding?.run {
            val fragmentTag = CellsFragment.FRAGMENT_CELLS_TAG
            if (supportFragmentManager.findFragmentByTag(fragmentTag) == null) {
                supportFragmentManager.beginTransaction().run {
                    val fragment = CellsFragment.newInstance()
                    add(R.id.fragment_container, fragment, fragmentTag)
                    commit()
                }
            }
        }

    }
}
