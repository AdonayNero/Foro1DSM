package com.dm.foro1

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dm.db.*

class LoginActivity : AppCompatActivity() {

    private var dbHelper: DBHelper? = null
    private var db: SQLiteDatabase? = null
    private var managerUsuario: UsuarioDB?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DBHelper(this)
        db = dbHelper!!.writableDatabase

        val usernameEditText: EditText = findViewById(R.id.editTextUsername)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val loginButton: Button = findViewById(R.id.buttonLogin)
        val textViewRegister: TextView = findViewById(R.id.textViewRegister)
        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener {

            managerUsuario = UsuarioDB(this)

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Código para manejar el inicio de sesión exitoso
            val cursor: Cursor? = managerUsuario!!.logeo(username,password)
            //Si hay registro...entonces logear
            if(cursor!!.count>0){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()  // Cierra la actividad actual
            } else {
                // Manejar inicio de sesión fallido
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}