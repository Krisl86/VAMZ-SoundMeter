package com.example.soundmeter.utilities

/**
 * From stackOverflow :)
 * Similar to C# events
 *
 * @param T
 */
class Event<T> {
    private val observers = mutableSetOf<(T) -> Unit>()

    operator fun plusAssign(observer: (T) -> Unit) {
        observers.add(observer)
    }

    operator fun minusAssign(observer: (T) -> Unit) {
        observers.remove(observer)
    }

    fun invoke(value: T) {
        for (observer in observers)
            observer(value)
    }
}