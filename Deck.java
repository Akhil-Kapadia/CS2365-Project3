package cs2365_project3;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author 
 */
public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>();
    
    /**
     * Method to create deck
     */
    public void createDeck()
    {
        for(int i = 0; i < 2; i++) //add two 0
        {
            Card card = new Card(0);
            deck.add(card);
        }
        
        for(int j = 0; j < 6; j++) //add six 1
        {
            Card card = new Card(1);
            deck.add(card);
        }
        
        for(int k = 0; k < 3; k++) //add three 2
        {
            Card card = new Card(2);
            deck.add(card);
        }
        
        Collections.shuffle(deck); //shuffle the deck
        
    }
    
    /**
     * Method to draw a card deck and check if card is zero to reshuffle back in deck
     * @return drawnValue integer
     */
    public int drawCard()
    {
        int drawnValue;
        drawnValue = deck.get(0).getValue();
        if(drawnValue != 0)
            deck.remove(0);
        else //if drawn card is zero you dont remove the card and you shuffle it back in
            Collections.shuffle(deck);
        return drawnValue;
        
    }
}
