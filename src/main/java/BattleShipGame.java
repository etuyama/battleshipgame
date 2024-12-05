import java.util.*;


public class BattleShipGame {
    static boolean playing;
    private BattleShipsManager battleShipsManager;
    int numberOfGuesses;
    List<List<Integer>> board;
    public static List<Integer> defaultList = Arrays.asList(0,0,0,0,0,0,0);
    public static HashMap<String, Integer> mapping = new HashMap<>();
    private List<BattleShip> battleShips;
    public static Scanner scanner = new Scanner(System.in);

    public BattleShipGame() {
        this.numberOfGuesses = 0;
        this.board = new ArrayList<>();
        this.battleShips = new ArrayList<>();
        this.battleShipsManager = new BattleShipsManager();
    }

    private static void setupMapping() {
        for (int i = 0; i < 7 ;i++) {
            String c = Character.toString((char) ('A' + i));
            mapping.put(c, i);
        }
    }

    public void setupGame() {
        for (int i = 0; i < 7 ;i++) {
            List<Integer> row = Arrays.asList(0,0,0,0,0,0,0);
            board.add(row);
        }
        battleShips = battleShipsManager.createShips();
        placeBattleShips();
    }

    public void printMap() {
        for (List<Integer> row : board) {
            System.out.println(row);
        }
    }

    private void placeBattleShips() {
        for (BattleShip battleShip : battleShips) {
            List<String> coordinates = battleShip.getCoordinates();
            for (String coordinate : coordinates) {
                int c1 = Integer.parseInt(coordinate.substring(0, 1));
                int c2 = Integer.parseInt(coordinate.substring(1, 2));
                board.get(c1).set(c2, 1);
            }
        }
    }

    private void gameLoop() {
        boolean gameOver = false;
        while (!gameOver) {
            String playerInput = askInput();
            checkHit(playerInput);
            gameOver = checkGameOver();
        }
    }

    private boolean checkGameOver() {
        return true;//temporary
    }

    private void checkHit(String playerInput) {
        int c1 = mapping.get(playerInput.substring(0, 1).toUpperCase());
        int c2 = Integer.parseInt(playerInput.substring(1,2));

        if (board.get(c1).get(c2) == 1) {
            board.get(c1).set(c2, -1);
            System.out.println("That's a hit on coordinates " + c1 + " " + c2 + "!");
        } else {
            System.out.println("Missed ^^");
        }
    }

    private String askInput() {
        String coordinate;
        System.out.println("Enter the coordinates you want to hit(format: A0, from A to G and from 0 to 6): ");
        while (true) {
            coordinate = scanner.nextLine();
            if (isValidInput(coordinate)) {
                break;
            } else {
                System.out.println("Invalid input! Try again: ");
            }
        }
        this.numberOfGuesses += 1;
        return coordinate;
    }

    private boolean isValidInput(String coordinate) {
        try {
            if (coordinate.length() != 2 || !mapping.containsKey(coordinate.substring(0, 1).toUpperCase()) || !mapping.containsValue((int) Integer.parseInt(coordinate.substring(1, 2)))) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        playing = true;
        setupMapping();
        while (playing) {
            BattleShipGame battleShipGame = new BattleShipGame();
            battleShipGame.setupGame();
            battleShipGame.gameLoop();
            //playing = askPlayAgain();
        }
    }
}
