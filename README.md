# WordlyGame

Created by **Renn Gilbert** for CSC1061 (Computer Science II) @ Arapahoe Community College

[Wordle](https://www.nytimes.com/games/wordle/index.html) clone built with JavaFX.

# Showcase

![The application in its finished state](https://github.com/Kylo33/WordlyGame/assets/56988649/60709ccd-7153-4290-9e10-2c1305285bfd)


# Features

- Statistics view (guess count distribution, win rate, and games played)
- Classic mode (1 word per day)
- Unlimited mode (unlimited words per day, a new one every time)
- Pretty Icons 👯

# What is Wordle?

Wordle is a word game owned by the New York Times. A random 5-letter word is chosen every day, and you are given 6 tries to guess the word. After every guess, you are given feedback about each letter — to make a more informed guess later. It's similar to [Mastermind](https://en.wikipedia.org/wiki/Mastermind_(board_game)) if you have heard of it.

- 🟩 = The letter at this position is the correct letter at the correct space in the chosen word.
- 🟨 = The letter at this position is in the chosen word, but at a different location.
- ⬛ = The letter at this position is not in the chosen word.

You have 6 guesses to get the word correct. The goal is to do it in as few guesses as possible.

# User Manual

After installing and running the application (see the next section), you are ready to rumble!

On the main screen, you can choose either **classic mode** or **unlimited mode**

- In **classic mode**, the chosen solution is unique every day.
  - Choose this to compete with your friends!
- In **unlimited mode**, the solution is chosen at random every time you play.
  - Great for practicing your skills.

After choosing a mode, make a guess. You can type it with your keyboard or click the letters on the bottom of the screen. Press **enter** or the return key on the on-screen keyboard to submit your guess. After you get it right (or take 6 guesses) a you can open the statistics page in the top right corner (a tooltip will pop up for you). The statistics are for bragging rights only.

If you'd like to return to the menu after completing the game, click the icon to the left of the statistics page.

# Getting Up and Running

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
