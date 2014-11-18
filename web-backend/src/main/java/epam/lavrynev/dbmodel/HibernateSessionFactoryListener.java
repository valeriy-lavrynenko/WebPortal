package epam.lavrynev.dbmodel;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



/**
 * Created by ASAt on 10.06.14.
 */

public class HibernateSessionFactoryListener implements ServletContextListener {

    public final Logger logger = Logger.getLogger(HibernateSessionFactoryListener.class);

    public ServiceRegistry serviceRegistry;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        logger.info("Hibernate Configuration created successfully");

        configuration.setSessionFactoryObserver(
                new SessionFactoryObserver() {
                    @Override
                    public void sessionFactoryCreated(SessionFactory factory) {}
                    @Override
                    public void sessionFactoryClosed(SessionFactory factory) {
                        ((StandardServiceRegistryImpl) serviceRegistry).destroy();
                    }
                }
        );

        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        logger.info("ServiceRegistry created successfully");

        SessionFactory sessionFactory = configuration
                .buildSessionFactory(serviceRegistry);
        logger.info("SessionFactory created successfully");

        servletContextEvent.getServletContext().setAttribute("SessionFactory", sessionFactory);
        logger.info("Hibernate SessionFactory Configured successfully");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent.getServletContext().getAttribute("SessionFactory");
        if(sessionFactory != null && !sessionFactory.isClosed()){
            logger.info("Closing sessionFactory");
            sessionFactory.close();
        }
        logger.info("Released Hibernate sessionFactory resource");
    }
}
