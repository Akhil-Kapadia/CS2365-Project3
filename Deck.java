package cs2365_project3;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>();
    
    public void createDeck()
    {
        for(int i = 0; i < 2; i++)
        {
            Card card = new Card(0);
            deck.add(card);
        }
        
        for(int j = 0; j < 6; j++)
        {
            Card card = new Card(1);
            deck.add(card);
        }
        
        for(int k = 0; k < 3; k++)
        {
            Card card = new Card(2);
            deck.add(card);
        }
        
        Collections.shuffle(deck);
        
    }
    
    public int drawCard()
    {
        int drawnValue;
        drawnValue = deck.get(0).getValue();
        deck.remove(0);
        return drawnValue;
        
    }
}
