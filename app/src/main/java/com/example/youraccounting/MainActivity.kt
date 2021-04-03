package com.example.youraccounting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

private var egressViewModel: EgressViewModel? = null
private var newEgressButton: Button? = null
private lateinit var list: RecyclerView
private var egressAdapter: EgressAdapter? = null
private var textViewTotal: TextView? = null

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundleInfo: Bundle = FiltrosActivity.bundleInfo

        var egressViewModelFactory: EgressViewModelFactory = EgressViewModelFactory.createFactory(this)
        egressViewModel = ViewModelProviders.of(this, egressViewModelFactory).get(EgressViewModel::class.java)

        newEgressButton = findViewById(R.id.new_egress_button)

        list = findViewById(R.id.recycler_view_egresses)
        list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        textViewTotal = findViewById(R.id.text_view_total)

        egressViewModel?.getEgressesBySelectedParameters(
            bundleInfo.getString("FECHA_INICIAL")?.toLong(),
            bundleInfo.getString("FECHA_FINAL")?.toLong(),
            bundleInfo.getString("CATEGORIA"),
            bundleInfo.getString("TIPO"),
            bundleInfo.getString("FORMA_PAGO"),
            bundleInfo.getString("PROVEEDOR")
        )?.observe(this, androidx.lifecycle.Observer {
            //egressViewModel?.getEgressesBySelectedParameters("MERCADO",null,null,null)?.observe(this, androidx.lifecycle.Observer {
            egressAdapter = EgressAdapter(it)
            list.adapter = egressAdapter
            egressAdapter?.setOnclickListener(View.OnClickListener { v ->
                var egressSelected: Egress = it.get(list.getChildAdapterPosition(v))
                EgressActivity.idSeleccionado = egressSelected.id
                EgressActivity.tipoAccion = "ACTUALIZAR"
                startActivity(Intent(this, EgressActivity::class.java))
            })
            var suma: Int = 0
            for (egress in it) {
                suma += egress.valor
            }
            text_view_total.text = "TOTAL: " + suma
        })

        newEgressButton?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.new_egress_button -> {
                startActivity(Intent(this, EgressActivity::class.java))
                EgressActivity.tipoAccion = "INSERTAR"
            }
        }
    }
}