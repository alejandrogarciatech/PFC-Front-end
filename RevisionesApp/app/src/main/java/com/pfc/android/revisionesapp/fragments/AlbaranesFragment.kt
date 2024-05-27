package com.pfc.android.revisionesapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfc.android.revisionesapp.activities.MainActivity
import com.pfc.android.revisionesapp.adapters.AlbaranesAdapter
import com.pfc.android.revisionesapp.databinding.FragmentAlbaranesBinding
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Albaran
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbaranesFragment : Fragment() {

    private lateinit var binding: FragmentAlbaranesBinding
    private lateinit var listaAlbaranes: ArrayList<Albaran>
    private lateinit var albaranesAdapter: AlbaranesAdapter
    private val apiService = RetrofitClient.instance.create(ApiService::class.java)
    private val call = apiService.getAlbaranes()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbaranesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstaceState: Bundle?){
        super.onViewCreated(view, savedInstaceState)

        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.title = "Albaranes"

        albaranesAdapter = AlbaranesAdapter(ArrayList(), requireContext())
        listaAlbaranes = ArrayList()
        albaranesAdapter = AlbaranesAdapter(listaAlbaranes, requireContext())

        binding.albaranesRecyclerView.adapter = albaranesAdapter
        binding.albaranesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        verAlbaranes()
    }

    private fun verAlbaranes(){
        call.enqueue(object : Callback<List<Albaran>> {
            override fun onResponse(call: Call<List<Albaran>>, response: Response<List<Albaran>>) {
                if (response.isSuccessful) {
                    listaAlbaranes = response.body() as ArrayList<Albaran>
                    albaranesAdapter.updateList(listaAlbaranes)
                }
            }
            override fun onFailure(call: Call<List<Albaran>>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        })
    }
}