package edu.victorlamas.actDemo02.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.victorlamas.actDemo02.databinding.ItemsBinding

class ItemsAdapter(
    val itemsList: MutableList<Item>,
    val itemClick: (Item) -> Unit,
    val imageClick: (Item) -> Unit,
    val itemLongClick: (Item, pos: Int) -> Unit,
) : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {

    // Devuelve el ViewHolder ya configurado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemsViewHolder {
        return ItemsViewHolder(
            ItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    // Devuelve el tamaño de la fuente de datos
    override fun getItemCount() = itemsList.size

    // Pasa los objetos al ViewHolder personalizado
    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    // Rellena cada una de las vistas que se inflarán para cada uno de los
    // elementos del RecyclerView
    inner class ItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Localiza los elementos en la vista
        private val binding = ItemsBinding.bind(view)

        // Configura los datos del ítem en la vista
        fun bind(item: Item) {
            binding.tvId.text = item.id.toString()
            binding.tvTitle.text = item.title
            binding.ivItem.setOnClickListener { imageClick(item) }

            // Listener para click en el ítem
            itemView.setOnClickListener { itemClick(item) }

            // Carga la imagen usando Glide
            Glide.with(itemView)
                .load(item.image)
                .centerCrop()
                .into(binding.ivItem)

            // Listener para clicks largos en el ítem
            itemView.setOnLongClickListener {
                itemLongClick(item, adapterPosition)
                true // Indica que el evento ha sido manejado
            }
        }
    }
}