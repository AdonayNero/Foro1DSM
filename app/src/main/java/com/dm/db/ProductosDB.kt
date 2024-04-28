package com.dm.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class ProductosDB (context: Context?){
    private var helper: DBHelper? = null
    private var db: SQLiteDatabase?=null

    init {
        helper= DBHelper(context)
        db = helper!!.getWritableDatabase()
    }
    companion object{
        val TABLE_NAME_PRODUCTOS="productos"
        val COL_ID="idProductos"
        val COL_NOMBRE="nombre"
        val COL_PRECIO="precio"
        val COL_CANTIDAD="cantidad"

        val CREATE_TABLE_PRODUCTOS=(
                "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_PRODUCTOS +"("
                        + COL_ID +" integer primary key autoincrement,"
                        + COL_NOMBRE + " varchar(50) NOT NULL,"
                        + COL_PRECIO + " decimal(10,2) NOT NULL,"
                        + COL_CANTIDAD + " integer NOT NULL);"
                )
    }


    fun generarContentValues(nombre: String, precio:String, cantidad:String): ContentValues?{
        val valores = ContentValues()
        valores.put(COL_NOMBRE, nombre)
        valores.put(COL_PRECIO, precio.toDouble())
        valores.put(COL_CANTIDAD,cantidad.toInt())
        return valores
    }


    //Invocar la funcion cuando se renderice la vista de productos
    fun insertValuesDefault() {
        //Productos iniciales que se cargaran en la bdd
        val productosIniciales = arrayOf(
            arrayOf("Leche",2.55,7),
            arrayOf("Gaseosa",1.99,15),
            arrayOf("Fideos",2.50,20),
            arrayOf("Cafe",4.99,22),
            arrayOf("Pescado",3.25,30),
            arrayOf("Jugo naranja",6.55,19),
            arrayOf("Chocolate",3.10,43),
            arrayOf("Frutas",2.55,7),
            arrayOf("Agua",2.25,38)
        )
        // Preparando columnas que deseo seleccionar con la consulta
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_PRECIO, COL_CANTIDAD)

        //Ejecutando y guardando resultados de la consulta
        var cursor: Cursor? = db!!.query(TABLE_NAME_PRODUCTOS, columns, null, null, null, null, null)
        // Validando que se ingrese la informacion solamente una vez, cuando se instala por primera vez la aplicacion
        if (cursor == null || cursor!!.count <= 0) {
            // Registrando categorias por defecto
            for (item in productosIniciales) {
                val nombre = item[0].toString()
                val precio = item[1].toString()
                val cantidad = item[2].toString()
                db!!.insert(TABLE_NAME_PRODUCTOS, null, generarContentValues(nombre,precio,cantidad))
            }
        }
    }

    //Al comprar un producto, invocar esta funcion para actualizar existencias en lista
    fun modificarCantidad(codigo: Int, cantidad: Int){

        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_PRECIO, COL_CANTIDAD)
        var cursor: Cursor? = db!!.query(TABLE_NAME_PRODUCTOS, columns, "$COL_ID=?", arrayOf(codigo.toString()), null, null, null)
        var nombre=cursor!!.getString(0);
        var precio=cursor!!.getDouble(1);

        db!!.update(TABLE_NAME_PRODUCTOS,generarContentValues(nombre,precio.toString(),cantidad.toString()),"$COL_ID=?", arrayOf(codigo.toString()))

    }

    fun mostrarListaProductos():Cursor?{
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_PRECIO, COL_CANTIDAD)
        var cursor: Cursor? = db!!.query(TABLE_NAME_PRODUCTOS, columns, null, null, null, null, null)

        return cursor;
    }

    fun obtenerProducto(idProductos: Int?): Cursor? {

        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_PRECIO, COL_CANTIDAD)
        var cursor: Cursor? = db!!.query(TABLE_NAME_PRODUCTOS, columns, "$COL_ID=?", arrayOf(idProductos.toString()), null, null, null)

        return cursor;
    }

}