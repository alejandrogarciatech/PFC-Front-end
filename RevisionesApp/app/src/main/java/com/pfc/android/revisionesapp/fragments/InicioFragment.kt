package com.pfc.android.revisionesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.activities.MainActivity
import com.pfc.android.revisionesapp.databinding.FragmentInicioBinding

class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEquipos.setOnClickListener {
            val equiposFragment = EquiposFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, equiposFragment)
                .commit()
            (activity as MainActivity).binding.bottomNavigation.selectedItemId =
                (activity as MainActivity).binding.bottomNavigation.menu.findItem(R.id.navigation_equipos).itemId
        }

        binding.btnIncidencias.setOnClickListener {
            val incidenciasFragment = IncidenciasFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, incidenciasFragment)
                .commit()
            (activity as MainActivity).binding.bottomNavigation.selectedItemId =
                (activity as MainActivity).binding.bottomNavigation.menu.findItem(R.id.navigation_incidencias).itemId
        }

        binding.btnAlbaranes.setOnClickListener {
            val albaranesFragment = AlbaranesFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, albaranesFragment)
                .commit()
            (activity as MainActivity).binding.bottomNavigation.selectedItemId =
                (activity as MainActivity).binding.bottomNavigation.menu.findItem(R.id.navigation_albaranes).itemId
        }

        binding.btnEspacios.setOnClickListener {
            val espaciosFragment = EspaciosFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, espaciosFragment)
                .commit()
            (activity as MainActivity).binding.bottomNavigation.selectedItemId =
                (activity as MainActivity).binding.bottomNavigation.menu.findItem(R.id.navigation_espacios).itemId
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}