package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class ConsoleClient {

    public Score createScore(String game, String player, int time) {
        Date date = new Date();
        Score score = new Score();
        score.setGame(game);
        score.setPlayer(player);

        int points = 0;
        switch (game) {
            case "minesweeper":
                points = 500 - time*15;
                break;
            case "stones":
                points = 200 - time*7;
                break;
            case "tic-tac-toe":
                points = 50 - time*2;
        }
        score.setPoints(points);
        score.setPlayedOn(date);
        return score;
    }

    private String readLine() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void addRating(RatingRestServiceClient rrsc, String game, String name) throws ScoreException {
        System.out.println("\n Zadaj rating 1 - 10 lebo bude zle: ");
        int r = 0;
        try {
            r = Integer.parseInt(readLine());
        } catch (NumberFormatException n) {
            System.out.println("zadal si zly rating, skus to znova:");
            addRating(rrsc, game, name);
        }
        if (r > 10 || r < 1) {
            System.out.println("Zadal si zly rating, skus to znova");
            addRating(rrsc, game, name);
        } else {
            Rating rating = new Rating(name, game, r);
            rrsc.addRating(rating);
        }
    }

    public void addCommentRating(ScoreRestServiceClient srsc, CommentRestServiceClient crsc, RatingRestServiceClient rrsc, String game, int time) throws ScoreException {
        String name;
        String input;
        do {
            System.out.println("Zadaj svoje meno");
            name = readLine();
        } while (name.equals(""));
        Score score = createScore(game, name, time);
        srsc.addScore(score);
        do {
            System.out.println("\n Zadaj komentar k hre: ");
            input = readLine();
        } while(    input.equals(""));
        Comment comment = new Comment(input, game, name);
        crsc.addComment(comment);
        addRating(rrsc, game, name);
    }
}
