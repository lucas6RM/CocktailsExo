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
import com.mercierlucas.cocktailsexo.data.local.model.DrinkLiteModel
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
        })

        with(binding){
            rvDrinks.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = drinkAdapter
            }

            btnNewCocktail.setOnClickListener {
                // nav to Create
            }
        }


        mainViewModel.apply {
            messageFromGetAllDrinksResponse.observe(viewLifecycleOwner){
               /* when(it){
                    "ok"           -> Log.i(ContentValues.TAG, getString(R.string.response_ok))
                    "unauthorized" -> Log.i(ContentValues.TAG, getString(R.string.unauthorized))
                    "error_param"  -> Log.i(ContentValues.TAG, getString(R.string.error_param))
                    else           -> Log.i(ContentValues.TAG, getString(R.string.error_connection_db))
                }
                */

            }


        }

        mainViewModel.drinkDetailsRoomListLiveData.observe(viewLifecycleOwner){
            Log.i("lucas_detailed", it.toString())

            val listeRoom = it.map { drinkDetailed ->
                DrinkLiteModel(
                    idDrink = drinkDetailed.idDrink.toString(),
                    strDrink = drinkDetailed.strDrink,
                    strDrinkThumb = drinkDetailed.strDrinkThumb,
                    isMine = drinkDetailed.isMine)
            }
            Log.i("lucas_entity", listeRoom.toString())

        }

        mainViewModel.drinkLiteEntityListLiveData.observe(viewLifecycleOwner){
            Log.i("lucas_entity", it.toString())


        }

    }






}