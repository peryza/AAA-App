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
    // Проверка необходимости авторизации
    fun isNeedAuthorization() = role != null && res != null

    // Проверка необходимости аккаунтинга
    fun isNeedAccounting() = ds != null && de != null && vol != null
}