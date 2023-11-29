package com.example.lottoweb.domain

import com.example.lottoweb.service.QueueWorkingCounterService
import org.springframework.context.annotation.Configuration
import java.util.LinkedList

/**
 * @author Unagi_zoso
 * @since 2023-11-24
 */
// 10분을 넘어가는 순간에도 로또를 구매할 수 있게 회차별로 queue를 미리 만들어두어 담을 수 있게 하였습니다.
// 현재 QUEUE_POOL_SIZE는 1000이며 적어도 1000개의 queue를 순회할 동안 가장 먼저 시작한 queue는 새로운 회차의
// 작업을 저장할 때 자신이 쓰던 queue를 비워 막히는 일이 없길 기대하여 선정한 값입니다.
@Configuration
class LottoJobMessageQueuePool(
    queueWorkingCounterService: QueueWorkingCounterService,
) {
    init {
        queueWorkingCounterService.initQueueWorkingFlag(QUEUE_POOL_SIZE)
    }

    companion object {
        @JvmStatic
        val QUEUE_POOL_SIZE = 1000

        @JvmStatic
        val queuePool = List<LinkedList<LottoJobMessage>>(QUEUE_POOL_SIZE) { LinkedList() }

        @JvmStatic
        fun getQueueIndexByRound(round: Int) = (round % QUEUE_POOL_SIZE)

        @JvmStatic
        fun getQueueByRound(round: Int): LinkedList<LottoJobMessage> {
            return queuePool[getQueueIndexByRound(round)]
        }
    }
}