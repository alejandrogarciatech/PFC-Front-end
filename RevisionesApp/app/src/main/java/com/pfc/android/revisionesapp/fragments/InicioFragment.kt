package com.pfc.android.revisionesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        val navController = findNavController()

        binding.btnEquipos.setOnClickListener {
                navController.navigate(R.id.action_inicioFragment_to_equiposFragment)
            (activity as MainActivity).binding.bottomNavigation.selectedItemId =
                (activity as MainActivity).binding.bottomNavigation.menu.findItem(R.id.equiposFragment).itemId
        }

        binding.btnIncidencias.setOnClickListener {
            navController.navigate(R.id.action_inicioFragment_to_incidenciasFragment)
            (activity as MainActivity).binding.bottomNavigation.selectedItemId =
                (activity as MainActivity).binding.bottomNavigation.menu.findItem(R.id.incidenciasFragment).itemId
        }

        binding.btnAlbaranes.setOnClickListener {
            navController.navigate(R.id.action_inicioFragment_to_albaranesFragment)
            (activity as MainActivity).binding.bottomNavigation.selectedItemId =
                (activity as MainActivity).binding.bottomNavigation.menu.findItem(R.id.albaranesFragment).itemId
        }

        binding.btnEspacios.setOnClickListener {
            navController.navigate(R.id.action_inicioFragment_to_espaciosFragment)
            (activity as MainActivity).binding.bottomNavigation.selectedItemId =
                (activity as MainActivity).binding.bottomNavigation.menu.findItem(R.id.espaciosFragment).itemId
        }

    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}