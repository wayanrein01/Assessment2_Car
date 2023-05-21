package org.d3if2085.assessment2.repository

import androidx.lifecycle.LiveData
import org.d3if2085.assessment2.database.dao.CarDao
import org.d3if2085.assessment2.database.entity.Car

class CarRepository(private val dao: CarDao) {

    val getAllCar: LiveData<List<Car>> = dao.getAllCar()

    fun getOneCar(id: Long): LiveData<Car> {
        return dao.getOneCar(id)
    }

    suspend fun insertCar(car: Car) {
        dao.insertCar(car)
    }

    suspend fun updateCar(car: Car) {
        dao.updateCar(car)
    }

    suspend fun deleteCar(car: Car) {
        dao.deleteCar(car)
    }
}