# Практическая работа №1 
**Тема работы:** Разработка базового Android-приложения с модульной архитектурой на языке Java.  
**Цель работы:** Получить практические навыки создания Android-приложений с модульной структурой и реализацией пользовательского интерфейса с использованием инструментов Android Studio и языка Java.  
## Подготовка среды и создание проекта
Работа началась с создания нового Android-проекта на Java под названием *Lesson1*. Для полноценной работы были установлены необходимые SDK-компоненты и настроен виртуальный эмулятор устройства на платформе Android.
### Добавление модуля
В рамках модульной архитектуры в проект был добавлен дополнительный модуль с названием *Layouttype*, после чего он был интегрирован в основную структуру приложения.
![image](https://github.com/user-attachments/assets/282f8cd9-b915-4f7b-afd6-9f2c58bdf3c3)
## Элементы интерфейса и их свойства
В основной разметке activity_main.xml был размещён компонент *TextView*, в котором текст был изменён на имя **Maksim Tenyutin** в соответствии с заданием. Это позволило освоить редактирование XML-файлов и работу с визуальным редактором Android Studio. Также были изучены базовые компоненты UI, структура activity, взаимодействие между кодом и визуальной частью, а также методы настройки свойств элементов.
![image](https://github.com/user-attachments/assets/44ee62b3-52ef-4fb8-a8f7-6c9cf3172579)
## Изучение различных типов макетов (Layout)
В процессе выполнения задания были изучены ключевые разновидности макетов, применяемых в Android Studio. Для каждого из них были реализованы практические примеры. В результате был создан интерфейс с элементами *View*, размещёнными с помощью различных контейнеров.
![image](https://github.com/user-attachments/assets/54aac923-ba9f-4a34-979b-f1fb99fa65a1)
![image](https://github.com/user-attachments/assets/51d0887a-d532-4988-9151-5eacc5b327aa)
Далее был создан собственный экран с модулем в activity_main вместе с использованием пройденных и изученных элементов, таких как: Button, TextView, *ImageView*.
![image](https://github.com/user-attachments/assets/88cc0b7c-48d3-44ef-8238-9c50eb604091)
## Поддержка разных ориентаций экрана
Была реализована адаптация интерфейса под разные режимы ориентации экрана. Для этого создан альтернативный файл разметки land/activity_second, обеспечивающий корректное отображение UI при изменении положения устройства.
![image](https://github.com/user-attachments/assets/c33e4593-7a78-4aad-9bd1-c54fda839a1f)
![image](https://github.com/user-attachments/assets/837c638e-a5d0-4e58-9174-226e8e570aa6)
## Работа с элементами программно
Следующим этапом стало изучение программного доступа к компонентам интерфейса. Были рассмотрены способы изменения свойств элементов, таких как TextView и CheckBox, через код. Также был изучен механизм получения ресурсов через класс R.
## Реализация обработки событий
Изучены принципы добавления обработчиков событий для кнопок и других элементов интерфейса. Были реализованы реакции UI на действия пользователя, что является неотъемлемой частью интерактивных приложений.
![image](https://github.com/user-attachments/assets/4c084da3-c85a-47ba-b320-123ef234c322)
![image](https://github.com/user-attachments/assets/b951a2e8-7b7f-4902-9431-a4c5581e9ec3)
## Итоги
В рамках этой работы была получена основа разработки Android-приложений на Java. Освоены базовые принципы модульной структуры, работа с интерфейсом, реализация событий и взаимодействие между визуальной и программной частью приложения.

---

**Работу выполнил**: Тенютин М.М  
**Группа**: БСБО-09-22
