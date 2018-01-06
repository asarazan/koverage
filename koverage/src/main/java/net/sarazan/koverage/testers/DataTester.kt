package net.sarazan.koverage.testers

import net.sarazan.koverage.Coverable
import net.sarazan.koverage.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

/**
 * Created by Aaron Sarazan on 12/21/17
 */
class DataTester<T: Any> private constructor(private val data: T) {

    companion object : Coverable {
        override fun <T : Any> cover(klass: KClass<T>) {
            if (klass.isData) {
                DataTester(klass.constructDefault()).cover()
            }
        }
    }

    private val klass = data.javaClass.kotlin

    fun cover() {
        coverComponents()
        coverCopy()
        coverHash()
        coverEquals()
        data.toString()
    }

    private fun coverComponents() {
        klass.declaredMemberFunctions
                .filter { it.name.startsWith("component") }
                .forEach { it.call(data) }
    }

    private fun coverCopy() {
        val copy = klass.copyFunction
        copy.callBy(mapOf(copy.parameters.first() to data))
    }

    private fun coverHash() {
        data.hashCode()
        klass.dataProperties.forEach {
            it.javaField?.let {
                if (!it.type.isPrimitive) {
                    val copy = data.dataClassCopy()
                    it.isAccessible = true
                    it.set(copy, null)
                    copy.hashCode()
                }
            }
        }
    }

    private fun coverEquals() {
        data == data
        data == null
        data == "foo"
        data == data.dataClassCopy()
        klass.dataProperties.forEach {
            prop ->
            val copyFn = klass.copyFunction
            val params = copyFn.parameters
            (1 until params.size).forEach {
                val param = params[it]
                val current = klass.declaredMemberProperties.find { it.simpleName == param.name }!!.get(data) as Any
                val copy = copyFn.callBy(mapOf(params.first() to data, param to current.different()))
                data == copy
            }
        }
    }
}