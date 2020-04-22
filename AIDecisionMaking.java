package cs2365_project3;

import java.util.ArrayList;
import java.util.Random;

class AIDecisionMaking
{
    Random rand = new Random();
    
    public Player getHighestFavor(Player CurrentPlayer, Dice D, ArrayList<Player> TotP)
    {
        Player ret = null;
        
        String GameObject = D.getDiceString();
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
        
        return -1;
    }
    
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
    
    //Reroll Stuff
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
            
            
        }
        
        //Reset rerolls for next turn
        CurrentPlayer.setRerolls(2);
        
        return D;
    }
    
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
    
    private ArrayList<Dice> Reroll(ArrayList<Dice> D, int Type, Player CurrentPlayer)
    {
        if(Type == 1)
        {
            //Health reroll so reroll Arrow, 1 or 2, or Gattling
            for(int a = 0; a < D.size(); a++)
            {
                if(D.get(a).getDiceInt() == 0 || D.get(a).getDiceInt() == 2 || D.get(a).getDiceInt() == 3 || D.get(a).getDiceInt() == 5)
                {
                    D.get(a).setReroll(true);
                    //CurrentPlayer.usedReroll(); will be changed in the turn class
                    a += D.size();
                }
            }
        }
        else if (Type == 2)
        {
            for(int a = 0; a < D.size(); a++)
            {
                if(D.get(a).getDiceInt() == 0 || D.get(a).getDiceInt() == 2 || D.get(a).getDiceInt() == 3)
                {
                    D.get(a).setReroll(true);
                    //CurrentPlayer.usedReroll();
                    a += D.size();
                }
                else if(D.get(a).getDiceInt() == 4 && CurrentPlayer.isFullHealth())
                {
                    D.get(a).setReroll(true);
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
                    D.get(a).setReroll(true);
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
                    D.get(a).setReroll(true);
                    //CurrentPlayer.usedReroll();
                    a += D.size();
                }
            }
        }
        
        
        return D;
    }
    
}