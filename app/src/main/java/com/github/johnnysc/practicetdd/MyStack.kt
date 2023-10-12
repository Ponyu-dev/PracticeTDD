package com.github.johnnysc.practicetdd

//
//  com.github.johnnysc.practicetdd
//  PracticeTDD
//
//  Created by ponyu on 11.10.2023
//  Copyright © 2023 ponyu. All rights reserved.
//

interface MyStack<T> {
    fun pop(): T
    fun push(item: T)

    abstract class Base<T>(
        maxCount: Int
    ) : MyStack<T>{
        protected var array: Array<Any?>
        protected var position = -1

        init {
            if(maxCount < 1)
                throw IllegalStateException("maxCount can only be positive")

            array = Array(maxCount){ null }
        }

        override fun push(item: T) {
            if(position + 1 >= array.size)
                throw IllegalStateException("Stack overflow exception, maximum is ${array.size}")

            position++;
            array[position] = item
        }
    }

    /* Last In First Out */
    class LIFO<T>(
        maxCount: Int
    ) : Base<T>(maxCount) {

        override fun pop(): T {
            if (position < 0) throw IllegalStateException("item from empty stack")

            val item = array[position] as T
            array[position] = null
            position -= 1
            return item
        }
    }

    /*First In First Out*/
    class FIFO<T>(
        maxCount: Int
    ): Base<T>(maxCount) {

        override fun pop(): T {
            if (position < 0)
                throw IllegalStateException("item from empty stack")
            val item = array[0] as T
            array.copyInto(array, 0, 1, position + 1)
            array[position] = null
            position--
            return item
        }

        /*
        //usual for
        override fun pop(): T {
            if (position < 0)
                throw IllegalStateException("Stack is empty")
            val item = array[0] as T
            for (i in 0 until position) {
                array[i] = array[i + 1]
            }
            array[position] = null
            position--
            return item
        }*/
    }
}