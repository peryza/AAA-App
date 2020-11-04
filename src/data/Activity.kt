package data

import java.time.LocalDate
import java.time.format.DateTimeParseException

data class Activity(
    val id: Long? = null,
    val role: String,
    val res: String,
    val ds: String,
    val de: String,
    val vol: String
) {

    fun toMap(): Map<String, String> {
        return mapOf(
            "id" to id.toString(),
            "role" to role,
            "res" to res,
            "ds" to ds,
            "de" to de,
            "vol" to vol
        )
    }

    fun hasValidData(): Boolean {
        try {
            LocalDate.parse(ds)
            LocalDate.parse(de)
        } catch (error: DateTimeParseException) {
            return false
        }
        if (vol.toIntOrNull() == null)
            return false
        return true
    }
}