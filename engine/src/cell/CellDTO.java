package cell;

import coordinate.Coordinate;

import java.util.List;

public class CellDTO {
        private Object value; // or the appropriate type for your use case
        private String originalValue;
        private List<Coordinate> dependsOn;
        private List<Coordinate> affecting;
        // Constructors, getters, and setters

        public CellDTO() {}

        public CellDTO(Object value, String originalValue, List<Coordinate> dependsOn, List<Coordinate> affecting) {
            this.value = value;
            this.originalValue = originalValue;
            this.dependsOn = dependsOn;
            this.affecting = affecting;
        }

        public Object getValue() {
            return value;
        }

        public String getOriginalValue() {
            return originalValue;
        }

        public List<Coordinate> getDependsOn() {
            return dependsOn;
        }

        public List<Coordinate> getAffecting() {
            return affecting;
        }
}