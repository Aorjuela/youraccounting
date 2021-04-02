package com.example.youraccounting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "egresses_table")
data class Egress(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "date_column") var date: String,
    @ColumnInfo(name = "date_miliseconds_column") var dateMilliseconds: Long,
    @ColumnInfo(name = "categoria_column") var categoria: String,
    @ColumnInfo(name = "tipo_column") var tipo: String,
    @ColumnInfo(name = "forma_pago_column") var formaPago: String,
    @ColumnInfo(name = "proveedor_column") var proveedor: String,
    @ColumnInfo(name = "descripcion_column") var descripcion: String,
    @ColumnInfo(name = "valor_column") var valor: Int
)
