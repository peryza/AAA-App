package data

enum class ExitCodes(val exitCode: Int) {
    Success(0),
    Help(1),
    InvalidLoginForm(2),
    UnknownLogin(3),
    InvalidPassword(4),
    UnknownRole(5),
    NoAccess(6),
    IncorrectActivity(7)
}