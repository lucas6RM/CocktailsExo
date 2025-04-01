package com.mercierlucas.cocktailsexo.ui.details

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mercierlucas.cocktailsexo.R
import com.mercierlucas.cocktailsexo.databinding.FragmentDetailsDrinkBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsDrinkFragment : Fragment() {

    private val detailsDrinkViewModel: DetailsDrinkViewModel by viewModels()

    private var _binding : FragmentDetailsDrinkBinding? = null
    private val binding : FragmentDetailsDrinkBinding
        get () = _binding!!

    private lateinit var navController: NavController
    private val args : DetailsDrinkFragmentArgs by navArgs()
    private lateinit var navDir: NavDirections

    private var cocktailName = ""
    private var imageUrl = ""
    private var ingredients = ""
    private var instructions = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentDetailsDrinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        val idDrink = args.idDrink
        val isMine = args.isMine

        detailsDrinkViewModel.drinkDetailsLiveData.observe(viewLifecycleOwner){ drink ->
            drink?.let {
                cocktailName = it.strDrink
                imageUrl = it.strDrinkThumb
                ingredients = it.strIngredients
                instructions = it.strInstructions
            }

            with(binding){
                tvFragmentDetailsTitle.text = cocktailName
                if(imageUrl.isNotEmpty())
                    Picasso
                        .get()
                        .load(imageUrl)
                        .placeholder(R.drawable.img)
                        .error(R.drawable.img)
                        .into(ivFragmentDetails)

                tvIngredientsList.text = ingredients
                tvInstructionsList.text = instructions
            }
        }

        with(binding){

            if(!isMine){
                btnGoEditCocktail.visibility = GONE
                detailsDrinkViewModel.getDrinkRemoteById(idDrink.toLong())
            }
            else{
                detailsDrinkViewModel.getDrinkRoomById(idDrink.toLong())
            }

            btnGoEditCocktail.setOnClickListener {
                navDir = DetailsDrinkFragmentDirections
                    .actionDetailsDrinkFragmentToEditDrinkFragment(idDrink.toLong())
                navController.navigate(navDir)
            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}