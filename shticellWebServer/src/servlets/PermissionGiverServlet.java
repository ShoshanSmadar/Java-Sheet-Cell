package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import com.google.gson.JsonParseException;

import java.io.OutputStream;
import java.util.Objects;

import static utils.constants.Constants.READ_PERMISSION;

@WebServlet(name = "PermissionGiverServlet", urlPatterns = {"/permission/giver"})
public class PermissionGiverServlet extends HttpServlet {
    private HashMap<String, EngineInformation> engineMap;

    @Override
    public void init() throws ServletException {
        super.init();
        // Get the HashMap from the ServletContext
        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
    }
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read the JSON payload from the request
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        try {
            // Parse the JSON data
            JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
            String name = jsonObject.get("name").getAsString();
            String sheetName = jsonObject.get("sheetName").getAsString();
            String permission = jsonObject.get("permission").getAsString();
            boolean isAllow = jsonObject.get("isAllow").getAsBoolean();

            // Process the data (e.g., allow or deny permission)
            synchronized (engineMap.get(sheetName)){
                if(Objects.equals(permission, READ_PERMISSION))
                {
                    engineMap.get(sheetName).allowReadingPermission(name, isAllow);
                    response.setStatus(HttpServletResponse.SC_OK);
                    writeResponse(response, "{\"status\":\"success\",\"message\":\"Permission allowed\"}");
                }
                else{
                    engineMap.get(sheetName).allowWritingPermission(name, isAllow);
                    response.setStatus(HttpServletResponse.SC_OK);
                    writeResponse(response, "{\"status\":\"success\",\"message\":\"Permission denied\"}");
                }
            }
        } catch (JsonParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeResponse(response, "{\"status\":\"error\",\"message\":\"Invalid JSON\"}");
        }
    }

    private void writeResponse(HttpServletResponse response, String jsonResponse) throws IOException {
        try (OutputStream os = response.getOutputStream()) {
            os.write(jsonResponse.getBytes("UTF-8"));
            os.flush();
        }
    }
}
