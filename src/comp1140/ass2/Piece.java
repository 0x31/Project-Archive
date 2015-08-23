package comp1140.ass2;

/**
 *
 */
public abstract class Piece {

    Coordinate[] occupiedCells;
    public Coordinate[] stringToCoordinates(String[] template) {
        int count = 0;
        for(String s:template) count += s.replace(" ","").length();
        Coordinate[] coordinates = new Coordinate[count];
        int i = 0, j=0, cellCount = 0;
        for(String s:template) {
            i=0;
            for (char c : s.toCharArray()) {
                if (c != ' ') coordinates[cellCount++] = new Coordinate(i, j);
                i++;
            }
            j++;
        }
        return coordinates;
    }

    public Coordinate[] getOccupiedCells(Coordinate origin, char orientation) {
        return occupiedCells;
    }

}
