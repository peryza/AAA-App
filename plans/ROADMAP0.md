1. Создаем консольный проект HelloWorld с файлом Main.kt и функцией main
и после запуска видим строку в терминале
2. Тестовые сценарии
2.1. Тестовые данные
Аккаунты:
admin - 0000
user - zzz

Авторизации:
admin READ A
admin READ B
admin READ C
admin WRITE A
admin WRITE B
admin WRITE C
admin EXECUTE A
admin EXECUTE B
admin EXECUTE C

user READ A
user EXECUTE A.B
user WRITE XY.UV.ABCDEFGHIJ

2.2. Тестовые сценарии
2.2.0. Создаём скритпы
build.sh для сборки jar файла
test.sh для запуска тестов

2.2.1. Вывод справки:
T1.1 run.sh (0 - выводится справка)
T1.2 run.sh -h (1 - выводится справка)

2.2.2. Аутентификация
T2.1 run.sh -login admin -pass 0000 (0 - успешный логин)
T2.2 run.sh -pass 0000 -login admin (0 - успешно)
T2.3 run.sh -login ??? -pass 123 (2 - неверный формат)
T2.4 run.sh -login Admin -pass 0000 (3 - неизвестный логин)
T2.5 run.sh -login admin -pass 1111 (4 - неверный пароль)

2.2.3. Авторизация
T3.1 run.sh -login admin -pass 0000 -role READ -res A (0 - успешная авторизация)
T3.2 run.sh -login admin -pass 0000 -role EXECUTE -res B (0 - успешная авторизация)
T3.3 run.sh -login admin -pass 0000 -role WRITE -res C (0 - успешная авторизация)

T3.4 run.sh -login user -pass zzz -role READ -res A (0 - успешная авторизация)
T3.5 run.sh -login user -pass zzz -role READ -res A.B (0 - успешная авторизация)
T3.6 run.sh -login user -pass zzz -role EXECUTE -res A.B.C (0 - успешная авторизация)
T3.7 run.sh -login user -pass zzz -role WRITE -res XY.UV.ABCDEFGHIJ (0 - успешная авторизация)
T3.8 run.sh -login admin -pass 0000 -role WRITE -res A.B.C.D (0 - успешная авторизация)

T3.9 run.sh -login admin -pass 0000 -role DELETE -res A (5 - неизвестная роль)
T3.10 run.sh -login user -pass zzz -role WRITE -res XY (6 - нет доступа)
T3.10 run.sh -login user -pass zzz -role WRITE -res D (6 - нет доступа)

T3.11 run.sh -login user -pass zzz -role EXECUTE -res A.BC (6 - нет доступа)

2.2.4. Аккаунтинг
...

2.3. Написать скрипт для запуска тестов
и сравнения результатов
2.3.1 Подсчитать количество прошедших и упавших тестов
2.3.2 Для интеграции с тревисом
Если количество упавших тестов > 0 то возвращаем 1
Если количество упавших тестов = 0 то возвращаем 0

2.4 Интегрировать проект на гитхабе с travis-ci

3. Упрощенный разбор командной строки

Допущение, на этом моменте программа
проверяет только количество параметров
параметры переданы правильные
не проверяет порядок этих параметров
считывает значения параметров из фиксированных позиций 0, 1, 3, 5, 7, 9, 11, 13 (напр. логин всегда в первой ячейке)
0
1 (-h)
4 (-login xxx -pass yyy)
8 (-login xxx -pass yyy -role aaa -res bbb)
14 (-login xxx -pass yyy -role aaa -res bbb -ds fff -de ggg -vol hhh)

3.1 создать метод проверки количества параметров checkAmountParams(args: Array<String>): Bool
проверим что количество аргументов 0,1,4,8 или 14
(Методы должны быть по-возможности без
side-effects - не должен менять состояние окружени,
stateless - запуск метода не должен зависеть от внешнего состояния,
idempotent - можно метод запускать несколько раз и на одни и те же входящие данные мы получаем тот же результат
)
3.2 создать data class Arguments(
    val h: Boolean,
    val login: String?,
    val pass: String?,
    val role: String?,
    val res: String?,
    val ds: String?,
    val de: String?,
    val vol: String?
)
3.3 создать метод в файле Main.kt который будет доставать значения из аргументов и раскладывать по полям parseValues(args: Array<String>): Arguments
3.4 вызвать parseValues в методе main и записать его в переменную val arguments

4. Вывод справки
4.1 в классе Main.kt создаём метод printHelp() который выведет справку
4.2 в классе Arguments создаём метод который проверяет что ни одного параметра нет isEmpty():Boolean
4.3 в классе Arguments создаём метод который проверяет есть параметр -h hasHelp():Boolean
4.4 в метод main дописываем оператор when который будет реагировать на количество входных параметров
4.4.1 добавляем проверки на справку
when {
    arguments.isEmpty() -> {
        printHelp()
        System.exit(1)
    }
    arguments.hasHelp() -> {
        printHelp()
        System.exit(1) // В kotlinx.cli вы не сможете повлиять на код возврата который будет всегда 0
    }
}

? Метод с регулярками

