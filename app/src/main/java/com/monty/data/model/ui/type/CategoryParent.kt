package com.monty.data.model.ui.type

import com.monty.data.model.ui.Category
import com.monty.data.model.ui.Group

enum class CategoryParent(val value: String) {
    SPORT("sport"),
    TOOLS("tools"),
    ELECTRO("electro"),
    MOTO("moto"),
}

object Categories {
    val list = listOf(
        Category(id = 1, parent = Group(CategoryParent.SPORT.value, "Šport"), name = "Cyklistika"),
        Category(id = 2, parent = Group(CategoryParent.SPORT.value, "Šport"), name = "Zimné športy"),
        Category(id = 3, parent = Group(CategoryParent.SPORT.value, "Šport"), name = "Vodné športy"),
        Category(id = 4, parent = Group(CategoryParent.SPORT.value, "Šport"), name = "Loptové športy"),
        Category(id = 5, parent = Group(CategoryParent.SPORT.value, "Šport"), name = "Outdoor"),
        Category(id = 6, parent = Group(CategoryParent.SPORT.value, "Šport"), name = "Iné"),
        Category(id = 7, parent = Group(CategoryParent.TOOLS.value, "Náradie a stroje"), name = "Vŕtačky"),
        Category(id = 8, parent = Group(CategoryParent.TOOLS.value, "Náradie a stroje"), name = "Súpravy náradia"),
        Category(id = 9, parent = Group(CategoryParent.TOOLS.value, "Náradie a stroje"), name = "Záhradná technika"),
        Category(id = 10, parent = Group(CategoryParent.TOOLS.value, "Náradie a stroje"), name = "Brúsky"),
        Category(id = 11, parent = Group(CategoryParent.TOOLS.value, "Náradie a stroje"), name = "Píly"),
        Category(id = 12, parent = Group(CategoryParent.TOOLS.value, "Náradie a stroje"), name = "Iné"),
        Category(id = 13, parent = Group(CategoryParent.ELECTRO.value, "Elektro"), name = "Foto"),
        Category(id = 14, parent = Group(CategoryParent.ELECTRO.value, "Elektro"), name = "Video"),
        Category(id = 15, parent = Group(CategoryParent.ELECTRO.value, "Elektro"), name = "PC"),
        Category(id = 16, parent = Group(CategoryParent.ELECTRO.value, "Elektro"), name = "Zvukotechnika"),
        Category(id = 17, parent = Group(CategoryParent.ELECTRO.value, "Elektro"), name = "Dataprojektory"),
        Category(id = 18, parent = Group(CategoryParent.ELECTRO.value, "Elektro"), name = "Iné"),
        Category(id = 19, parent = Group(CategoryParent.MOTO.value, "Moto"), name = "Osobné autá"),
        Category(id = 20, parent = Group(CategoryParent.MOTO.value, "Moto"), name = "Nákladné autá"),
        Category(id = 21, parent = Group(CategoryParent.MOTO.value, "Moto"), name = "Motorky"),
        Category(id = 22, parent = Group(CategoryParent.MOTO.value, "Moto"), name = "Prívesné vozíky"),
        Category(id = 23, parent = Group(CategoryParent.MOTO.value, "Moto"), name = "Autosedačky"),
        Category(id = 24, parent = Group(CategoryParent.MOTO.value, "Moto"), name = "Iné")
    )
}
