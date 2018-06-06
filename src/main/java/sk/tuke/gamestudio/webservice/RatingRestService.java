package sk.tuke.gamestudio.webservice;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/rating")
public class RatingRestService {

    @EJB
    RatingService ratingService;

    @POST
    //@Path("add")
    public Rating addRating(Rating rating) throws ScoreException {
        ratingService.addRating(rating);
        return rating;
    }

    @GET
    @Path("/{game}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAverageRatings(@PathParam("game") String game){
        return ratingService.getAverageRatings(game);
    }

    @GET
    @Path("/game{game}")
    @Produces("application/json")
    public List<Rating> getListOfCommentsByGame(@PathParam("game") String game){
        return ratingService.getListOfRatings(game);
    }
}
