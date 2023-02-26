# MyDrafts

[![API MyDrafts](https://github.com/algab/api-mydrafts/actions/workflows/master.yml/badge.svg)](https://github.com/algab/api-mydrafts/actions/workflows/master.yml)
[![codecov](https://codecov.io/gh/algab/api-mydrafts/branch/master/graph/badge.svg?token=4HHIZGIOLW)](https://codecov.io/gh/algab/api-mydrafts)

API MyDrafts, application to search and view information of movies and tv shows on TMDB and also save movies and tv shows to watch later and write your opinion about them.

To run the API locally you need to create an API Key in [TMDB Developers](https://developers.themoviedb.org/3/getting-started/introduction).

You need to create the following envs on your machine.

```
MONGO_URI
API_KEY
SECRET_JWT
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