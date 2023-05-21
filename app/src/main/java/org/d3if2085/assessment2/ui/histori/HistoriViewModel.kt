package org.d3if2085.assessment2.ui.histori

import androidx.lifecycle.ViewModel
import org.d3if2085.assessment2.database.dao.CarDao

class HistoriViewModel(db: CarDao) : ViewModel() {
    val data = db.getLastCar()
}