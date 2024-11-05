package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineInformation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "SheetHistoryServlet", urlPatterns = {"/sheet/history/get"})
@MultipartConfig
public class SheetHistoryServlet extends HttpServlet {
    private HashMap<String, EngineInformation> engineMap;

    @Override
    public void init() throws ServletException {
        super.init();
        // Get the HashMap from the ServletContext
        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the sheetName parameter from the request
        String sheetName = request.getParameter("sheetName");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Example data based on the sheetName; replace with your actual logic
        List<String> history = engineMap.get(sheetName).getHistory();

        // Convert list to JSON
        String json = new Gson().toJson(history);
        out.print(json);
        out.flush();
    }
}
