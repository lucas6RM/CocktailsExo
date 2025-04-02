package com.mercierlucas.cocktailsexo.data.network.dtos


import android.util.Log
import com.squareup.moshi.Json
import kotlin.reflect.full.memberProperties


data class DrinkDetailsDto(
    @Json(name = "dateModified")
    val dateModified: String?,
    @Json(name = "idDrink")
    val idDrink: String?,
    @Json(name = "strAlcoholic")
    val strAlcoholic: String?,
    @Json(name = "strCategory")
    val strCategory: String?,
    @Json(name = "strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String?,
    @Json(name = "strDrink")
    val strDrink: String?,
    @Json(name = "strDrinkAlternate")
    val strDrinkAlternate: String?,
    @Json(name = "strDrinkThumb")
    val strDrinkThumb: String?,
    @Json(name = "strGlass")
    val strGlass: String?,
    @Json(name = "strIBA")
    val strIBA: String?,
    @Json(name = "strImageAttribution")
    val strImageAttribution: String?,
    @Json(name = "strImageSource")
    val strImageSource: String?,
    @Json(name = "strIngredient1")
    val strIngredient1: String?,
    @Json(name = "strIngredient10")
    val strIngredient10: String?,
    @Json(name = "strIngredient11")
    val strIngredient11: String?,
    @Json(name = "strIngredient12")
    val strIngredient12: String?,
    @Json(name = "strIngredient13")
    val strIngredient13: String?,
    @Json(name = "strIngredient14")
    val strIngredient14: String?,
    @Json(name = "strIngredient15")
    val strIngredient15: String?,
    @Json(name = "strIngredient2")
    val strIngredient2: String?,
    @Json(name = "strIngredient3")
    val strIngredient3: String?,
    @Json(name = "strIngredient4")
    val strIngredient4: String?,
    @Json(name = "strIngredient5")
    val strIngredient5: String?,
    @Json(name = "strIngredient6")
    val strIngredient6: String?,
    @Json(name = "strIngredient7")
    val strIngredient7: String?,
    @Json(name = "strIngredient8")
    val strIngredient8: String?,
    @Json(name = "strIngredient9")
    val strIngredient9: String?,
    @Json(name = "strInstructions")
    val strInstructions: String?,
    @Json(name = "strInstructionsDE")
    val strInstructionsDE: String?,
    @Json(name = "strInstructionsES")
    val strInstructionsES: String?,
    @Json(name = "strInstructionsFR")
    val strInstructionsFR: String?,
    @Json(name = "strInstructionsIT")
    val strInstructionsIT: String?,
    @Json(name = "strInstructionsZH-HANS")
    val strInstructionsZHHANS: String?,
    @Json(name = "strInstructionsZH-HANT")
    val strInstructionsZHHANT: String?,
    @Json(name = "strMeasure1")
    val strMeasure1: String?,
    @Json(name = "strMeasure10")
    val strMeasure10: String?,
    @Json(name = "strMeasure11")
    val strMeasure11: String?,
    @Json(name = "strMeasure12")
    val strMeasure12: String?,
    @Json(name = "strMeasure13")
    val strMeasure13: String?,
    @Json(name = "strMeasure14")
    val strMeasure14: String?,
    @Json(name = "strMeasure15")
    val strMeasure15: String?,
    @Json(name = "strMeasure2")
    val strMeasure2: String?,
    @Json(name = "strMeasure3")
    val strMeasure3: String?,
    @Json(name = "strMeasure4")
    val strMeasure4: String?,
    @Json(name = "strMeasure5")
    val strMeasure5: String?,
    @Json(name = "strMeasure6")
    val strMeasure6: String?,
    @Json(name = "strMeasure7")
    val strMeasure7: String?,
    @Json(name = "strMeasure8")
    val strMeasure8: String?,
    @Json(name = "strMeasure9")
    val strMeasure9: String?,
    @Json(name = "strTags")
    val strTags: String?,
    @Json(name = "strVideo")
    val strVideo: String?,
){
    val ingredients :String
        get() {

            val pairs = listOf(
                strIngredient1 to strMeasure1,
                strIngredient2 to strMeasure2,
                strIngredient3 to strMeasure3,
                strIngredient4 to strMeasure4,
                strIngredient5 to strMeasure5,
                strIngredient6 to strMeasure6,
                strIngredient7 to strMeasure7,
                strIngredient8 to strMeasure8,
                strIngredient9 to strMeasure9,
                strIngredient10 to strMeasure10,
                strIngredient11 to strMeasure11,
                strIngredient12 to strMeasure12,
                strIngredient13 to strMeasure13,
                strIngredient14 to strMeasure14,
                strIngredient15 to strMeasure15
            )

            var strConcat = ""
            pairs.forEach { (ingredient, measure) ->
                if (!ingredient.isNullOrBlank()) {
                    strConcat += "$ingredient (${measure ?: ""})\n"
                }
            }
            return strConcat


            /*----- Reflection -----

            var strConcat = ""

            for (i in 1..15){
                val ingredient = this::class.memberProperties
                    .firstOrNull { it.name == "strIngredient$i" }
                    ?.call(this) as? String

                val measure = this::class.memberProperties
                    .firstOrNull { it.name == "strMeasure$i" }
                    ?.call(this) as? String

                if (!ingredient.isNullOrBlank()) {
                    strConcat += "$ingredient (${measure ?: ""})\n"
                }
            }

            return strConcat
*/

            /* ------ brute force ------
            var strConcat = ""

            strConcat += "${strIngredient1 ?: ""} (${strMeasure1 ?: ""})" +
            if(strIngredient1 != null) "\n" else ""
            strConcat += "${strIngredient2 ?: ""} (${strMeasure2 ?: ""})" +
                    if(strIngredient2 != null) "\n" else ""
            strConcat += "${strIngredient3 ?: ""} (${strMeasure3 ?: ""})" +
                    if(strIngredient3 != null) "\n" else ""
            strConcat += "${strIngredient4 ?: ""} (${strMeasure4 ?: ""})" +
                    if(strIngredient4 != null) "\n" else ""
            strConcat += "${strIngredient5 ?: ""} (${strMeasure5 ?: ""})" +
                    if(strIngredient5 != null) "\n" else ""
            strConcat += "${strIngredient6 ?: ""} (${strMeasure6 ?: ""})" +
                    if(strIngredient6 != null) "\n" else ""
            strConcat += "${strIngredient7 ?: ""} (${strMeasure7 ?: ""})" +
                    if(strIngredient7 != null) "\n" else ""
            strConcat += "${strIngredient8 ?: ""} (${strMeasure8 ?: ""})" +
                    if(strIngredient8 != null) "\n" else ""
            strConcat += "${strIngredient9 ?: ""} (${strMeasure9 ?: ""})" +
                    if(strIngredient9 != null) "\n" else ""
            strConcat += "${strIngredient10 ?: ""} (${strMeasure10 ?: ""})" +
                    if(strIngredient10 != null) "\n" else ""
            strConcat += "${strIngredient11 ?: ""} (${strMeasure11 ?: ""})" +
                    if(strIngredient11 != null) "\n" else ""
            strConcat += "${strIngredient12 ?: ""} (${strMeasure12 ?: ""})" +
                    if(strIngredient12 != null) "\n" else ""
            strConcat += "${strIngredient13 ?: ""} (${strMeasure13 ?: ""})" +
                    if(strIngredient13 != null) "\n" else ""
            strConcat += "${strIngredient14 ?: ""} (${strMeasure14 ?: ""})" +
                    if(strIngredient14 != null) "\n" else ""
            strConcat += "${strIngredient15 ?: ""} (${strMeasure15 ?: ""})" +
                    if(strIngredient15 != null) "\n" else ""

            return strConcat.replace("()","")
            */

        }
}