
package com.mercierlucas.cocktailsexo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsDao
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom


@Database(entities = [DrinkDetailsRoom::class], version = 3, exportSchema = false)
    abstract class AppDatabase: RoomDatabase(){
        abstract fun drinkDetailsDao(): DrinkDetailsDao
    }
