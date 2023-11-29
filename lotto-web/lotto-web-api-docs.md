
# Lotto-Web REST API

로또 웹서비스 REST API 명세

### 회원가입하기

유저를 생성합니다.
<br>
**기본 정보**

|메서드|URL|인증방식|
|---|----|-----|
|POST|158.180.76.84/api/users|X|

<br>
<br>

**request**

<br>
header

|이름|설명|필수여부|
|---|----|-----|
||||

<br>
<br>
body


|이름|타입|설명|
|---|---|---|
|username|String|로그인에 사용할 유저이름|
|password|String|로그인에 사용할 비밀번호|
|balance|Long| 유저가 가지게될 잔금|

<br>
<br>
path variable

|이름|타입|설명|필수여부|
|---|---|---|---|
||||


<br>
<br>
query parameter

|이름|타입|설명|필수여부|
|---|----|---|---|
|||||

<br>

**response**

<br>
header

|이름|타입|설명|
|---|----|-----|
|||| 

<br>
<br>
body


|이름|타입|설명|
|---|---|---|
||||

<br>
<br>

**예제**

**request**
```
curl -v -X POST -H "Content-Type: application/json" -d '{"username":"test", "password":"1234", "balance":10000}' 158.180.76.84/api/users
```

**response**
```
 HTTP/1.1 201 CREATED
```

---

### 유저 이름 중복여부 요청하기

유저 이름이 중복되는지 요청합니다.

<br>

**기본 정보**

|메서드|URL|인증방식|
|---|----|-----|
|GET|158.180.76.84/api/users/username/check|X|

<br>

**request**

<br>
header

|이름|설명|필수여부|
|---|----|-----|
||||

<br>
<br>
path variable

|이름|타입|설명|필수여부|
|---|---|---|---|
||||


<br>
<br>
query parameter

|이름|타입|설명|필수여부|
|---|----|---|---|
|username|String|중복여부 대상이 될 유저네임|O|

<br>
<br>

**response**

<br>

header

|이름|타입|설명|
|---|----|-----|
|||

<br>
<br>
body


|이름|타입|설명|
|---|---|---|
||||

<br>
<br>

**예제**

**request**
```
curl -X GET 158.180.76.84/api/users/username/check?username=test
```

**response**
```
HTTP/1.1  400 BAD REQUEST
Content-Type: application/json;charset=UTF-8  
{
  "statusCode": "BAD_REQUEST",
  "message": "username이 중복되었습니다."
}
```

---

### 로또 구매하기

구매할 로또를 요청합니다.

**기본 정보**

<br>

|메서드|URL|인증방식|
|---|----|-----|
|POST|158.180.76.84/api/lottos|SESSIONID|

<br>

**request**

<br>
header

|이름|설명|필수여부|
|---|----|-----|
|Cookie: JSESSIONID=${JSESSIONID}|로그인 후 서버로부터 받는 세션값|O|

<br>
<br>
body


|이름|타입|설명|
|---|---|---|
|numOfManual|Int|수동으로 살 로또의 수|
|manualLottoNumbers|List<String>|수동으로 살 로또의 숫자|
|numOfAuto|Int|자동으로 살 로또의 수|


<br>
<br>
path variable

|이름|타입|설명|필수여부|
|---|---|---|---|
||||


<br>
<br>
query parameter

|이름|타입|설명|필수여부|
|---|----|---|---|
|||||

<br>

**response**

<br>
header

|이름|타입|설명|
|---|----|-----|
||||

<br>
<br>
body


|이름|타입|설명|
|---|---|---|
||||

<br>
<br>

**예제**

**request**
```
curl -v -X POST -b "JSESSIONID=D5910D48C8A89735FA590C74AB5BD1E6" -H "Content-Type: application/json" -d '{"numOfManual":1, "manualLottoNumbers": ["1 2 3 4 5 6"], "numOfAuto":1}' 158.180.76.84/api/lottos
```

**response**
```
HTTP/1.1 201 CREATED
```
---

