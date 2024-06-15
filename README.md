# My Retail Service

This application aggregates data about a product from Cassandra and the RedSky API. 
Cassandra holds the price details, which includes the price and the currency that 
is used for the price. RedSky provides the name of the item.

## Dependencies

* [JDK 8](#jdk-8)
* [Cassandra](#cassandra)
* Redsky API

## Running the app

To run this application locally, JDK 8 and Cassandra must be installed locally. 
Currently, the application assumes that Cassandra is running on `localhost:9042`. 

### JDK 8

To install, following the instructions from [Oracle](http://www.oracle.com/technetwork/pt/java/javase/downloads/index.html).

### Cassandra 

For Mac users, run

```bash
brew install cassandra 
brew services start cassandra
```

Otherwise, read the official documentation from [Cassandra](http://cassandra.apache.org/doc/latest/getting_started/installing.html).

### Starting 

If Cassandra is not running at localhost:9042, the [`application.yml`](/src/main/resources/application.yml) will need to be updated. 

To start locally, run `./gradlew bootRun` or `gradlew bootRun`. 

The app, by default, will be running at `localhost:8080`. 

## Usage

In this section, `${URL}` will be substituted with the location where this app is running. If running locally, it'll be `localhost:8080`. If using the hosted version, it'll be `35.238.117.56:8080`.

### Get product details

`GET ${URL}/product/{id}`

where `{id}` is the product ID. 

Response:
   
```json
{
  "current_price": {
    "currency_code": "string",
    "value": 0
  },
  "id": 0,
  "name": "string"
}
```

### Update product pricing information

`POST ${URL}/product/{id}`

where `{id}` is the product ID. 

Request:

```json
{
  "value": 0,
  "currency_code": "string"
}
```

Response:
   
```json
{
  "value": 0,
  "currency_code": "string"
}
```

Note: If successful, this endpoint will return the request body. 

## Documentation

See the swagger at [`${URL}/swagger-ui.html`](http://35.238.117.56:8080/swagger-ui.html)
