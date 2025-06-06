# Практическая работа №7
**Тема:** Сетевое взаимодействие в Android: сокеты, HTTPURLConnection а также Firebase Authentication  
В рамках данной практической работы была поставлена задача:
- реализовать клиент-серверное взаимодействие через сокеты (TCP-соединение);
- отправлять HTTP-запросы, обрабатывать JSON-ответы и отображать информацию;
- интегрировать Firebase Authentication для работы с пользователями;
- учитывать особенности мобильных соединений (таймзоны, конвертация форматов и т.п.).

## Получение времени через TCP-сокет (TimeService)
**Реализация:**
- Установлено соединение с сервером точного времени time.nist.gov по порту 13, использован Socket API.
- После установления соединения считывается ответ сервера — строка с датой и временем в формате UTC.
- Полученное значение конвертируется в московское время с учетом часового пояса (Europe/Moscow) с помощью TimeZone и SimpleDateFormat.
- В интерфейсе приложения отображаются два поля: текущая дата и время, полученные от сервера.

![image](https://github.com/user-attachments/assets/16ab2ad8-b65f-45f4-b973-92292a06baa2)

![image](https://github.com/user-attachments/assets/170f7c79-9b14-4bbc-860f-88adfab4d248)

![image](https://github.com/user-attachments/assets/4edbfb92-0dbf-41b2-8592-0b02cf1340db)

**Результат:** пользователь может узнать точное время, синхронизированное с сервером, независимо от системных настроек смартфона.

## Получение IP и погоды через HttpURLConnection и JSON
В данном задании использованы сразу два внешних API:
Шаг 1: Получение внешнего IP
- Отправлен GET-запрос к https://ipinfo.io/json через HttpURLConnection.
- Из JSON-ответа извлечены поля:
    - ip — текущий внешний IP устройства;
    - city, region, country — данные о местоположении;
    - loc — координаты (широта, долгота).
Информация выведена на экран в виде текстовых полей.
________________________________________
Шаг 2: Получение текущей погоды
- Из loc (например, 55.75,37.61) извлекаются координаты.
- Формируется новый запрос к API https://api.open-meteo.com/.
- Из JSON-ответа {"current_weather": {...}} парсятся следующие данные:
    - температура воздуха;
    - скорость и направление ветра;
    - код погодного состояния.

![image](https://github.com/user-attachments/assets/5f4f1db1-2396-4797-8cd0-938ec379fbd5)

## Авторизация через Firebase Authentication
Создан модуль с использованием Firebase Authentication по схеме Email/Password.  
**Функциональность:**
- Регистрация нового пользователя;
- Вход в систему по логину и паролю;
- Выход из системы;
- Отправка email-подтверждения на почту пользователя.
**UI:**
- Интерфейс включает поля Email, Пароль, кнопки Регистрация, Вход, Выход.
- После успешного входа выполняется переход на главный экран приложения.
- При первой регистрации пользователю автоматически отправляется письмо с подтверждением.

![image](https://github.com/user-attachments/assets/eec65e24-28f4-4975-b90f-de740c446573)

![image](https://github.com/user-attachments/assets/3e35876a-c472-4a83-b003-dd928ab869e4)

![image](https://github.com/user-attachments/assets/02b2f1b2-24c4-496f-af38-5af44f903e1e)

![image](https://github.com/user-attachments/assets/b20474cf-6d86-4cb7-b228-0a41ced7e74a)

![image](https://github.com/user-attachments/assets/65e0327d-422c-4c90-b670-2666b287d592)

**Результат:** реализована безопасная аутентификация, соответствующая стандартам Firebase и поддерживающая все базовые операции с учётной записью.

# MireaProject
## Экран авторизации
- Использует Firebase Auth.
- После ввода корректных данных выполняется проверка, регистрация/вход и переход к следующему экрану.
- В случае ошибки — отображается соответствующее сообщение (Toast или Snackbar).
- При выходе пользователь возвращается к экрану входа.

![image](https://github.com/user-attachments/assets/5312965a-014b-48a7-af38-6cd5cc9388cb)

![image](https://github.com/user-attachments/assets/6d925d83-81c1-453c-9231-104e7db6a578)

## Фрагмент с фактом о кошках (Cat Fact Fragment)
•	Выполняется запрос к API https://catfact.ninja/fact.
•	В случае успешного ответа выводится случайный интересный факт о кошках.
•	При отсутствии интернета или ошибке запроса — отображается сообщение «Не удалось получить факт» (с использованием try/catch и проверкой HttpURLConnection.getResponseCode()).

![image](https://github.com/user-attachments/assets/254015b9-84b2-484d-a77c-b7e08bbcdc0a)

## Итоги выполнения

В ходе практической работы №7 были выполнены и протестированы следующие задачи:
Получение точного времени с сервера через TCP-сокет и конвертация в локальное время.
Определение внешнего IP и координат устройства, а также извлечение текущей погоды через HTTP и JSON.

Реализация аутентификации пользователей через Firebase с поддержкой email-подтверждений.

Разработка экранов для авторизации и отображения сетевой информации.
Учет особенностей работы с сетью на Android (тайм-ауты, формат времени, безопасность соединений).

---

**Работу выполнил**: Тенютин М.М  
**Группа**: БСБО-09-22
