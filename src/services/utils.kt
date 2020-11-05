package services

import data.ExitCodes

// Вывод справки
fun printHelpMessage() {
    val helpMessage = """
        -h - prints out help
        -login <str> -pass <str> - authentication
        -login <str> -pass <str> -res <str> -role <str> - authorization to a specific resource with the role
        -ds <YYYY-MM-DD> -de <YYYY-MM-DD> -vol <int> - adds a activity where -ds is the start date, -de is the end date and -vol is the volume
    """.trimIndent()
    println(helpMessage)
}

// Полное завершение приложения
fun terminate(exitCode: Int = ExitCodes.HELP.exitCode, printHelp: Boolean = true) {
    if (printHelp)
        printHelpMessage()
    System.exit(exitCode)
}