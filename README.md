# WordlyGame

Created by **Renn Gilbert** for CSC1061 @ Arapahoe Community College

## Instructions to Build

Note: Linux is the only supported OS for this project. I don't use Windows.

First, navigate to the project directory. Then, using Maven, run the following command:

```
mvn clean javafx:jlink
```

(if Maven is not installed, use your package manager to install it — i.e. `sudo apt install maven`)

## Instructions to Run

If you built the project, navigate to `target/wordly-game/`.
If you downloaded the zip from the releases, extract the archive and navigate into it.

In either case, run `bin/launch.sh` to launch the application. You may run it graphically or with the following command:

```
./bin/launch.sh
```
