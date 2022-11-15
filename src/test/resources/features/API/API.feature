#language: ru

@API
Функционал: АПИ тесты

  Сценарий: Проверяем получение данных о юзерах через GET-запрос

    И отправляется GET запрос, с названием "getUser" и параметрами:
      | url                  | https://reqres.in/api/users |
      | [formParam]page      | 2                           |
      | [header]Content-Type | application/json            |
    И для REST-запроса "getUser" код ответа равен "200"
    И для REST-запроса "getUser" проверяется тело ответа на наличие значений по jsonPath:
      | $.data[*].[?(@.first_name=="Lindsay")].first_name | ["Lindsay"] |
      | $.page                                            | 2           |

  Сценарий: Проверяем POST-запрос и создание User'a

    И в переменной "user" сохраняется значение "#{random{user-,7}}"
    И в переменной "job" сохраняется значение "#{random{job-,6}}"
    И содержимое JSON файла "newUser.json" сохраняется в переменной "UserJson"
    И для сохраненного Json "UserJson" устанавливаются значения:
      | $.name | #{user} |
      | $.job  | #{job}  |

    И отправляется POST запрос, с названием "createUser" и параметрами:
      | url                  | https://reqres.in/api/users |
      | body                 | #{UserJson}                 |
      | [header]Content-Type | application/json            |

    И для REST-запроса "createUser" код ответа равен "201"
    И для REST-запроса "createUser" проверяется тело ответа на наличие значений по jsonPath:
      | $.name | #{user} |
      | $.job  | #{job}  |

