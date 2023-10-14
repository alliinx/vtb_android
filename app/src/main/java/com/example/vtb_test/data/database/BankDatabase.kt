package com.example.vtb_test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vtb_test.data.database.converter.StringConverter
import com.example.vtb_test.data.database.dao.DepartmentDao
import com.example.vtb_test.data.database.entity.DepartmentEntity

@Database(
    entities = [DepartmentEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    StringConverter::class
)
abstract class BankDatabase : RoomDatabase() {

    abstract fun departmentDao(): DepartmentDao

//    companion object {
//        private var INSTANCE: BankDatabase? = null
//        private const val DATABASE_NAME = "main.db"
//
//        fun getInstance(application: Application): BankDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(
//                    application,
//                    BankDatabase::class.java,
//                    DATABASE_NAME
//                ).build().also { INSTANCE = it }
//            }
//    }
}