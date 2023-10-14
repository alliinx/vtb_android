package com.example.vtb_test.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banks_departments")
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = true) val departmentId: Long,
    val address: String,
    val salePointName: String,
    val openHours: List<String>,
    val openHoursIndividual: List<String>,
    val hasRamp: String,
    val latitude: Float,
    val longitude: Float,
    val metroStation: String,
    )