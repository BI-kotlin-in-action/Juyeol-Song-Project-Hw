package com.example.lottoweb.service

import com.example.lottoweb.domain.LottoJobQueuePool.Companion.getQueueByRound
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */

// 10 분을 넘기게 되면 당첨번호가 생성되고 당첨결과를 처리하게 됩니다. 이 때
// 10 분을 넘기게 된 순간에도 해당 회차에 로또를 생성하는 작업이 진행 중일 경우
// 먼저 당첨결과를 다 구해버릴 경우 해당 값이 유실되어버리진 않을까 싶어
// 당첨결과를 처리하기 전에 해당 회차의 로또 생성 작업 수가 0이 될 때까지 waiting 하게 두었습니다.
/**
 * 이 방식도 10 분을 넘어갈 때 한 번에 많은 로또 구매 요청이 들어오면 시간이 넘어가도 대기를 하게 되어
 * 결과를 늦게 반환하거나 정말 말도 안되게 많은 요청을 받아 이후 회차에도 영향을 줄 수 있는 설계는 아닐까 고민을 했습니다.
 */
@EnableScheduling // 스케쥴링 위한 쓰레드가 새로 생성됨.
@Service
class LottoSchedulerService(
    private val winningRecordService: WinningRecordService,
    private val resultRecordService: ResultRecordService,
    private val queueWorkingCounterService: QueueWorkingCounterService,
    private val lottoRoundControlService: LottoRoundControlService,
) {
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Scheduled(fixedDelayString = "\${scheduledTask.fixedDelay}", initialDelayString = "\${scheduledTask.initialDelay}")
    fun scheduledFunction() {
        // 현재 회차를 캡쳐하자마자 다음 회차로 증가시킴
        val capturedCurrentRound = lottoRoundControlService.getLottoRound()
        lottoRoundControlService.increaseRound()

        val winningRecordThisRound = winningRecordService.generateNewWinningRecord(capturedCurrentRound)

        // 이번 회차의 모든 로또 구매 작업이 끝날 때까지 대기
        while (queueWorkingCounterService.isNotCounterZero(capturedCurrentRound)) {}

        // 이번 회차 queue 에 있는 모든 작업을 처리
        for (lottoJob in getQueueByRound(capturedCurrentRound)) {
            resultRecordService.processResultRecords(lottoJob, winningRecordThisRound)
        }
    }
}
