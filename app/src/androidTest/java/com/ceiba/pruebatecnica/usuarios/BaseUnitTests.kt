package com.ceiba.pruebatecnica.usuarios

import org.junit.After
import org.junit.Assert
import org.junit.Before

open class BaseUnitTests {

    var finished = false
    var time = 2

    @Before
    open fun setUp() {
        finished = false
        time = 2
    }

    @After
    open fun tearDown() {
        Thread.sleep(time * 1000L)
        Assert.assertTrue(finished)
    }
}