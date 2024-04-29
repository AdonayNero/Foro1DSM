package com.dm.foro1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dm.db.*;

class RegisterActivity : AppCompatActivity() {

    private var dbHelper: DBHelper? = null
    private var db: SQLiteDatabase? = null
    private var managerUsuario: UsuarioDB?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DBHelper(this)
        db = dbHelper!!.writableDatabase

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val usernameEditText: EditText = findViewById(R.id.editTextUsername)
        val emailEditText: EditText = findViewById(R.id.editTextEmail)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val rePasswordEditText: EditText = findViewById(R.id.editTextRePassword)
        val registerButton: Button = findViewById(R.id.buttonRegister)
        val textViewRegister: TextView = findViewById(R.id.textViewLogin)
        textViewRegister.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            managerUsuario = UsuarioDB(this)
            val user = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val rePassword = rePasswordEditText.text.toString()
            if (password != rePassword){
                Toast.makeText(this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!email.matches(emailPattern.toRegex())) {
                Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Detiene la ejecución si el email no es válido
            }
            if (usernameEditText.text.isNotEmpty() && emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {

                // Aquí iría el código para registrar al usuario, posiblemente enviando los datos a un servidor o base de datos

                if(managerUsuario!!.registrarUsuario(user,password,email)==1){
                    Toast.makeText(this, "Registro correcto", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{//Si el correo ya se encuentra registrado, tira mensaje de error
                    Toast.makeText(this, "Este correo ya ha sido registrado", Toast.LENGTH_SHORT).show()
                }


            } else {
                Toast.makeText(this, "Todos los campos deben ser rellenados", Toast.LENGTH_SHORT).show()
            }
        }
    }
}