package serielizer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import range.RangeDTO;

import java.lang.reflect.Type;

public class RangeDTOSerializer implements JsonSerializer<RangeDTO> {
    @Override
    public JsonElement serialize(RangeDTO src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getName());
        jsonObject.add("coordinates", context.serialize(src.getCoordinates()));
        jsonObject.addProperty("sheetName", src.getSheetName());
        jsonObject.addProperty("rangeValues", src.getRangeValues());
        return jsonObject;
    }
}