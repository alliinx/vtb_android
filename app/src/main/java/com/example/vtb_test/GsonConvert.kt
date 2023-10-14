package com.example.vtb_test

import android.content.Context
import android.content.res.AssetManager
import com.example.vtb_test.data.database.dao.DepartmentDao
import com.example.vtb_test.data.database.entity.DepartmentEntity
import com.example.vtb_test.data.domain.usecase.InsertUseCase
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class GsonConvert (context: Context) {

    val jsonArray = JSONArray(JsonDataFromAsset())

    private fun JsonDataFromAsset(): String {
        var json: String? = null

        val inputStream: InputStream = context.assets.open("offices.json")
        val size: Int = inputStream.available()
        val bufferData = ByteArray(size)
        inputStream.read(bufferData)
        inputStream.close()
        json = String(bufferData,charset("UTF-8"))

        return json
    }

}