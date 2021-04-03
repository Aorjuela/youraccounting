package com.example.youraccounting

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EgressRepository(
    private var egressDao: EgressDao?,
    private var executor: ExecutorService
) {
    companion object {
        var INSTANCE: EgressRepository? = null
        fun getInstance(context: Context): EgressRepository? {
            if (INSTANCE == null){
                synchronized(EgressRepository::class){
                    if (INSTANCE == null){
                        var egressDatabase: EgressDatabase? = EgressDatabase.getInstance(context)
                        INSTANCE = EgressRepository(egressDatabase?.egressDao(),Executors.newSingleThreadExecutor())
                    }
                }
            }
            return INSTANCE
        }}

    fun insert(egress: Egress){
        executor.execute(Runnable { egressDao?.insert(egress) })
    }

    fun update(egress: Egress){
        executor.execute(Runnable { egressDao?.update(egress) })
    }

    fun delete(egress: Egress){
        executor.execute(Runnable { egressDao?.delete(egress) })
    }

    fun getEgressById(id: Int?): LiveData<Egress>?{
        return egressDao?.getEgressById(id)
    }

    fun getEgressesBySelectedParameters(fechaInicial: Long?, fechaFinal: Long?, categoria: String?, tipo: String?, formaPago: String?, proveedor: String?): LiveData<List<Egress>>?{
        return egressDao?.getEgressesBySelectedParameters(fechaInicial, fechaFinal, categoria, tipo, formaPago, proveedor)
    }

    fun getAllEgresses(): LiveData<List<Egress>>?{
        return egressDao?.getAllEgresses()
    }
}