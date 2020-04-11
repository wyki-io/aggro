# Aggro

[![codecov](https://codecov.io/gh/wyki-io/aggro/branch/master/graph/badge.svg)](https://codecov.io/gh/wyki-io/aggro)

This project aims to store timeseries data and give them back on different time
interval.

## Spotbug

You may need to first run `./mvnw install` before executing `./mvnw spotbugs:check`

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Formatting

This projects uses [ktlint](https://github.com/pinterest/ktlint) to format
Kotlin code. It runs the format during the Maven build.

You can configure your IDE to warn you about this formatting following the link
above.

## Running the application in dev mode 

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev -pl :app
```

You may need to first run `./mvnw install` in order to have the application
dependencies correctly installed in your local Maven repository.

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `aggro-0.1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/aggro-0.1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/aggro-0.1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide.
