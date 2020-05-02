package cs2365_project3;

import java.util.Random;
import java.util.ArrayList;

class AIDecisionMaking
{
    
    /**
     * The AIDecisionMaking class is responsible for any tactical decisions made
     * by AI Players within the game.
     * Responsibilities include, choosing who to Shoot or Heal as well as which 
     * Dice to Reroll.
     * @author Demetrios Mihaltses
     */
    
    //A Random number generator
    Random rand = new Random();
    
    
    /**
    * Method that gets the Player with Highest Favor to be targeted by the
    * Current Player in the turn.
    * @param CurrentPlayer Player, Player who is currently making a move
    * @param GameObject String, The String of the Dice Value in which the Player
    * is acting upon.
    * @param TotP ArrayList<Player>, ArrayList of the all the Players objects 
    * at the table.
    * @return Player, returns the Targeted Player with the Highest Favor
    */
    public Player getHighestFavor(Player CurrentPlayer, String GameObject, ArrayList<Player> TotP)
    {
        Player ret = null;
        
        String PlayerRole = CurrentPlayer.getRole();
        String CName = CurrentPlayer.getCharacterName();
        int CurrentPIndex = getCurrentPlayerIndex(CName, TotP);
        GameObject = UseDifferentShooting(GameObject, CurrentPIndex, TotP);
       
        
        //handles who to shoot, who to heal
        
        if(GameObject.equals("Beer"))
        {
            if(PlayerRole.equals("Sheriff"))
            {
                if(!CurrentPlayer.isFullHealth())
                {
                    return CurrentPlayer;
                }
                else
                {
                    //Sheriff heals the dude with lowest health
                    ArrayList<Player> sortedByH = sortPlayersHealth(TotP, false);
                    
                    if(sortedByH.get(0) != null)
                    {
                        return sortedByH.get(0);
                    }
                    else
                    {
                        return CurrentPlayer;
                    }
                    
                }
                
            }
            else if (PlayerRole.equals("Deputy"))
            {
                ArrayList<Player> Sheriffs = getPlayerType("Sheriff", CName, TotP);
                Sheriffs = sortPlayersHealth(Sheriffs, false);   
                //if there are no Deputy, then the CurrentPlayer will be the highest favor
                if(Sheriffs.size() > 0)
                {
                    if(Sheriffs.get(0).getHealth() <= CurrentPlayer.getHealth())
                    {
                        return Sheriffs.get(0);
                    }
                    else
                    {
                        return CurrentPlayer;
                    }
                }
                else
                {
                    return CurrentPlayer;
                }
            }
            else if (PlayerRole.equals("Outlaw"))
            {
                return CurrentPlayer;
            }
            else if (PlayerRole.equals("Renegade"))
            {
                return CurrentPlayer;
            }
            else if(PlayerRole.equals("Zombie"))
            {
                ArrayList<Player> DeadM = getPlayerType("Zombie Master", CName, TotP);
                DeadM = sortPlayersHealth(DeadM, false); 
                
                return DeadM.get(0);
            }
            else if(PlayerRole.equals("Zombie Master"))
            {
                return CurrentPlayer;
            }
            else if (PlayerRole.equals("Alive"))
            {
                ArrayList<Player> AL = getPlayerType("Alive", CName, TotP);
                AL = sortPlayersHealth(AL, false);   
                //if there are no Deputy, then the CurrentPlayer will be the highest favor
                    if(AL.get(0) != null)
                    {
                        return AL.get(0);
                    }
                    else
                    {
                        return CurrentPlayer;
                    }
            }
        }
        else if (CName.equals("CALAMITY JANET"))
        {
            //For the special abilities that Janet has
            if(GameObject.equals("Shoot person one over left or right") || GameObject.equals("Shoot person two over left or right"))
            {
                ArrayList<Player> AllP = TotP;
                
                int Left1 = 0;
                int Right1 = 0;
                
                if(CurrentPIndex > 0)
                {
                    Left1 = CurrentPIndex -1;
                }
                else
                {
                    Left1 = AllP.size()-1;
                }
                
                if(CurrentPIndex < AllP.size()-1)
                {
                    Right1 = CurrentPIndex +1;
                }
                else
                {
                    Right1 = 0;
                }
                
                int L_Favor1 = FavorSystemShooting(CurrentPlayer, AllP.get(Left1));
                int R_Favor1 = FavorSystemShooting(CurrentPlayer, AllP.get(Right1));
                
                int Left2 = 0;
                int Right2 = 0;
                
                if(CurrentPIndex > 1)
                {
                    Left2 = CurrentPIndex -2;
                }
                else
                {
                    Left2 = AllP.size()-2 + CurrentPIndex;
                }
                
                if(CurrentPIndex < AllP.size()-2)
                {
                    Right2 = CurrentPIndex +2;
                }
                else if (CurrentPIndex < AllP.size()-1)
                {
                    Right2 = 0;
                }
                else
                {
                    Right2 = 1;
                }
                
                int L_Favor2 = FavorSystemShooting(CurrentPlayer, AllP.get(Left2));
                int R_Favor2 = FavorSystemShooting(CurrentPlayer, AllP.get(Right2));
                
                //Find the highest in favor!
                
                if(L_Favor1 >= R_Favor1 && L_Favor1 >= R_Favor2 && L_Favor1 >= L_Favor2)
                {
                    return AllP.get(Left1);
                }
                else if(L_Favor2 >= R_Favor1 && L_Favor2 >= R_Favor2 && L_Favor2 >= L_Favor1)
                {
                    return AllP.get(Left2);
                }
                else if(R_Favor1 >= L_Favor1 && R_Favor1 >= R_Favor2 && R_Favor1 >= L_Favor2)
                {
                    return AllP.get(Right1);
                }
                else if(R_Favor2 >= R_Favor1 && R_Favor2 >= L_Favor2 && R_Favor2 >= L_Favor1)
                {
                    return AllP.get(Right2);
                }
                
            }
        }
        else if(GameObject.equals("Shoot person one over left or right"))
            {
                ArrayList<Player> AllP = TotP;
                
                int Left = 0;
                int Right = 0;
                
                if(CurrentPIndex > 0)
                {
                    Left = CurrentPIndex -1;
                }
                else
                {
                    Left = AllP.size()-1;
                }
                
                if(CurrentPIndex < AllP.size()-1)
                {
                    Right = CurrentPIndex +1;
                }
                else
                {
                    Right = 0;
                }
                
                int L_Favor = FavorSystemShooting(CurrentPlayer, AllP.get(Left));
                int R_Favor = FavorSystemShooting(CurrentPlayer, AllP.get(Right));
                
                if(L_Favor > R_Favor)
                {
                    return AllP.get(Left);
                }
                else if(R_Favor > L_Favor)
                {
                    return AllP.get(Right);
                }
                else
                {
                    //if equal, then randomize the decision
                    int Choice = rand.nextInt(2);
                    
                    if(Choice == 0)
                    {
                        //Left
                        return AllP.get(Left);
                    }
                    else
                    {
                        //Right
                        return AllP.get(Right);
                    }
                    
                   
                }
                
            }
            else if(GameObject.equals("Shoot person two over left or right"))
            {
                ArrayList<Player> AllP = TotP;
                int Left = 0;
                int Right = 0;
                
                if(CurrentPIndex > 1)
                {
                    Left = CurrentPIndex -2;
                }
                else
                {
                    Left = AllP.size()-2 + CurrentPIndex;
                }
                
                if(CurrentPIndex < AllP.size()-2)
                {
                    Right = CurrentPIndex +2;
                }
                else if (CurrentPIndex < AllP.size()-1)
                {
                    Right = 0;
                }
                else
                {
                    Right = 1;
                }
                
                int L_Favor = FavorSystemShooting(CurrentPlayer, AllP.get(Left));
                int R_Favor = FavorSystemShooting(CurrentPlayer, AllP.get(Right));
                
                if(L_Favor > R_Favor)
                {
                    return AllP.get(Left);
                }
                else if(R_Favor > L_Favor)
                {
                    return AllP.get(Right);
                }
                else
                {
                    //if equal, then randomize the decision
                    int Choice = rand.nextInt(2);
                    
                    if(Choice == 0)
                    {
                        //Left
                        return AllP.get(Left);
                    }
                    else
                    {
                        //Right
                        return AllP.get(Right);
                    }
                    
                   
                }
            }
            else if(GameObject.equals("Shoot Other Player"))
            {
                ArrayList<Player> AllP = TotP;
                Player P = AllP.get(AllP.size()-1 - CurrentPIndex);
                
                return P;
            }
            else if(GameObject.equals("Shoot To Left"))
            {
                ArrayList<Player> AllP = TotP;
                Player P = AllP.get(CurrentPIndex - 2);
                
                return P;
            }
            else if(GameObject.equals(("Shoot To Right")))
            {
                ArrayList<Player> AllP = TotP;
                Player P = AllP.get(CurrentPIndex + 2);
                
                return P;
            }
            
   
        
        
        return ret;
    }
    
