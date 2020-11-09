package services

import data.Arguments
import kotlinx.cli.*

class ArgumentsParser(programName: String): ArgParser(programName) {
    fun getParsedArgs(args: Array<String>): Arguments {
        val login by option(ArgType.String, shortName = "login", description = "User login").required()
        val pass by option(ArgType.String, shortName = "pass", description = "User password").required()
        val role by option(ArgType.String, shortName = "role", description = "Specified user role")
        val res by option(ArgType.String, shortName = "res", description = "Specified user res")
        val ds by option(ArgType.String, shortName = "ds", description = "Data start")
        val de by option(ArgType.String, shortName = "de", description = "Data end")
        val vol by option(ArgType.String, shortName = "vol", description = "User volume")
        parse(args)
        return Arguments(false, login, pass, role, res, ds, de, vol)
    }
}