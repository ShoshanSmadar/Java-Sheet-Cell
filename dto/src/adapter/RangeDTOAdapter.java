package adapter;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import range.RangeDTO;
import java.io.IOException;
import java.util.List;

public class RangeDTOAdapter extends TypeAdapter<RangeDTO> {
    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, RangeDTO range) throws IOException {
        out.beginObject();
        out.name("name").value(range.getName());
        out.name("coordinates");
        if (range.getCoordinates() != null) {
            gson.toJson(range.getCoordinates(), List.class, out);
        } else {
            out.nullValue();
        }
        out.name("sheetName").value(range.getSheetName());
        out.name("rangeValues").value(range.getRangeValues());
        out.endObject();
    }

    @Override
    public RangeDTO read(JsonReader in) throws IOException {
        return gson.fromJson(in, RangeDTO.class);
    }
}