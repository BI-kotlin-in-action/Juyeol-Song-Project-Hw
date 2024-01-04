package com.example.lottoweb.utils.converter

import jakarta.persistence.AttributeConverter
import java.util.SortedSet

class LottoNumbersConverter : AttributeConverter<SortedSet<Int>, String> {
    override fun convertToDatabaseColumn(attribute: SortedSet<Int>) : String {
        return attribute.joinToString(separator = " ")
    }

    override fun convertToEntityAttribute(dbData: String): SortedSet<Int> {
        return dbData.split(" ").map { it.toInt() }.toSortedSet()
    }
}
