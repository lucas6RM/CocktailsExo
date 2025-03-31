package com.mercierlucas.cocktailsexo.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface DrinkDetailsDao {
    @Query("SELECT * FROM drink_details_room")
    suspend fun getAll(): List<DrinkDetailsRoom>

    @Query("SELECT * FROM drink_details_room WHERE idDrink LIKE :idDrink LIMIT 1")
    suspend fun findByIdDrink(idDrink: Long) : DrinkDetailsRoom

    @Update
    suspend fun update(drinkDetailsRoom: DrinkDetailsRoom)

    @Insert
    suspend fun insert(drinkDetailsRoom: DrinkDetailsRoom)

    @Delete
    suspend fun delete(drinkDetailsRoom: DrinkDetailsRoom)

    @Query("DELETE FROM drink_details_room WHERE idDrink = :idDrink")
    suspend fun deleteById(idDrink: Long)

}