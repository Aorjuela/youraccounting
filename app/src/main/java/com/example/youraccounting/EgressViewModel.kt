package com.example.youraccounting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import java.util.ArrayList

class EgressViewModel(
    var egressRepository: EgressRepository
): ViewModel(){
    companion object{
        var egressesList: List<Egress>? = null
    }
    fun insert(egress: Egress){
        egressRepository.insert(egress)
    }
    fun update(egress: Egress){
        egressRepository.update(egress)
    }
    fun delete(egress: Egress){
        egressRepository.delete(egress)
    }
    fun getEgressById(id: Int?): LiveData<Egress>?{
        return egressRepository.getEgressById(id)
    }
    fun getEgressesBySelectedParameters(fechaIncicial: Long?, fechaFinal: Long?, categoria: String?, tipo: String?, formaPago: String?, proveedor: String?): LiveData<List<Egress>>?{
        return egressRepository.getEgressesBySelectedParameters(fechaIncicial, fechaFinal, categoria, tipo, formaPago, proveedor)
    }
    fun getAllEgresses(): LiveData<List<Egress>>?{
        egressesList = egressRepository.getAllEgresses()?.value
        return egressRepository.getAllEgresses()
    }
}