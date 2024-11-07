package adapter;

import com.google.gson.*;
import cell.CellDTO;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import coordinate.CoordinateDTO;
import java.io.IOException;
import java.util.List;

public class CellDTOAdapter extends TypeAdapter<CellDTO> {
    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, CellDTO cell) throws IOException {
        out.beginObject();
        out.name("coordinate");
        if (cell.getCoordinate() != null) {
            gson.toJson(cell.getCoordinate(), CoordinateDTO.class, out);
        } else {
            out.nullValue();
        }
        out.name("originalValue").value(cell.getOriginalValue());
        out.name("effectiveValue");
        if (cell.getEffectiveValue() != null) {
            gson.toJson(cell.getEffectiveValue(), Object.class, out);
        } else {
            out.nullValue();
        }
        out.name("lastVersionChanged").value(cell.getLastVersionChanged());
        out.name("affectedBy");
        if (cell.getAffectedBy() != null) {
            gson.toJson(cell.getAffectedBy(), List.class, out);
        } else {
            out.nullValue();
        }
        out.name("changersName").value(cell.getUserName());
        out.name("affecting");
        if (cell.getAffecting() != null) {
            gson.toJson(cell.getAffecting(), List.class, out);
        } else {
            out.nullValue();
        }
        out.name("sheetName").value(cell.getSheetName());
        out.endObject();
    }

    @Override
    public CellDTO read(JsonReader in) throws IOException {
        return gson.fromJson(in, CellDTO.class);
    }
}