package com.monty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.monty.data.model.ui.User
import io.reactivex.Flowable

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: User)

    @Query(value = "SELECT * FROM user WHERE user.id = :userId LIMIT 1")
    abstract fun getUserByIdFlowable(userId: String): Flowable<User>
}
