
# Lotto-Web Docs

로또 웹서비스 주요 기능 설명

<br>

### 회차 관리

서비스 기능 상 10분 주기로 로또 회차가 바뀌고 그 회차의 당첨번호가 나오며 그 회차에 구매된 로또의 결과가 나온다.
그래서 **가장 넓은 베이스**가 되는 것은 **10분 주기 스케쥴링**과 **회차를 관리**하는 것 이다.

실제 코드에서는 LottoRoundControl이라는 엔티티 클래스를 통해 관리된다.  

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

lotto_round_control 테이블에는 단 하나의 레코드만 존재하며 이는 프로그램 시작 시 initial_data.sql에 의해서 실행된다.

```
// application-dev.yml

properties:  
  hibernate:  
    hbm2ddl:  
      import_files: initial_data.sql

// initial_data.sql

INSERT INTO lotto_round_control (lotto_round_control_id, round) values (0, 1);
```     

그리고 LottoRoundControlRepository, LottoRoundControlService를 통해 사용된다.

<br>

### 10분 주기 스케쥴링

스케쥴링 기능 자체는 스프링 프레임워크에서 제공해주는 스프링 스케쥴링을 통해서 간단히 구현할 수 있다.
중요한 것은 이 내부에서 어떤 일이 일어나는지와 규정하기 애매한 문제들이다.

LottoSchedulerService에서 코드를 확인할 수 있다.

```
// LottoSchedulerService.kt

@Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 10 * 60 * 1000)  
fun scheduledFunction() {  
  // 현재 회차를 캡쳐하자마자 다음 회차로 증가시킴  
  val capturedCurrentRound = lottoRoundControlService.getLottoRound()  
  lottoRoundControlService.increaseRound()  
  
  val winningRecordThisRound = winningRecordService.generateNewWinningRecord(capturedCurrentRound)  
  
  // 이번 회차의 모든 로또 구매 작업이 끝날 때까지 대기  
  while (queueWorkingCounterService.isNotCounterZero(capturedCurrentRound)) {}  
  
  // 이번 회차 queue 에 있는 모든 메시지를 처리  
    for (message in getQueueByRound(capturedCurrentRound)) {  
	    val lottoJob = LottoJob.from(message.message)  
	    resultRecordService.processResultRecords(lottoJob, winningRecordThisRound)  
	}  
}
```

가장 먼저 현재 회차(1회차)를 가져온다.  10분 주기가 넘은 관계로 회차를 하나 증가시킨다(2회차). 회차를 증가시키지 않으면 10분 주기를 넘었음에도 불구하고 이전 회차(1회차)의 로또를 생성한다.(2회차 로또를 생성하여야하는데 1회차 로또를 생성한다.) 이미 현재 회차를 가져왔기에 이 값으로 로직을 진행하면 된다.(capturedCurrentRound = 1) 
그리고 현재 회차의 당첨로또를 생성한다.  이후 while을 사용하는 waiting 부분을 확인할 수 있다.  이후에 설명할 내용이지만 간략히 말하자면 10분 주기로 넘어가기 직전 대량의 로또 구매 요청이 들어와서 로또를 생성하는 와중에 당첨번호가 나오며 로또 구매 요청이 유실되는 것을 방지하기 위해 기다리는 것이라 생각하면 된다.

waiting이 끝난다면 이번 회차에 구매된 로또들을 queue에서 차례차례 불러와 당첨번호와 비교하고 그 결과를 처리하는 것이다.

이러한 과정이 매 10분 주기로 반복된다.

<br>

### QueueWorkingCounter

앞서 설명한 대로 이 클래스는 10분 주기를 넘었다해서 바로 당첨결과를 처리하지 않고 당첨번호가 나온 회차에서 아직 생성 중인 쓰레드가 있다면 이 작업을 완료할 때까지 당첨결과처리를 지연하기 위해 존재한다. 

LottoJobMessageJobQueuePool의 수만큼(100) 존재하며 초기화는 initial_data.sql에 의해 프로그램 시작 시에 생성된다.

```
// initial_data.sql

SET autocommit=0;  
START TRANSACTION;  
INSERT INTO queue_working_counter (queue_round, counter) VALUES (0, 0), (1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0), (7, 0), (8, 0), (9, 0), (10, 0), (11, 0), (12, 0), (13, 0), (14, 0), (15, 0), (16, 0), (17, 0), (18, 0), (19, 0), (20, 0), (21, 0), (22, 0), (23, 0), (24, 0), (25, 0), (26, 0), (27, 0), (28, 0), (29, 0), (30, 0), (31, 0), (32, 0), (33, 0), (34, 0), (35, 0), (36, 0), (37, 0), (38, 0), (39, 0), (40, 0), (41, 0), (42, 0), (43, 0), (44, 0), (45, 0), (46, 0), (47, 0), (48, 0), (49, 0), (50, 0), (51, 0), (52, 0), (53, 0), (54, 0), (55, 0), (56, 0), (57, 0), (58, 0), (59, 0), (60, 0), (61, 0), (62, 0), (63, 0), (64, 0), (65, 0), (66, 0), (67, 0), (68, 0), (69, 0), (70, 0), (71, 0), (72, 0), (73, 0), (74, 0), (75, 0), (76, 0), (77, 0), (78, 0), (79, 0), (80, 0), (81, 0), (82, 0), (83, 0), (84, 0), (85, 0), (86, 0), (87, 0), (88, 0), (89, 0), (90, 0), (91, 0), (92, 0), (93, 0), (94, 0), (95, 0), (96, 0), (97, 0), (98, 0), (99, 0);  
COMMIT;
```

