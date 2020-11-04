package services

import data.Activity
import data.RoleResource
import data.User
import db.tableActivity
import db.tableRolesResources
import db.tableUsers

class DatabaseWrapper {

    fun getUser(login: String): User {
        val response = tableUsers.find { it["login"] == login } ?: return User()
        val id: Long = response.getValue("id").toLong()
        val hashPassword: String = response.getValue("hashPassword")
        val salt: String = response.getValue("salt")
        return User(id, login, hashPassword, salt)
    }

    fun checkAccess(roleResource: RoleResource): Boolean {
        val response = tableRolesResources.filter {
            it["role"] == roleResource.role.name && it.getValue("idUser").toLong() == roleResource.idUser
                    && checkResourceAccess(roleResource.resource, it.getValue("resource"))
        }
        if (response.isEmpty())
            return false
        return true
    }

    private fun checkResourceAccess(resource: String, realResource: String): Boolean {
        val realResourceLast = realResource.substringAfterLast('.')
        if (realResourceLast in resource.split("."))
            return true
        return false
    }

    fun addActivity(activity: Activity) {
        tableActivity.add(activity.toMap())
    }
}