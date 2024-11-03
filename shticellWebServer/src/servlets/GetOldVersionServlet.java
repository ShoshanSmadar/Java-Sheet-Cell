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

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "GetOldVersionServlet", urlPatterns = {"/version/get"})
public class GetOldVersionServlet extends HttpServlet {
    private HashMap<String, EngineInformation> engineMap;

    @Override
    public void init() throws ServletException {
        super.init();
        // Get the HashMap from the ServletContext
        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String version = request.getParameter("versionNumber");
        String sheetName = request.getParameter("sheetName");
        Engine engine = engineMap.get(sheetName).getEngine();
        if (engine == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Engine information not found.");
            return;
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
                .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
                .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
                .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
                .create();
        try {
            String json = gson.toJson(engine.getOldVersionSheet(Integer.parseInt(version)));
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
        catch (Exception e) {
            response.setStatus(500);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(e.getMessage());
        }
    }
}
