package dev.yuua.journeylib.qnortz.functions

import dev.yuua.journeylib.qnortz.Qnortz
import dev.yuua.journeylib.qnortz.limit.LimitRouter
import org.reflections.Reflections
import java.lang.reflect.Constructor

interface ManagerStruct<T : FunctionStruct, E> {
    val qnortz: Qnortz
    val name: String
    val functionPackage: String
    val limitRouter: LimitRouter<E>

    val instances: MutableList<T>

    fun add(vararg events: T) {
        instances.addAll(events)
    }

    fun init()
}

inline fun <reified T : FunctionStruct, E> ManagerStruct<T, E>.functionClasses() =
    Reflections(functionPackage).getSubTypesOf(T::class.java)
        .filter {
            it.enclosingClass == null && !it.name.contains("$")
        }.map {
            (it.getConstructor() as Constructor<*>).newInstance() as T
        }.toMutableList()