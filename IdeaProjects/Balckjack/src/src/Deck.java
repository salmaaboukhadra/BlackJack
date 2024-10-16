package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Cards> deck;

    public Deck() {
        deck = new ArrayList<Cards>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                if (j == 0) {
                    Cards card = new Cards(i, j, 11);
                    deck.add(card);
                } else if (j >= 10) {
                    Cards card = new Cards(i, j, 10);
                    deck.add(card);
                } else {
                    Cards card = new Cards(i, j, j + 1);
                    deck.add(card);
                }
            }
        }
    }

    public void shuffleDeck() {

        Collections.shuffle(deck);
    }

    public Cards getCard(int i) {


        return deck.get(i);
    }

    public Cards removeCard(int i) {

        return deck.remove(i);
    }
}

