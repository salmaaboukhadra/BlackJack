package src;

import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GUI extends JComponent implements MouseListener {
    public BufferedImage backgroundImage;
    public BufferedImage logo;
    public BufferedImage chip;
    private ArrayList<Cards> dealerHand;
    private ArrayList<Cards> playerHand;
    private int dealerScore;
    private int playerScore;
    public boolean faceDown = true;
    public static boolean betMade = false;
    private int currentBalance;
    public static int currentBet;

    public GUI(ArrayList<Cards> dH, ArrayList<Cards> pH) {
        dealerHand = dH;
        playerHand = pH;
        dealerScore = 0;
        playerScore = 0;
        currentBalance = 1000;
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D GUIComponents = (Graphics2D) g;

        try {
            backgroundImage = ImageIO.read(new File("images/background.png"));
            chip = ImageIO.read(new File("images/chip.png"));
        }
        catch(IOException e) {}

        GUIComponents.drawImage(backgroundImage, 0, 0, null);
        GUIComponents.drawImage(logo, 510, 200, null);
        GUIComponents.drawImage(chip, 50, 300, null);
        GUIComponents.setColor(Color.WHITE);
        GUIComponents.setFont(new Font("Arial", Font.BOLD, 30));
        GUIComponents.drawString("DEALER", 515, 50);
        GUIComponents.drawString("PLAYER", 515, 380);
        GUIComponents.drawString("DEALER WON: ", 50, 100);
        GUIComponents.drawString(Integer.toString(dealerScore), 300, 100);
        GUIComponents.drawString("PLAYER WON: ", 50, 150);
        GUIComponents.drawString(Integer.toString(playerScore), 300, 150);
        GUIComponents.setFont(new Font("Arial", Font.BOLD, 15));
        GUIComponents.drawString("To start each round, you have to first", 50, 250);
        GUIComponents.drawString("bet an amount by clicking the chip below.", 50, 270);
        GUIComponents.setFont(new Font("Arial", Font.BOLD, 20));
        GUIComponents.drawString("CURRENT BALANCE: " + currentBalance, 50, 570);


        try {
            for (int i = 0; i < dealerHand.size(); i++) {
                if (i == 0) {
                    if(faceDown) {
                        dealerHand.get(i).printCard(GUIComponents, true, true, i);
                    }
                    else {
                        dealerHand.get(i).printCard(GUIComponents, true, false, i);
                    }
                }
                else {
                    dealerHand.get(i).printCard(GUIComponents, true, false, i);
                }
            }
        }
        catch (IOException e) {}

        try {
            for (int i = 0; i < playerHand.size(); i++) {
                playerHand.get(i).printCard(GUIComponents, false, false, i);
            }
        }
        catch (IOException e) {}
    }

    public void refresh(int cB, int uS, int dS, boolean fD) {
        currentBalance = cB;
        playerScore = uS;
        dealerScore = dS;
        faceDown = fD;
        this.repaint();
    }

    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if(mouseX>= 50 && mouseX<=200 && mouseY>=300 && mouseY<=450) {
            betMade = true;
            String[] options = new String[] {"1", "5", "10", "25", "100"};
            int response = JOptionPane.showOptionDialog(null, "Please enter your betting amount!", "BETTING",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if(response == 0) {
                currentBet = 1;
                currentBalance -= 1;
            }
            else if(response == 1) {
                currentBet = 5;
                currentBalance -= 5;
            }
            else if(response == 2) {
                currentBet = 10;
                currentBalance -= 10;
            }
            else if(response == 3) {
                currentBet = 25;
                currentBalance -= 25;
            }
            else if(response == 4) {
                currentBet = 100;
                currentBalance -= 100;
            }
            else {
                currentBet = 1;
                currentBalance -= 1;
                System.out.println("You haven't selected a proper bet. Thus, the bet is taken as 1.");
            }

            System.out.println("You have made your bet: " + currentBet + "." + " If you beat the dealer, you will increase your current balance by " + currentBet*2 +
                    "; if the dealer beats you you will decrease your current balance by " + currentBet + ".");

            Main.newGame.startGame();
        }

    }
    public void mouseExited(MouseEvent e) {

    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {

    }
    public void mouseClicked(MouseEvent e) {

    }}

