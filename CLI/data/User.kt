package data

data class User(
    val id: Int,
    val login: String,
    val hashPassword: String,
    val salt: String
)