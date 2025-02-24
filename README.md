# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
My Phase02 Diagram: 
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYsgwr5ks9K+KDTC8EDJO8ALnAWspqig5QIAePKwvuh6ouisTYgmhgumGbpkhSBq0uWo4mkS4YWhy3K8gagrCjAQGhmRbqdlh5QGo6zoErhLLlJ63pBgGQYhq60qRhyMDRhJQnaBxibJlBJYoTy2a5pg-4gjBJRXAMxGjAuk59NOs7NuOrZ9N+V4aYU2Q9jA-aDr0OkjnppnVlOQbGfOrltsuq7eH4gReCg6B7gevjMMe6SZJgNkXkU1DXtIACiu5JfUSXNC0D6qE+3RGY26Dtr+ZxAiW+VzmpCnFaxMAIfY4XIWFvpoRimFythXFMTxMDkmAsb+uV6CkUyzFieUVExtJ8hCmEg1oIxI2iZp4JSXWBXzVhGrcaSRgoNwmT9YJcbaMNZoRoUloyHtFKGP18btZ26nlCh4UqQgeZVSxllXBZCVWV2ORgH2A5DkunB+eugSQrau7QjAADio6spFp4xeezCade8NpZl9ijnlHnrUVbJPTAc2VaV1XtfB0LIdCLUYbJHUyNt7o9RSh1zad5FjTAE2rcG2gzWThMVSJ5pJpesHlP1nlM1tXU7cgsSI6MqiwtiCEQP4qsoIyACEAC8irKgtZ0UeNPK2rrDrGJ24tszbMkO190sI6OZjg5tkslamz302oqnqa7WmllMeNq+MlT9BHKAAJLSFHACMvYAMwACxPCemQGhWExfDoCCgA2uejisXyxwAcqO+d7DAjS-ccPuxUDdkg704dI1HFQx6OCfJ2nmdTNn+q6fcfQF0XIAl2P5dPFXNcT3XDeeyunj+Ru2A+FA2DcPAuqZO7owpFFZ6A2yP7lDeDS4-jwSi+gQ4L6MjcdvJlPlHNpbPygkGU8t8oYB8UyLCOAB8UAMyxPLHCis2a9U5g-NA3NRoXXEvzO6Qs6Lkxdj7Gqssibe06otHawCUC61hD-ZBolUEyxtEfPWMlvbvz9vvL0mQ3ofUpiHACndRj93KCnDOMBX7-RbsDByPReHx0TgIwewjV4QwCgESwe0ELJBgAAKQgDyehgRC7FzRufTGaZqiUjvC0WOBM1pziHLvYAKioBwAgAhKAsxY4JxEQUUmX8eh2IcU4lxbi+7SD-qmAoNUABW2i0Cwi0cpFAaJWrQKIWdfCfUBJczNjzGhfMrYC1orNRBWTRoALggLOWhCWawLSeQ9x0gqESxyfzJ2006J1OKUtKWTpAG62SVU4hbM-BaEyOQvxlAAnQCCXw+pHTGnsjYtgYZhgWnABgMkDIqQYBjMcc46A0DmHAnKHEtAHCKapm4WmHoIjOxiLbhIg4Cj16QwCF4exXYvSwGANgXehB4iJBPqjFuF9vqVGSqldKmVjDEwOQBUJ0EukrRANwPAGsYB9IdrxZFcIGnnXme8vA7J9l-iquURAHzTnB1wSCq50LTi3PsqDBRQA
