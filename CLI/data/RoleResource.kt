package data

data class RoleResource(
    val id: Long? = null,
    val role: Roles,
    val resource: String,
    val idUser: Long
)