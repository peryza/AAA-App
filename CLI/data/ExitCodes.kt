package data

enum class ExitCodes(val exitCode: Int) {
    SUCCESS(0),
    HELP(1),
    INVALID_LOGIN_FORM(2),
    UNKNOWN_LOGIN(3),
    INVALID_PASSWORD(4),
    UNKNOWN_ROLE(5),
    NO_ACCESS(6),
    INCORRECT_ACTIVITY(7)
}