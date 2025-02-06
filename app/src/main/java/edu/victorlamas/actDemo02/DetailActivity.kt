package edu.victorlamas.actDemo02

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import edu.victorlamas.actDemo02.databinding.ActivityDetailBinding
import edu.victorlamas.actDemo02.model.Item

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
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

        // Obtiene el item desde el Intent. Si es nulo, cierra la actividad
        val item: Item?;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            item = intent.getParcelableExtra(EXTRA_ITEM, Item::class.java)
        } else {
            item = intent.getParcelableExtra(EXTRA_ITEM)
        }
        if (item == null) {
            finish()
            return
        } else {
            // Configura la vista con los datos del item
            binding.mToolbar.title = item.title
            binding.tvDescDetail.text = item.description

            // Carga la imagen usando Glide, con esquinas redondeadas
            Glide.with(this)
                .load(item.image)
                .fitCenter()
                .transform(RoundedCorners(16))
                .into(binding.ivDetail)
        }

        // Configura la Toolbar y habilita el botón de "volver"
        setSupportActionBar(binding.mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Muestra un Toast si el usuario intenta usar el botón de "retroceso"
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(
                    this@DetailActivity,
                    "Use the \"back\" button on the Toolbar.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    // Controla el comportamiento del botón de "volver" en la Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Inicia el DetailActivity pasando un item como parámetro
    companion object {
        private const val EXTRA_ITEM = "ITEM"

        fun navigateToDetail(activity: Activity, item: Item) {
            activity.startActivity(Intent(activity, DetailActivity::class.java).apply {
                putExtra(EXTRA_ITEM, item)
            })
        }
    }
}