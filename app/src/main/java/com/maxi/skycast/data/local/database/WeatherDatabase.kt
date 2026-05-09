package com.maxi.skycast.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maxi.skycast.data.local.dao.CityDao
import com.maxi.skycast.data.local.entity.CityEntity

@Database(
    entities = [CityEntity::class],
    version = 1,
    exportSchema = true
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
}