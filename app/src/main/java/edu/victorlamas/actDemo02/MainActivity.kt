package edu.victorlamas.actDemo02

import android.os.Bundle
import android.view.Gravity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import edu.victorlamas.actDemo02.databinding.ActivityMainBinding
import edu.victorlamas.actDemo02.model.Item
import edu.victorlamas.actDemo02.model.ItemsAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // Configura el adaptador con una lista de items y define los listeners
        // para los clicks en cada item
        itemsAdapter = ItemsAdapter(
            Item.items,
            // Usa el objeto pasado para mostrar la información en el detalle
            itemClick = { item ->
                DetailActivity.navigateToDetail(this, item)
            }, imageClick = { item -> // Crea un snackbar al tocar las imágenes
                val imageSnackbar = movedSnackbar("Image clicked: ${item.title}")
                imageSnackbar.show()
            }, itemLongClick = { item, pos -> // Elimina elementos del RecyclerView mediante una pulsación larga
                val itemBackup: Item = itemsAdapter.itemsList[pos] // Crear copia del item
                itemsAdapter.itemsList.remove(item) // Borrar item
                itemsAdapter.notifyItemRemoved(pos) // Actualizar adapter
                // Uso de SnackBar para confirmar el borrado del elemento.
                val longClickSnackbar = movedSnackbar("Item deleted: ${item.title}")
                    .setAction("Undo") {
                        itemsAdapter.itemsList.add(pos, itemBackup) // Restaurar copia
                        itemsAdapter.notifyItemInserted(pos) // Actualizar adapter
                }
                longClickSnackbar.show()
            }
        )
        binding.recyclerView.adapter = itemsAdapter
    }

    // Mueve todos los snackbar a la parte baja de la pantalla
    private fun movedSnackbar(textToShow: String): Snackbar {
        val snackbar = Snackbar.make(
            binding.root,
            textToShow,
            Snackbar.LENGTH_LONG
        )

        val params = CoordinatorLayout.LayoutParams(snackbar.view.layoutParams)
        params.gravity = Gravity.BOTTOM
        params.setMargins(0, 0, 0, -binding.root.paddingBottom)
        snackbar.view.layoutParams = params
        return snackbar
    }
}