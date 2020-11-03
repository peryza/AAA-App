package services

import data.*
import db.*

class DatabaseWrapper {

    fun getUser(login: String): User {
        val response = tableUsers.find { it["login"] == login }
        if (response == null) {
            terminate(exitCode = ExitCodes.UnknownLogin.exitCode, printHelp = false)
        }
        val id: Int = response!!.getValue("id").toInt()
        val hashPassword: String = response.getValue("hashPassword")
        val salt: String = response.getValue("salt")
        return User(id, login, hashPassword, salt)
    }

    private fun checkAccess(roleResource: RoleResource): Boolean {
        val response = tableRolesResources.filter {
            it["role"] == roleResource.role && it.getValue("idUser").toInt() == roleResource.idUser
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