package sk.tuke.gamestudio.games.minesweeper;


import sk.tuke.gamestudio.games.minesweeper.core.Field;

public interface UserInterface {
    void newGameStarted(Field field);

    void update();
}
