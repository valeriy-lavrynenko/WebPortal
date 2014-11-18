package epam.lavrynev.rest;

import com.google.gson.internal.LinkedTreeMap;
import epam.lavrynev.dbmodel.db.ArticleStateEntity;
import org.codehaus.jackson.JsonNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by ASAt on 10.06.14.
 */
@Path("/articles")
public class Articles {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getArticles(@Context HttpServletRequest request,
                                @DefaultValue("0") @QueryParam("limit") Integer limit,
                                @DefaultValue("0") @QueryParam("from") Integer fromRow){
        RestUtil manager = new RestUtil(request);
        Map container = new LinkedTreeMap();
        container.put("articles",manager.getModel().getArticles(limit, fromRow));
        container.put("totalArticles",manager.getModel().getArticlesCount());
        return manager.getGson().toJson(container);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String setArticle(JsonNode jsonRequestBody,@Context HttpServletRequest request ,@Context HttpServletResponse servletResponse){
        RestUtil manager = new RestUtil(request);
        ArticleStateEntity articleStateEntity = manager.getGson().fromJson(jsonRequestBody.toString(),ArticleStateEntity.class);
        Integer id = manager.getModel().insertArticle(articleStateEntity);
        Map container = new LinkedTreeMap();
        container.put("article", id);
        return manager.getGson().toJson(container);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getArticlesById(@PathParam("id") Integer id,@Context HttpServletRequest request){
        RestUtil manager = new RestUtil(request);
        return manager.getGson().toJson(manager.getModel().getArticlesById(id));
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateArticleById(@PathParam("id") Integer id, JsonNode jsonRequestBody,@Context HttpServletRequest request ,@Context HttpServletResponse response){
        RestUtil manager = new RestUtil(request);
        ArticleStateEntity articleStateEntity =  manager.getGson().fromJson(jsonRequestBody.toString(),ArticleStateEntity.class);
        manager.getModel().updateArticle(id,articleStateEntity);
        return Response.ok().build();
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public String getArticlesSearch(@Context HttpServletRequest request,
                                    @DefaultValue("0") @QueryParam("limit") Integer limit,
                                    @DefaultValue("0") @QueryParam("from") Integer fromRow,
                                    @QueryParam("q") String query,
                                    @QueryParam("tag") String tag,
                                    @QueryParam("byTitle") Boolean isByTitle,
                                    @QueryParam("byContent") Boolean isByContent
                                    ){
        RestUtil manager = new RestUtil(request);
        Map container = new LinkedTreeMap();
        container.put("articles",manager.getModel().getArticlesBySearch(limit, fromRow, query, tag,isByTitle,isByContent));
        container.put("totalArticles",manager.getModel().getArticlesCount(query,tag,isByTitle,isByContent));
        return manager.getGson().toJson(container);
    }

}
