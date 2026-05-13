package com.maxi.skycast.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.maxi.skycast.data.local.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Query("""
        SELECT * FROM cities ORDER BY name ASC
    """)
    fun getAllCities(): Flow<List<CityEntity>>

    @Query(
        """
            SELECT * FROM cities
            WHERE id = :id
        """
    )
    suspend fun getCityById(id: Int): CityEntity?

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun addCity(city: CityEntity): Long // returns row id, -1 if ignored

    @Update
    suspend fun updateCity(city: CityEntity)

    @Query("""
        DELETE FROM cities
        WHERE id = :id
    """)
    suspend fun deleteCity(id: Int)
}