    /**
    * Method that gets a numeric value to decide who holds a Higher Favor
    * @param CPlayer Player, the Current Player who is making moves
    * @param  OPlayer Player, another Player being assessed for a favor value
    * @return Integer, The numeric Value to determine ranking. If the Number is 
    * higher, the More Favorable the Player is to be Targeted.
    */
    public int FavorSystemShooting(Player CPlayer, Player OPlayer)
    {
        String PlayerRole = CPlayer.getRole();
        String OtherRole = OPlayer.getRole();
        
        if(PlayerRole.equals("Sheriff"))
        { 
            //get based on players health
            return OPlayer.getHealth();
        }
        else if (PlayerRole.equals("Deputy"))
        {
            if (OtherRole.equals("Sheriff"))
            {
                return 0;
            }
            else
            {
                return OPlayer.getHealth();
            }
        }
        else if (PlayerRole.equals("Outlaw"))
        {
            if (OtherRole.equals("Sheriff"))
            {
                return 100;
            }
            else
            {
                return OPlayer.getHealth();
            }
        }
        else if (PlayerRole.equals("Renegade"))
        {
            return OPlayer.getHealth();
        }
        else if(PlayerRole.equals("Alive"))
        {
            if(OtherRole.equals("Zombie") || OtherRole.equals("Zombie Master"))
            {
                return 100;
            }
            else
            {
                return 0;
            }
                
        }
        else if(PlayerRole.equals("Zombie") || PlayerRole.equals("Zombie Master"))
        {
            if(OtherRole.equals("Alive"))
            {
                return 100;
            }
            else
            {
                return 0;
            }
        }
        
        return -1;
    }
    
