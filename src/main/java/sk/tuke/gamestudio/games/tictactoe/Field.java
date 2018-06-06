package sk.tuke.gamestudio.games.tictactoe;

public class Field {

    private final int rowCount;
    private final int columnCount;
    boolean firstPlayer = true;
    private Tile[][] tiles;
    private GameState state = GameState.PLAYING;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];
        generate();
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Tile getTiles(int row, int column) {
        return tiles[row][column];
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void generate() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tiles[i][j] = new Tile();
            }
        }
    }

    public void changePlayer() {
        firstPlayer = firstPlayer == true ? false : true;
    }

    public void isFull() {
        //boolean isFull = true;
        int n = 0;

        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                if (getTiles(i, j).getState().equals(Tile.State.EMPTY)) {
                    n++;

                }
            }
        }
        state = n > 0 ? GameState.PLAYING : GameState.DRAW;
    }

    private boolean checkRowCol(Tile t1, Tile t2, Tile t3) {
        return (!t1.getState().equals(Tile.State.EMPTY) && t1.getState().equals(t2.getState()) && t2.getState().equals(t3.getState()));
    }

    private boolean checkDiagonalsForWin() {
        return ((checkRowCol(getTiles(0, 0), getTiles(2, 2), getTiles(2, 2))));
    }

    private boolean checkColumsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(getTiles(0, i), getTiles(1, i), getTiles(2, i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(getTiles(i, 0), getTiles(i, 1), getTiles(i, 2))) {
                return true;
            }
        }
        return false;
    }

    public boolean isSolved() {
        return (checkColumsForWin() || checkRowsForWin() || checkDiagonalsForWin());
    }

//    public void whoIsWinner(){
//        if (isSolved()){
//            System.out.println("Mame vytaza!!!");
//            state = firstPlayer ? GameState.SECONDWON: GameState.FIRSTWON;
//            String winner;
//            if(state.equals(GameState.SECONDWON)){
//                winner = "druhy hrac";
//            }else{winner = "prvy hrac";}
//            System.out.println("vyhral " + winner);
//        } else if(state== GameState.DRAW) {
//            System.out.println("Je remiza, nikto nevyhral");
//        }
//    }


}
