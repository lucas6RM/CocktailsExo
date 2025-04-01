package com.mercierlucas.cocktailsexo.ui.main

import android.content.ContentValues
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mercierlucas.cocktailsexo.R
import com.mercierlucas.cocktailsexo.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding : FragmentMainBinding? = null
    private val binding : FragmentMainBinding
        get () = _binding!!

    private lateinit var navController: NavController
    private lateinit var navDir: NavDirections

    private lateinit var drinkAdapter : DrinkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        drinkAdapter = DrinkAdapter(onDrinkClicked = { idDrink,isMine ->
            mainViewModel.clickOnDrinkIsDone(idDrink,isMine)
            navDir = MainFragmentDirections
                .actionMainFragmentToDetailsDrinkFragment(idDrink,isMine)
            navController.navigate(navDir)
        })

        with(binding){
            rvDrinks.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = drinkAdapter
            }

            btnNewCocktail.setOnClickListener {
                mainViewModel.refreshFullDrinkList()
                navController.navigate(R.id.action_mainFragment_to_createDrinkFragment)
            }
        }


        mainViewModel.apply {
            messageFromGetAllDrinksResponse.observe(viewLifecycleOwner){
                when(it){
                    200    -> Log.i(ContentValues.TAG, getString(R.string.response_ok))
                    403    -> Log.i(ContentValues.TAG, getString(R.string.error_param))
                    404    -> Log.i(ContentValues.TAG, getString(R.string.unauthorized))
                    500    -> Log.i(ContentValues.TAG, getString(R.string.server_error))
                    else   -> Log.i(ContentValues.TAG, getString(R.string.error_connection_db))
                }
            }


            allDrinksLiveData.observe(viewLifecycleOwner){
                drinkAdapter.submitList(it){
                    binding.rvDrinks.scrollToPosition(0)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(mainViewModel){
            getAllDrinksDetailedRoom()
            getRemoteDrinks()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}