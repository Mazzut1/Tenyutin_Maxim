# Практическая работа №2 
**Тема:** Жизненный цикл Activity, переходы между экранами, диалоги и уведомления в Android-приложениях  
**Цель практической работы:**
Укрепить навыки в следующих областях:
- использование инструментов отладки в Android Studio;
- понимание этапов жизненного цикла Activity и методов сохранения состояния;
- работа с Intent (явными и неявными) для взаимодействия между экранами и приложениями;
- вывод информации пользователю при помощи Toast, Notification и DialogFragment.
## Отладка приложений
В Android Studio был изучен режим отладки Debug, который дает возможность пошагового выполнения программы, установки точек останова и наблюдения за переменными.
Также применялся Android Profiler для мониторинга работы CPU, использования памяти и сетевой активности в реальном времени.
Через Logcat выводились диагностические сообщения с использованием Log.d(TAG, "сообщение") и других уровней: Debug, Info, Warning, Error.
## Жизненный цикл Actitvity
Был создан модуль ru.mirea.tenyutin.lesson2 с модулем ActivityLifecycle. 
В MainActivity переопределены все ключевые методы жизненного цикла: onCreate, onStart, onResume, onPause, onStop, onDestroy, onRestart, onSaveInstanceState, onRestoreInstanceState. Добавлен элемент EditText в activity_main.xml. В каждом методе логгирование состояния через Log.i(TAG, "onStart()") и другие сообщения. 
![image](https://github.com/user-attachments/assets/5fa2014d-8b5d-47f5-b7d4-74d68a489cca)

**Ответы на вопросы:**
1.	Будет ли вызван метод onCreate после нажатия на кнопку Home и возврата в приложение?
 Нет, будет вызван onRestart(), затем onStart() и onResume(), но не onCreate(), так как активность не уничтожалась.
2.	Изменится ли значение поля EditText после нажатия на кнопку Home и возврата в приложение?
Нет, не изменится. Значение сохранится, так как активность не была уничтожена.
3.	Изменится ли значение поля EditText после нажатия на кнопку Back и возврата в приложение?
Да, изменится. Активность уничтожается (onDestroy()), и при повторном запуске вызывается onCreate(), поле сбрасывается в исходное состояние.
## Создание и вызов Activity
### Явные намерения
Создан модуль MultiActivity, в котором реализована вторая активность SecondActivity. В MainActivity добавлена кнопка, по нажатию на которую срабатывает переход на SecondActivity с помощью явного Intent.
Также реализовано поле EditText и кнопка «Отправить». Введённый текст передаётся через intent.putExtra() и отображается во втором окне в TextView. 
![image](https://github.com/user-attachments/assets/07b06132-9a75-4b75-9183-d0b2e9da7e4d)
![image](https://github.com/user-attachments/assets/4f430ac8-9d18-47fd-a0d5-18c030c450b4)
### Неявные намерения
В модуле IntentFilter реализованы 2 кнопки. В MainActivity реализована кнопка, открывающая браузер по ссылке https://www.mirea.ru/ с использованием Intent.ACTION_VIEW. 
Вторая кнопка, выполняющая передачу ФИО с помощью Intent.ACTION_SEND и Intent.EXTRA_TEXT.
![image](https://github.com/user-attachments/assets/f5287af7-264e-4f8b-8887-7560649c3e88)
![image](https://github.com/user-attachments/assets/c326d480-4025-4e4a-9947-eefe6ccdb3a5)
![image](https://github.com/user-attachments/assets/5f9825a7-46cf-46cd-a759-a90b853019b4)
## Диалоговые окна
Toast - уведомления: В модуле ToastApp добавлено поле EditText и кнопка. При нажатии отображается сообщение (Toast) с количеством введённых символов. 
![image](https://github.com/user-attachments/assets/22d3f3e9-35ff-44e2-a2db-9153daff5c8d)
## Notification
Создан модуль NotificationApp, в котором реализована кнопка, по нажатию на которую появляется системное уведомление, сформированное через NotificationCompat.Builder.
![image](https://github.com/user-attachments/assets/b2917f38-05d7-4b96-ab95-4088fcae7ca2)
![image](https://github.com/user-attachments/assets/69f60894-bd9d-4bc2-b502-53270146b9a5)
## Диалоговые окна
В модуле Dialog реализован AlertDialogFragment, наследующий DialogFragment. В MainActivity реализован диалог с тремя вариантами выбора: «Иду дальше», «На паузе», «Нет». В зависимости от выбора выводится соответствующее сообщение. 
![image](https://github.com/user-attachments/assets/91bcf082-d31c-486a-a0c0-a82f6973396f)
![image](https://github.com/user-attachments/assets/5f9040e9-413f-47f2-9314-a03949dfb3c4)
## Самостоятельная работа
Были реализованы классы: MyTimeDialogFragment, MyDateDialogFragment, MyProgressDialogFragment. В разметке добавлены 3 кнопки, каждая кнопка открыват соответствующее диалоговое окно:
- Ввод времени (TimeDialog)
- Ввод даты (DateDialog)
- Прогресс (ProgressDialog)
![image](https://github.com/user-attachments/assets/d0b90b38-dd33-4b63-bde2-f66aeee5740f)
![image](https://github.com/user-attachments/assets/1b8d5231-0395-4ec3-83fd-3f458389f4ce)
![image](https://github.com/user-attachments/assets/8f5ebc06-5a58-4159-9a5d-e7411b045cbb)
![image](https://github.com/user-attachments/assets/6920ffb5-9108-4ca1-8502-21ab08c771d1)
## Заключение
В ходе выполнения данной практической работы были освоены:
- средства отладки и логирования в Android Studio;
- основные этапы жизненного цикла Activity;
- реализация переходов между экранами с помощью Intent;
- вывод информации пользователю с помощью Toast, Notification и различных диалогов;
- базовые принципы работы с интерфейсом и навигацией между активностями.

---

**Работу выполнил**: Тенютин М.М  
**Группа**: БСБО-09-22
