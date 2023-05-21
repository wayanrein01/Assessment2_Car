package org.d3if2085.assessment2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.d3if2085.assessment2.database.entity.Car

@Dao
interface CarDao {

    @Query("SELECT * FROM tbl_car ORDER BY id ASC")
    fun getAllCar(): LiveData<List<Car>>

    @Query("SELECT * FROM tbl_car ORDER BY id DESC")
    fun getLastCar(): LiveData<List<Car>>

    @Query("SELECT * FROM tbl_car WHERE id = :id")
    fun getOneCar(id: Long): LiveData<Car>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCar(car: Car)

    @Update
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)
}