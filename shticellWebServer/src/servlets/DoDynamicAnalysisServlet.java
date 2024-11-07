package servlets;

import adapter.CellDTOAdapter;
import adapter.CoordinateDTOAdapter;
import adapter.RangeDTOAdapter;
import adapter.SheetDTOAdapter;
import cell.CellDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import coordinate.CoordinateDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import range.RangeDTO;
import sheet.SheetDTO;
import utils.EngineInformation;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "DoDynamicAnalysisServlet", urlPatterns = {"/dynamicAnalysis/do"})
@MultipartConfig
public class DoDynamicAnalysisServlet extends HttpServlet {
    private HashMap<String, EngineInformation> engineMap;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
            .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
            .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
            .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
            .create();

    @Override
    public void init() throws ServletException {
        super.init();
        // Get the HashMap from the ServletContext
        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Part cellsPart = request.getPart("cells");
        String cellsJson = new String(cellsPart.getInputStream().readAllBytes());

        // Use TypeToken to deserialize the list of CellDTOs
            Type listType = new TypeToken<List<CellDTO>>(){}.getType();
            List<CellDTO> cells = gson.fromJson(cellsJson, listType);            Part userNamePart = request.getPart("userName");
            String userName = new String(userNamePart.getInputStream().readAllBytes());
            Part sheetNamePart = request.getPart("sheetName");
            String sheetName = new String(sheetNamePart.getInputStream().readAllBytes());

            SheetDTO responseSheet = engineMap.get(sheetName).getEngine().doDynamicAnalysis(userName, cells);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(responseSheet));
    }
}