package com.dm.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class CarritoDB (context: Context?){

    private var helper: DBHelper? = null
    private var db: SQLiteDatabase?=null

    init {
        helper= DBHelper(context)
        db = helper!!.getWritableDatabase()
    }

    companion object{
        val TABLE_NAME_CARRITO="carrito"
        val COL_ID="idCarrito"
        val COL_IDCOMPRA="idCompra"
        val COL_IDPRODUCTOS="idProductos"
        val COL_CANTIDAD="cantidad"

        val CREATE_TABLE_CARRITO=(
                "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_CARRITO +"("
                        + COL_ID +" integer primary key autoincrement,"
                        + COL_IDCOMPRA + " integer NOT NULL,"
                        + COL_IDPRODUCTOS + " integer NOT NULL,"
                        + COL_CANTIDAD + " integer NOT NULL,"
                        + "FOREIGN KEY("+ COL_IDCOMPRA +") REFERENCES compra("+ COL_IDCOMPRA +"),"
                        + "FOREIGN KEY("+ COL_IDPRODUCTOS +") REFERENCES productos("+ COL_IDPRODUCTOS +"));"
                )
    }

    fun generarContentValues(idCompra: Int?, idProductos:Int?, cantidad:Int?): ContentValues?{
        val valores = ContentValues()
        valores.put(COL_IDCOMPRA, idCompra)
        valores.put(COL_IDPRODUCTOS, idProductos)
        valores.put(COL_CANTIDAD,cantidad)
        return valores
    }

    //El carrito contiene todos los productos de una o varias compras segun el idCompra
    fun agregarAlCarrito(idCompra: Int?,idProductos: Int?,cantidad: Int?){
        //Se genera un registro por cada producto que el usuario seleccione en la lista de productos, se relaciona con el idCompra.
        db!!.insert(TABLE_NAME_CARRITO,null,generarContentValues(idCompra,idProductos,cantidad))
    }

    //Para eliminar un elemnto concreto del carrito del usuario
    fun eliminarDelCarrito(idCarrito: Int?){
        db!!.delete(TABLE_NAME_CARRITO, "$COL_ID=?", arrayOf(idCarrito.toString()))
    }

    //Se obtienen todos los registros que pertenescan a un idCompra especifico
    fun obtenerCarritoActual(idCompra: Int?): Cursor?{

        val columns = arrayOf(COL_ID, COL_IDCOMPRA, COL_IDPRODUCTOS, COL_CANTIDAD)
        val cursor: Cursor? = db!!.query(TABLE_NAME_CARRITO, columns, "$COL_IDCOMPRA=?", arrayOf(idCompra.toString()), null, null, null)

        return cursor;
    }


}