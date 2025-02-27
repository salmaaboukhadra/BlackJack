package src;

import javax.swing.JFrame;

public class Main {

    public static JFrame menuFrame = new JFrame();
    public static JFrame gameFrame = new JFrame();

    private static int playerScore = 0;
    private static int dealerScore = 0;
    public static int currentBalance = 1000;

    public static Game newGame = new Game(gameFrame);
    private static boolean isFirstTime = true;

    public static enum STATE{
        MENU,
        GAME
    };

    public static STATE currentState = STATE.MENU;

    public static void main(String[] args) throws InterruptedException {
        if(currentState == STATE.MENU) {
            openMenu();
        }
    }

    public static void openMenu() {
        menuFrame.setTitle("BLACKJACK!");
        menuFrame.setSize(1130, 665);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setResizable(false);

        Options begin = new Options();
        menuFrame.add(begin);
        menuFrame.setVisible(true);
    }

    public static Thread gameRefreshThread = new Thread () {
        public void run () {
            while(true){
                newGame.atmosphereComponent.refresh(currentBalance, playerScore, dealerScore-1, newGame.faceDown);

            }
        }
    };

    public static Thread gameCheckThread = new Thread () {
        public void run () {
            while(true) {
                if (isFirstTime||newGame.roundOver) {
                    System.out.println("Lets refresh the game!");
                    if (newGame.dealerWon){
                        dealerScore++;
                        currentBalance-= GUI.currentBet;
                    }
                    else {
                        playerScore++;
                        currentBalance+= GUI.currentBet*2;
                    }
                    gameFrame.getContentPane().removeAll();
                    newGame = new Game(gameFrame);
                    newGame.formGame();

                    isFirstTime = false;
                }
            }
        }
    };
}

