package epam.lavrynev.rest;

import com.google.gson.Gson;
import epam.lavrynev.dbmodel.ModelUtil;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;

@Path("/MyRest")
public class MyRest {

    // This method is called if TEXT_PLAIN is request
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello(@Context SecurityContext sc,@DefaultValue("Jersey") @QueryParam("name") String name ) {
        System.out.println(sc.getAuthenticationScheme());
        System.out.println(sc.getUserPrincipal().getName());
        System.out.println(sc.isUserInRole("admin"));
        return "Hello "+name;
    }

    // This method is called if XML is request
    @GET
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }

    // This method is called if HTML is request
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
                + "<body><h1>" + "Hello Jersey123" + "</body></h1>" + "</html> ";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayJSONHello(@Context HttpServletRequest request) {
        SessionFactory sessionFactory = (SessionFactory) request.getSession().getServletContext().getAttribute("SessionFactory");
        ModelUtil model = new ModelUtil(sessionFactory);

        return new Gson().toJson(model.getArticles(0,0));
    }


    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void postHello(@FormParam("name") String name, @Context HttpServletResponse servletResponse ) throws IOException {
        System.out.println(name);
        servletResponse.sendRedirect("/");
    }


}
