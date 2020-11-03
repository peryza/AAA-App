package data

data class Activity(
    val id: Int,
    val idRoleResource: Int,
    val ds: String,
    val de: String,
    val vol: String
) {

    fun toMap(): Map<String, String> {
        return mapOf(
            "id" to id.toString(),
            "idRoleResource" to idRoleResource.toString(),
            "ds" to ds,
            "de" to de,
            "vol" to vol
        )
    }
}