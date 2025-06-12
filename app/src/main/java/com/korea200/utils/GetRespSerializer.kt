package com.korea200.utils

import com.korea200.data.GetResp
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive


class GetRespSerializer<T>(
    private val valueSerializer: KSerializer<T> //The serializer of the 'value' param — expecting only Kword || DatabaseDoc
) : KSerializer<GetResp<T>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("GetResp")

    override fun deserialize(decoder: Decoder): GetResp<T> {
        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("GetRespSerializer works only with JsonDecoder")
        val element = jsonDecoder.decodeJsonElement()//Expect a str || List<Kword>

        return when(element) {
            is JsonPrimitive -> {
                if (element.isString)
                    GetResp.StrMssg(element.content)
                else
                    throw SerializationException("Expected a JSON string primitive for StrMssg, but got a number, boolean, or null: $element")
            }
            is JsonArray -> {
                val list = jsonDecoder.json.decodeFromJsonElement(ListSerializer(valueSerializer), element)
                GetResp.ListMssg(list)
            }
            else ->
                throw SerializationException("Invalid JSON type: $element")
        }
    }

    override fun serialize(encoder: Encoder, value: GetResp<T>) {
        val jsonEncoder = encoder as? JsonEncoder ?: throw SerializationException("GetRespSerializer works only with JsonEncoder.")
        val outputElement: JsonElement = when (value) {
            is GetResp.StrMssg -> jsonEncoder.json.encodeToJsonElement(String.serializer(), value.value)
            is GetResp.ListMssg -> jsonEncoder.json.encodeToJsonElement(ListSerializer(valueSerializer), value.value)
        }

        jsonEncoder.encodeJsonElement(outputElement)
    }

}