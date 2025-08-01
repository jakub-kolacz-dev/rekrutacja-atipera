git clone https://github.# GitHub Repository API

A Spring Boot application that interacts with GitHub's API to retrieve repository and branch information for a specified user.

## Features

- Fetch non-fork repositories for a given GitHub username
- Retrieve branch information for each repository
- RESTful API endpoint for data access

## Prerequisites

- Java 21+
  -com/your-username/github-repository-api.git
  cd github-repository-api

## Build and Run

./gradlew build

./gradlew bootRun

## API Endpoints

Get User RepositoriesGET /api/github/repositories/{username}

Returns all non-fork repositories for the specified GitHub username along with branch information.


[
  {
    "name": "repository-name",
    "owner": {
      "login": "username"
    },
    "fork": false,
    "branches": [
      {
        "name": "master",
        "commit": {
          "sha": "commit-hash"
        }
      }
    ]
  }
]

## Technologies Used
- Spring Boot
- Java 21
- Gradle
- Spock Framework (Testing)
- Lombok

