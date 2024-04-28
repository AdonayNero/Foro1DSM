package com.dm.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
    companion object {
        private const val DB_NAME="tiendaForo.sqlite"
        private const val DB_VERSION=1
    }

    override fun onCreate(db: SQLiteDatabase){
        db.execSQL(UsuarioDB.CREATE_TABLE_USUARIO)
        db.execSQL(ProductosDB.CREATE_TABLE_PRODUCTOS)
        db.execSQL(CompraDB.CREATE_TABLE_COMPRA)
        db.execSQL(CarritoDB.CREATE_TABLE_CARRITO)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

}