처음엔 JPA repository의 save를 통해서 service 시작 시 초기화하게 하였지만 어느 클래스에서 초기화를 맡게할지 애매했고 테이블 생성 타이밍과 빈 등록 타이밍에 문제가 생기진 않을까? 하는 걱정도 있었다.(이 부분은 정확하지 않아 추후에 더 알아볼 예정이다.)


```
// LottoService.kt 중 일부

@Transactional(isolation = Isolation.READ_COMMITTED)  
fun processLottoBuyRequest(lottoBuyRequest: LottoBuyRequest, username: String) {  
  val capturedCurrentRound = lottoRoundControlService.getLottoRound()  
    
  queueWorkingCounterService.increaseCounter(capturedCurrentRound)  
  
  .
  .
  .
    
  queueWorkingCounterService.decreaseCounter(capturedCurrentRound)  
}
```

로또를 구매하게 되면 가장 먼저 해당 회차의 queue에서 작업하고 있음을 나타내기 위해 해당 회차의 queueWorkingCounter의 값을 증가시키고 구매 로직이 끝나면 값을 다시 내린다. 
이 때 여러 쓰레드에서 동시에 이 값에 수정을 할 수 있기에 비관적 락을 걸어서 처리하였다. 트랜잭션만으로 동시 수정에 대한 동시성 문제를 해결할 수 있는지 잘 판단이 안선다.

queue를 100개에서 돌려사용하기에 회차가 정수 1씩 상승하는 점을 이용해 나머지 연산을 통해서 queue의 인덱스에 접근합니다. 

```
// QueueWorkingCounterService.kt

@Transactional(isolation = READ_COMMITTED)  
fun increaseCounter(queueRound: Int) {  
    val queueWorkingCounter = queueWorkingCounterRepository.findByRoundWithPessimisticLock(  
        getQueueIndexByRound(queueRound),  
    )  
    queueWorkingCounter.increaseCounter()  
}

// LottoJobMessageQueuePool.kt

@JvmStatic  
fun getQueueIndexByRound(round: Int) = (round % QUEUE_POOL_SIZE) // 나머지 연산 사용
  
@JvmStatic  
fun getQueueByRound(round: Int): LinkedList<LottoJobMessage> {  
    return queuePool[getQueueIndexByRound(round)]  
}
```


<br>

### LottoJobMessageJobQueue

queuePool을 private으로 가진다.  getQueueByRound()를 통해 현재 회차 queue를 참조할 수 있다.
```
@Configuration  
class LottoJobMessageQueuePool() {  
    companion object {  
        @JvmStatic  
	    val QUEUE_POOL_SIZE = 100  
  
	    private val queuePool = List<LinkedList<LottoJobMessage>>(QUEUE_POOL_SIZE) { LinkedList() }
  
	    @JvmStatic  
  	    fun getQueueIndexByRound(round: Int) = (round % QUEUE_POOL_SIZE)  
  
	    @JvmStatic  
	    fun getQueueByRound(round: Int): LinkedList<LottoJobMessage> {  
	        return queuePool[getQueueIndexByRound(round)]  
	    }  
	}  
}
```


### 느낀 점


먼저 현실의 문제를 프로그램으로 해결하려할 때 프로그램으로 현실의 문제를 자연스럽게 구현할 수도 있지만 어느 정도 타협을 두고 세부적인 부분을 정의하여 현실의 문제를 반영할 수 있도록 문제를 재정의하여 프로그램을 만들 줄 알아야하는 것 같다. 적절한 예인지는 모르겠지만 이번 문제 10분 주기를 막 넘어가기 직전 다량의 로또 구매 요청이 올 시 어떻게 처리할 것인가에 대해서 고민할 때 이런 생각이 들었다.

그리고 동시성 문제를 고려해본게 이번이 처음이라 아직 부족한 부분이 너무 크다는 걸 통감한다. 이번 프로젝트를 하며 고려하였던 동시성 문제로는 여러 쓰레드에서 현재 회차의 queue 접근하면서 queueWorkingCounter를 동시에 증가시켰을 때를 염두하였다. 동시에 낙관적 락, 비관적 락, 트랙잭션 격리수준 등에 대해 조금 알아봤지만 그래도 눈앞에 있는 문제에 바로 그 개념들을 적용시키기엔 아직 지식수준이 못 미치는 것 같다. 그리고 동시성 문제 환경을 테스트 코드에서 어떻게 구현하지? 하는 고민도 들었다. 앞으로 다양한 프로젝트를 하며 동시성 문제도 반드시 고려를 해야만 할 것 같다. 

뿐만 아니라 스프링의 기본적인 원리, DB, 성급하게 다른 기술을 도입하려는 것 등 스스로에게 미숙한 부분을 많이 확인할 수 있었다. 빠른 시일 내에 이러한 부분을 채워나가야겠다.


- 송주열 올림.