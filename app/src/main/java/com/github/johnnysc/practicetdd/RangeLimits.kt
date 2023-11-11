package com.github.johnnysc.practicetdd

//
//  com.github.johnnysc.practicetdd
//  PracticeTDD
//
//  Created by ponyu on 20.10.2023
//  Copyright © 2023 ponyu. All rights reserved.
//

interface RangeLimits {
    fun pair(number: Int) : RangePair

    class Base(
        private val list: List<Int>
    ) : RangeLimits{
        override fun pair(number: Int): RangePair {
            var left = Int.MIN_VALUE
            var right = Int.MAX_VALUE

            if(list.isEmpty()) return RangePair(left, right)

            list.forEach {
                if (it in (left + 1) until number) left = it
                if (right > it && it > number) right = it
            }

            return RangePair(left, right)
        }
    }
}

data class RangePair(
    val left: Int,
    val right: Int
)