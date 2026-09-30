package com.tecsup.tecsupstore.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.tecsupstore.model.Producto

@Composable
fun ProductoCard(producto: Producto, modifier: Modifier = Modifier) {
    // Estado que controla si el menú contextual está abierto o cerrado
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = producto.emoji, fontSize = 32.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "S/ %.2f".format(producto.precio),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            // El DropdownMenu va dentro del mismo Box que el ícono que lo activa,
            // así el menú se ancla justo debajo del botón ⋮ de ESTA tarjeta.
            Box {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Más opciones"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Favoritos") },
                        onClick = {
                            expanded = false
                            Toast.makeText(context, "${producto.nombre} agregado a favoritos", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Compartir") },
                        onClick = {
                            expanded = false
                            Toast.makeText(context, "Compartiendo ${producto.nombre}", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Reportar") },
                        onClick = {
                            expanded = false
                            Toast.makeText(context, "Reporte enviado: ${producto.nombre}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}