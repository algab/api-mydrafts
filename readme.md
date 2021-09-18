# MyDrafts

API MyDrafts

To run the API locally you need to create an API Key in [TMDB Developers](https://developers.themoviedb.org/3/getting-started/introduction).

You need to create the following envs on your machine.

```
MONGO_DB
MONGO_USER
MONGO_PASS
MONGO_HOST
API_KEY
```

### Running API

```
mvn spring-boot:run
```

### Running Tests

```
mvn clean test
```

### Running Tests with Coverage

```
mvn clean test jacoco:report
```