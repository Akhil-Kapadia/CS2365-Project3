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
    
    /**
    * Method that allows the player to choose which player to shoot from
    * possible options.
    * @param player1 Player, one of the players that can be shot.
    * @param player2 Player, one of the players that can be shot.
    * @return int, the index of the chosen player
    */
    public int chooseShoot(Player player1, Player player2)
    {
        System.out.println("Please choose which player you would like to shoot");
        for(int i = 0; i < tableSeating.size(); i++)
        {
            if(tableSeating.get(i).equals(player1) || tableSeating.get(i).equals(player2))
                System.out.println(i + ": " + tableSeating.get(i).getCharacterName());
        }
        return scan.nextInt();
    }
    
    /**
    * Method that allows the player to choose which player to shoot from
    * possible options with Calamity Janet's ability.
    * @param player1 Player, one of the players that can be shot.
    * @param player2 Player, one of the players that can be shot.
    * @param player3 Player, one of the players that can be shot.
    * @param player4 Player, one of the players that can be shot.
    * @return Integer, the index of the chosen player
    */
    public int chooseShoot(Player player1, Player player2, Player player3, Player player4)
    {
        System.out.println("Please choose which player you would like to shoot");
        for(int i = 0; i < tableSeating.size(); i++)
        {
            if(tableSeating.get(i).equals(player1) || tableSeating.get(i).equals(player2) 
                || tableSeating.get(i).equals(player3) || tableSeating.get(i).equals(player4))
                System.out.println(i + ": " + tableSeating.get(i).getCharacterName());
        }
        return scan.nextInt();
    }
    
    /**
    * Method that allows the player to choose which player to heal from possible options
    * @return Integer, the index of the chosen player
    */
    public int chooseHeal()
    {
        boolean possible = false;
        System.out.println("Please choose who you would like to heal");
        for(int i = 0; i < tableSeating.size(); i++)
        {
            if(!(tableSeating.get(i).isFullHealth())) //if player not full HP, print them out
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
    
    /**
    * Method that allows the player to choose which player to shoot from
    * possible options with Calamity Janet's ability.
    * @param diceArray ArrayList, the list of dice that were rolled
    * @param current Player, one of the players that can be shot.
    * @return diceArray ArrayList, the list of dice with updated reroll values
    */
    public ArrayList<Dice> chooseReroll(ArrayList<Dice> diceArray, Player current)
    {
        System.out.println("Please enter the dice you would like to reroll (ex: 1345):");
        for(int i = 0; i < diceArray.size(); i++)
            System.out.println(i + ": " + diceArray.get(i).getDiceString());
        String input = scan.nextLine();
        for(int j = 0; j < input.length(); j++)
            diceArray.get((int)input.charAt(j)-48).setReroll(true, current.getCharacterName());
        return diceArray;
    }
    
     public int chooseBrokenArrow()
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
    
}
