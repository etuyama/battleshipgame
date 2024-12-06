import java.util.*;


public class BattleShipGame {
    private final BattleShipsManager battleShipsManager;
    private List<BattleShip> battleShips;
    private final List<List<Character>> board;
    private final HashMap<Character, Integer> mapping;
    private final List<String> triedCoodinates;

    private int numberOfGuesses;
    private static final Scanner scanner = new Scanner(System.in);
    private static final char water = 'O';
    private static final char hitShip = 'X';
    private static final char missedHit = 'N';

    public BattleShipGame() {
        this.numberOfGuesses = 0;
        this.board = new ArrayList<>();
        this.battleShips = new ArrayList<>();
        this.battleShipsManager = new BattleShipsManager();
        this.triedCoodinates = new ArrayList<>();
        this.mapping = new HashMap<>();
    }

    private void setupMapping() {
        for (int i = 1; i < 8 ;i++) {
            char c = (char) ('A' + i - 1);
            this.mapping.put(c, i);
        }
    }

    public void setupGame() {
        for (int i = 0; i < 7 ;i++) {
            List<Character> row = Arrays.asList(water, water, water, water, water, water, water);
            board.add(row);
        }
        battleShips = battleShipsManager.createShips();
    }

    public void printMap() {
        System.out.println("   1  2  3  4  5  6  7");
        for (int i = 0; i < 7 ; i++) {
            System.out.println(getKeyByValue(mapping, i + 1)+ " " + board.get(i));
        }
    }

    private void gameLoop() {
        boolean gameOver = false;
        while (!gameOver) {
            printMap();
            String playerInput = askInput();
            checkHit(playerInput);
            gameOver = checkGameOver();
        }
        System.out.println("Game Over! Number of guesses: " + this.numberOfGuesses);
    }

    private boolean checkGameOver() {
        return battleShipsManager.getAllShipCoordinates().isEmpty();
    }

    private void checkHit(String playerInput) {
        int c1 = mapping.get(playerInput.toUpperCase().charAt(0)) - 1;
        int c2 = Integer.parseInt(playerInput.substring(1,2)) - 1;
        String translatedInput = c1 + "" + c2;

        if (battleShipsManager.getAllShipCoordinates().contains(translatedInput)) {
            board.get(c1).set(c2, hitShip);
            BattleShip ship = battleShipsManager.getShipFromCoordinate(translatedInput);
            battleShipsManager.getAllShipCoordinates().remove(translatedInput); //we might not need this
            battleShipsManager.getShipFromCoordinate(translatedInput).getCoordinates().remove(translatedInput);
            System.out.println("That's a hit on coordinate " + playerInput.toUpperCase().charAt(0) + (c2 + 1 )+ "!");
            if (isShipSunk(ship)) {
                System.out.println("You sunk " + ship.getName() + "!");
            }
        } else {
            board.get(c1).set(c2, missedHit);
            System.out.println("Missed ^^");
        }
    }

    private String askInput() {
        String coordinate;
        System.out.println("Enter the coordinates you want to hit(format: A1, from A to G and from 1 to 7): ");
        while (true) {
            coordinate = scanner.nextLine();
            if (isValidInput(coordinate)) {
                break;
            } else if (triedCoodinates.contains(coordinate)){
                System.out.println("You already tried to hit '" + coordinate + "'! Try again: ");
            }  else {
                System.out.println("Invalid input! Try again: ");
            }
        }
        this.numberOfGuesses += 1;
        this.triedCoodinates.add(coordinate);
        return coordinate;
    }

    private boolean isValidInput(String coordinate) {
        try {
            if (coordinate.length() != 2 || !mapping.containsKey(coordinate.toUpperCase().charAt(0)) || !mapping.containsValue(Integer.parseInt(coordinate.substring(1, 2))) || this.triedCoodinates.contains(coordinate)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isShipSunk(BattleShip ship) {
        return ship.getCoordinates().isEmpty();
    }

    public static Character getKeyByValue(Map<Character, Integer> map, int value) {
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static boolean askPlayAgain() {
        while(true) {
            System.out.println("Would you like to play again? (y/n)");
            String userChoice = scanner.nextLine();
            if (userChoice.equalsIgnoreCase("y")) {
                return true;
            } else if (userChoice.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    public static void main(String[] args) {
        boolean playing = true;
        while (playing) {
            BattleShipGame battleShipGame = new BattleShipGame();
            battleShipGame.setupMapping();
            battleShipGame.setupGame();
            battleShipGame.gameLoop();
            playing = askPlayAgain();
        }
        System.out.println("Thank you for playing! Highest score this session: ");
    }
}
