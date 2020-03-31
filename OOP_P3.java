/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop_p3;

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
    private int[] Favor;
    
    
    public Player(String CN, String R, int MH, int AC, int PI, int RR)
    {
        CharacterName = CN;
        Role = R;
        MaxHealth = MH;
        CurrentHealth = MaxHealth;
        ArrowCount = AC;
        PlayerIndex = PI;
        RerollsRemaining = RR;
    }
    
    public int getHeath()
    {
        return CurrentHealth;
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
    
    public boolean CanReroll()
    {
        if(RerollsRemaining >0)
        {
            return true;
        }
        return false;
    }
    
    /*
    
    //Might have to use a Separate Class for applying the Abilities, I'll work on that tomorrow(WEDNESDAY)
    
    public void UseAbility(Dice[] D)
    {
        String Ability = getAbility();
        
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
    }*/
    
    
}