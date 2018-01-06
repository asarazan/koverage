package net.sarazan.koverage

import org.junit.Test

/**
 * Created by Aaron Sarazan on 12/21/17
 */
class KoverageTests {

    @Test
    fun testProperties() {
        Koverage.cover<BasicProperties>()
        Koverage.cover<PrivateSetters>()
    }

    @Test
    fun testData() {
        Koverage.cover<SomeDataClass>()
    }

    @Test
    fun testEnum() {
        Koverage.cover<SomeEnum>()
    }

    @Test
    fun testCompanion() {
        Koverage.cover<WithCompanion>()
        Koverage.cover<WithCompanion.Companion>()
    }

    @Test
    fun testSingleton() {
        Koverage.cover<Singleton>()
    }

    @Test
    fun testConstructors() {
        Koverage.cover<WeirdConstructors>()
        Koverage.cover<DataWeirdConstructors>()
    }
}