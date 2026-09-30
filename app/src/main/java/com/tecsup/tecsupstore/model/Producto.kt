package com.tecsup.tecsupstore.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val categoria: String,
    val emoji: String
)

val categorias = listOf("Todos", "Laptops", "Accesorios", "Audio")

val productosEjemplo = listOf(
    Producto(1, "Laptop Lenovo IdeaPad", 2499.0, "Laptops", "💻"),
    Producto(2, "Laptop HP Pavilion", 2899.0, "Laptops", "🖥️"),
    Producto(3, "Mouse inalámbrico", 59.9, "Accesorios", "🖱️"),
    Producto(4, "Teclado mecánico", 189.0, "Accesorios", "⌨️"),
    Producto(5, "Audífonos Bluetooth", 149.0, "Audio", "🎧"),
    Producto(6, "Parlante portátil", 99.0, "Audio", "🔊")
)