### 특정 유저의 선택한 회차 로또 결과 요청하기

특정 유저의 선택한 회차 로또 결과를 JSON으로 받습니다.

<br>

**기본 정보**

|메서드|URL|인증방식|
|---|----|-----|
|GET|158.180.76.84/api/lotto-results/username/{username}?round={round}|SESSIONID|

<br>

**request**

<br>
header

|이름|설명|필수여부|
|---|----|-----|
|Cookie: JSESSIONID=${JSESSIONID}|로그인 후 서버로부터 받는 세션값|O|

<br>
<br>
path variable

|이름|타입|설명|필수여부|
|---|---|---|---|
|username|String|조회 대상 유저이름|O|


<br>
<br>
query parameter

|이름|타입|설명|필수여부|
|---|----|---|---|
|round|Int|조회할 회차|O|


<br>

**response**

<br>
header

|이름|타입|설명|
|---|----|-----|
|Content-type|응답 데이터 타입 content-type: aplication/json|O|

<br>
<br>
body


|이름|타입|설명|
|---|---|---|
|round|Int|조회한 회차|
|currentCounterOfThisRound|Int|조회한 회차에서 구매한 순서|
|username|String|구매한 유저이름|
|matchedNumbers|String|일치한 숫자|
|numOfMatched|Int|일치한 개수|
|prizeRank|String|당첨순위|
|prizeAmount|Int|당첨상금|


<br>
<br>

**예제**

**request**
```
curl -v -X GET -b "JSESSIONID=D5910D48C8A89735FA590C74AB5BD1E6" 158.180.76.84/api/result-records/username/test?round=10
```

**response**
```
HTTP/1.1  200 OK 
Content-Type: application/json;
{
  "lottoResults": [
    {
      "round": 10,
      "currentCounterOfThisRound": 1,
      "username": "test",
      "matchedNumbers": "6",
      "numOfMatched": 1,
      "prizeRank": "낙",
      "prizeAmount": 0
    },
    {
      "round": 10,
      "currentCounterOfThisRound": 2,
      "username": "test",
      "matchedNumbers": "12 42",
      "numOfMatched": 2,
      "prizeRank": "낙",
      "prizeAmount": 0
    }
  ]
}
```

---

### 특정 유저의 선택한 회차 때 구매한 로또 정보 요청하기

특정 유저의 선택한 회차 때 구매한 로또 정보를 JSON으로 받습니다.

<br>

**기본 정보**

|메서드|URL|인증방식|
|---|----|-----|
|GET|158.180.76.84/api/lotto-records/username/{username}?round={round}|SESSIONID|

<br>

**request**

<br>
header

|이름|설명|필수여부|
|---|----|-----|
|Cookie: JSESSIONID=${JSESSIONID}|로그인 후 서버로부터 받는 세션값|O|

<br>
<br>
path variable

|이름|타입|설명|필수여부|
|---|---|---|---|
|username|String|조회 대상 유저이름|O|


<br>
<br>
query parameter

|이름|타입|설명|필수여부|
|---|----|---|---|
|round|Int|조회할 회차|O|

<br>

**response**

<br>
header

|이름|타입|설명|
|---|----|-----|
|Content-type|응답 데이터 타입 content-type: aplication/json|O|

<br>
<br>
body


|이름|타입|설명|
|---|---|---|
|round|Int|조회할 회차|
|currentCounterOfThisRound|Int|조회한 회차에서 구매한 순서|
|username|String|구매한 유저이름|
|lottoNumbers|String|로또 숫자|

<br>
<br>

**예제**

**request**
```
curl -v -X GET -b "JSESSIONID=D5910D48C8A89735FA590C74AB5BD1E6" 158.180.76.84/api/lotto-records/username/test?round=10
```

**response**
```
HTTP/1.1  200 OK 
Content-Type: application/json;
{
  "lottoRecords": [
    {
      "round": 10,
      "currentCounterOfThisRound": 1,
      "username": "test",
      "lottoNumbers": "1 2 3 4 5 6"
    },
    {
      "round": 10,
      "currentCounterOfThisRound": 2,
      "username": "test",
      "lottoNumbers": "12 29 37 39 42 44"
    }
  ]
}
```

