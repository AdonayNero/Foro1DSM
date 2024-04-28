package com.dm.foro1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dm.foro1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Productos())


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.itProducto -> replaceFragment(Productos())
                R.id.itCarrito -> replaceFragment(CarritoCompra())
                R.id.itFactura -> replaceFragment(Factura())
                else -> {

                }


            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManage = supportFragmentManager
        val fragmentTransaction = fragmentManage.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


}