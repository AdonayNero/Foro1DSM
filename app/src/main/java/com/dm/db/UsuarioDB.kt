package com.dm.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class UsuarioDB (context: Context?){

    private var helper: DBHelper? = null
    private var db: SQLiteDatabase?=null

    init {
        helper= DBHelper(context)
        db = helper!!.getWritableDatabase()
    }

    companion object{
        val TABLE_NAME_USUARIO="usuario"
        val COL_ID="idUsuario"
        val COL_NOMBRE="nombre"
        val COL_CONTRA="contra"
        val COL_CORREO="correo"

        val CREATE_TABLE_USUARIO=(
                "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_USUARIO +" ("
                        + COL_ID +" integer primary key autoincrement,"
                        + COL_NOMBRE + " varchar(50) NOT NULL,"
                        + COL_CONTRA + " varchar(50) NOT NULL,"
                        + COL_CORREO + " varchar(50) NOT NULL);"
                )
    }

    fun generarContentValues(nombre: String?, contra:String?, correo:String?): ContentValues?{
        val valores = ContentValues()
        valores.put(COL_NOMBRE, nombre)
        valores.put(COL_CONTRA, nombre)
        valores.put(COL_CORREO,correo)
        return valores
    }

    fun registrarUsuario(nombre: String?, contra:String?, correo:String?):Int{
        if(comprobar_existencia(correo)==1){
            db!!.insert(TABLE_NAME_USUARIO,null,generarContentValues(nombre,contra,correo))
            return 1; //Registrado con exito
        }else{
            return 0; //No se pudo registrar
        }
    }

    fun logeo(correo: String?, contra:String?):Cursor?{
        val columns= arrayOf(COL_ID, COL_NOMBRE, COL_CORREO);
        var cursor: Cursor? = db!!.query(TABLE_NAME_USUARIO, columns, "$COL_CORREO=? and $COL_CONTRA=?", arrayOf(correo.toString(),contra.toString()), null, null, null)

        //La clase que invoque esta funcion debe comprobar si cursor se encuentra vacio o no, para permitirle redireccionar
        return cursor;
    }

    //Comprobando si ya existe correo
    fun comprobar_existencia(correo: String?): Int?{

        //Columnas a obtener de la consulta
        val columns = arrayOf(COL_ID, COL_NOMBRE,COL_CORREO)
        //Ejecutando consulta y almacenando resultado en cursor
        var cursor: Cursor? = db!!.query(TABLE_NAME_USUARIO, columns, "$COL_CORREO=?", arrayOf(correo.toString()), null, null, null)
        //Si cursor posee registros, correo ya esta ocupado
        if(cursor!!.count>0){
            //Si retorno 0 significa error al registrar
            return 0;
        }
        //Confirmando que el correo no ha sido utilizado
        return 1;
    }


}