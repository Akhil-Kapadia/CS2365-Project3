package cs2365_project3;

/**
 * Card class is responsible for creation of new cards.
 * @author Jacob Strickland
 */
public class Card {
    private int value;
    
    /**
    * Constructor for the Card class
    * @param v Integer, value of the card
    */
    public Card(int v)
    {
        this.value = v;
    }
    
    /**
    * Method to get the value of the card
    * @return Integer, the value of the card
    */
    public int getValue()
    {
        return this.value;
    }
}
