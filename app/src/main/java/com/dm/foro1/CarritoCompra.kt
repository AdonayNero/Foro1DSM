package com.dm.foro1

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dm.db.*;

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CarritoCompra.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarritoCompra : Fragment() {

    private var dbHelper: DBHelper? = null
    private var db: SQLiteDatabase? = null
    private var managerCarrito: CarritoDB?=null
    private var managerProducto: ProductosDB?=null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView

    private var productsList = ArrayList<Producto>()

    private fun initializeRecyclerView() {
        managerCarrito= CarritoDB(context)
        managerProducto= ProductosDB(context)
        val contenidoCarrito: Cursor?= managerCarrito!!.obtenerCarritoActual(1)
        contenidoCarrito!!.moveToFirst()
        do {
            //Problema de indices
            val registro: Cursor?=managerProducto!!.obtenerProducto(contenidoCarrito.getInt(2))
            val id=registro!!.getInt(0)
            val nombre=registro!!.getString(1)
            val precio=registro!!.getDouble(2)
            val cantidad=registro!!.getInt(3)
            val producto = Producto(id,nombre,precio,cantidad)
            productsList.add(producto)

        }while(contenidoCarrito!!.moveToNext())


        productAdapter = ProductAdapter(productsList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = productAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBHelper(context)
        db = dbHelper!!.writableDatabase
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_carrito_compra, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewProductos)
        initializeRecyclerView()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CarritoCompra.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CarritoCompra().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}