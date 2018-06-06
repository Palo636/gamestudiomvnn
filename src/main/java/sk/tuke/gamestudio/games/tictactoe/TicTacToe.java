package sk.tuke.gamestudio.games.tictactoe;

public class TicTacToe {
    private static TicTacToe instance;
    private Field field = new Field(3, 3);
    private ConsoleUI ui;
    private static long startMillis;


    public TicTacToe(){
        instance = this;
        ui = new ConsoleUI(field);
        newGame();
    }

    public static void main(String[] args) {
        new TicTacToe();


    }

    public void newGame(){
        startMillis = System.currentTimeMillis();
        ui.newGameStarted(field);
    }

    public static TicTacToe getInstance() {
        return instance;
    }

    public static int getPlayingSeconds() {
        int playingTime;
        playingTime = (int) ((System.currentTimeMillis() - startMillis) * 0.001);
        return playingTime;
    }
}
