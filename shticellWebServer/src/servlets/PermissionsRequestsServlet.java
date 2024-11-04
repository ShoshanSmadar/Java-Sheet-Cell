package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static utils.constants.Constants.READ_PERMISSION;

@WebServlet(name = "PermissionsRequestsServlet", urlPatterns = {"/permission/permission"})
@MultipartConfig
public class PermissionsRequestsServlet extends HttpServlet {
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

        Map<String, String> requests = engineMap.get(sheetName).getAllRequests();

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Convert map to JSON
        String json = new Gson().toJson(requests);
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String sheetName = request.getParameter("sheetName");
//        String requestType = request.getParameter("requestType");
//        String userName = request.getParameter("userName");
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        // Now you can parse the parameters from the request body
        String[] params = sb.toString().split("&");
        String sheetName = null;
        String requestType = null;
        String userName = null;

        for (String param : params) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                switch (pair[0]) {
                    case "sheetName":
                        sheetName = pair[1];
                        break;
                    case "requestType":
                        requestType = pair[1];
                        break;
                    case "userName":
                        userName = pair[1];
                        break;
                }
            }
        }

        synchronized (engineMap.get(sheetName)) {
            if(requestType.equals(READ_PERMISSION)) {
                engineMap.get(sheetName).askReadingPermission(userName);
            }
            else
                engineMap.get(sheetName).askWritingPermission(userName);
        }

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
