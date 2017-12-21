package net.sarazan.koverage

import org.junit.Test

/**
 * Created by Aaron Sarazan on 12/21/17
 */
class KoverageTests {

    @Test
    fun testProperties() {
        Koverage.cover<BasicProperties>()
    }

    @Test
    fun testData() {
        Koverage.cover<SomeDataClass>()
    }

    @Test
    fun testEnum() {
        Koverage.cover<SomeEnum>()
    }
}