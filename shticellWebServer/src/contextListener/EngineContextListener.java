package contextListener;

import engine.Engine;
import engine.EngineImpl;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContextEvent;


@WebListener
public class EngineContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // This method is called when the web application is starting up
        Engine engine = new EngineImpl(); // Initialize your engine here
        sce.getServletContext().setAttribute("engine", engine);
        System.out.println("Engine has been initialized and set in the ServletContext.");
    }

}
