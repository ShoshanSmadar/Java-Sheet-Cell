package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sheetPermisionHistory.SheetPermissionHistory;
import sheetRights.SheetRights;
import utils.EngineInformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.constants.Constants.READ_PERMISSION;
import static utils.constants.Constants.WRITE_PERMISSION;

@WebServlet(name = "sheetUsersInformationServlet", urlPatterns = {"/sheet/users/information"})
public class sheetUsersInformationServlet extends HttpServlet {
    private HashMap<String, EngineInformation> engineMap;

    @Override
    public void init() throws ServletException {
        super.init();
        // Get the HashMap from the ServletContext
        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sheetName = request.getParameter("sheetName");

        EngineInformation engine = engineMap.get(sheetName);
        List<SheetPermissionHistory> PermissionHistoryList = new ArrayList<>();

        for( Map.Entry<String, SheetRights> entry : engine.getReadingRights().entrySet()){
            PermissionHistoryList.add(new SheetPermissionHistory(entry.getKey(),entry.getValue(), READ_PERMISSION));
        }
        for( Map.Entry<String, SheetRights> entry : engine.getWritingRights().entrySet()){
            PermissionHistoryList.add(new SheetPermissionHistory(entry.getKey(),entry.getValue(), WRITE_PERMISSION));
        }

        String json = new Gson().toJson(PermissionHistoryList);

        try (var outputStream = response.getOutputStream()) {
            outputStream.write(json.getBytes("UTF-8")); // Write the JSON string as bytes
            outputStream.flush(); // Flush the output stream to ensure all data is sent
        }
    }
}
