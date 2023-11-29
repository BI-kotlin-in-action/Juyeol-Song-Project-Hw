function buyLottos() {
    const numOfManual = document.getElementById("numOfManual").value;
    const manualLottoNumbers = [];
    for (var i = 0; i < numOfManual; i++) {
        manualLottoNumbers.push(document.getElementById("manualLottoNumbers" + i).value);
    }
    const numOfAuto = document.getElementById("numOfAuto").value;
    const json = JSON.stringify({
        numOfManual: numOfManual,
        manualLottoNumbers: manualLottoNumbers,
        numOfAuto: numOfAuto,
    });
    fetch("/api/lottos", {
        method: "POST",
        credentials: "include",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: json
    })
        .then(response => {
            // API 호출이 성공한 경우
            if (response.ok) {
                showToast("로또 구매 완료")
                document.getElementById("manualLottoInputs").innerHTML = "";
            }
            else {
                showToast("로또 구매 실패")
            }
        })
        .catch(() => {
            showToast("로또 구매 실패")
        });
}


function fetchCurrentRound() {
    // 여기에 API 호출을 수행하는 코드를 작성
    fetch("/api/winning-records/max-available-round", {
        method: "GET",
        credentials: "include",
        headers: {
            Accept: "application/json",
        },
    })
        .then(response => response.json())
        .then(data => {
            if (data.currentRound === previousQueriedRound) {
                return;
            }
            generateRoundOptions(data.currentRound, "selectLottoRound");
            generateRoundOptions(data.currentRound + 1, "selectLottoRoundForLottoRecords");

            previousQueriedRound = data.currentRound;
            showToast("최신 회차 갱신: " + data.currentRound);
        });
}


function getWinningLottoInfo() {
    const round = document.getElementById("selectLottoRound").value;
    fetch("/api/winning-records" + "?round=" + round, {
        method: "GET",
        credentials: "include",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
    })
        .then(response => {
            // API 호출이 성공한 경우
            if (response.ok) {
                return response.json();
            } else {
                showToast("아직 당첨 결과가 없습니다.");
            }
        })
        .then(data => {
            document.getElementById("winningLottoInfo").innerHTML =
                "회차: " + data.round + " " +
                "당첨 번호: " + data.winningNumbers
        }).catch(() => {
        showToast("아직 당첨 결과가 없습니다.")
    });
}

function getLottoResults() {
    getWinningLottoInfo();
    const round = document.getElementById("selectLottoRound").value;
    const username = document.getElementById("selectUsername").value;
    fetch("/api/result-records" + "/username/" + username + "?round=" + round, {
        method: "GET",
        credentials: "include",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
    })
        .then(response => {
            // API 호출이 성공한 경우
            if (response.ok) {
                return response.json();
            } else {
                showToast("아직 결과를 조회할 수 없습니다.");
            }
        })
        .then(data => {
            addLottoResults(data.lottoResults);
        });
}

function addLottoResults(lottoResults) {
    const lottoResultsContainer = document.getElementById("lottoResultsContainer");
    lottoResultsContainer.innerHTML = "";
    for (var i = 0; i < lottoResults.length; i++) {
        const lottoResult = lottoResults[i];
        const lottoResultDiv = document.createElement("div");
        lottoResultDiv.innerHTML =
            lottoResult.round + "  회차  " +
            lottoResult.currentCounterOfThisRound + "  번째  " +
            lottoResult.username + "  님의 로또 결과:   " +
            lottoResult.matchedNumbers + "  " +
            lottoResult.numOfMatched + " 개 일치  " +
            lottoResult.prizeRank + "  상금  " +
            lottoResult.prizeAmount + "  카카오원"
        ;
        lottoResultsContainer.appendChild(lottoResultDiv);
    }
}

function getLottoHistory() {
    const round = document.getElementById("selectLottoRoundForLottoRecords").value;
    const username = document.getElementById("selectUsernameForLottoRecords").value;
    fetch("/api/lotto-records/" + "username/" + username + "?round=" + round, {
        method: "GET",
        credentials: "include",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
    })
        .then(response => {
            // API 호출이 성공한 경우
            if (response.ok) {
                return response.json();
            } else {
                showToast("아직 결과를 조회할 수 없습니다.");
            }
        })
        .then(data => {
            addLottoRecords(data.lottoRecords);
        });
}

function addLottoRecords(lottoRecords) {
    const lottoResultsContainer = document.getElementById("lottoRecordsContainer");
    lottoResultsContainer.innerHTML = "";
    for (var i = 0; i < lottoRecords.length; i++) {
        const lottoRecord = lottoRecords[i];
        const lottoResultDiv = document.createElement("div");
        lottoResultDiv.innerHTML =
            lottoRecord.round + "  회차  " +
            lottoRecord.currentCounterOfThisRound + "  번째  " +
            lottoRecord.username + "  님의 로또 번호:  " +
            lottoRecord.lottoNumbers
        ;
        lottoResultsContainer.appendChild(lottoResultDiv);
    }
}

previousQueriedRound = 0;
setInterval(fetchCurrentRound, 1000);
