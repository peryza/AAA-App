import data.Activity
import data.ExitCodes.*
import data.RoleResource
import data.User
import data.Roles
import services.DatabaseWrapper
import services.HandlerCLI
import services.printHelpMessage
import java.math.BigInteger
import java.security.MessageDigest


class App {
    private val dbWrapper = DatabaseWrapper()

    fun run(args: Array<String>): Int {
        val handlerCLI = HandlerCLI()
        val arguments = handlerCLI.parse(args)
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

    private fun authentication(login: String, pass: String): Pair<Int, User> {
        if (!isLoginValid(login))
            return INVALID_LOGIN_FORM.exitCode to User()

        val user = dbWrapper.getUser(login)
        if (!user.isInvalidUser())
            return UNKNOWN_LOGIN.exitCode to User()

        if (!isPasswordValid(pass, user.salt!!, user.hashPassword!!))
            return INVALID_PASSWORD.exitCode to User()

        return SUCCESS.exitCode to user
    }

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


    private fun accounting(activity: Activity): Int {
        if (!activity.hasValidData())
            return INCORRECT_ACTIVITY.exitCode

        dbWrapper.addActivity(activity)
        return SUCCESS.exitCode
    }

    private fun isLoginValid(login: String) = login.matches(Regex("[0-9a-zA-Z]+"))

    private fun isPasswordValid(pass: String, salt: String, hashPassword: String) =
        getHashPassword(pass, salt) == hashPassword

    private fun getHashPassword(pass: String, salt: String) = applyMD5(applyMD5(pass) + salt)

    private fun applyMD5(password: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(password.toByteArray())).toString(16).padStart(32, '0')
    }
}