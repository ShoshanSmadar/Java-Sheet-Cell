package servlets;

import adapter.CellDTOAdapter;
import adapter.CoordinateDTOAdapter;
import adapter.RangeDTOAdapter;
import adapter.SheetDTOAdapter;
import cell.CellDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import coordinate.CoordinateDTO;
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

@WebServlet(name = "AddRangeServlet", urlPatterns = {"/range/add"})
public class AddRangaServlet extends HttpServlet{

    private HashMap<String, EngineInformation> engineMap;

    @Override
    public void init() throws ServletException {
        super.init();
        // Get the HashMap from the ServletContext
        engineMap = (HashMap<String, EngineInformation>) getServletContext().getAttribute("engineMap");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
                .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
                .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
                .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
                .create();

        // Parse the JSON body to a RangeDTO object
        RangeDTO rangeDTO = gson.fromJson(request.getReader(), RangeDTO.class);

        try{
            engineMap.get(rangeDTO.getSheetName()).getEngine().addRange(rangeDTO.getName(), rangeDTO.getRangeValues());
            String json = gson.toJson(engineMap.get(rangeDTO.getSheetName()).getEngine().getSheetDTO());
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }
        catch(Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(e.getMessage());
        }
    }
}
