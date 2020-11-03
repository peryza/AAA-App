package services
import data.Arguments

class HandlerCLI() {

    fun parse(args: Array<String>): Arguments {
        val arguments = Arguments()
        val sizeArgs = args.size
        if (isInvalidSize(sizeArgs)) return arguments
        for (i in 0 until sizeArgs step 2) {
            fillField(arguments, args[i], args[i + 1])
        }
        return arguments
    }
    private fun isInvalidSize(sizeArgs: Int): Boolean = (sizeArgs !in arrayOf(0, 1, 4, 8, 14))

    private fun fillField(arguments: Arguments, arg: String, argValue: String) {
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