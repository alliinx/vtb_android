package com.example.vtb_test.data.domain.usecase

import com.example.vtb_test.data.database.dao.DepartmentDao
import com.example.vtb_test.data.database.entity.DepartmentEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class InsertUseCase (private val departmentDao: DepartmentDao,
                     private val dispatcher: CoroutineDispatcher
){
    suspend operator fun invoke(departmentEntity: DepartmentEntity) {
        return withContext(dispatcher) {
            departmentDao.insertDepartment(departmentEntity)
        }
    }

}