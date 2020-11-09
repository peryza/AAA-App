package db

import data.Activity
import data.RoleResource
import data.Roles.*
import data.User

val tableUsers = mutableListOf(
    User(id = 1, login = "vasya", hashPassword = "7a5a73db77e24a3964fa333fd43be8bb", salt = "bv5PehSMfV11Cd"),
    User(id = 2, login = "admin", hashPassword = "b43cfeda0e8e4bd96561535db8a1d377", salt = "QxLUF1bgIAdeQX"),
    User(id = 3, login = "q", hashPassword = "4a220600490d41af793cbb5c4494e435", salt = "YYLmfY6IehjZMQ")
)

val tableRolesResources = mutableListOf(
    RoleResource(id = 1, role = READ, resource = "A", idUser = 1),
    RoleResource(id = 2, role = WRITE, resource = "A.B.C", idUser = 1),
    RoleResource(id = 3, role = EXECUTE, resource = "A.B", idUser = 2),
    RoleResource(id = 4, role = EXECUTE, resource = "B", idUser = 3),
    RoleResource(id = 5, role = READ, resource = "A.B.C", idUser = 2),
    RoleResource(id = 6, role = WRITE, resource = "A.B", idUser = 2),
    RoleResource(id = 7, role = READ, resource = "A", idUser = 2)
)
val tableActivity: MutableList<Activity> = mutableListOf()