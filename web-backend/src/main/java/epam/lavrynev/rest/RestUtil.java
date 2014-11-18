package epam.lavrynev.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import epam.lavrynev.dbmodel.ModelUtil;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ASAt on 16.06.14.
 */
public class RestUtil {
    private HttpServletRequest request;
    private Gson gson;
    private ModelUtil model;
    private SessionFactory sessionFactory;

    public RestUtil(HttpServletRequest request) {
        this.request = request;
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        sessionFactory = (SessionFactory) request.getSession().getServletContext().getAttribute("SessionFactory");
        model = new ModelUtil(sessionFactory);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public Gson getGson() {
        return gson;
    }

    public ModelUtil getModel() {
        return model;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
