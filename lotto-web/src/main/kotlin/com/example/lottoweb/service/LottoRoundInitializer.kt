package com.example.lottoweb.service

import com.example.lottoweb.domain.model.LottoRoundControl
import com.example.lottoweb.repository.LottoRoundControlRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

/**
 * @author Unagi_zoso
 * @since 2023-11-28
 */
// 프로그램 실행 시 LottoRoundControl 테이블에 하나의 레코드를 저장한다.
@Component
class LottoRoundInitializer(
    private val lottoRoundControlRepository: LottoRoundControlRepository,

) {
    @PostConstruct
    fun initializeLottoRound() {
        lottoRoundControlRepository.save(LottoRoundControl(round = 1))
    }
}
