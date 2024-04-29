package com.dm.foro1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val productList: List<Producto>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewProductID: TextView = view.findViewById(R.id.textViewProductID)
        val textViewProductName: TextView = view.findViewById(R.id.textViewProductName)
        val textViewProductPrice: TextView = view.findViewById(R.id.textViewProductPrice)
        val textViewProductQuantity: TextView = view.findViewById(R.id.textViewProductQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.textViewProductID.text = "Product ID: ${product.id}"
        holder.textViewProductName.text = "Nombre: ${product.nombre}"
        holder.textViewProductPrice.text = "Precio: $${product.precio}"
        holder.textViewProductQuantity.text = "Cantidad: ${product.cantidad}"
    }

    override fun getItemCount() = productList.size
}
