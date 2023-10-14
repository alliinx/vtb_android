package com.example.vtb_test.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vtb_test.data.database.entity.DepartmentEntity

@Dao
interface DepartmentDao {

    @Insert
    fun insertDepartment(data: DepartmentEntity)

    @Query("SELECT * FROM banks_departments WHERE departmentId=:departmentId")
    fun getDepartmentById(departmentId: Int): DepartmentEntity

    @Query("SELECT * FROM banks_departments")
    fun getAllDepartment(): List<DepartmentEntity>
}