---

### 로그인한 유저의  잔금 조회하기

특정 유저의 자금을 조회합니다.

<br>

**기본 정보**

|메서드|URL|인증방식|
|---|----|-----|
|GET|158.180.76.84/api/users/balance|SESSIONID|

<br>

**request**

<br>
header

|이름|설명|필수여부|
|---|----|-----|
|Cookie: JSESSIONID=${JSESSIONID}|로그인 후 서버로부터 받는 세션값|O|

<br>
<br>
path variable

|이름|타입|설명|필수여부|
|---|---|---|---|
||||


<br>
<br>
query parameter

|이름|타입|설명|필수여부|
|---|----|---|---|
|||||

<br>

**response**

<br>
header

|이름|타입|설명|
|---|----|-----|
|Content-type|응답 데이터 타입 content-type: aplication/json|O|

<br>
<br>
body


|이름|타입|설명|
|---|---|---|
|balance|Long|로그인한 유저의 잔금|

<br>
<br>

**예제**

**request**
```
curl -v -X GET -b "JSESSIONID=D5910D48C8A89735FA590C74AB5BD1E6" 158.180.76.84/api/users/balance
```

**response**
```
HTTP/1.1  200 OK 
Content-Type: application/json;
{
  "balance": 9800
}
```


---

### 특정 유저 입금하기

특정 유저의 잔금을 증가시킵니다.

<br>

**기본 정보**

|메서드|URL|인증방식|
|---|----|-----|
|PATCH|158.180.76.84/api/users/balance/deposit?money={money}|SESSIONID|

<br>

**request**

<br>
header

|이름|설명|필수여부|
|---|----|-----|
|Cookie: JSESSIONID=${JSESSIONID}|로그인 후 서버로부터 받는 세션값|O|

<br>
<br>
path variable

|이름|타입|설명|필수여부|
|---|---|---|---|
||||


<br>
<br>
query parameter

|이름|타입|설명|필수여부|
|---|----|---|---|
|money|Long|입금할 금액|O|

<br>

**response**

<br>
header

|이름|타입|설명|
|---|----|-----|
|Content-type|응답 데이터 타입 content-type: aplication/json|O|

<br>
<br>
body


|이름|타입|설명|
|---|---|---|
||||

<br>
<br>

**예제**

**request**
```
curl -v -X PATCH -b "JSESSIONID=D5910D48C8A89735FA590C74AB5BD1E6" 158.180.76.84/api/users/balance/deposit?money=500
```

**response**
```
HTTP/1.1  200 OK 
```


---


### 특정 유저 출금하기

특정 유저의 잔금을 감소시킵니다.

<br>

**기본 정보**

|메서드|URL|인증방식|
|---|----|-----|
|PATCH|158.180.76.84/api/users/balance/withdraw?money={money}|SESSIONID|

<br>

**request**

<br>
header

|이름|설명|필수여부|
|---|----|-----|
|Cookie: JSESSIONID=${JSESSIONID}|로그인 후 서버로부터 받는 세션값|O|

<br>
<br>
path variable

|이름|타입|설명|필수여부|
|---|---|---|---|
||||


<br>
<br>
query parameter

|이름|타입|설명|필수여부|
|---|----|---|---|
|money|Long|출금할 금액|O|

<br>

**response**

<br>
header

|이름|타입|설명|
|---|----|-----|
|Content-type|응답 데이터 타입 content-type: aplication/json|O|

<br>
<br>
body


|이름|타입|설명|
|---|---|---|
||||

<br>
<br>

**예제**

**request**
```
curl -v -X PATCH -b "JSESSIONID=D5910D48C8A89735FA590C74AB5BD1E6" 158.180.76.84/api/users/balance/withdraw?money=500
```

**response**
```
HTTP/1.1  200 OK 
```
