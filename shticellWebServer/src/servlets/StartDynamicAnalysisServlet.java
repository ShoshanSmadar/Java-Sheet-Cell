package servlets;

import adapter.CellDTOAdapter;
import adapter.CoordinateDTOAdapter;
import adapter.RangeDTOAdapter;
import adapter.SheetDTOAdapter;
import cell.CellDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import coordinate.CoordinateDTO;
import engine.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import range.RangeDTO;
import sheet.SheetDTO;
import utils.EngineInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "StartDynamicAnalysisServlet", urlPatterns = {"/dynamicAnalysis/start"})
public class StartDynamicAnalysisServlet extends HttpServlet {
    private HashMap<String, EngineInformation> engineMap;

    @Override
    public void init() throws ServletException {
        super.init();
        // Get the HashMap from the ServletContext
        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sheetName = request.getParameter("sheetName");
        String username = request.getParameter("userName");
        String currentVersionParam = request.getParameter("currentVersion");
        int currentVersion;
        BufferedReader reader = request.getReader();

        // Validate input parameters
        if (sheetName == null || username == null || currentVersionParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing required parameters.");
            return;
        }

        try {
            currentVersion = Integer.parseInt(currentVersionParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid version format.");
            return;
        }

        // Check if the sheet exists in the engine map
        if (!engineMap.containsKey(sheetName) || engineMap.get(sheetName) == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Sheet not found.");
            return;
        }

        List<CellDTO> cellsList;
        try {
            cellsList = engineMap.get(sheetName).getEngine().dynamicAnalysisStart(username, currentVersion - 1);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error processing analysis: " + e.getMessage());
            return;
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
                .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
                .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
                .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
                .create();

        // Write the JSON response
        String json = gson.toJson(cellsList);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
