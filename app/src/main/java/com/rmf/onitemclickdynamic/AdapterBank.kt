package com.rmf.onitemclickdynamic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rmf.onitemclickdynamic.databinding.ItemBankBinding

class AdapterBank(private val list: List<DataBank>, private val listener: OnItemClickListener) : RecyclerView.Adapter<AdapterBank.ViewHolder>() {

    /** Property**/
    private lateinit var rv: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBankBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataBank = list[holder.adapterPosition]
        holder.bind(dataBank)
    }

    override fun getItemCount(): Int = list.size

    /** Tambahan Untuk Mendeteksi RV diklik **/
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    inner class ViewHolder(val binding: ItemBankBinding) : RecyclerView.ViewHolder(binding.root){

        init { // Initial Item Click Listener
            binding.root.setOnClickListener {

                listener.onItemClick(adapterPosition,rv,list)
            }
        }

        fun bind(dataBank: DataBank){ // untuk onBindViewHolder
            binding.apply {
                textNamaBank.text = dataBank.namaBank
                checklist.isVisible = dataBank.check
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int,recyclerView: RecyclerView, list: List<DataBank>)
    }
}