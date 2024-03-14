package com.example.gps_kotlin.history


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gps_kotlin.databinding.FragmentHistoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var myApi: MyApi
    private lateinit var adapter: MyDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MyDataAdapter(emptyList())
        binding.RecycleID.layoutManager = LinearLayoutManager(requireContext())
        binding.RecycleID.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://smart-toll.onrender.com/") // mool ninna base url padre, excluding apaga MyAPi padhdina endpoint
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        myApi = retrofit.create(MyApi::class.java)

        fetchDataFromMongoDB()
    }

    private fun fetchDataFromMongoDB() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = myApi.getData()
                // Update the adapter's data with the fetched data on the main thread
                launch(Dispatchers.Main) {
                    adapter.updateData(data)
                }
            } catch (e: Exception) {
                // Handle errors
                e.printStackTrace()
            }
        }
    }
}
