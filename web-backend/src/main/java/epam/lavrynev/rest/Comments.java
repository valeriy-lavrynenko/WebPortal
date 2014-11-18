package epam.lavrynev.rest;

import epam.lavrynev.dbmodel.db.CommentsEntity;
import org.codehaus.jackson.JsonNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ASAt on 16.06.14.
 */
@Path("/comments")
public class Comments {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getComments(@Context HttpServletRequest request,@QueryParam("articleId") Integer articleId){
        RestUtil manager = new RestUtil(request);
        return manager.getGson().toJson(manager.getModel().getCommentsByArticle(articleId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setComment(JsonNode jsonRequestBody,@Context HttpServletRequest request ,@Context HttpServletResponse servletResponse){
        RestUtil manager = new RestUtil(request);
        CommentsEntity commentsEntity = manager.getGson().fromJson(jsonRequestBody.toString(),CommentsEntity.class);
        manager.getModel().insertComment(commentsEntity);
        return Response.ok().build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCommentsById(@PathParam("id") Integer id,@Context HttpServletRequest request){
        RestUtil manager = new RestUtil(request);
        return manager.getGson().toJson(manager.getModel().getCommentsById(id));
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCommentsById(@PathParam("id") Integer id, JsonNode jsonRequestBody,@Context HttpServletRequest request ,@Context HttpServletResponse response){
        RestUtil manager = new RestUtil(request);
        CommentsEntity commentsEntity = manager.getGson().fromJson(jsonRequestBody.toString(),CommentsEntity.class);
        manager.getModel().updateComment(id,commentsEntity);
        return Response.ok().build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCommentsAll(@Context HttpServletRequest request,@QueryParam("limit") Integer limit){
        RestUtil manager = new RestUtil(request);
        return manager.getGson().toJson(manager.getModel().getComments(limit));
    }

}
