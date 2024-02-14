package com.example.cmong_pilates_attendance_project

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.data.UserEntity
import com.example.data.db.UserDao
import com.example.data.db.UserDatabase
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class UserRoomDatabaseTest {
    private lateinit var userDao: UserDao
    // Context를 가져오는 것 때문에 위치를 변경하였습니다.
    
    @Before
    fun setup() {
        val roomdb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = roomdb.userDao()
    }

    @Test
    fun `Room_INSERT_테스트`() {
        val data = UserEntity(
            name = "홍길동",
            phoneNumber = "01012345678",
            duration = "2주",
            startDateTime = Calendar.getInstance().timeInMillis,
            endDateTime = Calendar.getInstance().timeInMillis + 1000,
        )
        userDao.insertUser(data).test().assertComplete()

        val test = userDao.selectUsers().test()
        test.assertValueCount(1)
        test.assertValue { it[0].name == data.name }
    }

    @Test
    fun `Room_UPDATE_테스트`() {
        val beforeData = UserEntity(
            name = "홍길동",
            phoneNumber = "01012345678",
            duration = "2주",
            startDateTime = Calendar.getInstance().timeInMillis,
            endDateTime = Calendar.getInstance().timeInMillis + 1000,
        )
        userDao.insertUser(beforeData).test().assertComplete()


        val data = UserEntity(
            name = "홍길동2",
            phoneNumber = "01012341234",
            duration = "4주",
            startDateTime = Calendar.getInstance().timeInMillis,
            endDateTime = Calendar.getInstance().timeInMillis + 1000,
        )
        userDao.updateUser(data).test().assertComplete()

        val test = userDao.selectUsers().test()

        val result = userDao.selectUsers().blockingGet()
        Log.d("data", "result => ${result[0].name}")

//        test.assertValue { it[0].name == data.name }
    }
}