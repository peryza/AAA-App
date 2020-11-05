package data

data class Arguments(
    var h: Boolean = false,
    var login: String? = null,
    var pass: String? = null,
    var role: String? = null,
    var res: String? = null,
    var ds: String? = null,
    var de: String? = null,
    var vol: String? = null
) {

    // Проверка на команду справки
    fun hasHelp() = h

    // Проверка на отсутствие аргументов
    fun isEmpty() =
        (login == null && pass == null && role == null && res == null && ds == null && de == null && vol == null)

    // Проверка необходимости вывода справки
    fun isNeedHelp() = hasHelp() || isEmpty() || !isNeedAuthentication()

    // Проверка необходимости аутентификации
    fun isNeedAuthentication() = login != null && pass != null

    // Проверка необходимости авторизации
    fun isNeedAuthorization() = role != null && res != null

    // Проверка необходимости аккаунтинга
    fun isNeedAccounting() = ds != null && de != null && vol != null
}