    /**
    * Method that gets all the Players who hold a specific role
    * @param T String, The Role that you want to retrieve
    * @param CN String, The Character Name of the Current Player making moves
    * @param TotP ArrayList<Player>, ArrayList of the all the Players objects at
    * the table.
    * @return ArrayList<Player> , a List of all Player Objects who have the
    * designated Role.
    */
    private ArrayList<Player> getPlayerType(String T, String CN, ArrayList<Player> TotP)
    {
        ArrayList<Player> Ret = new ArrayList<Player>();
        ArrayList<Player> TotalPlayers = TotP;
        
        for(int a = 0; a < TotalPlayers.size(); a++)
        {
            Player P = TotalPlayers.get(a);
           
            if(P.getRole().equals(T) && !P.getCharacterName().equals(CN))
            {
                Ret.add(P);
            }
            
        }
        return Ret;
    }
    
    /**
    * Method that Sorts a List of Players by their Current Health
    * @param Data ArrayList<Player>, ArrayList of the all the Players objects at
    * the table.
    * @param GreatestToSmallest Boolean, whether or not you want it to sort 
    * Greatest to Smallest or vice versa.
    * @return ArrayList<Player> , a List of all Player Objects who have been
    * sorted via their Current Health.
    */
    private ArrayList<Player> sortPlayersHealth(ArrayList<Player> Data, boolean GreatestToSmallest)
    {
        if(GreatestToSmallest)
        {
            for(int j = 0; j < Data.size()-1; j++)
            {
		for (int i = 0; i < Data.size()-1; i++)
        	{
            		if (Data.get(i).getHealth() < Data.get(i+1).getHealth())
            		{
                		Player PlaceHolder = Data.get(i+1);
                		Data.set(i+1, Data.get(i));
               			Data.set(i, PlaceHolder);
            		}
        	}
            }
        }
        else
        {
            for(int j = 0; j < Data.size()-1; j++)
            {
		for (int i = 0; i < Data.size()-1; i++)
        	{
            		if (Data.get(i).getHealth() > Data.get(i+1).getHealth())
            		{
                		Player PlaceHolder = Data.get(i+1);
                		Data.set(i+1, Data.get(i));
               			Data.set(i, PlaceHolder);
            		}
        	}
            }
        }
        
        return Data;
    }
    
