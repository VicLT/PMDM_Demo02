package edu.victorlamas.actDemo02.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: Int, val title: String, val description: String, val image: String
) : Parcelable {
    companion object {
        val items: MutableList<Item> = mutableListOf()

        // Inicializa 10 objetos Item en la lista items
        init {
            for (i in 1..10) {
                items.add(
                    Item(
                        i,
                        "Item $i",
                        "Description for item $i",
                        "https://picsum.photos/200/200?image=$i"
                    )
                )
            }
        }
    }
}