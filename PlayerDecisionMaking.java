package cs2365_project3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The PlayerDecisionMaking class is responsible for any tactical decisions made
 * by the user within the game.
 * Responsibilities include: choosing who to shoot or heal, as well as which 
 * dice to reroll.
 * @author Jacob Strickland
 */
public class PlayerDecisionMaking {
    
    ArrayList<Player> tableSeating;
    
    Scanner scan = new Scanner(System.in);
    
    public PlayerDecisionMaking(ArrayList<Player> tableSeating)
    {
        this.tableSeating = tableSeating;
    }
    
    
    public int chooseBrokenArrow(GameGUI gui)
    {
        boolean possible = false;
        System.out.println("Please choose who you would like to remove an arrow from");
        for(int i = 0; i < tableSeating.size(); i++)
        {
            if(tableSeating.get(i).getArrowCount() != 0) //if player not full HP, print them out
            {
                System.out.println(i + ": " + tableSeating.get(i).getCharacterName());
                possible = true;
            }
        }
        if(possible)
            return scan.nextInt();
        else
            return -1;
    }
     
    public int chooseDuel(GameGUI gui)
    {
        System.out.println("Please choose who you would like to duel");
        for(int i = 0; i < tableSeating.size(); i++)
        {
            System.out.println(i + ": " + tableSeating.get(i).getCharacterName());
        }
        
        return scan.nextInt();
    }
    
    
}
