package com.fitness.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fitness.domain.database.models.LocationTrackModel

@Dao
interface LocationTrackDao : LocationTrackApi {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun saveOrUpdateData(locationTrackModel: LocationTrackModel)

    @Query("DELETE from location_track")
    override suspend fun clearData()

    @Query("SELECT * from location_track")
    override suspend fun getData(): List<LocationTrackModel>

}