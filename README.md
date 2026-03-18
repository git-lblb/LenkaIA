# LenkaIA 2 (JavaFX)

This is a JavaFX application. The entry point is `Main` (see `src/Main.java`).

## Prerequisites

- Java Development Kit (JDK) **24** (the project is compiled with `--release 24`)
- Apache Maven (`mvn`)

To confirm your setup:

```sh
java -version
mvn -version
```

## Run (recommended)

From the project root:

```sh
mvn javafx:run
```

If you want a clean rebuild first:

```sh
mvn clean javafx:run
```

## Build (compile only)

```sh
mvn clean compile
```

Compiled classes will be in `target/classes`.

## Run the prebuilt JAR (optional)

There is a `lenkaia.jar` in the repo root. You can try:

```sh
java -jar lenkaia.jar
```

If this fails due to missing JavaFX runtime/native libraries, use the Maven run command (`mvn javafx:run`) instead.
