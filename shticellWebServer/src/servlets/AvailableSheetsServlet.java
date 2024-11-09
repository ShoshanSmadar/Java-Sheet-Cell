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
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import range.RangeDTO;
import sheet.SheetDTO;
import utils.EngineInformation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.System.out;

@WebServlet (name = "AvailableSheetsServlet", urlPatterns = {"/sheet/available"})
public class AvailableSheetsServlet extends HttpServlet {
    private HashMap<String, EngineInformation> engineMap;

    @Override
    public void init() throws ServletException {
        super.init();
        // Get the HashMap from the ServletContext
        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        try(PrintWriter out = response.getWriter()) {
//            out.write(serializeMap());
//        }
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userName = request.getParameter("userName");

        try (PrintWriter out = response.getWriter()) {
            if (engineMap == null || engineMap.isEmpty()) {
                response.getWriter().write("[]"); // Send an empty JSON array to the client
                return;
            }
            if (engineMap == null || engineMap.isEmpty()) {
                // Send an empty JSON array if engineMap is null or empty
                out.write("[]");
            } else {
                // Serialize the map if it's not null or empty
                out.write(serializeMap(userName));
            }
        }
    }


    public String serializeMap(String userName) {
        List<Map<String, Object>> sheetInfoList = new ArrayList<>();

        for (Map.Entry<String, EngineInformation> entry : engineMap.entrySet()) {
            Map<String, Object> sheetInfo = new HashMap<>();
            sheetInfo.put("key", entry.getKey());
            sheetInfo.put("sheetOwner", entry.getValue().getSheetOwner());
            sheetInfo.put("fileSize", entry.getValue().getFileSize());
            sheetInfo.put("userPermission", entry.getValue().getCurrentUserPermission(userName));

            sheetInfoList.add(sheetInfo);
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
                .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
                .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
                .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
                .create();
        return gson.toJson(sheetInfoList);
    }

    private List<String> getSheetsName(){
        List<String> stringList = new ArrayList<>();
        for(String key : engineMap.keySet()){
            stringList.add(key);
        }
        return stringList;
    }
}
