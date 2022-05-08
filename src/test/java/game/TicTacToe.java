package game;


import java.util.*;

public class TicTacToe {
    private record Position(int x, int y) {
    }

    private final Set<List<Position>> winningMoves = new HashSet<>(Arrays.asList(
            Arrays.asList(new Position(0, 0), new Position(0, 1), new Position(0, 2)),
            Arrays.asList(new Position(1, 0), new Position(1, 1), new Position(1, 2)),
            Arrays.asList(new Position(2, 0), new Position(2, 1), new Position(2, 2)),

            Arrays.asList(new Position(0, 0), new Position(1, 0), new Position(2, 0)),
            Arrays.asList(new Position(0, 1), new Position(1, 1), new Position(2, 1)),
            Arrays.asList(new Position(0, 2), new Position(1, 2), new Position(2, 2)),

            Arrays.asList(new Position(0, 0), new Position(1, 1), new Position(2, 2)),
            Arrays.asList(new Position(2, 0), new Position(1, 1), new Position(0, 2))
    ));

    private final List<List<Sign>> board;

    private enum Sign {
        X, O
    }

    private Sign currentPlayer = null; 

    public TicTacToe() {
        this.board = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            var column = new ArrayList<Sign>(3);
            this.board.add(column);
            for (int j = 0; j < 3; j++) {
                column.add(null);
            }
        }
    }

    private void validateMove(int x, int y) {
        if (x < 0 || y < 0 || x > 3 || y > 3) {
            throw new IllegalArgumentException("Trying to place a sign out of board's bounds");
        }
        
        if (board.get(x).get(y) != null) {
            throw new IllegalStateException("The filed is already taken");
        }
    }

    public void placeX(int x, int y) {
        if (currentPlayer == Sign.O) {
            throw new IllegalStateException("Cannot do 2 moves in a row");
        }
        
        validateMove(x, y);
        
        board.get(x).set(y, Sign.X);
        currentPlayer = Sign.O;
    }

    public void placeO(int x, int y) {
        if (currentPlayer == Sign.X) {
            throw new IllegalStateException("Cannot do 2 moves in a row");
        }

        validateMove(x, y);
        
        board.get(x).set(y, Sign.O);
        currentPlayer = Sign.X;
    }

    public GameResult getResult() {
        var xPositions = new ArrayList<Position>();
        var oPositions = new ArrayList<Position>();

        var x = 0;
        for (var column : board) {
            var y = 0;
            for (var sign : column) {
                if (sign == Sign.X) {
                    xPositions.add(new Position(x, y));
                }

                if (sign == Sign.O) {
                    oPositions.add(new Position(x, y));
                }

                y++;
            }
            x++;
        }

        if (winningMoves.stream().anyMatch(xPositions::containsAll)) {
            return GameResult.XWon;
        }

        if (winningMoves.stream().anyMatch(oPositions::containsAll)) {
            return GameResult.OWon;
        }

        int totalSigns = xPositions.size() + oPositions.size();

        if (totalSigns == 9) {
            return GameResult.Draw;
        } else {
            return null;
        }
    }
}
