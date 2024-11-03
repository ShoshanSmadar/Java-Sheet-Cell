package adapter;

import com.google.gson.stream.JsonReader;
import coordinate.CoordinateDTO;
import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class CoordinateDTOAdapter extends TypeAdapter<CoordinateDTO> {
    @Override
    public void write(JsonWriter out, CoordinateDTO coordinate) throws IOException {
        out.beginObject();
        out.name("row").value(coordinate.getRow());
        out.name("col").value(coordinate.getCol());
        out.endObject();
    }

    @Override
    public CoordinateDTO read(JsonReader in) throws IOException {
        CoordinateDTO coordinate = new CoordinateDTO();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "row" -> coordinate.setRow(in.nextInt());
                case "col" -> coordinate.setCol(in.nextInt());
            }
        }
        in.endObject();
        return coordinate;
    }
}