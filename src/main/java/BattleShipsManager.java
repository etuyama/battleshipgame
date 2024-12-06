import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class BattleShipsManager {
    private static List<String> illegalCoordinates;
    private static final Random r = new Random();
    private static final List<Integer> bounds = Arrays.asList(0,1,2,3,4,5,6);
    private List<BattleShip> ships;

    public List<BattleShip> createShips() {
        ships = new ArrayList<>();
        illegalCoordinates = new ArrayList<>();
        List<String> takenNames = new ArrayList<>();
        boolean canCreate = false;
        while (!canCreate) {
            for (int i = 0; i < 3; i++) {
                BattleShip ship = new BattleShip();
                setCoordinates(ship);
                addName(ship, takenNames);
                if (ship.getCoordinates().size() == 3) {
                    ships.add(ship);
                }
            }
            if (ships.size() == 3) {
                canCreate = true;
            }   else {
                illegalCoordinates.clear();
                ships.clear();
                takenNames.clear();
            }
        }
        return ships;
    }

    private void setCoordinates(BattleShip ship) {
        setFirst(ship);
        setSecond(ship);
        setThird(ship);
    }

    private void setFirst(BattleShip ship) {
        boolean canSet = false;
        while (!canSet) {
            String coordinate = "";
            int c1 = r.nextInt(6);
            int c2 = r.nextInt(6);
            coordinate += c1;
            coordinate += c2;
            if (!isIllegalCoordinate(coordinate)) {
                ship.addCoordinate(coordinate);
                addIllegalCoordinates(coordinate);
                canSet = true;
            }
        }
    }

    private void setSecond(BattleShip ship) {
        boolean canSet = false;
        while (!canSet) {
            String coordinate = ship.getFirstCoordinate();
            canSet = checkCanSet(ship, coordinate);
        }
    }

    private void setThird(BattleShip ship) {
        boolean canSet = false;
        while (!canSet) {
            String firstCoordinate = ship.getFirstCoordinate();
            String secondCoordinate = ship.getSecondCoordinate();
            List<String> possibleCoordinates = generatePossibleThirdCoordinate(firstCoordinate, secondCoordinate);
            int chosenInt = r.nextInt(possibleCoordinates.size());
            String coordinate = possibleCoordinates.get(chosenInt);
            ship.addCoordinate(coordinate);
            addIllegalCoordinates(coordinate);
            canSet = true;
        }
    }

    private List<String> generatePossibleThirdCoordinate(String firstCoordinate, String secondCoordinate) {
        List<String> coordinates = new ArrayList<>();

        int fc1 = Integer.parseInt(firstCoordinate.substring(0, 1));
        int fc2 = Integer.parseInt(firstCoordinate.substring(1, 2));
        int sc1 = Integer.parseInt(secondCoordinate.substring(0, 1));
        int sc2 = Integer.parseInt(secondCoordinate.substring(1, 2));

        if (fc1 == sc1) {
            String c1 ="";
            String c2 = "";

            int max = Math.max(fc2, sc2);
            int min = Math.min(fc2, sc2);

            c1 += fc1 + "" + (min - 1);
            c2 += fc1 + "" + (max + 1);

            if (isWithinBounds(c1) && !isIllegalCoordinate(c1)) {
                coordinates.add(c1);
            }
            if (isWithinBounds(c2) && !isIllegalCoordinate(c2)) {
                coordinates.add(c2);
            }
        } else if (fc2 == sc2) {
            String c1 ="";
            String c2 = "";

            int max = Math.max(fc1, sc1);
            int min = Math.min(fc1, sc1);

            c1 += (min - 1) + "" + fc2;
            c2 += (max + 1) + "" + fc2;

            if (isWithinBounds(c1) && !isIllegalCoordinate(c1)) {
                coordinates.add(c1);
            }
            if (isWithinBounds(c2) && !isIllegalCoordinate(c2)) {
                coordinates.add(c2);
            }
        }
        return coordinates;
    }

    private boolean checkCanSet(BattleShip ship, String coordinate) {
        boolean canSet;
        List<String> possibleCoordinates = generatePossibleCoordinates(coordinate);
        while (true) {
            int chosenInt = r.nextInt(possibleCoordinates.size());
            String chosenCoordinate = possibleCoordinates.get(chosenInt);
            if (!isIllegalCoordinate(chosenCoordinate)) {
                ship.addCoordinate(chosenCoordinate);
                addIllegalCoordinates(chosenCoordinate);
                canSet = true;
                break;
            }
        }
        return canSet;
    }

    private void addIllegalCoordinates(String c) { illegalCoordinates.add(c); }

    private boolean isIllegalCoordinate(String c) { return illegalCoordinates.contains(c); }

    private boolean isWithinBounds(String c) {
        try {
            int c1 = Integer.parseInt(c.substring(0, 1));
            int c2 = Integer.parseInt(c.substring(1, 2));
            return bounds.contains(c1) && bounds.contains(c2);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return false;
        }
    }

    private List<String> generatePossibleCoordinates(String c) {
        List<String> coordinates = new ArrayList<>();
        List<String> possibleCoordinates = new ArrayList<>();

        int c1 = Integer.parseInt(c.substring(0, 1));
        int c2 = Integer.parseInt(c.substring(1, 2));

        String p1 = "";
        String p2 = "";
        String p3 = "";
        String p4 = "";

        p1 += (c1 - 1) + "" + c2;
        p2 += c1 + "" + (c2 - 1);
        p3 += c1 + "" + (c2 + 1);
        p4 += (c1 + 1) + "" + c2;

        possibleCoordinates.add(p1);
        possibleCoordinates.add(p2);
        possibleCoordinates.add(p3);
        possibleCoordinates.add(p4);

        for (String s : possibleCoordinates) {
            if (isWithinBounds(s) && !isIllegalCoordinate(s)) {
                coordinates.add(s);
            }
        }

        return coordinates;
    }

    private void addName(BattleShip ship, List<String> takenNames) {
        while (true) {
            ship.setName();
            if (!takenNames.contains(ship.getName())) {
                takenNames.add(ship.getName());
                break;
            }
        }
    }

    public BattleShip getShipFromCoordinate(String coordinate){
        for (BattleShip ship : ships) {
            if (ship.getCoordinates().contains(coordinate)) {
                return ship;
            }
        }
        return null;
    }

    public List<String> getAllShipCoordinates() {
        List<String> coordinates = new ArrayList<>();
        for (BattleShip ship : ships) {
            coordinates.addAll(ship.getCoordinates());
        }
        return coordinates;
    }
}

