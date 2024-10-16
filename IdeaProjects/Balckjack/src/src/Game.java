package src;

import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.*;

public class Game {

  ArrayList<Cards> dealerHand;
  ArrayList<Cards> playerHand;

  public boolean faceDown;
  public boolean dealerWon;
  public volatile boolean roundOver;

  JFrame frame;
  Deck deck;
  GUI atmosphereComponent;
  GUI cardComponent;

  JButton btnHit;
  JButton btnStand;
  JButton btnDouble;
  JButton btnExit;

  public Game(JFrame f) {
    deck = new Deck();
    deck.shuffleDeck();
    dealerHand = new ArrayList<Cards>();
    playerHand = new ArrayList<Cards>();
    atmosphereComponent = new GUI(dealerHand, playerHand);
    frame = f;
    faceDown = true;
    dealerWon = true;
    roundOver = false;
  }

  public void formGame() {

    System.out.println("GAME FORMED");
    frame.setTitle("BLACKJACK!");
    frame.setSize(1130, 665);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);

    btnHit = new JButton("HIT");
    btnHit.setBounds(390, 550, 100, 50);
    btnHit.setFont(new Font("Arial", Font.BOLD, 16));
    btnStand = new JButton("STAND");
    btnStand.setBounds(520, 550, 100, 50);
    btnStand.setFont(new Font("Arial", Font.BOLD, 16));
    btnDouble = new JButton("DOUBLE");
    btnDouble.setBounds(650, 550, 100, 50);
    btnDouble.setFont(new Font("Arial", Font.BOLD, 16));
    btnExit = new JButton("EXIT CASINO");
    btnExit.setBounds(930, 240, 190, 50);
    btnExit.setFont(new Font("Arial", Font.BOLD, 16));

    frame.add(btnHit);
    frame.add(btnStand);
    frame.add(btnDouble);
    frame.add(btnExit);

    btnExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You have left the casino with " +  Main.currentBalance + "."); //we print out the message by getting our current balance from the Main class.
        System.exit(0);
      }
    });

    atmosphereComponent = new GUI(dealerHand, playerHand);
    atmosphereComponent.setBounds(0, 0, 1130, 665);
    frame.add(atmosphereComponent);
    frame.setVisible(true);
  }

  public void startGame() {

    for(int i = 0; i<2; i++) {
      dealerHand.add(deck.getCard(i));
    }
    for(int i = 2; i<4; i++) {
      playerHand.add(deck.getCard(i));
    }
    for (int i = 0; i < 4; i++) {
      deck.removeCard(0);
    }

    cardComponent = new GUI(dealerHand, playerHand);
    cardComponent.setBounds(0, 0, 1130, 665);
    frame.add(cardComponent);
    frame.setVisible(true);

    checkHand(dealerHand);
    checkHand(playerHand);

    btnHit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addCard(playerHand);
        checkHand(playerHand);
        if (getSumOfHand(playerHand)<17 && getSumOfHand(dealerHand)<17){
          addCard(dealerHand);
          checkHand(dealerHand);
        }
      }
    });

    btnDouble.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addCard(playerHand);
        addCard(playerHand);
        checkHand(playerHand);
        if (getSumOfHand(playerHand)<17 && getSumOfHand(dealerHand)<17){
          addCard(dealerHand);
          checkHand(dealerHand);
        }
      }
    });

    btnStand.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        while (getSumOfHand(dealerHand)<17) {
          addCard(dealerHand);
          checkHand(dealerHand);
        }
        if ((getSumOfHand(dealerHand)<21) && getSumOfHand(playerHand)<21) {
          if(getSumOfHand(playerHand) > getSumOfHand(dealerHand)) {
            faceDown = false;
            dealerWon = false;
            JOptionPane.showMessageDialog(frame, "PLAYER WON!");
            rest();
            roundOver = true;
          }
          else {
            faceDown = false;
            JOptionPane.showMessageDialog(frame, "DEALER WON!");
            rest();
            roundOver = true;
          }
        }
      }
    });
  }

  public void checkHand (ArrayList<Cards> hand) {
    if (hand.equals(playerHand)) {
      if(getSumOfHand(hand) == 21){
        faceDown = false;
        dealerWon = false;
        JOptionPane.showMessageDialog(frame, "PLAYER WON!");
        rest();
        roundOver = true;
      }
      else if (getSumOfHand(hand) > 21) {
        faceDown = false; JOptionPane.showMessageDialog(frame, "DEALER WON!");
        rest();
        roundOver = true;
      }
    }
    else {
      if(getSumOfHand(hand) == 21) {
        faceDown = false;
        JOptionPane.showMessageDialog(frame, "DEALER WON!");
        rest();
        roundOver = true;
      }
      else if (getSumOfHand(hand) > 21) {
        faceDown = false;
        dealerWon = false;
        JOptionPane.showMessageDialog(frame, "DEALER  WON!");
        rest();
        roundOver = true;
      }
    }
  }

  public void addCard(ArrayList<Cards> hand) {
    hand.add(deck.getCard(0));
    deck.removeCard(0);
    faceDown = true;
  }

  public boolean hasAceInHand(ArrayList<Cards> hand) {
    for (int i = 0; i < hand.size(); i++){
      if(hand.get(i).getvalueinBJ() == 11) {
        return true;
      }
    }
    return false;
  }

  public int aceCountInHand(ArrayList<Cards> hand){
    int aceCount = 0;
    for (int i = 0; i < hand.size(); i++) {
      if(hand.get(i).getvalueinBJ() == 11) {
        aceCount++;
      }
    }
    return aceCount;
  }

  public int getSumWithHighAce(ArrayList<Cards> hand) {
    int handSum = 0;
    for (int i = 0; i < hand.size(); i++){
      handSum = handSum + hand.get(i).getvalueinBJ();
    }
    return handSum;
  }

  public int getSumOfHand (ArrayList<Cards> hand) {
    if(hasAceInHand(hand)) {
      if(getSumWithHighAce(hand) <= 21) {
        return getSumWithHighAce(hand);
      }
      else{
        for (int i = 0; i < aceCountInHand(hand); i++) {
          int sumOfHand = getSumWithHighAce(hand)-(i+1)*10;
          if(sumOfHand <= 21) {
            return sumOfHand;
          }
        }
      }
    }
    else {
      int sumOfHand = 0;
      for (int i = 0; i < hand.size(); i++) {
        sumOfHand = sumOfHand + hand.get(i).getvalueinBJ();
      }
      return sumOfHand;
    }
    return 22;
  }

  public static void rest() {
    try {
      Thread.sleep(500);
    }
    catch (InterruptedException e) {}
  }

}