package epam.lavrynev.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by ASAt on 16.06.14.
 */
@Path("/static")
public class SingleMethodRequests {

    @GET
    @Path("tags")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTags(@Context HttpServletRequest request){
        RestUtil manager = new RestUtil(request);
        return manager.getGson().toJson(manager.getModel().getTagsState());
    }

    @GET
    @Path("searches")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSearches(@Context HttpServletRequest request){
        RestUtil manager = new RestUtil(request);
        return manager.getGson().toJson(manager.getModel().getSearchStatistic());
    }
}
