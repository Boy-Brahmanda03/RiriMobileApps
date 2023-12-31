package com.riridev.ririapp.ui.listagensi


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.riridev.ririapp.data.dummy.InstansiDummy
import com.riridev.ririapp.databinding.ActivityListAgensiBinding
import com.riridev.ririapp.ui.adapter.ListAgensiAdapter

class ListAgensi : AppCompatActivity() {
    private lateinit var binding: ActivityListAgensiBinding
    private val adapter = ListAgensiAdapter { agensi ->
        // Handle click event on agensi item
        val intent = Intent(this, DetailAgensi::class.java)
        intent.putExtra("AGENSI_DATA", agensi)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAgensiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvAgensi.layoutManager = layoutManager

        // Set adapter for RecyclerView
        binding.rvAgensi.adapter = adapter

        // Set data for adapter (replace with your actual data source)
        adapter.submitList(InstansiDummy.dataInstansi)
    }
}
