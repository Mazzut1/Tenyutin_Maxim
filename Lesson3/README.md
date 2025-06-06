# Практическая работа №3
**Тема:** Намерения, обмен данными, вывозы системных приложений и работы с фрагментами в приложения на базе android.  
**Цель работы**
В данной практике была поставлена задача закрепить навыки:
- работы с явными и неявными Intent;
- передачи и получения информации между экранами;
- интеграции встроенных приложений Android (звонки, карты, браузер);
- создания фрагментов с учетом ориентации устройства;
- применения Activity Result API для обмена результатами.
## Работа с намерениями
Создал модуль ItenApp. В нем реализовал пару активностей.
1.	В MainActivity получено текущее системное время и передано во вторую активность (SecondActivity) с помощью Intent.
2.	В SecondActivity отображается текст: “Квадрат значения моего номера по списку в группе составляет число, а текущее время время”.
![image](https://github.com/user-attachments/assets/b5d0b774-bf76-4dc7-b906-230f5e613469)
![image](https://github.com/user-attachments/assets/30bd2de7-bee3-4fbd-8277-6ee9df499dfa)
Также в рамках задания был изучен и реализован механизм Activity Result API, который, в отличие от обычного вызова Intent, позволяет не только запускать другую активность, но и получать от неё результат.
![image](https://github.com/user-attachments/assets/c3efacd5-8c8a-4a87-a95f-c4c74fce76ec)
## Обмен данными между активностями
Для реализации передачи и возврата пользовательских данных создан модуль Favorite Book.
1.	Приложение содержит два экрана:
2.	На первом (MainActivity) размещены текст и кнопка перехода.
3.	На втором (ShareActivity) отображаются любимая книга и цитата разработчика, а также два поля ввода и кнопка возврата.
Данные, введённые пользователем, передаются обратно при помощи Activity Result API.
![image](https://github.com/user-attachments/assets/3318747b-5fa7-4f0e-bb25-ac4b5c100fce)
![image](https://github.com/user-attachments/assets/8959f8af-94ed-4845-bf09-0e316a237525)
![image](https://github.com/user-attachments/assets/355cb4fa-1706-418c-b3ed-e8dc4c1ec163)
## Использование встроенных приложений Android
Создан отдельный модуль SystemIntentApp, демонстрирующий взаимодействие с системными возможностями устройства. Реализованы три кнопки:
- Позвонить — открывает встроенное приложение для набора номера;
- Открыть браузер — запускает браузер по заданной ссылке;
- Открыть карту — вызывает приложение «Карты» с заданными координатами.
Это задание показало, как можно использовать неявные Intent для работы с другими приложениями системы.
![image](https://github.com/user-attachments/assets/478ca0ef-0d9c-4142-95bd-3f0dbfc60218)
![image](https://github.com/user-attachments/assets/d0c94294-3b16-4391-8a96-cc4ce4d481e5)
![image](https://github.com/user-attachments/assets/8b9ca52c-eb0f-4d1a-90b3-5f4c15c1c686)
![image](https://github.com/user-attachments/assets/4bf77945-bad1-4def-bc34-76566c1d48ae)
## Работа с фрагментами
В модуле **SimpleFragmentApp** созданы два отдельных фрагмента:
•	FirstFragment и
•	SecondFragment.
Оба фрагмента имеют собственный текст. В MainActivity реализованы кнопки, которые по нажатию заменяют один фрагмент на другой с помощью фрагментных транзакций.
Дополнительно реализована поддержка ландшафтной ориентации — при повороте экрана отображаются оба фрагмента одновременно, что реализовано через файл layout-land/activity_main.xml.
![image](https://github.com/user-attachments/assets/759277da-62c5-4284-b2d4-298fbfeb90b3)
![image](https://github.com/user-attachments/assets/376a3913-c69b-476b-85cb-b61c50f7807e)
![image](https://github.com/user-attachments/assets/fe2e7623-0cbc-4ac7-86d2-bde44e692158)
## Итог практической работы
По результатам выполнения задания были успешно освоены:
- создание Intent и передача информации между экранами;
- использование Activity Result API для возврата данных;
- вызов встроенных приложений Android (телефон, браузер, карты);
- построение и переключение фрагментов, в том числе с учётом ориентации экрана.
# Дополнительный проект: MireaProject
В рамках самостоятельной части был разработан проект MireaProject на основе шаблона Navigation Drawer Activity.
Проект включает два фрагмента:
•	DataFragment, содержащий сведения о отрасли - Веб-разработка: где мы сегодня и куда движемся;
•	WebViewFragment, в котором реализован встроенный браузер для открытия сайта Mirea
Навигация реализована через:
•	редактирование файла mobile_navigation.xml, куда были добавлены фрагменты;
•	настройку меню в activity_main_drawer.xml, в котором добавлены пункты: Информация и Браузер, с соответствующими иконками.
![image](https://github.com/user-attachments/assets/f7364da6-bea8-4ef9-9ef8-e57caa0cd64d)
![image](https://github.com/user-attachments/assets/c1c38ee0-83ae-461e-a327-dfc8053f6b4a)
![image](https://github.com/user-attachments/assets/21b167e3-225d-4f2c-aa52-5e79fe6568f9)

--- 

**Работу выполнил**: Тенютин М.М  
**Группа**: БСБО-09-22




