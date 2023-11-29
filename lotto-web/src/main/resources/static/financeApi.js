function getBalance() {
    fetch("/api/users/balance", {
        method: "GET",
        credentials: "include",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById("balance-view").innerHTML = "현재 금액: " + data.balance;
        })
        .catch(() => {
        });
}

function withdraw() {
    const moneyInput = document.getElementById("withdraw");
    const money = moneyInput.value;

    fetch("/api/users/balance/withdraw?money=" + money, {
        method: "PATCH",
        credentials: "include",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
    })
        .then(response => {
            // API 호출이 성공한 경우
            if (response.ok) {
                return response;
            }
            // API 호출이 실패한 경우
            return response.json().then(data => {
                throw new Error(data.errorMessage);
            });
        })
        .then(() => {
            // money 값을 0으로 만들어줌
            moneyInput.value = 0;
        })
        .catch(error => {
            // API 호출이 실패한 경우에 대한 처리
            console.error('API 호출 중 오류 발생:', error);
        });
}

function deposit() {
    const moneyInput = document.getElementById("deposit");
    const money = moneyInput.value;

    fetch("/api/users/balance/deposit?money=" + money, {
        method: "PATCH",
        credentials: "include",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
    })
        .then(response => {
            // API 호출이 성공한 경우
            if (response.ok) {
                return response;
            }
            // API 호출이 실패한 경우
            return response.json().then(data => {
                throw new Error(data.errorMessage);
            });
        })
        .then(() => {
            // money 값을 0으로 만들어줌
            moneyInput.value = 0;
        })
        .catch(error => {
        });
}


setInterval(getBalance, 1000)