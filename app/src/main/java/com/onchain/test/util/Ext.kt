package com.onchain.test.util

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.downscale(scale: Int): BigDecimal {
    return this.setScale(scale, RoundingMode.DOWN)
}