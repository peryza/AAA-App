import data.Activity
import data.ExitCodes.*
import data.RoleResource
import data.User
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
        return Success.exitCode
    }

    private fun authentication(login: String, pass: String): Pair<Int, User> {
        if (isLoginValid(login))
            return InvalidLoginForm.exitCode to User()
        val user = dbWrapper.getUser(login)
        if (!user.isInvalidUser())
            return UnknownLogin.exitCode to User()
        if (!isPasswordValid(pass, user.salt!!, user.hashPassword!!))
            return InvalidPassword.exitCode to User()
        return Success.exitCode to user
    }

    private fun authorization(resource: RoleResource): Int {
        if (!resource.isRoleValid())
            return UnknownRole.exitCode
        if (dbWrapper.checkAccess(resource))
            return Success.exitCode
        return NoAccess.exitCode
    }

    private fun accounting(activity: Activity): Int {
        if (!activity.hasValidData())
            return IncorrectActivity.exitCode
        dbWrapper.addActivity(activity)
        return Success.exitCode
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

