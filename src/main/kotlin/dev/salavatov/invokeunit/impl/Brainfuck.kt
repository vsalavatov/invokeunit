package dev.salavatov.invokeunit.impl

import dev.salavatov.invokeunit.InvokeUnitBase
import dev.salavatov.invokeunit.impl.Brainfuck.Commands.Companion.render
import javax.naming.OperationNotSupportedException

class Brainfuck : InvokeUnitBase<Brainfuck>() {
    private sealed class Commands {
        object Inc: Commands() {                        // ()
            override fun toString(): String = "+"
        }
        object Dec: Commands() {                        // (U)
            override fun toString(): String = "-"
        }
        object Right: Commands() {                      // (U, U)
            override fun toString(): String = ">"
        }
        object Left: Commands() {                       // (U, U, U)
            override fun toString(): String = "<"
        }
        object Print: Commands() {                      // (U, U, U, U)
            override fun toString(): String = "."
        }
        object Read: Commands() {                       // (U, U, U, U, U)
            override fun toString(): String = ","
        }
        class Loop(val nested: Brainfuck): Commands() { // (<brainfuck>)
            override fun toString(): String =
                "[${nested.commands.render()}]"
        }

        companion object {
            fun List<Commands>.render(): String = joinToString("", "", "") { it.toString() }
        }
    }

    private val commands = mutableListOf<Commands>()

    override fun dispatch(args: List<Brainfuck>): Brainfuck {
        assert(args.size == 1)
        return this.apply {
            commands += Commands.Loop(args.first())
        }
    }

    override fun dispatch(numArgs: Int) = this.apply {
        commands += when (numArgs) {
            0 -> Commands.Inc
            1 -> Commands.Dec
            2 -> Commands.Right
            3 -> Commands.Left
            4 -> Commands.Print
            5 -> Commands.Read
            else -> {
                throw OperationNotSupportedException("$numArgs operation is not defined")
            }
        }
    }

    override fun execute() = Unit.also { initState(commands) }

    private data class State(val data: MutableMap<Int, Byte>, var address: Int) {
        operator fun invoke(commands: List<Commands>) {
            for (command in commands) {
                when (command) {
                    Commands.Inc -> inc()
                    Commands.Dec -> dec()
                    Commands.Right -> right()
                    Commands.Left -> left()
                    Commands.Print -> print()
                    Commands.Read -> read()
                    is Commands.Loop -> loop(command.nested.commands)
                }
            }
        }

        private fun get(address: Int = this.address): Byte = data.getOrDefault(address, 0)
        private fun set(address: Int = this.address, mod: (Byte) -> Byte) =
            data.compute(address) { _, prevValue ->
                if (prevValue == null) mod(0)
                else mod(prevValue)
            }

        fun inc() {
            set { it.inc() }
        }

        fun dec() {
            set { it.dec() }
        }

        fun right() {
            address++
        }

        fun left() {
            address--
        }

        fun print() {
            print(Char(get().toInt()))
        }

        fun read() {
            val bytes = System.`in`.readNBytes(1)
            set { bytes[0] }
        }

        fun loop(commands: List<Commands>) {
            while (true) {
                if (get() == 0.toByte()) {
                    break
                } else {
                    this(commands)
                    if (get() == 0.toByte()) break
                }
            }
        }
    }

    private val initState get() = State(mutableMapOf(), 0)

    override fun toString(): String {
        return commands.render()
    }
}