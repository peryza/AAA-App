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

    fun hasHelp() = h

    fun isEmpty() =
        (login == null && pass == null && role == null && res == null && ds == null && de == null && vol == null)

    fun hasNeedHelp() = hasHelp() || isEmpty() || !hasNeedAuthentication()

    fun hasNeedAuthentication() = login != null && pass != null

    fun hasNeedAuthorization() = role != null && res != null

    fun hasNeedAccounting() = ds != null && de != null && vol != null
}