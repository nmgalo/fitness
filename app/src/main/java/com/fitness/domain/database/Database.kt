package com.fitness.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fitness.domain.database.daos.LocationTrackDao
import com.fitness.domain.database.models.LocationTrackModel

@Database(
    entities = [
        LocationTrackModel::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun getLocationTrackDao(): LocationTrackDao
}