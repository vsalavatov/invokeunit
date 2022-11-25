import dev.salavatov.invokeunit.InvokeUnit
import dev.salavatov.invokeunit.U
import dev.salavatov.invokeunit.impl.Brainfuck

fun main() {
     !BrainfuckEx.helloWorld
}

object BrainfuckEx {
    operator fun U.invoke(): Brainfuck = InvokeUnit.brainfuck

    // ++++++++[>++++[>++>+++>+++>+<<<<-]>+>->+>>+[<]<-]>>.>>---.+++++++..+++.>.<<-.>.+++.------.--------.>+.>++.
    val helloWorld =
        U()()()()()()()()()(U()(U,U)()()()()(U()(U,U)()()(U,U)()()()(U,U)()()()(U,U)()(U,U,U)(U,U,U)(U,U,U)(U,U,U)(U)
        )(U,U)()(U,U)(U)(U,U)()(U,U)(U,U)()(U()(U,U,U))(U,U,U)(U))(U,U)(U,U)(U,U,U,U)(U,U)(U,U)(U)(U)(U)(U,U,U,U)()()(
        )()()()()(U,U,U,U)(U,U,U,U)()()()(U,U,U,U)(U,U)(U,U,U,U)(U,U,U)(U,U,U)(U)(U,U,U,U)(U,U)(U,U,U,U)()()()(U,U,U,U
        )(U)(U)(U)(U)(U)(U)(U,U,U,U)(U)(U)(U)(U)(U)(U)(U)(U)(U,U,U,U)(U,U)()(U,U,U,U)(U,U)()()(U,U,U,U)
}