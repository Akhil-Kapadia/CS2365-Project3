package cs2365_project3;

class Player
{
    private String CharacterName;
    private String Role;
    
    private int CurrentHealth;
    private int MaxHealth;
    private int ArrowCount; 
    private int PlayerIndex;
    private int RerollsRemaining;
    private boolean User;
    
    //Needs: CharacterName, Role, Player Index, and Max Health to intialize
    public Player(String CN, String R, int PI, int MH, boolean U)
    {
        CharacterName = CN;
        Role = R;
        ArrowCount = 0;
        PlayerIndex = PI;
        RerollsRemaining = 2;
        MaxHealth = MH;
        CurrentHealth = MaxHealth;
        User = U;
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
    
    public boolean getUser()
    {
        return User;
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
    
    public boolean equals(Player player2)
    {
        return getPlayerIndex() == player2.getPlayerIndex();
    }

}