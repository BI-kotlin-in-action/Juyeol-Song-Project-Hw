package com.example.lottoweb.utils.jsonbind

import com.example.lottoweb.domain.LottoNumbers
import com.example.lottoweb.domain.LottoNumbers.Companion.NUM_OF_LOTTO_NUMBERS
import com.example.lottoweb.domain.LottoNumbers.Companion.numbersOneToFortyFive
import com.example.lottoweb.exception.InvalidLottoNumbersException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

/**
 * @author Unagi_zoso
 * @since 2023-12-28
 */
class LottoNumbersListDeserializer : JsonDeserializer<LottoNumbers>() {
    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): LottoNumbers {
        val node: JsonNode = parser.codec.readTree(parser)
        val lottoNumbersAsString = node.asText()
        return LottoNumbers.from(validate(lottoNumbersAsString))
    }

    private fun validate(value: String) : String {
        val numbers = value.split(" ").map { it.toInt() }
        if (numbers.size != NUM_OF_LOTTO_NUMBERS || // 기입된 번호가 6자리인지 검사
            numbers.distinct().size != NUM_OF_LOTTO_NUMBERS || // 중복된 번호가 있는지 검사
            numbers.any { it !in numbersOneToFortyFive } // 1~45 사이의 숫자인지 검사
        ) {
            throw InvalidLottoNumbersException()
        }
        return value
    }
}