    /**
    * Method that gets the exact index where the Current Player is at the Table
    * @param CN String, The Character Name of the Current Player making moves
    * @param TotP ArrayList<Player>, ArrayList of the all the Players objects at
    * the table.
    * @return Integer, the Index where the Current Player is at the Table
    */
    private int getCurrentPlayerIndex(String CN, ArrayList<Player> TotP)
    {
        ArrayList<Player> TotalPlayers = TotP;
        
        for(int a = 0; a < TotalPlayers.size(); a++)
        {
            Player P = TotalPlayers.get(a);
           
            if(P.getCharacterName().equals(CN))
            {
                return a;
            }
        }
        
        return -1;
    }
    
    /**
    * Method that checks if it is even possible for a Player to shoot 2 to left 
    * or right.
    * @param CurrentMove String, The Action that the Player is looking to apply
    * @param PlayerI Integer, The Index where the Current Player is located at 
    * the table.
    * @param TotP ArrayList<Player>, ArrayList of the all the Players objects at
    * the table.
    * @return String, An adjusted Action based upon the Players positioning at 
    * the Table and the size of the Table.
    */
    private String UseDifferentShooting(String CurrentMove, int PlayerI, ArrayList<Player> TotP)
    {
        String ret = CurrentMove;
        
        if(CurrentMove.equals("Shoot person one over left or right"))
        {
            if(TotP.size() == 2)
            {
                ret = "Shoot Other Player";
            }
       
        }
        else if(CurrentMove.equals("Shoot person two over left or right"))
        {
           if(TotP.size() == 4)
            {
                int DiffL = PlayerI - 2;
                int DiffR = PlayerI + 2;
                
                if(DiffL >= 0)
                {
                    ret = "Shoot To Left";
                }
                else
                {
                    ret = "Shoot To Right";
                }
            }
           else if(TotP.size() == 3)
           {
               ret = "Shoot person one over left or right";
           }
           else if(TotP.size() == 2)
           {
                ret = "Shoot Other Player";
           }
        }
        
        return ret;
    }
    
    /**
    * Method that handles all of the Rerolling Decisions
    * @param CurrentPlayer Player, the Current Player who is making moves
    * @param D ArrayList<Dice>, The ArrayList of Dice objects that will be 
    * checked for Rerolling.
    * @param TotP ArrayList<Player>, ArrayList of the all the Players objects at
    * the table.
    * @return ArrayList<Dice> , will return an ArrayList of all the Dice Objects
    */
     public ArrayList<Dice> RerollHandler(Player CurrentPlayer, ArrayList<Dice> D, ArrayList<Player> TotP)
    {
        for(int a = 0; a < 2 && CurrentPlayer.CanReroll(); a++)
        {
            int TNT = DiceCounter(1, D);
            int GAT = DiceCounter(5, D);
            if(TNT == 3 || GAT == 3)
            {
                //If you have 3 TNT sticks or Gattling gun, no more rerolls
                CurrentPlayer.setRerolls(0);
            }
            else if(CurrentPlayer.getHealth() < 2)
            {
                //if health < 2, roll for Beer
                D = Reroll(D, 1, CurrentPlayer);
            }
            else if(GAT == 2)
            {
                //reroll for Gattling gun
                D = Reroll(D, 2, CurrentPlayer);
            }
            else if (CurrentPlayer.isFullHealth())
            {
                //if full Health, reroll Beer
                D = Reroll(D, 3, CurrentPlayer);
            }
            else if(CurrentPlayer.getRole().equals("Outlaw"))
            {
                //outlaws reroll for 2 or 3 Dice
                D = Reroll(D, 4, CurrentPlayer);
            }
            else if(CurrentPlayer.getRole().equals("Renegade"))
            {
                //renegade reroll for beer
                D = Reroll(D, 1, CurrentPlayer);
            }
            else if(CurrentPlayer.getRole().equals("Deputy"))
            {
                //deputy rerolls for beer
                D = Reroll(D, 1, CurrentPlayer);
            }
            else if(CurrentPlayer.getRole().equals("Alive") || CurrentPlayer.getRole().equals("Zombie") || CurrentPlayer.getRole().equals("Zombie Master"))
            {
                String Act = DOAReroll(TotP, CurrentPlayer);
                if(Act.equals("Reroll 2"))
                {
                    D = Reroll(D, 6, CurrentPlayer);
                }
                else if (Act.equals("Reroll 1"))
                {
                    D = Reroll(D, 5, CurrentPlayer);
                }
            }

            
            
        }
                
        return D;
    }
    