4. Написать код для аутентификации
4.1 В Arguments добавляем метода hasAuthentification(): Boolean
4.2 Добавляем проверку в when
arguments.hasAuthentification() -> {
    // TODO
}
4.3 создаем метод внутри main authenticate(login: String, pass: String): Long который будет проводить аутентификацию 
и возвращать код возврата
4.4 внутрь hasAuthentification добавить вызов authenticate
arguments.hasAuthentification() -> {
    val code = authenticate(arguments.login, arguments.pass) // code 0, 2, 3 или 4
    System.exit(code) // Когда начнем переписывать нам придется переделать этот момент
}
4.5 создать метод внутри main isLoginValid(): Boolean который будет проверять на формат логина
4.6 внутри authenticate вызываем isLoginValid и если результат false то возвращаем 2 иначе проверяем дальше

4.7 Создаём "базу данных" пользователей

4.7.1 Создаём класс UserDB (потом мы его переименуем в UserService)
4.7.2 В классе UserDB создаём метод hasLogin(login: String): Boolean который проверяет наличие логина // Метод пустой на текущий момент
4.7.3 В методе main создать экземпляр класса UserDB и передать его внутрь метода authenticate
4.7.4 внутри authenticate вызываем userDB.hasLogin если вернул false возвращаем 3 иначе обрабатываем дальше
4.7.5 В классе UserDB создаём метод findPasswordByLogin(login: String): String // Метод пустой на текущий момент
4.7.6 внутри authenticate проверяем совпадает ли пароль с найденным в базе validatePassword(passArg: String, passDB: String): Boolean
если false то вернём 4 иначе вернём 0

// Абстракция и Инкапсуляция позволяет мысленно сосредотачиваться на чем-то одном
4.8 Методы внутри базы данных
4.8.1 Мы должны создать класс User
data class User(
    val id: Long,
    val login: String,
    val pass: String
)
4.8.2 Создаем коллекцию users внутри класса UserDB (иммутабельный список) юзеров и заполняем его двумя тестовыми записями
4.8.3 Заменяем заглушку внутри метода hasLogin на реальный код:
перебирам элементы users и сравниваем логин (ФВП any)
4.8.4 Заменяем заглушку внутри метода findPasswordByLogin на реальный код:
перебираем элементы users и ищем пользователя по логину и возвращаем пароль из найденного пользователя

4.9. Получаем код возврата функции authenticate в main, делаем проверку - если не 0, то завершаем приложение.

4.10.1 Создаем метод внутри main authorization(login: String, role: String, res: String): Long который будет проводить аутентификацию 
     и возвращать код возврата.
4.10.2 В Arguments добавляем метода hasAuthorization(): Boolean
4.10.3 В main создаем проверку на возможность авторизации через arguments.hasAuthorization(), если false - завершаем приложение с кодом 0, иначе продолжаем


4.11 Мы должны создать класс RoleResource
       data class RoleResource(
           val login: String,
           val role: String,
           val recourse: String
       )
     
4.12.1 Создаем класс RoleRecourseDB.
4.12.2 В классе RoleRecourseDB создаем метод checkResourceAccess(resource: String, realResource: String): Boolean, который проверяет досту к ресурсу
4.12.3 В классе RoleRecourseDB создаем метод checkAccess(roleResource: RoleResource): Boolean, который проверяет доступ по роли и ресурсу
4.12.4 Создаем коллекцию rolesResources внутри класса RoleRecourseDB (иммутабельный список) заполняем его тестовыми записями

4.13 В методе main создать экземпляр класса RoleRecourseDB и передать его внутрь метода authorization

4.14 Создать класс ролями
enum class Roles() {
    READ,
    WRITE,
    EXECUTE
}
4.15.1 Вызываем authorization в main
4.15.2 Внутри authorization проверяем
try {
        Roles.valueOf(roleString)
    } catch (error: java.lang.IllegalArgumentException) {
        Возвращаем код 5
    }
    
4.15.3 Внутри authorization после try вызываем RoleRecourseDB.checkResourceAccess если вернул false возвращаем 6 иначе возвращаем 0

4.16 Получаем код возврата функции authorization в main, делаем проверку - если не 0, то завершаем приложение.

4.17.1 Мы должны создать класс Activity
            data class Activity(
                val id: login = String,
                    val role: String,
                    val res: String,
                    val ds: String,
                    val de: String,
                    val vol: String
            )
4.17.2 В классе Activity сделать метод hasValidData(): Boolean - для проверки валидности даты

4.18.1 Создаем класс ActivityDB.
4.18.2 Создаем коллекцию activities внутри класса ActivityDB (иммутабельный список)
4.18.3 В классе ActivityDB создаем метод добавление активности в activities - addActivity(activity)

4.19.1 Создаем метод внутри main accounting(activity: Activity): Long который будет проводить аккаунтинг и возвращать код возврата.
4.19.2 В Arguments добавляем метода hasAccounting(): Boolean
4.19.3 В main создаем проверку на возможность аккаунтинга через arguments.hasAccounting(), если false - завершаем приложение с кодом 0, иначе продолжаем
4.19.4 В методе main создать экземпляр класса ActivityDB и передать его внутрь метода accounting

4.21.1 В main создаем экземпляр activity класса Activity
4.21.2 Вызываем accounting(activity) в main 
4.21.3 Внутри accounting вызвать метод activity.hasValidData, если false, то возвращаем 7, иначе продолжаем  
4.21.4 Внутри accounting вызвать ActivityDB.addActivity(activity), возвращаем 0

4.22 Получаем код возврата функции accounting в main, делаем проверку - если не 0, то завершаем приложение, иначе заверашем приложение с кодом 0.



