package db

import data.Activity
import data.RoleResource
import data.Roles.*
import data.User

val tableUsers = mutableListOf(
    User(1, "vasya", "7a5a73db77e24a3964fa333fd43be8bb", "bv5PehSMfV11Cd"),
    User(2, "admin", "b43cfeda0e8e4bd96561535db8a1d377", "QxLUF1bgIAdeQX"),
    User(3, "q", "4a220600490d41af793cbb5c4494e435", "YYLmfY6IehjZMQ")
)

val tableRolesResources = mutableListOf(
    RoleResource(1, READ, "A", 1),
    RoleResource(2, WRITE, "A.B.C", 1),
    RoleResource(3, EXECUTE, "A.B", 2),
    RoleResource(4, EXECUTE, "B", 3),
    RoleResource(5, READ, "A.B.C", 2),
    RoleResource(6, WRITE, "A.B", 2),
    RoleResource(7, READ, "A", 2)
)

val tableActivity: MutableList<Activity> = mutableListOf()