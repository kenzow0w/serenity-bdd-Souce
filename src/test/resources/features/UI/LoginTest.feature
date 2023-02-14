#language: ru

@UI
Функционал: Первый UI тест

  Сценарий: Проверяем авторизацию пользователя, добавление товара в корзину

    Дано текущий пользователь "sauce"
    Затем пользователь переходит на страницу авторизации
    И открывается "Autorizhation page"
    И нажимается кнопка "Login"
    И пользователь авторизуется как "#{currentUser}"

    И открывается "Main store's page"
    И из списка "List for sort items" выбирается элемент с текстом "Name (Z to A)"
    И в переменной "MyItem" сохраняется значение "Sauce Labs Backpack"

    И прокручиваем страницу до элемента из списка "Inventory item name" с текстом из переменной "#{MyItem}"

    И открывается "Menu page"
    И проверяется невидимость элемента "Сart"
    И открывается "Items page"
    И нажимается кнопка "add to cart"
    И проверяется видимость элемента "remove from cart"
    И проверяется, что в корзине находится 1 товар
    И пользователь завершает текущий сеанс