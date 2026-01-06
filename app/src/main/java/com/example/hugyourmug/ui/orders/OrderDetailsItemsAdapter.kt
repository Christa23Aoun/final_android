package com.example.hugyourmug.ui.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hugyourmug.R
import com.example.hugyourmug.data.model.OrderItem

class OrderDetailsItemsAdapter :
    RecyclerView.Adapter<OrderDetailsItemsAdapter.ItemViewHolder>() {

    private var items: List<OrderItem> = emptyList()
    private var freeItemId: String? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtItemName)
        val txtPrice: TextView = itemView.findViewById(R.id.txtItemPrice)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtItemQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_detail, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.txtName.text = item.name
        holder.txtQuantity.text = "x${item.quantity}"

        holder.txtName.setTextColor(holder.itemView.context.getColor(android.R.color.white))
        holder.txtPrice.setTextColor(holder.itemView.context.getColor(R.color.coffee_brown))

        if (item.id == freeItemId) {
            holder.txtPrice.text = "FREE"
            holder.txtName.setTextColor(holder.itemView.context.getColor(R.color.coffee_brown))
        } else {
            holder.txtPrice.text = "$%.2f".format(item.price)
        }
    }

    override fun getItemCount() = items.size

    fun setFreeItemValue(enabled: Boolean) {
        freeItemId = if (enabled) {
            items.maxByOrNull { it.price }?.id
        } else {
            null
        }
        notifyDataSetChanged()
    }

    fun updateList(newItems: List<OrderItem>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = items.size
            override fun getNewListSize() = newItems.size
            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                items[oldPos].id == newItems[newPos].id
            override fun areContentsTheSame(oldPos: Int, newPos: Int) =
                items[oldPos] == newItems[newPos]
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }
}
