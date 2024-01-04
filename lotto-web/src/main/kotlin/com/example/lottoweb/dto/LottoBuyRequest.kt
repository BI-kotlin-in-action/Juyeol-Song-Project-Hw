package com.example.lottoweb.dto

import com.example.lottoweb.domain.LottoNumbersList
import com.example.lottoweb.utils.jsonbind.LottoNumbersListDeserializer
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import jakarta.validation.constraints.PositiveOrZero

// 사용자가 로또를 구매할 때 요청하는 DTO
data class LottoBuyRequest(
    @field:PositiveOrZero
    val numOfManual: Int,
    @field:JsonDeserialize(contentUsing = LottoNumbersListDeserializer::class)
    val manualLottoNumbers: LottoNumbersList,
    @field:PositiveOrZero
    val numOfAuto: Int,
) {
    // 기존엔 DTO에서 manualLottoNumbers를 문자열 리스트로 받았는데 역직렬화하는 단계에서 LottoNumbersList로 받는 것이 더 좋아보여서 변경했습니다.
    // manualLottoNumbers를 커스텀 deserializer를 통해 역직렬화를하는데 기본 생성자를 만들지 않으면
    // com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of .. 같은 에러가 발생합니다.
    // ObjectMapper deserializer 내부 동작을 깊이 공부해보려고 시간을 꽤 투자해도 방법이 안나오니 막막하네요.
    // 참고해봤던 사항은 ObjectMapper에 KotlinModule을 등록하거나 ObjectMapper의 큰 로직을 공부하는 정도입니다.
    // 왜 현재의 방식에선 기본 생성자가 필요한 지 이유를 알 수 있을까요?
    @JsonCreator
    constructor() : this(0, LottoNumbersList.from(listOf()), 0)
}
