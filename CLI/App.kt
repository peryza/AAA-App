import data.ExitCodes.Success
import services.DatabaseWrapper
import services.HandlerCLI


class App {
    private val dbWrapper = DatabaseWrapper()

    fun run(args: Array<String>): Int {
        val handlerCLI = HandlerCLI()
        val arguments = handlerCLI.parse(args)
        return Success.exitCode
    }

    private fun isLoginValid(login: String) = login.matches(Regex("[0-9a-zA-Z]+"))
}

