package range;

import coordinate.CoordinateDTO;

import java.util.List;

public class RangeDTO {
    String name;
    List<CoordinateDTO> coordinates;

    public RangeDTO(List<CoordinateDTO> coordinates, String name){
        this.coordinates = coordinates;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public List<CoordinateDTO> getCoordinates(){
        return coordinates;
    }
}
