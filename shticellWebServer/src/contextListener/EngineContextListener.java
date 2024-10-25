package contextListener;

import engine.Engine;
import engine.EngineImpl;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContextEvent;

import java.util.HashMap;


@WebListener
public class EngineContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // This method is called when the web application is starting up
        HashMap<String, Engine> engineMap = new HashMap<>();
        sce.getServletContext().setAttribute("engineMap", engineMap);
        System.out.println("EngineMap has been initialized and set in the ServletContext.");
    }

}
