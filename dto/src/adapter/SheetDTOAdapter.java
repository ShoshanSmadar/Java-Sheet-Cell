package adapter;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import sheet.SheetDTO;
import cell.CellDTO;
import coordinate.CoordinateDTO;
import range.RangeDTO;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SheetDTOAdapter extends TypeAdapter<SheetDTO> {
    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, SheetDTO sheet) throws IOException {
        out.beginObject();
        out.name("SheetName").value(sheet.getSheetName());
        out.name("sheetVersion").value(sheet.getSheetVersion());

        out.name("cellMap");
        if (sheet.getCellMap() != null) {
            out.beginObject();
            for (Map.Entry<CoordinateDTO, CellDTO> entry : sheet.getCellMap().entrySet()) {
                String key = gson.toJson(entry.getKey(), CoordinateDTO.class);
                out.name(key);
                if (entry.getValue() != null) {
                    gson.toJson(entry.getValue(), CellDTO.class, out);
                } else {
                    out.nullValue();
                }
            }
            out.endObject();
        } else {
            out.nullValue();
        }

        out.name("sizeOfColumns").value(sheet.getSizeOfColumns());
        out.name("lengthOfCol").value(sheet.getLengthOfCol());
        out.name("sizeOfRows").value(sheet.getSizeOfRows());
        out.name("heightOfRow").value(sheet.getHeightOfRow());

        out.name("rangeMap");
        if (sheet.getRangeMap() != null) {
            gson.toJson(sheet.getRangeMap(), new TypeToken<Map<String, RangeDTO>>() {}.getType(), out);
        } else {
            out.nullValue();
        }
        out.endObject();
    }

    @Override
    public SheetDTO read(JsonReader in) throws IOException {
        SheetDTO sheet = new SheetDTO();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "SheetName" -> sheet.setSheetName(in.nextString());
                case "sheetVersion" -> sheet.setSheetVersion(in.nextInt());
                case "cellMap" -> {
                    sheet.setCellMap(new HashMap<>());
                    in.beginObject();
                    while (in.hasNext()) {
                        CoordinateDTO key = gson.fromJson(in.nextName(), CoordinateDTO.class);
                        CellDTO value = gson.fromJson(in, CellDTO.class);
                        sheet.getCellMap().put(key, value);
                    }
                    in.endObject();
                }
                case "sizeOfColumns" -> sheet.setSizeOfColumns(in.nextInt());
                case "lengthOfCol" -> sheet.setLengthOfCol(in.nextInt());
                case "sizeOfRows" -> sheet.setSizeOfRows(in.nextInt());
                case "heightOfRow" -> sheet.setHeightOfRow(in.nextInt());
                case "rangeMap" -> sheet.setRangeMap(gson.fromJson(in, new TypeToken<Map<String, RangeDTO>>() {
                }.getType()));
            }
        }
        in.endObject();
        return sheet;
    }
}