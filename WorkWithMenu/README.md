# Работа с меню

В проекте с прогнозом погоды с помощью меню реализовать переключение языка.

Пример кода.

```java
Locale locale = new Locale("ru");
Locale.setDefault(locale);
Configuration config = getBaseContext().getResources().getConfiguration();
config.locale = locale;
getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
```

## Демонстрация

![gif_1](https://github.com/EkaterinaKugot/Mobile_development/blob/main/WorkWithMenu/result.gif)





