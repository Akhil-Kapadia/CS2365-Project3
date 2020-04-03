/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop_p3;

import java.util.*;
import java.io.*;
import java.util.Random;

/**
 *
 * @author Demetrios
 */
public class OOP_P3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}



class Player
{
    private String CharacterName;
    private String Role;
    
    private int CurrentHealth;
    private int MaxHealth;
    private int ArrowCount; 
    private int PlayerIndex;
    private int RerollsRemaining;
    
    //Needs: CharacterName, Role, Player Index, and Max Health to intialize
    public Player(String CN, String R, int PI, int MH)
    {
        CharacterName = CN;
        Role = R;
        ArrowCount = 0;
        PlayerIndex = PI;
        RerollsRemaining = 2;
        MaxHealth = MH;
        CurrentHealth = MaxHealth;
    }
    
    
    //When player gets hit with Damage, the function will check if dead or not after the Damage takes place. If Dead will return true
    public boolean TakeDamage(int D)
    {
        CurrentHealth -= D;
        
        if(CurrentHealth > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public int getHealth()
    {
        return CurrentHealth;
    }
    
    public void addHealth()
    {
        if(CurrentHealth < MaxHealth)
        {
            CurrentHealth++;
        }
    }
    
    public boolean isFullHealth()
    {
        if(CurrentHealth == MaxHealth)
        {
            return true;
        }
        return false;
    }
    
    public String getCharacterName()
    {
        return CharacterName;
    }
    
    public int getPlayerIndex()
    {
        return PlayerIndex;
    }
    
    public String getRole()
    {
        return Role;
    }
    
    public String getAbility()
    {
        String ret = "NONE";
        
        if(CharacterName.equals("BART CASSIDY"))
        {
            ret = "Take Arrow, not lose Life";
        }
        else if(CharacterName.equals("BLACK JACK"))
        {
            ret = "Reroll Dynamite";
        }
        else if(CharacterName.equals("CALAMITY JANET"))
        {
            ret = "Use 3A as 3B, vise versa";
        }
        else if(CharacterName.equals("EL GRINGO"))
        {
            ret = "Lose 1 or more health, Take Arrow";
        }
        else if(CharacterName.equals("JESSE JONES"))
        {
            ret = "If 4 health or less and use Beer, Add 2 Health";
        }
        else if(CharacterName.equals("JOURDONNAIS"))
        {
            ret = "Never lose 1 or more health to Indians";
        }
        else if(CharacterName.equals("KIT CARLSON"))
        {
            ret = "For each Gattling, you can discard player Arrows";
        }
        else if(CharacterName.equals("LUCKY DUKE"))
        {
            ret = "Extra reroll";
        }
        else if(CharacterName.equals("PAUL REGRET"))
        {
            ret = "Never lose Health to Gattling";
        }
        else if(CharacterName.equals("PEDRO RAMIREZ"))
        {
            ret = "Loss of Health, you can discard 1 Arrow";
        }
        else if(CharacterName.equals("ROSE DOOLAN"))
        {
            ret = "Can use 3A or 3B for players one further away";
        }
        else if(CharacterName.equals("SID KETCHUM"))
        {
            ret = "Start of turn, any player gains 1 health";
        }
        else if(CharacterName.equals("SLAB THE KILLER"))
        {
            ret = "Per turn, can use Beer to 3A or 3B";
        }
        else if(CharacterName.equals("SUZY LAFAYETTE"))
        {
            ret = "No 3A or 3B, Add 1 health";
        }
        else if(CharacterName.equals("VULTURE SAM"))
        {
            ret = "Each time Player dies, Add 2 health";
        }
        else if(CharacterName.equals("WILLY THE KID"))
        {
            ret = "One need 2 points to use Gattling";
        }
        
        
        return ret;
    }
    
    public void usedReroll()
    {
        RerollsRemaining--;
    }
    public void setRerolls(int N)
    {
        RerollsRemaining = N;
    }
    
    public boolean CanReroll()
    {
        if(RerollsRemaining >0)
        {
            return true;
        }
        return false;
    }
    
    public int getArrowCount()
    {
        return ArrowCount;
    }
    
    public void setArrowCount(int A)
    {
        ArrowCount += A;
    }
    
    
    
    /*
    
    //Will be used for Abilities shit
    
    public void UseAbility(Dice[] D)
    {
        
        
        if(CharacterName.equals("BART CASSIDY"))
        {
            
        }
        else if(CharacterName.equals("BLACK JACK"))
        {
            
        }
        else if(CharacterName.equals("CALAMITY JANET"))
        {
            
        }
        else if(CharacterName.equals("EL GRINGO"))
        {
            
        }
        else if(CharacterName.equals("JESSE JONES"))
        {
            
        }
        else if(CharacterName.equals("JOURDONNAIS"))
        {
            
        }
        else if(CharacterName.equals("KIT CARLSON"))
        {
            
        }
        else if(CharacterName.equals("LUCKY DUKE"))
        {
            
        }
        else if(CharacterName.equals("PAUL REGRET"))
        {
            
        }
        else if(CharacterName.equals("PEDRO RAMIREZ"))
        {
            
        }
        else if(CharacterName.equals("ROSE DOOLAN"))
        {
            
        }
        else if(CharacterName.equals("SID KETCHUM"))
        {
            
        }
        else if(CharacterName.equals("SLAB THE KILLER"))
        {
           
        }
        else if(CharacterName.equals("SUZY LAFAYETTE"))
        {
            
        }
        else if(CharacterName.equals("VULTURE SAM"))
        {
            
        }
        else if(CharacterName.equals("WILLY THE KID"))
        {
           
        }
    }
    
    */
    
    
}


class AIDecisionMaking
{
    Random rand = new Random();
    
    Game GameC;
    Turn TurnC;
    
    public AIDecisionMaking(Game G, Turn T)
    {
        GameC = G;
        TurnC = T;
    }
    
    public Player getHighestFavor(Player CurrentPlayer, Dice D)
    {
        Player ret = null;
        
        String GameObject = D.getDiceString();
        String PlayerRole = CurrentPlayer.getRole();
        String CName = CurrentPlayer.getCharacterName();
        int CurrentPIndex = getCurrentPlayerIndex(CName);
        GameObject = UseDifferentShooting(GameObject, CurrentPIndex);
       
        
        //handles who to shoot, who to heal
        
        if(GameObject.equals("Beer"))
        {
            if(PlayerRole.equals("Sheriff"))
            {
                //heal self if not full, else heal the lowest deputy
                if(!CurrentPlayer.isFullHealth())
                {
                    return CurrentPlayer;
                }
                else
                {
                    ArrayList<Player> Deputys = getPlayerType("Deputy", CName);
                    Deputys = sortPlayersHealth(Deputys, false);
                    
                    //if there are no Deputy, then the CurrentPlayer will be the highest favor
                    if(Deputys.size() > 0)
                    {
                        return Deputys.get(0);
                    }
                    else
                    {
                        return CurrentPlayer;
                    }
                }
            }
            else if (PlayerRole.equals("Deputy"))
            {
                if(!CurrentPlayer.isFullHealth())
                {
                    return CurrentPlayer;
                }
                else
                {
                    ArrayList<Player> Sheriffs = getPlayerType("Sheriff", CName);
                    Sheriffs = sortPlayersHealth(Sheriffs, false);
                    
                    //if there are no Deputy, then the CurrentPlayer will be the highest favor
                    if(Sheriffs.size() > 0)
                    {
                        return Sheriffs.get(0);
                    }
                    else
                    {
                        return CurrentPlayer;
                    }
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
                ArrayList<Player> AllP = GameC.getTableSeating();
                
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
                
                int L_Favor = FavorSystemShooting(PlayerRole, AllP.get(Left).getRole());
                int R_Favor = FavorSystemShooting(PlayerRole, AllP.get(Right).getRole());
                
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
                ArrayList<Player> AllP = GameC.getTableSeating();
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
                
                int L_Favor = FavorSystemShooting(PlayerRole, AllP.get(Left).getRole());
                int R_Favor = FavorSystemShooting(PlayerRole, AllP.get(Right).getRole());
                
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
                ArrayList<Player> AllP = GameC.getTableSeating();
                Player P = AllP.get(AllP.size()-1 - CurrentPIndex);
                
                return P;
            }
            else if(GameObject.equals("Shoot To Left"))
            {
                ArrayList<Player> AllP = GameC.getTableSeating();
                Player P = AllP.get(CurrentPIndex - 2);
                
                return P;
            }
            else if(GameObject.equals(("Shoot To Right")))
            {
                ArrayList<Player> AllP = GameC.getTableSeating();
                Player P = AllP.get(CurrentPIndex + 2);
                
                return P;
            }
            
   
        
        
        return ret;
    }
    
    private ArrayList<Player> getPlayerType(String T, String CN)
    {
        ArrayList<Player> Ret = new ArrayList<Player>();
        ArrayList<Player> TotalPlayers = GameC.getTableSeating();
        
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
    
    private int getCurrentPlayerIndex(String CN)
    {
        ArrayList<Player> TotalPlayers = GameC.getTableSeating();
        
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
    
    private int FavorSystemShooting(String PlayerRole, String OtherRole)
    {
        if(PlayerRole.equals("Sheriff"))
        { 
            if (OtherRole.equals("Deputy"))
            {
                return 1;
            }
            else if (OtherRole.equals("Outlaw"))
            {
                return 2;
            }
            else if (OtherRole.equals("Renegade"))
            {
                return 2;
            }
        }
        else if (PlayerRole.equals("Deputy"))
        {
            if (OtherRole.equals("Sheriff"))
            {
                return 1;
            }
            else if (OtherRole.equals("Outlaw"))
            {
                return 2;
            }
            else if (OtherRole.equals("Renegade"))
            {
                return 2;
            }
        }
        else if (PlayerRole.equals("Outlaw"))
        {
            if (OtherRole.equals("Deputy"))
            {
                return 2;
            }
            else if (OtherRole.equals("Sheriff"))
            {
                return 3;
            }
            else if (OtherRole.equals("Renegade"))
            {
                return 1;
            }
        }
        else if (PlayerRole.equals("Renegade"))
        {
            if (OtherRole.equals("Deputy"))
            {
                return 1;
            }
            else if (OtherRole.equals("Outlaw"))
            {
                return 3;
            }
            else if (OtherRole.equals("Sheriff"))
            {
                return 2;
            }
        }
        
        return 0;
    }
    
    private String UseDifferentShooting(String CurrentMove, int PlayerI)
    {
        String ret = CurrentMove;
        
        if(CurrentMove.equals("Shoot person one over left or right"))
        {
            if(GameC.getTableSeating().size() == 2)
            {
                ret = "Shoot Other Player";
            }
       
        }
        else if(CurrentMove.equals("Shoot person two over left or right"))
        {
           if(GameC.getTableSeating().size() == 4)
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
           else if(GameC.getTableSeating().size() == 3)
           {
               ret = "Shoot person one over left or right";
           }
           else if(GameC.getTableSeating().size() == 2)
           {
                ret = "Shoot Other Player";
           }
        }
        
        return ret;
    }
    
}
