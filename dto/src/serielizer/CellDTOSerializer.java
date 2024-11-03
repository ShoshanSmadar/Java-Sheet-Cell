package serielizer;

import cell.CellDTO;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class CellDTOSerializer implements JsonSerializer<CellDTO> {
    @Override
    public JsonElement serialize(CellDTO src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("coordinate", context.serialize(src.getCoordinate()));
        jsonObject.addProperty("originalValue", src.getOriginalValue());
        jsonObject.add("effectiveValue", context.serialize(src.getEffectiveValue()));
        jsonObject.addProperty("lastVersionChanged", src.getLastVersionChanged());
        //jsonObject.add("affectedBy", context.serialize(src.getAffectedBy()));
        //jsonObject.add("affecting", context.serialize(src.getAffecting()));
        jsonObject.addProperty("sheetName", src.getSheetName());
        return jsonObject;
    }
}