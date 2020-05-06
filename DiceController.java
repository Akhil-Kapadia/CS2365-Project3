package cs2365_project3;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Dice Controller class is responsible for performing actions on the collective dice array.
 * Responsibilities include: rolling all the dice, setup of the dice types, sorting the array
 * @author Colin Morrsion
 * Collaborators: Jacob Strickland
 */
public class DiceController {
    
    ArrayList<Dice> diceArray;
    
    /**
     * The constructor for the DiceController class
     * @param types Integer[], how much of each dice to make
     */
    public DiceController(int[] types)
    {
        int index = 0;
        this.diceArray = new ArrayList<>(5);
        for(int i = 0; i < types[0]; i++) //normal dice
        {
            Dice newDice = new Dice(index, true, 1); //set the dice index to be 1-5 and all flags to be true for first roll
            diceArray.add(newDice);
            index++;
        }
        for(int i = 0; i < types[1]; i++) //duel dice
        {
            Dice newDice = new Dice(index, true, 2); //set the dice index to be 1-5 and all flags to be true for first roll
            diceArray.add(newDice);
            index++;
        }
        for(int i = 0; i < types[2]; i++) //coward dice
        {
            Dice newDice = new Dice(index, true, 3); //set the dice index to be 1-5 and all flags to be true for first roll
            diceArray.add(newDice);
            index++;
        }
        for(int i = 0; i < types[3]; i++) //loudmouth dice
        {
            Dice newDice = new Dice(index, true, 4); //set the dice index to be 1-5 and all flags to be true for first roll
            diceArray.add(newDice);
            index++;
        }
    }
    
    /**
     * Method that sets the dice array arraylist
     * @param DA ArrayList, the arraylist of dice to be set
     */
    public void setDiceArray(ArrayList<Dice> DA)
    {
        this.diceArray = DA;
    }
    
    /**
     * Method that gets the dice array arraylist
     * @return ArrayList, the arraylist of dice
     */
    public ArrayList<Dice> getDiceArray()
    {
        return new ArrayList<>(diceArray);
    }
    
    /**
     * Method that sorts the dice array arraylist
     * @return ArrayList, the arraylist of dice
     */
    public ArrayList<Dice> sortDiceArray() //sort the dice array by order of how you resolve
    {
        ArrayList<Dice> sortDiceArray = getDiceArray();
        for(int i = 0; i < sortDiceArray.size()-1; i++)
        {
            if(sortDiceArray.get(i).getDiceInt() > sortDiceArray.get(i+1).getDiceInt())
            {
                Collections.swap(sortDiceArray, i, i+1);
                i = 0;
            }
        }
        
        return diceArray;
    }
    
    /**
     * Method rolls all dice for turn and checks for reroll and if true the rerolls
     * @return array ArrayList, the array list of newly rolled dice
     */
    public ArrayList<Dice> rollAllDice()
    {
        ArrayList<Dice> array = getDiceArray();
        for(Dice dice : array)
        {
            if(dice.getReroll())
            {
                dice.rollDice();
                dice.setReroll(false, ""); //resets all reroll flags to false
            }
        }
        
        return array;
    }
    
    /**
     * Method that checks the frequency of of give dice value
     * @param diceValue String, the dice face that the frequency is to be checked for
     * @return count Integer, the frequency of given string in dice arraylist
     */
    public int checkFrequency(String diceValue)
    { 
        int count = 0;
        for(Dice dice : getDiceArray())
            if(dice.getDiceString().equals(diceValue))
                count++;
        
        return count;
    }
    
    /**
     * Method prints the output of all rolled dice 
     * @return output String, string containing what was rolled
     */
    public String printAllDice()
    {
        String output = "";
        int c = 0;
        for(Dice dice : getDiceArray())
        {
            output = output + "Dice " + c + ": " + dice.getDiceString() + "\n";
            //System.out.println("Dice " + c + ": " + dice.getDiceString());
            c++;
        }
        output = output + "\n";
        //System.out.println();
        return output;
    }
    
}
