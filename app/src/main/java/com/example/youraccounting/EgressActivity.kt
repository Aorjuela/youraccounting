package com.example.youraccounting

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import java.text.SimpleDateFormat
import java.util.*

class EgressActivity : AppCompatActivity(), View.OnClickListener {
    private var egressViewModel: EgressViewModel? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var textViewFecha: TextView? = null
    private var spinnerCategoriasGasto: Spinner? = null
    private var spinnerTiposGasto: Spinner? = null
    private var spinnerFormasPago: Spinner? = null
    private var spinnerProveedores: Spinner? = null
    private var editTextDescripcionGasto: EditText? = null
    private var editTextValorGasto: EditText? = null
    private var butttonDescartar: Button? = null
    private var buttonCargar: Button? = null

    private val calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val day = calendar.get(Calendar.DAY_OF_MONTH)
    private val formatoHora: SimpleDateFormat = SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", Locale("es","ES"))
    private var dateMilliseconds: Long = 0

    companion object{
        var tipoAccion: String = "INSERTAR"
        var idSeleccionado: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_egress)

        var egressViewModelFactory: EgressViewModelFactory = EgressViewModelFactory.createFactory(this)
        egressViewModel = ViewModelProviders.of(this, egressViewModelFactory).get(EgressViewModel::class.java)

        textViewFecha = findViewById(R.id.text_view_fecha)

        spinnerCategoriasGasto = findViewById(R.id.spinner_categorias_gasto)
        spinnerTiposGasto = findViewById(R.id.spinner_tipos_gasto)
        spinnerFormasPago = findViewById(R.id.spinner_formas_pago)
        spinnerProveedores = findViewById(R.id.spinner_proveedores)

        editTextDescripcionGasto = findViewById(R.id.edit_text_descripcion)
        editTextValorGasto = findViewById(R.id.edit_text_valor)

        butttonDescartar = findViewById(R.id.button_descartar)
        buttonCargar = findViewById(R.id.button_cargar)

        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        dateMilliseconds = calendar.timeInMillis
        //textViewFecha?.text = dateMilliseconds.toString()
        textViewFecha?.text = formatoHora.format(calendar.time)

        var arrayAdapterCategoriasGasto: ArrayAdapter<CharSequence>? = ArrayAdapter.createFromResource(this,R.array.opciones_categoria_gasto, android.R.layout.simple_spinner_item)
        var arrayAdapterTiposGasto: ArrayAdapter<CharSequence>? = ArrayAdapter.createFromResource(this,R.array.opciones_tipo_gasto, android.R.layout.simple_spinner_item)
        var arrayAdapterFormasPago: ArrayAdapter<CharSequence>? = ArrayAdapter.createFromResource(this,R.array.opciones_forma_pago, android.R.layout.simple_spinner_item)
        var arrayAdapterProveedores: ArrayAdapter<CharSequence>? = ArrayAdapter.createFromResource(this,R.array.opciones_proveedor, android.R.layout.simple_spinner_item)

        spinnerCategoriasGasto?.adapter = arrayAdapterCategoriasGasto
        spinnerTiposGasto?.adapter = arrayAdapterTiposGasto
        spinnerFormasPago?.adapter = arrayAdapterFormasPago
        spinnerProveedores?.adapter = arrayAdapterProveedores

        textViewFecha?.setOnClickListener(this);
        butttonDescartar?.setOnClickListener(this)
        buttonCargar?.setOnClickListener(this)

        if(tipoAccion.equals("ACTUALIZAR")){
            egressViewModel?.getEgressById(idSeleccionado)?.observe(this, androidx.lifecycle.Observer { egress ->
                textViewFecha?.text = egress.date
                spinnerCategoriasGasto?.setSelection(obtenerIndiceporSpinneryValorItem("CATEGORIA_GASTO",egress.categoria))
                spinnerTiposGasto?.setSelection(obtenerIndiceporSpinneryValorItem("TIPO_GASTO",egress.tipo))
                spinnerFormasPago?.setSelection(obtenerIndiceporSpinneryValorItem("FORMA_PAGO",egress.formaPago))
                spinnerProveedores?.setSelection(obtenerIndiceporSpinneryValorItem("PROVEEDORES",egress.proveedor))
                editTextDescripcionGasto?.text = Editable.Factory.getInstance().newEditable(egress.descripcion)
                editTextValorGasto?.text = Editable.Factory.getInstance().newEditable(egress.valor.toString())
            })
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.text_view_fecha -> {
                datePickerDialog = DatePickerDialog (this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    /*val calendar: Calendar? = Calendar.getInstance()*/
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calendar.set(Calendar.HOUR, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    dateMilliseconds = calendar.timeInMillis
                    //textViewFecha?.text = dateMilliseconds.toString()
                    textViewFecha?.text = formatoHora.format(calendar?.time)},year,month,day)
                datePickerDialog?.show()
            }
            R.id.button_descartar ->{
                startActivity(Intent(this,FiltrosActivity::class.java))
            }
            R.id.button_cargar -> {
                when(tipoAccion){
                    "INSERTAR" -> egressViewModel?.insert(Egress(0,textViewFecha?.text.toString(),dateMilliseconds,spinnerCategoriasGasto?.selectedItem.toString(),spinnerTiposGasto?.selectedItem.toString(),spinnerFormasPago?.selectedItem.toString(),spinnerProveedores?.selectedItem.toString(),editTextDescripcionGasto?.text.toString(),Integer.parseInt(editTextValorGasto?.text.toString())))
                    "ACTUALIZAR" -> egressViewModel?.update(Egress(idSeleccionado,textViewFecha?.text.toString(),dateMilliseconds,spinnerCategoriasGasto?.selectedItem.toString(),spinnerTiposGasto?.selectedItem.toString(),spinnerFormasPago?.selectedItem.toString(),spinnerProveedores?.selectedItem.toString(),editTextDescripcionGasto?.text.toString(),Integer.parseInt(editTextValorGasto?.text.toString())))
                }
                startActivity(Intent(this,FiltrosActivity::class.java))
            }
        }
    }

    fun obtenerIndiceporSpinneryValorItem(nombreSpinner: String, valorItem: String): Int{
        lateinit var opcionesSpinner: ArrayList<String>
        when(nombreSpinner){
            "CATEGORIA_GASTO" -> opcionesSpinner = arrayListOf(*resources.getStringArray(R.array.opciones_categoria_gasto))
            "TIPO_GASTO" -> opcionesSpinner = arrayListOf(*resources.getStringArray(R.array.opciones_tipo_gasto))
            "FORMA_PAGO" -> opcionesSpinner = arrayListOf(*resources.getStringArray(R.array.opciones_forma_pago))
            "PROVEEDORES" -> opcionesSpinner = arrayListOf(*resources.getStringArray(R.array.opciones_proveedor))
        }
        return opcionesSpinner?.indexOf(valorItem)
    }
}