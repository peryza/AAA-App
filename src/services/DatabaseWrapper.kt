package services

import data.Activity
import data.RoleResource
import data.User
import db.tableActivity
import db.tableRolesResources
import db.tableUsers

class DatabaseWrapper {

    fun getUser(login: String): User {
        val response = tableUsers.find { it.login == login } ?: return User()
        val id: Long = response.id!!
        val hashPassword: String = response.hashPassword!!
        val salt: String = response.salt!!
        return User(id, login, hashPassword, salt)
    }

    fun checkAccess(roleResource: RoleResource): Boolean {
        val response = tableRolesResources.filter {
            it.role == roleResource.role && it.idUser == roleResource.idUser
                    && checkResourceAccess(roleResource.resource, it.resource)
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
        tableActivity.add(activity)
    }
}