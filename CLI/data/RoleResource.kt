package data

val ROLES: Array<String> = arrayOf("READ", "WRITE", "EXECUTE")

data class RoleResource(
    val id: Long? = null,
    val role: String,
    val resource: String,
    val idUser: Long
) {
    fun isRoleValid() = role in ROLES
}