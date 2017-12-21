package net.sarazan.koverage

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

/**
 * Created by Aaron Sarazan on 12/21/17
 */
@Suppress("UNCHECKED_CAST")
object PropertyTester : Coverable {

    override fun <T : Any> cover(klass: KClass<T>) {
        if (klass.memberProperties.isEmpty()) return
        val obj = klass.allocateUnsafe()
        klass.memberProperties.forEach {
            prop ->
            (prop as KProperty1<T, Any>).javaField?.let {
                it.isAccessible = true
                if (prop is KMutableProperty1) {
                    (prop as KMutableProperty1<T, Any>).set(obj, it.type.kotlin.default())
                } else {
                    it.set(obj, it.type.kotlin.default())
                }
                prop.get(obj)
            }
        }
    }
}