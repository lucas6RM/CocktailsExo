package com.mercierlucas.cocktailsexo.ui.create

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.mercierlucas.cocktailsexo.R
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom
import com.mercierlucas.cocktailsexo.databinding.FragmentCreateDrinkBinding
import com.mercierlucas.cocktailsexo.databinding.FragmentMainBinding
import com.mercierlucas.feedarticles.Utils.showToast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateDrinkFragment : Fragment() {

    private val createDrinkViewModel: CreateDrinkViewModel by viewModels()

    private var _binding : FragmentCreateDrinkBinding? = null
    private val binding : FragmentCreateDrinkBinding
        get () = _binding!!

    private lateinit var navController: NavController
    private lateinit var navDir: NavDirections

    private lateinit var cocktailName : String
    private lateinit var imageUrl : String
    private lateinit var ingredients : String
    private lateinit var instructions : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateDrinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        createDrinkViewModel.isNewDrinkCreated.observe(viewLifecycleOwner){
            if (it){
                navController.popBackStack()
            }
        }

        with(binding){
            btnCreateCocktail.setOnClickListener {
                cocktailName = etFragmentCreateCocktailName.text.toString().trim()
                imageUrl = etFragmentCreateCocktailUrlImg.text.toString().trim()
                ingredients = etFragmentCreateIngredients.text.toString().trim()
                instructions = etFragmentCreateInstructions.text.toString().trim()

                if (cocktailName.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty())
                    createDrinkViewModel.insertDrinkDetailedRoom(
                        DrinkDetailsRoom(
                            idDrink = 0,
                            strDrink = cocktailName,
                            strDrinkThumb = imageUrl,
                            strIngredients = ingredients,
                            strInstructions = instructions
                        )
                    )
                else
                    context?.showToast(getString(R.string.empty_required_fields))
            }

            etFragmentCreateCocktailUrlImg.setOnFocusChangeListener{ _,_ ->
                imageUrl = etFragmentCreateCocktailUrlImg.text.toString().trim()
                if(imageUrl.isNotEmpty())
                    Picasso
                        .get()
                        .load(imageUrl)
                        .placeholder(R.drawable.img)
                        .error(R.drawable.img)
                        .into(ivFragmentCreate)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}