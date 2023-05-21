package org.d3if2085.assessment2.repository
import androidx.lifecycle.LiveData
import org.d3if2085.assessment2.database.dao.UserDao
import org.d3if2085.assessment2.database.entity.User

class UserRepository(private val dao: UserDao) {

    val getAllUser: LiveData<List<User>> = dao.getAllUser()

    fun getOneUser(id: Long): LiveData<User> {
        return dao.getOneUser(id)
    }

    fun getUserByUsername(username: String): LiveData<User> {
        return dao.getUserByUsername(username)
    }

    suspend fun insertUser(user: User) {
        dao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        dao.updateUser(user)
    }

}