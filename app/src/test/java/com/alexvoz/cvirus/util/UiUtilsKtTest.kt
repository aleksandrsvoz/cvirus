package com.alexvoz.cvirus.util


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UiUtilsKtTest{

    @Test
    fun `number with spaces test returns true`(){
        val result = (1234567).toInt().getNumberWithSpaces()

        assertThat(result).isEqualTo("1 234 567")
    }

    @Test
    fun `number with spaces test returns false`(){
        val result = (1234567).toInt().getNumberWithSpaces()

        assertThat(result).isNotEqualTo("1234567")
    }
}