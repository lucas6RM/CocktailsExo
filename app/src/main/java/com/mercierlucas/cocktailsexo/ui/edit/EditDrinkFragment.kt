package com.mercierlucas.cocktailsexo.ui.edit

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mercierlucas.cocktailsexo.R
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom
import com.mercierlucas.cocktailsexo.databinding.FragmentCreateDrinkBinding
import com.mercierlucas.cocktailsexo.databinding.FragmentEditDrinkBinding
import com.mercierlucas.cocktailsexo.ui.details.DetailsDrinkFragmentArgs
import com.mercierlucas.feedarticles.Utils.showToast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDrinkFragment : Fragment() {

    private val editViewModel: EditDrinkViewModel by viewModels()

    private var _binding : FragmentEditDrinkBinding? = null
    private val binding : FragmentEditDrinkBinding
        get () = _binding!!

    private lateinit var navController: NavController
    private lateinit var navDir: NavDirections
    private val args : EditDrinkFragmentArgs by navArgs()

    private var cocktailName = ""
    private var imageUrl = ""
    private var ingredients = ""
    private var instructions = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDrinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        val idDrink = args.idDrink
        editViewModel.apply {
            getDrinkRoomById(idDrink)
            drinkDetailsLiveData.observe(viewLifecycleOwner){ drink ->
                drink?.let {
                    cocktailName = it.strDrink
                    imageUrl = it.strDrinkThumb
                    ingredients = it.strIngredients
                    instructions = it.strInstructions
                }
                with(binding){
                    etFragmentEditCocktailName.setText(cocktailName)
                    etFragmentEditCocktailUrlImg.setText(imageUrl)
                    if(imageUrl.isNotEmpty())
                        Picasso
                            .get()
                            .load(imageUrl)
                            .placeholder(R.drawable.img)
                            .error(R.drawable.img)
                            .into(ivFragmentEdit)

                    etFragmentEditIngredients.setText(ingredients)
                    etFragmentEditInstructions.setText(instructions)
                }
            }
        }

        editViewModel.isDrinkUpdated.observe(viewLifecycleOwner){
            if (it){
                navController.popBackStack()
            }
        }

        editViewModel.isDrinkDeleted.observe(viewLifecycleOwner){
            if (it){
                navController.popBackStack()
            }
        }

        with(binding){
            btnEditCocktail.setOnClickListener {
                cocktailName = etFragmentEditCocktailName.text.toString().trim()
                imageUrl = etFragmentEditCocktailUrlImg.text.toString().trim()
                ingredients = etFragmentEditIngredients.text.toString().trim()
                instructions = etFragmentEditInstructions.text.toString().trim()

                if (cocktailName.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty())
                    editViewModel.updateDrinkRoom(
                        DrinkDetailsRoom(
                            idDrink = idDrink,
                            strDrink = cocktailName,
                            strDrinkThumb = imageUrl,
                            strIngredients = ingredients,
                            strInstructions = instructions,
                            isMine = true
                        )
                    )
                else
                    context?.showToast(getString(R.string.empty_required_fields))
            }

            etFragmentEditCocktailUrlImg.setOnFocusChangeListener{ _,_ ->
                imageUrl = etFragmentEditCocktailUrlImg.text.toString().trim()
                if(imageUrl.isNotEmpty())
                    Picasso
                        .get()
                        .load(imageUrl)
                        .placeholder(R.drawable.img)
                        .error(R.drawable.img)
                        .into(ivFragmentEdit)
            }

            btnDeleteCocktail.setOnClickListener {
                editViewModel.deletedByIdDrinkDetailedRoom(idDrink)
            }
        }








    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}