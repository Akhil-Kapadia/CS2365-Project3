package cs2365_project3;

/**
 * The Player Class provides the essential data that all Players in the game
 * will need.
 * Included is a series of getter and setter methods for other classes to 
 * retrieve and alter the data.
 * @author Demetrios Mihaltses
 */
class Player
{
    //A String for the Character Name of the Player
    private String CharacterName;
    //A String for the Role of the Player
    private String Role;
    
    //An Integer for the Current Health of the Player
    private int CurrentHealth;
    //An Integer for the Max Health of the Player
    private int MaxHealth;
    //An Integer for the amount of Arrows the Player has
    private int ArrowCount; 
    //An Integer for the Player Index at the Table
    private int PlayerIndex;
    //An Integer for the amount of Rerolls a Player has left
    private int RerollsRemaining;
    
    //A Boolean to tell if the Player is an AI or not
    private boolean User;
    
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
    
    
    /**
    * Method that applies Damage to the Player
    * @param D Integer, amount of Damage done to Player
    * @return Boolean, whether or not the Player is dead or not
    */
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
    
    /**
    * Method that gets whether or not the Player is AI or not
    * @return Boolean, whether or not the Player is AI or not
    */
    public boolean getUser()
    {
        return User;
    }
    
    /**
    * Method that gets the Players Health
    * @return Integer, the Player's health
    */
    public int getHealth()
    {
        return CurrentHealth;
    }
    
    /**
    * Method that adds to the Players Health
    * @param h Integer, number of Health that will be added
    */
    public void addHealth(int h)
    {
        for(int i = h; i > 0; i--)
        {
            if(CurrentHealth < MaxHealth)
            {
                CurrentHealth++;
            }
        }
    }
    
    /**
    * Method that checks if the Player is at Max Health
    * @return Boolean, whether or not the Player is at Max Health
    */
    public boolean isFullHealth()
    {
        if(CurrentHealth == MaxHealth)
        {
            return true;
        }
        return false;
    }
    
    /**
    * Method that gets the Character Name of the Player
    * @return String, The Character Name of the Player
    */
    public String getCharacterName()
    {
        return CharacterName;
    }
    
    /**
    * Method that gets the Player Index of the Player
    * @return Integer, The Player Index of the Player
    */
    public int getPlayerIndex()
    {
        return PlayerIndex;
    }
    
    /**
    * Method that gets the Role of the Player
    * @return String, Role of the Player
    */
    public String getRole()
    {
        return Role;
    }
    
    /**
    * Method that gets the Players special ability
    * @return String, a description of the Players special ability
    */
    public String getAbility()
    {
        String ret = "NONE";
        
        if(CharacterName.equals("BLACK JACK"))
        {
            ret = "Reroll Dynamite";
        }
        else if(CharacterName.equals("CALAMITY JANET"))
        {
            ret = "Use shoot two over as shoot one over and vise versa";
        }
        else if(CharacterName.equals("JESSE JONES"))
        {
            ret = "If 4 health or less and use Beer, add 2 Health";
        }
        else if(CharacterName.equals("PAUL REGRET"))
        {
            ret = "Never lose Health to Gatling";
        }
        else if(CharacterName.equals("SUZY LAFAYETTE"))
        {
            ret = "No 3A or 3B, Add 1 health";
        }
        else if(CharacterName.equals("VULTURE SAM"))
        {
            ret = "Each time Player dies, Add 2 health";
        }
        else if(CharacterName.equals("APACHE KID"))
        {
            ret = "If you roll an arrow you may take the Chief Arrow from another player";
        }
        else if(CharacterName.equals("BILL NOFACE"))
        {
            ret = "Apply arrow results only after last roll";
        }
        else if(CharacterName.equals("BELLE STAR"))
        {
            ret = "After each roll you can change 1 dynamite to gatling";
        }
        else if(CharacterName.equals("GREG DIGGER"))
        {
            ret = "You can use each whiskey rolled twice";
        }
        else //handle zombie
            ret = "No ability";
        
        
        return ret;
    }
    
    /**
    * Method that sets the Role of the Player
    * @param role String, The Role that will be set for the Player
    */
    public void setRole(String role)
    {
        this.Role = role;
    }
    
    /**
    * Method that subtracts a Reroll from the Player
    */
    public void usedReroll()
    {
        this.RerollsRemaining--;
    }
    
    /**
    * Method that sets the amount of Rerolls a Player has
    * @param N Integer, Amount of Rerolls the Player will have
    */
    public void setRerolls(int N)
    {
        RerollsRemaining = N;
    }
    
    /**
    * Method that checks if the Player can Reroll
    * @return Boolean, whether or not the Player has Rerolls left
    */
    public boolean CanReroll()
    {
        if(RerollsRemaining >0)
        {
            return true;
        }
        return false;
    }
    
    /**
    * Method that gets the amount of Arrows the Player has
    * @return Integer, the Arrow Count of the Player
    */
    public int getArrowCount()
    {
        return ArrowCount;
    }
    
    /**
    * Method that sets the amount of Arrows the Player has
    * @param A Integer, Amount of Arrows that will be added to the Players Count
    */
    public void setArrowCount(int A)
    {
        ArrowCount += A;
    }
    
    /**
    * Method that compares this Player object, with another Player object
    * @param player2 Player, another Player object that you want to be compared
    * with
    * @return Boolean, whether or not the two Player objects are equal
    */
    public boolean equals(Player player2)
    {
        return getPlayerIndex() == player2.getPlayerIndex();
    }

}