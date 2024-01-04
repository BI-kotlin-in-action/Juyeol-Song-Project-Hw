package com.example.lottoweb.utils.jsonbind

import com.example.lottoweb.domain.LottoNumbers
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

/**
 * @author Unagi_zoso
 * @since 2024-01-02
 */
class LottoNumbersSerializer : JsonSerializer<LottoNumbers>() {
    override fun serialize(
        lottoNumbers: LottoNumbers,
        gen: JsonGenerator,
        serializers: SerializerProvider,
    ) {
        gen.writeString(lottoNumbers.getNumbersAsString())
    }
}
