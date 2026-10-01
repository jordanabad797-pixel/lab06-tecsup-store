package com.tecsup.tecsupstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.tecsupstore.components.ProductoCard
import com.tecsup.tecsupstore.model.categorias
import com.tecsup.tecsupstore.model.productosEjemplo

@Composable
fun TiendaScreen(
    favoritos: List<Int>,
    onToggleFavorito: (Int) -> Unit
) {
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    val productosFiltrados = if (categoriaSeleccionada == "Todos") {
        productosEjemplo
    } else {
        productosEjemplo.filter { it.categoria == categoriaSeleccionada }
    }
    val secciones = productosFiltrados.groupBy { it.categoria }

    Column {
        LazyRow(
            modifier = Modifier.padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categorias) { categoria ->
                FilterChip(
                    selected = categoria == categoriaSeleccionada,
                    onClick = { categoriaSeleccionada = categoria },
                    label = { Text(categoria) }
                )
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            secciones.forEach { (categoria, lista) ->
                item {
                    Text(
                        text = categoria,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(lista, key = { it.id }) { producto ->
                    ProductoCard(
                        producto = producto,
                        esFavorito = producto.id in favoritos,
                        onToggleFavorito = { onToggleFavorito(producto.id) }
                    )
                }
            }
        }
    }
}