package data

data class User(
    val id: Long? = null,
    val login: String? = null,
    val hashPassword: String? = null,
    val salt: String? = null
) {
    // Проверка на существование пользователя
    fun isInvalidUser() = id != null
}