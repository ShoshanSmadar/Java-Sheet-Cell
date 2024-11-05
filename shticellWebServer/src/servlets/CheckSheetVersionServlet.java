package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineInformation;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "CheckSheetVersionServlet", urlPatterns = {"/sheet/version/check"})

public class CheckSheetVersionServlet extends HttpServlet {
    private HashMap<String, EngineInformation> engineMap;

    @Override
    public void init() throws ServletException {
        super.init();
        // Get the HashMap from the ServletContext
        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sheetName = request.getParameter("sheetName");

        if (sheetName == null || sheetName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Sheet name is required");
            return;
        }


        int number = engineMap.get(sheetName).getEngine().getSheetCurrentVersion();

        response.setContentType("text/plain");
        response.getWriter().write(String.valueOf(number));
    }
}
