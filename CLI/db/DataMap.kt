package db

val tableUsers = mutableListOf(
    mapOf("id" to "1", "login" to "vasya", "hashPassword" to "7a5a73db77e24a3964fa333fd43be8bb", "salt" to "bv5PehSMfV11Cd"),
    mapOf("id" to "2", "login" to "admin", "hashPassword" to "b43cfeda0e8e4bd96561535db8a1d377", "salt" to "QxLUF1bgIAdeQX"),
    mapOf("id" to "3", "login" to "q", "hashPassword" to "4a220600490d41af793cbb5c4494e435", "salt" to "YYLmfY6IehjZMQ"))

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