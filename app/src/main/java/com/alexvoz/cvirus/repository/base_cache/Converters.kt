package com.alexvoz.cvirus.repository.base_cache

import android.text.TextUtils
import androidx.room.TypeConverter
import com.alexvoz.cvirus.data.covid_data.network.Country
import com.alexvoz.cvirus.data.covid_data.network.Global
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromGsonToCountries(countriesGson: String): List<Country> {
        if (TextUtils.isEmpty(countriesGson)) {
            return listOf()
        }
        val gson = GsonBuilder().create()
        val type = object : TypeToken<List<Country>>() {}.type
        return gson.fromJson(countriesGson, type)
    }

    @TypeConverter
    fun fromCountriesToGson(countries: List<Country>): String {
        return Gson().toJson(countries)
    }

    @TypeConverter
    fun fromGsonToGlobal(globalGson: String): Global {
        val gson = GsonBuilder().create()
        val type = object : TypeToken<Global>() {}.type
        return gson.fromJson(globalGson, type)
    }

    @TypeConverter
    fun fromGlobalToGson(global: Global): String {
        return Gson().toJson(global)
    }
}