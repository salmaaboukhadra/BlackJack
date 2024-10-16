package src;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;

public class Cards {
    private int suit;
    private int pip;
    private int valueinBJ;
    private int xcoordinate;
    private int ycoordinate;

    public Cards() {
        suit = 0;
        pip = 0;
        valueinBJ = 0;
    }

    public Cards(int s, int p, int v) {
        suit = s;
        pip = p;
        valueinBJ = v;
    }

    public int getSuit() {

        return suit;
    }
    public int getpip() {

        return pip;
    }
    public int getvalueinBJ() {

        return valueinBJ;
    }

    public void printCard(Graphics2D GUIComponents, boolean dealerTurn, boolean faceDown, int placeValue) throws IOException {

        BufferedImage deckImg = ImageIO.read(new File("images/cardSpriteSheet.png")); //we read the sprite sheet image.
        int imgWidth = 950;
        int imgHeight = 392;

        BufferedImage[][] cardPictures = new BufferedImage[4][13];
        BufferedImage backOfACard = ImageIO.read(new File("images/backsideOfACard.jpg"));

        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 13; r++) {
                cardPictures[c][r] = deckImg.getSubimage(r*imgWidth/13, c*imgHeight/4, imgWidth/13, imgHeight/4);
            }
        }

        if (dealerTurn) {
            ycoordinate = 75;
        }
        else {
            ycoordinate = 400;
        }

        xcoordinate = 500 + 75*placeValue;

        if (faceDown) {
            GUIComponents.drawImage(backOfACard, xcoordinate, ycoordinate, null );
        }
        else {
            GUIComponents.drawImage(cardPictures[suit][pip], xcoordinate, ycoordinate, null);
        }
    }
}