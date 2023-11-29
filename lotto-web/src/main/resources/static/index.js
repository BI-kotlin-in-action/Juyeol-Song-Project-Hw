function showToast(message, duration = 3000) {
    const toast = document.getElementById('toast');
    toast.innerHTML = message;
    toast.classList.add('show');

    setTimeout(() => {
        toast.classList.remove('show');
    }, duration);
}

function addManualLottoInputs() {
    const manualCount = parseInt(document.getElementById('numOfManual').value);
    const inputContainer = document.getElementById('manualLottoInputs');

    // 이전에 추가된 입력란을 모두 삭제
    inputContainer.innerHTML = "";

    // 수동 로또 개수만큼 입력란을 동적으로 생성
    for (let i = 0; i < manualCount; i++) {
        const input = document.createElement('input');
        input.type = 'text';
        input.id = 'manualLottoNumbers' + i;
        input.name = 'manualLottoNumbers';
        input.placeholder = '로또 번호 입력 ' + (i + 1);
        inputContainer.appendChild(input);
        inputContainer.appendChild(document.createElement('br'));
    }
}

const isRunningWithdraw = { state: false };
const isRunningDeposit = { state: false };
const isRunningBuyLotto = { state: false };
const isRunningGetLottoQueriedResults = { state: false };
const isRunningGetLottoRecords = { state: false };

function executeThenDelay(func, isRunning, delay = 1000) {
    if (isRunning.state) {
        return;
    }
    isRunning.state = true;
    func();
    setTimeout(() => {
        isRunning.state = false;
    }, delay);
}

function generateRoundOptions(currentRound, containerId) {
    // 숫자 입력값 가져오기
    const selectedNumber = parseInt(currentRound);

    // 작은 수 선택 드롭다운 메뉴
    const smallerNumberSelect = document.getElementById(containerId);

    // 기존 옵션 제거
    smallerNumberSelect.innerHTML = '';

    // 1부터 선택한 숫자까지의 옵션 생성
    for (let i = selectedNumber; i >= 1 ; i--) {
        const option = document.createElement('option');
        option.value = i;
        option.text = i;
        smallerNumberSelect.add(option);
    }
}
