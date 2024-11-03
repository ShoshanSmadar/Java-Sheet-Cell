package serielizer;

import com.google.gson.*;
import coordinate.CoordinateDTO;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class CoordinateDTOSerializer implements JsonSerializer<CoordinateDTO> {
    @Override
    public JsonElement serialize(CoordinateDTO src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("row", src.getRow());
        jsonObject.addProperty("col", src.getCol());
        return jsonObject;
    }
}