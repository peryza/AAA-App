import data.*
import data.ExitCodes.*
import services.DatabaseWrapper
import services.printHelpMessage
import java.math.BigInteger
import java.security.MessageDigest
import kotlinx.cli.*


class App {
    private val dbWrapper = DatabaseWrapper()

    fun run(args: Array<String>): Int {
        val parser = ArgParser("handler")
        val login by parser.option(ArgType.String, shortName = "login", description = "User login").required()
        val pass by parser.option(ArgType.String, shortName = "pass", description = "User password").required()
        val role by parser.option(ArgType.String, shortName = "role", description = "Specified user role")
        val res by parser.option(ArgType.String, shortName = "res", description = "Specified user res")
        val ds by parser.option(ArgType.String, shortName = "ds", description = "Data start")
        val de by parser.option(ArgType.String, shortName = "de", description = "Data end")
        val vol by parser.option(ArgType.String, shortName = "vol", description = "User volume")
        parser.parse(args)
        val arguments = Arguments(false, login, pass, role, res, ds, de, vol)

        if (arguments.isNeedHelp()) {
            printHelpMessage()
            return HELP.exitCode
        }
        val (exitCodeAuthentication, user) = authentication(arguments.login!!, arguments.pass!!)
        if (exitCodeAuthentication != SUCCESS.exitCode)
            return exitCodeAuthentication

        if (!arguments.isNeedAuthorization())
            return SUCCESS.exitCode
        val exitCodeAuthorization = authorization(arguments.role!!, arguments.res!!, user.id!!)
        if (exitCodeAuthorization != SUCCESS.exitCode)
            return exitCodeAuthorization

        if (!arguments.isNeedAccounting())
            return SUCCESS.exitCode

        val activity = Activity(
                role = arguments.role!!,
                res = arguments.res!!,
                ds = arguments.ds!!,
                de = arguments.de!!,
                vol = arguments.vol!!
        )
        val exitCodeAccounting = accounting(activity)
        if (exitCodeAccounting != SUCCESS.exitCode)
            return exitCodeAccounting

        return SUCCESS.exitCode
    }

    // Осуществление аутенфитикации
    private fun authentication(login: String, pass: String): Pair<Int, User> {
        if (!isLoginValid(login))
            return INVALID_LOGIN_FORM.exitCode to User()

        val user = dbWrapper.getUser(login)
        return when {
            !user.isInvalidUser() -> UNKNOWN_LOGIN.exitCode to User()
            !isPasswordValid(pass, user.salt!!, user.hashPassword!!) -> INVALID_PASSWORD.exitCode to User()
            else -> SUCCESS.exitCode to user
        }
    }

    // Осуществление авторизации
    private fun authorization(roleString: String, res: String, idUser: Long): Int = try {
        val role = Roles.valueOf(roleString)
        val resource = RoleResource(role = role, resource = res, idUser = idUser)
        if (dbWrapper.checkAccess(resource))
            SUCCESS.exitCode
        else
            NO_ACCESS.exitCode
    } catch (error: java.lang.IllegalArgumentException) {
        UNKNOWN_ROLE.exitCode
    }

    // Осуществление аккаунтинга
    private fun accounting(activity: Activity): Int {
        if (!activity.hasValidData())
            return INCORRECT_ACTIVITY.exitCode

        dbWrapper.addActivity(activity)
        return SUCCESS.exitCode
    }

    // Проверка валидности формы логина
    private fun isLoginValid(login: String) = login.matches(Regex("[0-9a-zA-Z]+"))

    // Проверка валидности пароля
    private fun isPasswordValid(pass: String, salt: String, hashPassword: String) =
            getHashPassword(pass, salt) == hashPassword

    // Получение хешированного пароля
    private fun getHashPassword(pass: String, salt: String) = applyMD5(applyMD5(pass) + salt)

    // Применение MD5
    private fun applyMD5(password: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(password.toByteArray())).toString(16).padStart(32, '0')
    }
}