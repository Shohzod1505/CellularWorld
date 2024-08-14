package ru.itis.kpfu.cellularworld.data

/**
 * Сущность "Клетка"
 * @property name Наименование клетки
 * @property description Описание клетки
 * @property image Изображение клетки
 * */
data class Cells(
    val name: String,
    val description: String,
    val image: String,
)
