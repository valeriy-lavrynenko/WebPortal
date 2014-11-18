package epam.lavrynev.dbmodel;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Created by ASAt on 08.06.14.
 */
public class HibernateManager {
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static final SessionFactory sessionFactory = configSessionFactory();

    private static StandardServiceRegistry serviceRegistry;

    private static SessionFactory configSessionFactory() {
        File file = new File("src/main/resources/hibernate.cfg.xml");
        Configuration configuration = new Configuration().configure(file);

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
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void destroy(){
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }

}
