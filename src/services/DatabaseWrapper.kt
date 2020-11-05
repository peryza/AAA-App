package services

import data.Activity
import data.RoleResource
import data.User
import db.tableActivity
import db.tableRolesResources
import db.tableUsers

class DatabaseWrapper {

    // Получение данных пользователя
    fun getUser(login: String): User {
        val response = tableUsers.find { it.login == login } ?: return User()
        return User(response.id, login, response.hashPassword, response.salt)
    }

    // Проверка доступа
    fun checkAccess(roleResource: RoleResource): Boolean {
        val response = tableRolesResources.filter {
            it.role == roleResource.role && it.idUser == roleResource.idUser
                    && checkResourceAccess(roleResource.resource, it.resource)
        }
        if (response.isEmpty())
            return false
        return true
    }

    // Проверка доступа к ресурсу
    private fun checkResourceAccess(resource: String, realResource: String): Boolean {
        val realResourceLast = realResource.substringAfterLast('.')
        if (realResourceLast in resource.split("."))
            return true
        return false
    }

    // Добавление активности
    fun addActivity(activity: Activity) {
        tableActivity.add(activity)
    }
}