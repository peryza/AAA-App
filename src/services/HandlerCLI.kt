package services

import data.Arguments

class HandlerCLI {

    // Активируем парсинг аргументов
    fun parse(args: Array<String>): Arguments {
        val arguments = Arguments()
        val sizeArgs = args.size
        if (isInvalidSize(sizeArgs)) return arguments
        for (i in 0 until sizeArgs step 2) {
            fillField(arguments, args[i], if (sizeArgs != 1) args[i + 1] else null)
        }
        return arguments
    }

    // Проверяем валидность кол-во ых аргументов
    private fun isInvalidSize(sizeArgs: Int): Boolean = (sizeArgs !in arrayOf(0, 1, 4, 8, 14))

    // Заполняем поля класса Arguments
    private fun fillField(arguments: Arguments, arg: String, argValue: String?) {
        when (arg) {
            "-h" -> arguments.h = true
            "-login" -> arguments.login = argValue
            "-pass" -> arguments.pass = argValue
            "-res" -> arguments.res = argValue
            "-role" -> arguments.role = argValue
            "-ds" -> arguments.ds = argValue
            "-de" -> arguments.de = argValue
            "-vol" -> arguments.vol = argValue
        }
    }
}