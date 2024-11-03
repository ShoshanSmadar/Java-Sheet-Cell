package serielizer;

import cell.CellDTO;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import coordinate.CoordinateDTO;
import range.RangeDTO;
import sheet.SheetDTO;

import java.lang.reflect.Type;
import java.util.Map;

public class SheetDTOSerializer implements JsonSerializer<SheetDTO> {
    @Override
    public JsonElement serialize(SheetDTO src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("SheetName", src.getSheetName());
        jsonObject.addProperty("sheetVersion", src.getSheetVersion());
        jsonObject.addProperty("SheetName", src.getSheetName());

        // Manually handle cellMap to avoid deep recursion
        JsonObject cellMapJson = new JsonObject();
        for (Map.Entry<CoordinateDTO, CellDTO> entry : src.getCellMap().entrySet()) {
            cellMapJson.add(entry.getKey().getRow() + "," + entry.getKey().getCol(), context.serialize(entry.getValue()));
        }
        jsonObject.add("cellMap", cellMapJson);

        jsonObject.addProperty("sizeOfColumns", src.getSizeOfColumns());
        jsonObject.addProperty("lengthOfCol", src.getLengthOfCol());
        jsonObject.addProperty("sizeOfRows", src.getSizeOfRows());
        jsonObject.addProperty("heightOfRow", src.getHeightOfRow());

        // Manually handle rangeMap to avoid deep recursion
        JsonObject rangeMapJson = new JsonObject();
        for (Map.Entry<String, RangeDTO> entry : src.getRangeMap().entrySet()) {
            rangeMapJson.add(entry.getKey(), context.serialize(entry.getValue()));
        }
        jsonObject.add("rangeMap", rangeMapJson);

        return jsonObject;
    }
}