    /**
    * Method that counts how many Dice have been rolled with a selected face
    * @param Face Integer, the numeric value of the Face that you are trying to
    * check for.
    * @param D ArrayList<Dice>, The ArrayList of Dice objects that will be 
    * checked for Rerolling.
    * @return Integer, will return the number of Dice with the selected face
    */
    private int DiceCounter(int Face, ArrayList<Dice> D)
    {
        int ret = 0;
        for(int a = 0; a < D.size(); a++)
        {
            if(D.get(a).getDiceInt() == Face)
            {
                ret++;
            }
        }
        
        return ret;
    }
    
    /**
    * Method that sets dice with certain faces to be Rerolled
    * @param D ArrayList<Dice>, The ArrayList of Dice objects that will be 
    * checked for Rerolling.
    * @param Type Integer, is a numeric value that will tell the method which 
    * dice could be rerolled.
    * @param CurrentPlayer Player, the Current Player who is making moves
    * @return ArrayList<Dice> , will return an ArrayList of all the Dice Objects
    */
    private ArrayList<Dice> Reroll(ArrayList<Dice> D, int Type, Player CurrentPlayer)
    {
        if(Type == 1)
        {
            //Health reroll so reroll Arrow, 1 or 2, or Gattling
            for(int a = 0; a < D.size(); a++)
            {
                if(D.get(a).getDiceInt() == 0 || D.get(a).getDiceInt() == 2 || D.get(a).getDiceInt() == 3 || D.get(a).getDiceInt() == 5)
                {
                    D.get(a).setReroll(true, CurrentPlayer.getCharacterName());
                    //CurrentPlayer.usedReroll(); will be changed in the turn class
                    a += D.size();
                }
            }
        }
        else if (Type == 2)
        {
            for(int a = 0; a < D.size(); a++)
            {
                
                if(D.get(a).getDiceInt() == 4 && CurrentPlayer.isFullHealth())
                {
                    D.get(a).setReroll(true, CurrentPlayer.getCharacterName());
                    //CurrentPlayer.usedReroll();
                    a += D.size();
                }
                else if(D.get(a).getDiceInt() == 0 || D.get(a).getDiceInt() == 2 || D.get(a).getDiceInt() == 3)
                {
                    D.get(a).setReroll(true, CurrentPlayer.getCharacterName());
                    //CurrentPlayer.usedReroll();
                    a += D.size();
                }
            }
        }
        else if(Type == 3)
        {
            for(int a = 0; a < D.size(); a++)
            {
                if(D.get(a).getDiceInt() == 4)
                {
                    D.get(a).setReroll(true, CurrentPlayer.getCharacterName());
                    //CurrentPlayer.usedReroll();
                    a += D.size();
                }
            }
        }
        else if(Type == 4)
        {
            
            for(int a = 0; a < D.size(); a++)
            {
                if(D.get(a).getDiceInt() == 0 || D.get(a).getDiceInt() == 4 || D.get(a).getDiceInt() == 5)
                {
                    D.get(a).setReroll(true, CurrentPlayer.getCharacterName());
                    //CurrentPlayer.usedReroll();
                    a += D.size();
                }
            }
        }
        else if(Type == 5)
        {
            for(int a = 0; a < D.size(); a++)
            {
                if(D.get(a).getDiceInt() == 2)
                {
                    D.get(a).setReroll(true, CurrentPlayer.getCharacterName());
                    //CurrentPlayer.usedReroll();
                    a += D.size();
                }
            }
        }
        else if(Type == 6)
        {
            for(int a = 0; a < D.size(); a++)
            {
                if(D.get(a).getDiceInt() == 3)
                {
                    D.get(a).setReroll(true, CurrentPlayer.getCharacterName());
                    //CurrentPlayer.usedReroll();
                    a += D.size();
                }
            }
        }
        
        
        
        return D;
    }
    
