package sk.tuke.gamestudio.client;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.weather.CurrentWeather;
import sk.tuke.gamestudio.games.minesweeper.Minesweeper;
import sk.tuke.gamestudio.games.stones.Stones;
import sk.tuke.gamestudio.games.tictactoe.TicTacToe;
import sk.tuke.gamestudio.service.ScoreException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    private static int time = 0;

    public static void main(String[] args) throws ScoreException {
        ScoreRestServiceClient srsc = new ScoreRestServiceClient();
        CommentRestServiceClient crsc = new CommentRestServiceClient();
        RatingRestServiceClient rrsc = new RatingRestServiceClient();
        WeatherRestServiceClient wrsc = new WeatherRestServiceClient();
        ConsoleClient consoleClient = new ConsoleClient();
        CurrentWeather weather;
        weather = wrsc.getWeather();
        wrsc.printWeather(weather);
        do {
            try {
                System.out.println("\n Zdravim ta v gamestudio, vyber si z moznosti: \n" +
                        "M-Minesweeper\n" +
                        "S-Stones\n" +
                        "P-Piskvorky\n" +
                        "X-Exit\n" +
                        "T-Tabulka\n" +
                        "C-Komenty\n" +
                        "R-Ratings");
                String input = readLine().toUpperCase();
                String game = null;
                switch (input) {
                    case "R":
                        System.out.println("1-zoznam ratingov\n 2-priemerny rating");
                        input = readLine().toUpperCase();
                        switch (input) {
                            case "1":
                                System.out.println("\n M-minesweeper ratings\n S-stones ratings\n P-piskvorky");
                                input = readLine().toUpperCase();
                                List<Rating> ratingList = null;
                                if (input.equals("M")) {
                                    ratingList = rrsc.getListOfRatings("minesweeper");
                                }
                                if (input.equals("S")) {
                                    ratingList = rrsc.getListOfRatings("stones");
                                }
                                if (input.equals("P")) {
                                    ratingList = rrsc.getListOfRatings("tic-tac-toe");
                                }
                                for (int i = 0; i < ratingList.size(); i++) {
                                    System.out.println(ratingList.get(i).getPlayer() + " dal hre " + ratingList.get(i).getGame() + " rating " + ratingList.get(i).getRating());
                                }
                                break;
                            case "2":
                                System.out.println("\n M-minesweeper rating\n S-stones rating");
                                input = readLine().toUpperCase();
                                switch (input) {
                                    case "M":
                                        System.out.println("hra minesweeper ma rating: " + rrsc.getAverageRatings("minesweeper"));
                                        break;
                                    case "S":
                                        System.out.println("hra stones ma rating: " + rrsc.getAverageRatings("stones"));
                                        break;
                                    case "P":
                                        System.out.println("hra piskvorky ma rating " + rrsc.getAverageRatings("tic-tac-toe"));
                                }
                        }
                        break;
                    case "C":
                        List<Comment> listComment;
                        System.out.println("Zadaj nazov hry z ktorej chces zobrazit komenty:\"" +
                                "M, P or S");
                        input = readLine().toUpperCase();
                        if (input.equals("M")) {
                            game = "minesweeper";
                        }
                        if (input.equals("S")) {
                            game = "stones";
                        }
                        if (input.equals("P")) {
                            game = "tic-tac-toe";
                        }
                        listComment = crsc.getListOfCommentsByGame(game);

                        for (int i = 0; i < listComment.size(); i++) {
                            System.out.println("hrac " + listComment.get(i).getPlayer() + " napisal komentar \" " + listComment.get(i).getComment() + " \" k hre " + listComment.get(i).getGame());
                        }
                        break;
                    case "T":
                        List<Score> list;
                        System.out.println("chces to podla hry-G alebo playera-P?");
                        input = readLine();
                        if (input.toUpperCase().equals("G")) {
                            System.out.println("pre aku hru to chces minesweeper-M, piskvorky-P  or stones-S");
                            input = readLine().toUpperCase();
                            if (input.equals("M")) {
                                input = "minesweeper";
                            }
                            if (input.equals("S")) {
                                input = "stones";
                            }
                            if (input.equals("P")) {
                                input = "tic-tac-toe";
                            }
                            try {
                                list = srsc.getBestScoresForGame(input);
                            } catch (Exception e) {
                                break;
                            }
                        } else {
                            System.out.println("zadaj meno hraca");
                            input = readLine();
                            list = srsc.getScoresForPlayer(input);
                        }
                        System.out.println("--------------------------------------------");
                        System.out.println("TABULKA SCORE VYZERA:");

                        for (int i = 0; i < list.size(); i++) {
                            System.out.println("hrac " + list.get(i).getPlayer() + " ma score " +list.get(i).getPoints() );
                        }
                        System.out.println("--------------------------------------------");
                        break;
                    case "M":
                        game = "minesweeper";
                        new Minesweeper();
                        time = Minesweeper.getPlayingSeconds();
                        consoleClient.addCommentRating(srsc, crsc, rrsc, game, time);
                        break;
                    case "S":
                        game = "stones";
                        new Stones();
                        consoleClient.addCommentRating(srsc, crsc, rrsc, game, time);
                        break;
                    case "P":
                        game = "tic-tac-toe";
                        new TicTacToe();
                        time = TicTacToe.getPlayingSeconds();
                        consoleClient.addCommentRating(srsc, crsc, rrsc, game, time);
                        break;
                    case "X":
                        System.out.println("ukoncuje sa gamestudio");
                        System.exit(0);
                    default:
                        System.out.println("!!!zly vstup, skus to znova!!!");
                }
            }catch (RuntimeException e){}
        } while (true);
    }

    static private String readLine() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

}
