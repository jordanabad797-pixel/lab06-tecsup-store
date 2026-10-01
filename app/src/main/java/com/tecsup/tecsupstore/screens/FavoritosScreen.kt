package com.tecsup.tecsupstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.tecsup.tecsupstore.components.ProductoCard
import com.tecsup.tecsupstore.model.productosEjemplo

@Composable
fun FavoritosScreen(
    favoritos: List<Int>,
    onToggleFavorito: (Int) -> Unit
) {
    val productosFavoritos = productosEjemplo.filter { it.id in favoritos }

    if (productosFavoritos.isEmpty()) {
        PantallaSimple(
            titulo = "Favoritos",
            icono = Icons.Default.Favorite,
            mensaje = "Aún no tienes favoritos"
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productosFavoritos, key = { it.id }) { producto ->
                ProductoCard(
                    producto = producto,
                    esFavorito = true,
                    onToggleFavorito = { onToggleFavorito(producto.id) }
                )
            }
        }
    }
}