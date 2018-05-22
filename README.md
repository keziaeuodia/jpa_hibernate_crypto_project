# jpa_hibernate_crypto_project
Uses CryptoCompare's 3rd party API connection to collect and persist crypto currencies data

## Tech Stack
 - Java
 - Spring Boot
 - JPA Hibernate
 - MySQL
 - AWS EC2 & RDS

## Functionality
 - **searchMinute()** calls the CryptoCompare API's minute data, and returns an ArrayList of
the previous 7 days of minutely data for the selected cryptocurrency symbol. 
- **searchHour()** calls the CryptoCompare API's minute data, and returns an ArrayList of
the previous 7 days of hourly data for the selected cryptocurrency symbol. If the param persist = true, checkDuplicate() will be called to check duplicate data on the database and if it returns false, the data will be persisted to the database.
- **searchDay()** calls the CryptoCompare API's minute data, and returns an ArrayList of
the previous 7 days of hourly data for the selected cryptocurrency symbol. If the param persist = true, checkDuplicate() will be called to check duplicate data on the database and if it returns false, the data will be persisted to the database.

## Cryptocurrency Key Endpoints

#### Search & Persist Data 

GET request that searches for the crypto market data minutely, daily, or hourly, for the entered symbol. Params used are fsym (from symbol), tsym (to symbol), limit (the number of data), and persist (whether the data should be persisted to the database). If no symbol is entered as fsym and tsym, the default search is for BTC/USD. If the param persist = true, checkDuplicate() will be called to check duplicate data on the database and if it returns false, the data will be persisted to the database.

```
data/histominute?fsym={SYMBOL}&tsym={SYMBOL}&limit={int LIMIT}&persist={boolean}
data/histohour?fsym={SYMBOL}&tsym={SYMBOL}&limit={int LIMIT}&persist={boolean}
data/histoday?fsym={SYMBOL}&tsym={SYMBOL}&limit={int LIMIT}&persist={boolean}
```

## More Info
For more information on the CryptoCompare API, check out https://www.cryptocompare.com/api/#
