package com.dm.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase


class CompraDB (context: Context?) {
    private var helper: DBHelper? = null
    private var db: SQLiteDatabase?=null

    init {
        helper= DBHelper(context)
        db = helper!!.getWritableDatabase()
    }

    companion object{
        val TABLE_NAME_COMPRA="compra"
        val COL_ID="idCompra"
        val COL_IDUSUARIO="idUser"
        val COL_ESTADO="estado"

        val CREATE_TABLE_COMPRA=(
                "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_COMPRA +"("
                        + COL_ID +" integer primary key autoincrement,"
                        + COL_IDUSUARIO + " integer NOT NULL,"
                        + COL_ESTADO + " integer NOT NULL,"
                        + "FOREIGN KEY("+ COL_IDUSUARIO +") REFERENCES carrito("+ COL_IDUSUARIO +"));"
                )
    }


    fun generarContentValues(idUsuario: Int?,estado: Int?): ContentValues?{
        val valores = ContentValues()

        //Cuando finalice compra se modificara a 0 en otra funcion...
        valores.put(COL_IDUSUARIO, idUsuario)
        valores.put(COL_ESTADO, estado) //Significa activa
        return valores
    }

    //Funcion que genera una nueva compra del usuario
    fun nuevaCompra(idUsuario: Int?):Int{

        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_ESTADO)

        //Ejecutando y guardando resultados de la consulta
        //Verificando si el usuario actual posee una compra activa
        var cursor: Cursor? = db!!.query(TABLE_NAME_COMPRA, columns, "$COL_IDUSUARIO=? and $COL_ESTADO=?", arrayOf(idUsuario.toString(),"1"), null, null, null)

        //Si cursor tiene almacenado un registro con estado=1 con este idUsuario...
        if (cursor!!.count > 0) {
            // El id del usuario actual posee una compra activa
            //No podemos generar una nueva, mandamos 0 en señal de error
            return 0;
        }

        //Cuando se genera una compra, siempre inicia con estado 1, ya que esta activa
        db!!.insert(TABLE_NAME_COMPRA,null,generarContentValues(idUsuario,1))
        return 1;
    }

    fun finalizarCompra(idUsuario: Int?): Int{

        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_ESTADO)

        //Buscando la compra activa de este usuario
        var cursor: Cursor? = db!!.query(TABLE_NAME_COMPRA, columns, "$COL_IDUSUARIO=? and $COL_ESTADO=?", arrayOf(idUsuario.toString(),"1"), null, null, null)

        //Si no se puede encontrar la compra activa actal del usuario...
        if(cursor!!.count <= 0 || cursor==null){
            //Retornamos error
            return 0
        }

        //Modificando campo con estado 0 para finalizar compra activa que corresponde al idUsuario enviado
        db!!.update(TABLE_NAME_COMPRA,generarContentValues(idUsuario,0),"$COL_IDUSUARIO=? and $COL_ESTADO=?", arrayOf(idUsuario.toString(),"1"))
        return 1
    }

    //Buscar compra activa por idCompra
    fun obtenerCompraActivaPorIDCompra(idCompra: Int?): Cursor?{
        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_ESTADO)
        var cursor: Cursor? = db!!.query(TABLE_NAME_COMPRA, columns, "$COL_ID=? and $COL_ESTADO=?", arrayOf(idCompra.toString(),"1"), null, null, null)
        return cursor
    }

    //Buscar compra activa por idUsuario
    fun obtenerCompraActivaPorIDUsuario(idUsuario: Int?): Cursor?{
        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_ESTADO)
        var cursor: Cursor? = db!!.query(TABLE_NAME_COMPRA, columns, "$COL_IDUSUARIO=? and $COL_ESTADO=?", arrayOf(idUsuario.toString(),"1"), null, null, null)
        return cursor
    }

    //Para listar todas las compras ya finalizadas del usuario
    fun obtenerComprasFinalizadas(idCompra: Int?): Cursor?{
        var cursor: Cursor? =null;


        return cursor;
    }
}