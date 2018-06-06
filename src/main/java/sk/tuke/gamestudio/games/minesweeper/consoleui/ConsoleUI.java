package sk.tuke.gamestudio.games.minesweeper.consoleui;

import sk.tuke.gamestudio.games.minesweeper.Minesweeper;
import sk.tuke.gamestudio.games.minesweeper.UserInterface;
import sk.tuke.gamestudio.games.minesweeper.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**
     * Playing field.
     */
    private Field field;

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Starts the game.
     *
     * @param field field of mines and clues
     */


    @Override
    public void newGameStarted(Field field) {
        System.out.println(ANSI_RED + "\nNazdar " + System.getProperty("user.name") + ANSI_RESET);

        this.field = field;
        do {
            update();
            processInput();
            if (field.getState() == GameState.SOLVED) {

                String name = System.getProperty("user.name");
                System.out.println("SUPER, Gratulujem ti " + name);
                int time = Minesweeper.getInstance().getPlayingSeconds();
                break;
                //System.exit(0);
            }
            if (field.getState() == GameState.FAILED) {
                System.out.println("mozno nabuduce");
                update();
                //System.exit(0);
                break;
            }
        } while (true);

    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        System.out.printf("\n  ");
        for (int i = 1; i < field.getColumnCount() + 1; i++) {
            System.out.printf(ANSI_YELLOW + "%d" + ANSI_RESET, i);
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < field.getRowCount(); i++) {
            System.out.printf(ANSI_YELLOW + "%c" + ANSI_RESET, i + 'A');
            System.out.print(" ");
            for (int j = 0; j < field.getColumnCount(); j++) {
                if ((field.getTile(i, j).getState().equals(Tile.State.OPEN)) && ((field.getTile(i, j) instanceof Mine))) {
                    System.out.print("X ");
                }
                if (field.getTile(i, j).getState().equals(Tile.State.OPEN) && field.getTile(i, j) instanceof Clue) {
                    System.out.print(((Clue) field.getTile(i, j)).getValue() + " ");
                }
                if (field.getTile(i, j).getState().equals((Tile.State.MARKED))) {
                    System.out.print("M ");
                }
                if (field.getTile(i, j).getState().equals(Tile.State.CLOSED)) {
                    System.out.print("- ");
                }

            }
            System.out.println();
        }

        System.out.println("\nPocet zostavajucich min je: " + field.getRemainingMineCount());
        System.out.println("Cas hry je : " + Minesweeper.getInstance().getPlayingSeconds() + "\n");

    }

    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        System.out.println("X – ukončenie hry, MA1 – označenie dlaždice v riadku A a stĺpci 1, OB4 – odkrytie dlaždice v riadku B a stĺpci 4.");
        String input = readLine().toUpperCase();
        try {
            handleInput(input);
        } catch (WrongFormatException e) {
            e.getMessage();
        }
    }

    private void handleInput(String input) throws WrongFormatException {

        Pattern pattern = Pattern.compile("([O,M])([A-I])([0-9])");
        Matcher matcher = pattern.matcher(input);
        if (input.equals("X")) {
            throw new sk.tuke.gamestudio.games.minesweeper.consoleui.ExitException();
        } else if (matcher.matches()) {
            int row = matcher.group(2).charAt(0) - 'A';
            int column = Integer.parseInt(matcher.group(3));
            if (input.charAt(0) == 'M') {
                field.markTile(row, column - 1);
            }
            if (input.charAt(0) == 'O') {
                field.openTile(row, column - 1);
            }

        } else {
            System.out.println("!!! ZLY VSTUP!!!");
            ;
        }
    }
}


