package dev.salavatov.invokeunit

import dev.salavatov.invokeunit.impl.Brainfuck

typealias U = Unit

abstract class InvokeUnitBase<T> {
    open operator fun invoke(vararg args: U): T = dispatch(args.size)
    open operator fun invoke(arg0: T, vararg args: T): T = dispatch(listOf(arg0, *args))

    abstract fun execute()

    operator fun not() = execute()

    protected abstract fun dispatch(numArgs: Int): T
    protected abstract fun dispatch(args: List<T>): T
}

object InvokeUnit {
    val brainfuck: Brainfuck get() = Brainfuck()
}