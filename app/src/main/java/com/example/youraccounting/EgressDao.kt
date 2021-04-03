package com.example.youraccounting

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EgressDao {

    @Insert
    fun insert(egress : Egress)

    @Update
    fun update(egress: Egress)

    @Delete
    fun delete(egress : Egress)

    @Query("SELECT * FROM egresses_table WHERE id =:id")
    fun getEgressById(id: Int?) : LiveData<Egress>

    @Query("SELECT * FROM egresses_table WHERE (:fechaInicial is null or date_miliseconds_column >=:fechaInicial) and (:fechaFinal is null or date_miliseconds_column <=:fechaFinal) and (:categoria is null or categoria_column =:categoria) and (:tipo is null or tipo_column =:tipo) and (:formaPago is null or forma_pago_column =:formaPago) and (:proveedor is null or proveedor_column =:proveedor) ORDER BY date_miliseconds_column DESC")
    fun getEgressesBySelectedParameters(fechaInicial: Long?, fechaFinal: Long?, categoria: String?, tipo: String?, formaPago: String?, proveedor: String?) : LiveData<List<Egress>>

    @Query("SELECT * FROM egresses_table")
    fun getAllEgresses(): LiveData<List<Egress>>
}