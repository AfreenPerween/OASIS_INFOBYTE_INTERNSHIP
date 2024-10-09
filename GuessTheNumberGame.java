import javax.swing.JOptionPane;
import java.util.Random;

    class Player {
        private String name;
        private int score;

        public Player(String name) {
            this.name = name;
            this.score = 0;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void addScore(int points) {
            score += points;
        }
    }

    class Computer {
        private int numberToGuess;

        public void generateNumber() {
            Random rand = new Random();
            numberToGuess = rand.nextInt(100) + 1;
        }

        public int getNumberToGuess() {
            return numberToGuess;
        }
    }

    public class GuessTheNumberGame {
        private Player player;
        private Computer computer;
        private int attemptsLimit;
        private int roundLimit;

        public GuessTheNumberGame(Player player, int attemptsLimit, int roundLimit) {
            this.player = player;
            this.computer = new Computer();
            this.attemptsLimit = attemptsLimit;
            this.roundLimit = roundLimit;
        }

        public void startGame() {
            for (int round = 1; round <= roundLimit; round++) {
                JOptionPane.showMessageDialog(null, "Round " + round + " begins!");
                computer.generateNumber();
                int attempts = 0;
                boolean guessedCorrectly = false;

                while (attempts < attemptsLimit && !guessedCorrectly) {
                    String input = JOptionPane.showInputDialog(null, player.getName() + ", guess the number (1-100):");
                    int guess = Integer.parseInt(input);
                    attempts++;

                    if (guess == computer.getNumberToGuess()) {
                        guessedCorrectly = true;
                        int points = calculatePoints(attempts);
                        player.addScore(points);
                        JOptionPane.showMessageDialog(null, "Correct! You've earned " + points + " points.");
                    } else if (guess > computer.getNumberToGuess()) {
                        JOptionPane.showMessageDialog(null, "Your guess is too high!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Your guess is too low!");
                    }
                }

                if (!guessedCorrectly) {
                    JOptionPane.showMessageDialog(null, "Out of attempts! The correct number was " + computer.getNumberToGuess());
                }
            }

            JOptionPane.showMessageDialog(null, player.getName() + ", your final score is: " + player.getScore());
        }

        private int calculatePoints(int attempts) {
            return 100 - (attempts * 10);
        }

        public static void main(String[] args) {
            String playerName = JOptionPane.showInputDialog(null, "Enter your name:");
            Player player = new Player(playerName);
            int attemptsLimit = 5;
            int roundLimit = 3;

            GuessTheNumberGame game = new GuessTheNumberGame(player, attemptsLimit, roundLimit);
            game.startGame();
        }
    }


