package net.sarazan.koverage.testers

import net.sarazan.koverage.Coverable
import kotlin.reflect.KClass

/**
 * Created by Aaron Sarazan on 12/21/17
 */
object EnumTester : Coverable {

    override fun <T : Any> cover(klass: KClass<T>) {
        if (!klass.java.isEnum) return
        val values = klass.java.enumConstants
        values.forEach {
            val name = (it as Enum<*>).name
//            Enum.valueOf(klass.java, name)
            klass.java.getMethod("valueOf", String::class.java).invoke(null, name)
        }
    }
}