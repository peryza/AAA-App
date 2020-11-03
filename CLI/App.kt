import data.ExitCodes.Help
import data.ExitCodes.Success
import services.HandlerCLI

class App {
    fun run(args: Array<String>): Int {
        val handlerCLI = HandlerCLI()
        val arguments = handlerCLI.parse(args)
        when {
            arguments.hasHelp() -> return Help.exitCode
            arguments.isEmpty() -> return Help.exitCode
        }
        return Success.exitCode
    }
}