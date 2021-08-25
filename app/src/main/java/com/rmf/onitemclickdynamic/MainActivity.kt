package com.rmf.onitemclickdynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rmf.onitemclickdynamic.databinding.ActivityMainBinding
import com.rmf.onitemclickdynamic.databinding.ItemBankBinding

class MainActivity : AppCompatActivity(), AdapterBank.OnItemClickListener {

    private var listHeaderBank: ArrayList<DataHeaderBank> = ArrayList()
    private var listBank: ArrayList<DataBank> = ArrayList()

    private lateinit var binding: ActivityMainBinding



    private var posisidataBankVA = -1
    private lateinit var dataBankVASebelumnya: DataBank


    private var posisidataBankTransfer = -1
    private lateinit var dataBankTransferSebelumnya: DataBank

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        loadData()
    }

    private fun loadData() {

        binding.root.removeAllViews() // bersihkan view children di linearlayout,  root = linearLayout di xml

        for (dataHeader in listHeaderBank) {

            val textHeaderBank = TextView(this)
            textHeaderBank.apply {
                text = dataHeader.nama
            }

            binding.root.addView(textHeaderBank)

            val recyclerView = RecyclerView(this)
            val listBankRV = ArrayList<DataBank>()

            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = AdapterBank(listBankRV, this@MainActivity)
            }

            binding.root.addView(recyclerView)

            for (dataBank in listBank) {
                if (dataBank.idHeader == dataHeader.idHeader) {
                    listBankRV.add(dataBank)
                }
            }
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun initData() {

        /** -- Contoh Data dari Api -- **/

        //Header
        listHeaderBank.add(DataHeaderBank(1, "Transfer Virtual Account"))
        listHeaderBank.add(DataHeaderBank(2, "Transfer Bank"))

        //Bank
        listBank.add(DataBank("Bank A", idHeader = 1))
        listBank.add(DataBank("Bank B", idHeader = 1))
        listBank.add(DataBank("Bank C", idHeader = 1))

        listBank.add(DataBank("Transfer Bank A", idHeader = 2))
        listBank.add(DataBank("Transfer Bank B", idHeader = 2))
        listBank.add(DataBank("Transfer Bank C", idHeader = 2))
    }

    override fun onItemClick(position: Int, recyclerView: RecyclerView, list: List<DataBank>) {


        if(list[position].idHeader == 1){
            //check data bank berdasarkan id header
            if (this::dataBankVASebelumnya.isInitialized
                && dataBankVASebelumnya.idHeader == list[position].idHeader
                && list[posisidataBankVA].check
            ) {
                list[posisidataBankVA].check = false
                recyclerView.adapter?.notifyItemChanged(posisidataBankVA)
            }

            list[position].check = !list[position].check

            posisidataBankVA = position
            dataBankVASebelumnya = list[posisidataBankVA]
            recyclerView.adapter?.notifyItemChanged(position)
        }
        else{ //id header 2
            if (this::dataBankTransferSebelumnya.isInitialized
                && dataBankTransferSebelumnya.idHeader == list[position].idHeader
                && list[posisidataBankTransfer].check
            ) {
                list[posisidataBankTransfer].check = false
                recyclerView.adapter?.notifyItemChanged(posisidataBankTransfer)
            }

            list[position].check = !list[position].check

            posisidataBankTransfer = position
            dataBankTransferSebelumnya = list[posisidataBankTransfer]
            recyclerView.adapter?.notifyItemChanged(position)
        }
    }

}