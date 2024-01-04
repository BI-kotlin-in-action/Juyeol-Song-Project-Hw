
# Lotto-Web Docs

로또 웹서비스 주요 기능 설명

<br>

### 회차 관리

서비스 기능 상 10분 주기로 로또 회차가 바뀌고 그 회차의 당첨번호가 나오며 그 회차에 구매된 로또의 결과가 나온다.
그래서 **가장 중요한 요소**는 **10분 주기 스케쥴링**과 **회차를 관리**하는 것 이다.

실제 코드에서는 LottoRoundControl이라는 엔티티 객체를 통해 관리된다.  

```
// LottoRoundControl.kt

@Entity(name = "lotto_round_control")
data class LottoRoundControl(
    @Id
    val lottoRoundControlId: Long = 0L,
    var round: Int = 1,
) {
    fun increaseRound() {
        round++
    }
}
```

lotto_round_control 테이블에는 단 하나의 레코드만 존재하며 이는 프로그램 시작 시 data.sql에 의해서 생성된다.

```
// data.sql
// IGNORE를 사용하여 매번 다시 실행하여도 중복되는 레코드가 존재할 시 무시하도록 한다.
INSERT IGNORE INTO  lotto_round_control (lotto_round_control_id, round) values (0, 1);
```     

그리고 LottoRoundControlRepository, LottoRoundControlService를 통해 사용된다.

<br>

### 10분 주기 스케쥴링

스케쥴링 기능 자체는 스프링 프레임워크에서 제공해주는 스프링 스케쥴링을 통해서 간단히 구현할 수 있다.
중요한 것은 이 내부에서 어떤 일이 일어나는지와 규정하기 애매한 문제들이다.

스케쥴링 시간은 각 환경 application.yml에 정의하였다.

```
// application-dev.yml

# 개발 환경 스케쥴 시간 설정 (1분)
scheduledTask:
fixedDelay: 60000
initialDelay: 60000

// application-prod.yml

# 운영 환경 스케쥴 시간 설정 (10분)
scheduledTask:
fixedDelay: 600000
initialDelay: 600000

```
LottoSchedulerService에서 코드를 확인할 수 있다.

```
// LottoSchedulerService.kt

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
```

<br>

### QueueWorkingCounter

이 객체는 10분 주기를 넘었다해서 바로 당첨결과를 처리하지 않고 당첨번호가 나온 회차에서 아직 생성 중인 쓰레드가 있다면 이 작업을 완료할 때까지 당첨결과처리를 지연하기 위해 존재한다. 

LottoJobQueuePool의 수만큼(100) 존재하며 초기화는 프로그램 시작 시 data.sql으로 생성된다.

```
// data.sql

SET autocommit=0;
START TRANSACTION;
INSERT IGNORE INTO queue_working_counter (queue_round, counter) VALUES (0, 0), (1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0), (7, 0), (8, 0), (9, 0), (10, 0), (11, 0), (12, 0), (13, 0), (14, 0), (15, 0), (16, 0), (17, 0), (18, 0), (19, 0), (20, 0), (21, 0), (22, 0), (23, 0), (24, 0), (25, 0), (26, 0), (27, 0), (28, 0), (29, 0), (30, 0), (31, 0), (32, 0), (33, 0), (34, 0), (35, 0), (36, 0), (37, 0), (38, 0), (39, 0), (40, 0), (41, 0), (42, 0), (43, 0), (44, 0), (45, 0), (46, 0), (47, 0), (48, 0), (49, 0), (50, 0), (51, 0), (52, 0), (53, 0), (54, 0), (55, 0), (56, 0), (57, 0), (58, 0), (59, 0), (60, 0), (61, 0), (62, 0), (63, 0), (64, 0), (65, 0), (66, 0), (67, 0), (68, 0), (69, 0), (70, 0), (71, 0), (72, 0), (73, 0), (74, 0), (75, 0), (76, 0), (77, 0), (78, 0), (79, 0), (80, 0), (81, 0), (82, 0), (83, 0), (84, 0), (85, 0), (86, 0), (87, 0), (88, 0), (89, 0), (90, 0), (91, 0), (92, 0), (93, 0), (94, 0), (95, 0), (96, 0), (97, 0), (98, 0), (99, 0);
COMMIT;
```

```
// LottoBuyService.kt 중 일부

@Transactional(isolation = Isolation.READ_COMMITTED)
fun processLottoBuyRequest(lottoBuyRequest: LottoBuyRequest, username: String): BoughtLottosResponse {
    // 로또 생성 시 10분이 지나더라도 생성 진입 시 잡아둔 round를 사용한다.
    // ex ) 0시 10분에 당첨번호 공개 시 0시 9분 58초에 구매하여
    // 현재 회차를 캡쳐한다면 당첨번호가 공개되었다 해도 구매한 로또는 전부 그 회차의 로또가 됩니다.
    val capturedCurrentRound = lottoRoundControlService.getLottoRound()
    queueWorkingCounterService.increaseCounter(capturedCurrentRound)
    // 이번 회차에 생성 작업하고 있음을 DB에 기록한다.

    val user = userService.findUserByUsername(username)
    payAllCost(user, lottoBuyRequest.numOfManual, lottoBuyRequest.numOfAuto)
    val response = makeLottos(capturedCurrentRound, user, lottoBuyRequest, username)
    queueWorkingCounterService.decreaseCounter(capturedCurrentRound)
    return response
}
```

로또를 구매하게 되면 가장 먼저 해당 회차의 queue에서 작업하고 있음을 나타내기 위해 해당 회차의 queueWorkingCounter의 값을 증가시키고 구매 로직이 끝나면 값을 다시 내린다. 

queue를 100개에서 돌려사용하기에 회차가 정수 1씩 상승하는 점을 이용해 나머지 연산을 통해서 queue의 인덱스에 접근합니다. 

```
// QueueWorkingCounterService.kt

@Transactional
fun increaseCounter(queueRound: Int) {
    queueWorkingCounterRepository.findByQueueRound(getQueueIndexByRound(queueRound))
        .increaseCounter()
}

// LottoJobQueuePool.kt

@JvmStatic  
fun getQueueIndexByRound(round: Int) = (round % QUEUE_POOL_SIZE) // 나머지 연산 사용
  
@JvmStatic  
fun getQueueByRound(round: Int): LinkedList<LottoJobMessage> {  
    return queuePool[getQueueIndexByRound(round)]  
}
```


<br>

### LottoJobQueuePool

queuePool을 private으로 가진다.  getQueueByRound()를 통해 현재 회차 queue를 참조할 수 있다.
```
@Configuration
class LottoJobQueuePool {
    companion object {
        @JvmStatic
        val QUEUE_POOL_SIZE = 100

        private val queuePool = List<LinkedList<LottoJob>>(QUEUE_POOL_SIZE) { LinkedList() }

        @JvmStatic
        fun getQueueIndexByRound(round: Int) = (round % QUEUE_POOL_SIZE)

        @JvmStatic
        fun getQueueByRound(round: Int): LinkedList<LottoJob> {
            return queuePool[getQueueIndexByRound(round)]
        }
    }
}
```


- 송주열 씀.
