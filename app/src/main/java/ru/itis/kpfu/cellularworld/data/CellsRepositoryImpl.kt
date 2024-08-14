package ru.itis.kpfu.cellularworld.data

import ru.itis.kpfu.cellularworld.domain.CellsRepository
import kotlin.random.Random

/**
 * Слушатель на список клеток
 * */
interface CellsListener {
    /**
     * Зарождение "Жизни"
     * */
    fun addLife(newList: ArrayList<Cells>)
    /**
     * Убийство "Жизни"
     * */
    fun killLife(newList: ArrayList<Cells>)
}

/**
 * Реализация интерфейса [CellsRepository]
 * */
object CellsRepositoryImpl : CellsRepository {

    /**
     * Список клеток
     * */
    val cellList = arrayListOf<Cells>()
    private var listener: CellsListener? = null

    /**
     * Подключение слушателя
     * */
    fun setOnCellsListener(listener: CellsListener) {
        this.listener = listener
    }

    private fun generateCells(): Boolean {
        return Random.nextBoolean()
    }

    override fun createCells() {
        if (generateCells()) {
            cellList.add(
                Cells(
                    name = "Живая",
                    description = "и шевелится!",
                    image = "\uD83D\uDCA5"
                )
            )
        } else cellList.add(
            Cells(
                name = "Мёртвая",
                description = "или прикидывается",
                image = "\uD83D\uDC80"
            )
        )
        listener?.addLife(cellList)
        listener?.killLife(cellList)
    }
}
