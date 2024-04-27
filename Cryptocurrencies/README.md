# Получение котировок криптовалют с помощью сервисов

Реализуйте приложение, следящее за котировкой вашей любимой криптовалютой.

Пример запроса к бирже (см. ключ в заголовке)

```bash
curl -H "X-CMC_PRO_API_KEY: b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c" -H "Accept: application/json" -d "start=1&limit=5000&convert=USD" -G https://sandbox-api.coinmarketcap/com/v1/cryptocurrency/listings/latest
```

Вариант усложнённый: сохранять историю котировок в списке (дату, время, котировку).

Пример ответа API биржи в виде файла прилагается к заданию.

## Демонстрация

![png_1](https://github.com/EkaterinaKugot/Mobile_development/blob/main/Cryptocurrencies/1.png)
![png_2](https://github.com/EkaterinaKugot/Mobile_development/blob/main/Cryptocurrencies/2.png)



