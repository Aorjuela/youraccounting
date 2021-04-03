package com.example.youraccounting

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_filtros.*
import java.text.SimpleDateFormat
import java.util.*

class FiltrosActivity : AppCompatActivity(), View.OnClickListener {
    private var egressViewModel: EgressViewModel? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var textViewFechaInicio: TextView? = null
    private var textViewFechaFin: TextView? = null
    private var spinnerCategoriasGasto: Spinner? = null
    private var spinnerTiposGasto: Spinner? = null
    private var spinnerFormasPago: Spinner? = null
    private var spinnerProveedores: Spinner? = null
    private var buttonBuscar: Button? = null

    private val calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val day = calendar.get(Calendar.DAY_OF_MONTH)
    private val formatoHora: SimpleDateFormat = SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", Locale("es","ES"))

    private var fechaInicialMilliseconds: Long = 0
    private var fechaFinalMilliseconds: Long = 0

    companion object{
        var bundleInfo: Bundle = Bundle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtros)

        var egressViewModelFactory: EgressViewModelFactory = EgressViewModelFactory.createFactory(this)
        egressViewModel = ViewModelProviders.of(this, egressViewModelFactory).get(EgressViewModel::class.java)

        textViewFechaInicio = findViewById(R.id.text_view_fecha_inicio)
        textViewFechaFin = findViewById(R.id.text_view_fecha_fin)

        spinnerCategoriasGasto = findViewById(R.id.spinner_categorias_gasto)
        spinnerTiposGasto = findViewById(R.id.spinner_tipos_gasto)
        spinnerFormasPago = findViewById(R.id.spinner_formas_pago)
        spinnerProveedores = findViewById(R.id.spinner_proveedores)

        buttonBuscar = findViewById(R.id.button_buscar)

        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        fechaFinalMilliseconds = calendar.timeInMillis

        var arrayAdapterCategoriasGasto: ArrayAdapter<CharSequence>? = ArrayAdapter.createFromResource(this,R.array.opciones_categoria_gasto, android.R.layout.simple_spinner_item)
        var arrayAdapterTiposGasto: ArrayAdapter<CharSequence>? = ArrayAdapter.createFromResource(this,R.array.opciones_tipo_gasto, android.R.layout.simple_spinner_item)
        var arrayAdapterFormasPago: ArrayAdapter<CharSequence>? = ArrayAdapter.createFromResource(this,R.array.opciones_forma_pago, android.R.layout.simple_spinner_item)
        var arrayAdapterProveedores: ArrayAdapter<CharSequence>? = ArrayAdapter.createFromResource(this,R.array.opciones_proveedor, android.R.layout.simple_spinner_item)

        spinnerCategoriasGasto?.adapter = arrayAdapterCategoriasGasto
        spinnerTiposGasto?.adapter = arrayAdapterTiposGasto
        spinnerFormasPago?.adapter = arrayAdapterFormasPago
        spinnerProveedores?.adapter = arrayAdapterProveedores

        textViewFechaInicio?.setOnClickListener(this)
        textViewFechaFin?.setOnClickListener(this)
        buttonBuscar?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.text_view_fecha_inicio -> {
                datePickerDialog = DatePickerDialog (this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth -> calendar.set(
                    Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calendar.set(Calendar.HOUR, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    fechaInicialMilliseconds = calendar.timeInMillis
                    //textViewFechaInicio?.text = fechaInicialMilliseconds.toString()
                    textViewFechaInicio?.text = formatoHora.format(calendar?.time)},year,month,day)
                datePickerDialog?.show()
            }
            R.id.text_view_fecha_fin -> {
                datePickerDialog = DatePickerDialog (this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calendar.set(Calendar.HOUR, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    fechaFinalMilliseconds = calendar.timeInMillis
                    //textViewFechaFin?.text = fechaFinalMilliseconds.toString()
                    textViewFechaFin?.text = formatoHora.format(calendar.time)},year,month,day)
                datePickerDialog?.show()
            }
            R.id.button_buscar -> {
                bundleInfo.putString("FECHA_INICIAL", if (text_view_fecha_inicio.text == "N/A") null else fechaInicialMilliseconds.toString())
                bundleInfo.putString("FECHA_FINAL", if (text_view_fecha_inicio.text == "N/A") null else fechaFinalMilliseconds.toString())
                bundleInfo.putString("CATEGORIA", if (spinnerCategoriasGasto?.selectedItem.toString() == "N/A") null else spinnerCategoriasGasto?.selectedItem.toString())
                bundleInfo.putString("TIPO", if (spinnerTiposGasto?.selectedItem.toString() == "N/A") null else spinnerTiposGasto?.selectedItem.toString())
                bundleInfo.putString("FORMA_PAGO", if (spinnerFormasPago?.selectedItem.toString() == "N/A") null else spinnerFormasPago?.selectedItem.toString())
                bundleInfo.putString("PROVEEDOR", if (spinnerProveedores?.selectedItem.toString() == "N/A") null else spinnerProveedores?.selectedItem.toString())
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }
}