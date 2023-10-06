package cn.yjl.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

inline fun <reified T> GsonBuilder.registerTypeAdapter(
    crossinline serialize: (src: T?, typeOfSrc: Type?, context: JsonSerializationContext?) -> JsonElement
): GsonBuilder =
    registerTypeAdapter(T::class.java, object : JsonSerializer<T> {
        override fun serialize(src: T, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
            serialize(src, typeOfSrc, context)
    })


inline fun <reified T> GsonBuilder.registerTypeAdapter(
    crossinline serialize: (src: T?) -> JsonElement
): GsonBuilder = registerTypeAdapter<T> { src, _, _ ->
    serialize(src)
}
