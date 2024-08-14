package ru.itis.kpfu.cellularworld.presentation.fragments.cells

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.itis.kpfu.cellularworld.R
import ru.itis.kpfu.cellularworld.data.Cells
import ru.itis.kpfu.cellularworld.data.CellsListener
import ru.itis.kpfu.cellularworld.data.CellsRepositoryImpl
import ru.itis.kpfu.cellularworld.databinding.FragmnetCellsBinding
import ru.itis.kpfu.cellularworld.databinding.ItemCellsBinding
import ru.itis.kpfu.cellularworld.domain.CellsRepository

/**
 * Экран "Клеточное наполнение"
 * */
class CellsFragment : Fragment(R.layout.fragmnet_cells), CellsListener {

    private val binding by viewBinding(FragmnetCellsBinding::bind)
    private val cellsRepository: CellsRepository = CellsRepositoryImpl
    private val cellsList = CellsRepositoryImpl.cellList

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        onButtonClicked()
        CellsRepositoryImpl.setOnCellsListener(this)
    }

    private fun onButtonClicked() {
        binding.buttonCreate.setOnClickListener {
            cellsRepository.createCells()
            adapterCellsDelegate.notifyItemInserted(cellsList.size - 1)
            scrollToLastPosition()
        }
    }

    private fun cellsDelegate() = adapterDelegateViewBinding<Cells, Cells, ItemCellsBinding>(
        { layoutInflater, parent ->
            ItemCellsBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) {
        with(binding) {
            bind {
                cellsName.text = item.name
                cellsDescription.text = item.description
                cellsImage.text = item.image
                when(item.name) {
                    "Мёртвая" -> cellsImage.setBackgroundResource(R.drawable.cells_dead)
                    "Живая" -> cellsImage.setBackgroundResource(R.drawable.cells_live)
                    "Жизнь" -> cellsImage.setBackgroundResource(R.drawable.cells_life)
                }
            }
        }
    }

    private val adapterCellsDelegate = ListDelegationAdapter(cellsDelegate())

    private fun initAdapter() {
        adapterCellsDelegate.apply {
            items = cellsList
        }

        with(binding.rvCells) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = adapterCellsDelegate
        }
    }

    private fun scrollToLastPosition() {
        binding.rvCells.layoutManager?.let { layoutManager ->
            if (layoutManager is LinearLayoutManager) {
                val lastPosition = cellsList.size - 1
                layoutManager.scrollToPosition(lastPosition)
            }
        }
    }

    override fun addLife(newList: ArrayList<Cells>) {
        if (cellsList.size >= 3) {
            val lastThree = cellsList.takeLast(3)
            if (lastThree.all { it.name == "Живая" }) {
                cellsList.add(
                    Cells(
                        name = "Жизнь",
                        description = "Ку-ку!",
                        image = "\uD83D\uDC23"
                    )
                )
            }
        }
    }

    override fun killLife(newList: ArrayList<Cells>) {
        if (cellsList.size >= 3) {
            val lastThree = cellsList.takeLast(3)
            if (lastThree.all { it.name == "Мёртвая" }) {
                val indexToUpdate  = cellsList.indexOfLast { it.name == "Жизнь" }
                if (indexToUpdate != -1) {
                    cellsList[indexToUpdate] = Cells(
                        name = "Мёртвая",
                        description = "или прикидывается",
                        image = "\uD83D\uDC80"
                    )
                    adapterCellsDelegate.notifyItemChanged(indexToUpdate)
                }
            }
        }
    }

    companion object {
        const val FRAGMENT_CELLS_TAG = "FRAGMENT_CELLS"

        /**
         * Инстанс для навигации на [CellsFragment]
         */
        fun newInstance(): CellsFragment = CellsFragment()
    }

}
