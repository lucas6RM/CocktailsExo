package com.mercierlucas.cocktailsexo.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mercierlucas.cocktailsexo.R
import com.mercierlucas.cocktailsexo.data.local.model.DrinkLiteEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.drinkDetailsRoomListLiveData.observe(viewLifecycleOwner){
            Log.i("lucas_detailed", it.toString())

            val listeRoom = it.map { drinkDetailed ->
                DrinkLiteEntity(
                    idDrink = drinkDetailed.idDrink.toString(),
                    strDrink = drinkDetailed.strDrink,
                    strDrinkThumb = drinkDetailed.strDrinkThumb,
                    isMine = drinkDetailed.isMine)
            }
            Log.i("lucas_entity", listeRoom.toString())

        }

        viewModel.drinkLiteEntityListLiveData.observe(viewLifecycleOwner){
            Log.i("lucas_entity", it.toString())


        }


        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}