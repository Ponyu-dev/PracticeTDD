package com.github.johnnysc.practicetdd

interface Validation {

    fun isValid(text: String) : Result

    sealed class Result {
        data class MinLengthInsufficient(private val minLength: Int) : Result()
        data class UpperCaseLettersCountInsufficient(private val upperCaseLettersCount: Int) :
            Result()
        data class LowerCaseLettersCountInsufficient(private val lowerCaseLettersCount: Int) :
            Result()
        data class NumbersCountInsufficient(private val numbersCount: Int) :
            Result()
        data class SpecialSignsInsufficient(private val specialSignsCount: Int) :
            Result()

        object Valid : Result()
    }

    class Password (
        private val minLength: Int = 1,
        private val upperCaseLettersCount: Int = 0,
        private val lowerCaseLettersCount: Int = 0,
        private val numbersCount: Int = 0,
        private val specialSignsCount: Int = 0,
    ) : Validation {
        private var matchersList = mutableListOf<Matcher>()

        init {

            if (minLength < 1 ||
                upperCaseLettersCount < 0 ||
                lowerCaseLettersCount < 0 ||
                numbersCount < 0 ||
                specialSignsCount < 0
                ) throw IllegalStateException()

            matchersList.add(Matcher.Length(minLength))

            if (upperCaseLettersCount > 0)
                matchersList.add(Matcher.UpperCaseLettersCount(upperCaseLettersCount))
            if (lowerCaseLettersCount > 0)
                matchersList.add(Matcher.LowerCaseLettersCount(lowerCaseLettersCount))
            if (numbersCount > 0)
                matchersList.add(Matcher.NumbersCount(numbersCount))
            if (specialSignsCount > 0)
                matchersList.add(Matcher.SpecialSignsCount(specialSignsCount))
        }

        override fun isValid(text: String) : Result {
            var validationResult: Result = Result.Valid

            matchersList.forEach {
                validationResult = it.match(text)
                if(validationResult != Result.Valid) return@forEach
            }

            return validationResult
        }
    }

    interface Matcher {
        fun match(str: String): Result

        abstract class Abstract(
            private val minCount: Int,
            private val error: Result,
            private val matchPattern: (Char) -> Boolean
        ) : Matcher {
            override fun match(str: String): Result =
                if (str.count(matchPattern) < minCount) error else Result.Valid
        }

        class Length(
            private val minCount: Int
        ) : Abstract(
            minCount,
            Result.MinLengthInsufficient(minCount),
            { true }
        ) {
            override fun match(str: String): Result = super.match(str)
        }

        class UpperCaseLettersCount(
            private val minCont: Int
        ) : Abstract(
            minCont,
            Result.UpperCaseLettersCountInsufficient(minCont),
            { it.isUpperCase() }
        ) {
            override fun match(str: String): Result = super.match(str)
        }

        class LowerCaseLettersCount(
            private val minCont: Int
        ) : Abstract(
            minCont,
            Result.LowerCaseLettersCountInsufficient(minCont),
            { it.isLowerCase() }
        ) {
            override fun match(str: String): Result = super.match(str)
        }

        class NumbersCount(
            private val minCont: Int
        ) : Abstract(
            minCont,
            Result.NumbersCountInsufficient(minCont),
            { it.isDigit() }
        ) {
            override fun match(str: String): Result = super.match(str)
        }

        class SpecialSignsCount(
            private val minCont: Int
        ) : Abstract(
            minCont,
            Result.SpecialSignsInsufficient(minCont),
            { !it.isLetterOrDigit() }
        ) {
            override fun match(str: String): Result = super.match(str)
        }
    }
}
