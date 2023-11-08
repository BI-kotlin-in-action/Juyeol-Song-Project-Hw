package domain.winninglotto

import domain.lotto.Lotto
import domain.lottomachine.AutomaticLottoMachineStrategy

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */

/**
 * WinningLotto
 * 당첨 번호를 가지고 있는 객체 (Singleton)
 * 당첨 번호를 생성하고, 당첨 번호와 비교하여 당첨 결과를 반환한다.
 */
object WinningLotto {
    val winningLotto = Lotto(generateStrategy = { AutomaticLottoMachineStrategy.generateLotto() })
}
