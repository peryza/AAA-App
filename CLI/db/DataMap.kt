package db

val tableUsers = mutableListOf(
    mapOf("id" to "1", "login" to "vasya", "pass" to "123", "salt" to ""),
    mapOf("id" to "2", "login" to "admin", "pass" to "admin", "salt" to ""),
    mapOf("id" to "3", "login" to "q", "pass" to "ytrewq", "salt" to ""))

val tableRolesResources = mutableListOf(
    mapOf("id" to "1", "role" to "A", "res" to "READ", "idUser" to "1"),
    mapOf("id" to "2", "role" to "A.B.C", "res" to "WRITE", "idUser" to "1"),
    mapOf("id" to "3", "role" to "A.B", "res" to "EXECUTE", "idUser" to "2"),
    mapOf("id" to "4", "role" to "B", "res" to "EXECUTE", "idUser" to "3"),
    mapOf("id" to "5", "role" to "A.B.C", "res" to "READ", "idUser" to "2"),
    mapOf("id" to "6", "role" to "A.B", "res" to "WRITE", "idUser" to "2"),
    mapOf("id" to "7", "role" to "A", "res" to "READ", "idUser" to "2")
)

val tableActivity: MutableList<Map<String, String>> = mutableListOf()