    /**
    * Method that handles Dice Rerolling if the Game Mode has been changed to 
    * Dead Or Alive.
    * @param TotP ArrayList<Player>, ArrayList of the all the Players objects at
    * the table.
    * @param @param CurrentPlayer Player, the Current Player who is making moves
    * @return String, An adjusted Action based upon the Players role at and the 
    * size of the Table.
    */
    private String DOAReroll(ArrayList<Player> TotP, Player CurrentPlayer)
    {
        String PlayerRole = CurrentPlayer.getRole();
        
        if(PlayerRole.equals("Zombie Master"))
        {
            PlayerRole = "Zombie";
        }
        
        String Ret = "";
        int PIndex = 0;
        for(int a = 0; a < TotP.size(); a++)
        {
            if(TotP.get(a).getCharacterName().equals(CurrentPlayer.getCharacterName()))
            {
                PIndex = a;
                a+= TotP.size();
            }
        }
        //Make a table with an array of 5, you're in the middle
        Player[] Table = new Player[5];
        if(TotP.size() >= 5)
        {
            if(PIndex == 1)
            {
                Table[0] = TotP.get(TotP.size()-1);
                Table[1] = TotP.get(0);
                Table[2] = CurrentPlayer;
                Table[3] = TotP.get(PIndex+1);
                Table[4] = TotP.get(PIndex+2);
            }
            else if(PIndex == 0)
            {
                Table[0] = TotP.get(TotP.size()-2);
                Table[1] = TotP.get(TotP.size()-1);
                Table[2] = CurrentPlayer;
                Table[3] = TotP.get(PIndex+1);
                Table[4] = TotP.get(PIndex+2);
            }
            else if(PIndex == TotP.size()-2)
            {
                Table[0] = TotP.get(PIndex-2);
                Table[1] = TotP.get(PIndex-1);
                Table[2] = CurrentPlayer;
                Table[3] = TotP.get(TotP.size()-1);
                Table[4] = TotP.get(0);
            }
            else if (PIndex == TotP.size()-1)
            {
                Table[0] = TotP.get(PIndex-2);
                Table[1] = TotP.get(PIndex-1);
                Table[2] = CurrentPlayer;
                Table[3] = TotP.get(0);
                Table[4] = TotP.get(1);
            }
            else
            {
                Table[0] = TotP.get(PIndex-2);
                Table[1] = TotP.get(PIndex-1);
                Table[2] = CurrentPlayer;
                Table[3] = TotP.get(PIndex+1);
                Table[4] = TotP.get(PIndex+2);
            }
            
            if(Table[0].getRole().equals(PlayerRole) && Table[4].getRole().equals(PlayerRole))
            {
                Ret = "Reroll 2";
            }
             
        }
        else if(TotP.size() >= 3)
        {
            Player L = null;
            Player R = null;
            if(PIndex == 0)
            {
                L = TotP.get(TotP.size() -1);
                R = TotP.get(1);
            }
            else if (PIndex == TotP.size()-1)
            {
                L = TotP.get(PIndex -1);
                R = TotP.get(0);
            }
            else
            {
                L = TotP.get(PIndex - 1);
                R = TotP.get(PIndex + 1);
            }
            
            if(L.getRole().equals(PlayerRole) && R.getRole().equals(PlayerRole))
            {
                Ret = "Reroll 1";
            }
        }
        else
        {
            Ret = "None";
        }
        
        
        
        
        return Ret;
    }
    
}