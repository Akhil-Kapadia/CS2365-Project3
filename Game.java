package cs2365_project3;

import java.util.Random;
import java.util.ArrayList;

public class Game {
    Random rand = new Random();
    
    int sher = 1; //used for game set up as a decrementing counter
    int depu, outl, reneg;  //used for game set up as a decrementing counter
    int roles[] = {sher, reneg, outl, depu};  //used for how many of each role are currently in the game
    
    private ArrayList<String> characterNames;
    
    private int totalPlayers;
    private ArrayList<Player> tableSeating = new ArrayList<>(totalPlayers);
    
    public void setTotalPlayers()
    {
        this.totalPlayers = rand.nextInt(5) + 4; //random range 4 to 8
    }
    
    public int getTotalPlayers()
    {
        return this.totalPlayers;
    }
    
    public void setCharacterNames()
    {
        this.characterNames = new ArrayList<>();
        this.characterNames.add("BART CASSIDY");
        this.characterNames.add("BLACK JACK");
        this.characterNames.add("CALAMITY JANET");
        this.characterNames.add("EL GRINGO");
        this.characterNames.add("JESSE JONES");
        this.characterNames.add("JOURDONNAIS");
        this.characterNames.add("KIT CARLSON");
        this.characterNames.add("LUCKY DUKE");
        this.characterNames.add("PAUL REGRET");
        this.characterNames.add("PEDRO RAMIREZ");
        this.characterNames.add("ROSE DOOLAN");
        this.characterNames.add("SID KETCHUM");
        this.characterNames.add("SLAB THE KILLER");
        this.characterNames.add("SUZY LAFAYETTE");
        this.characterNames.add("VULTURE SAM");
        this.characterNames.add("WILLY THE KID");
    }
    
    public String getCharacterName()
    {
        //get a random name from the list to return and remove that name from the list
        int randomIndex = rand.nextInt(characterNames.size());
        String name = characterNames.get(randomIndex);
        characterNames.remove(randomIndex);
        return name;
    }
    
    public void gameSetup()
    {
        //dice creation
        for(int d = 0; d < 5; d++)
            dice[d] = createDice(); //this will change based on how the dice code works
        
        //player creation
        setTotalPlayers();
        switch (getTotalPlayers()) { //number of roles changes based on number of players
            case 4:
                roles[1] = 1;
                roles[2] = 2;
                roles[3] = 0;
                reneg = 1;
                outl = 2;
                depu = 0;
                break;
            case 5:
                roles[1] = 1;
                roles[2] = 2;
                roles[3] = 1;
                reneg = 1;
                outl = 2;
                depu = 1;
                break;
            case 6:
                roles[1] = 1;
                roles[2] = 3;
                roles[3] = 1;
                reneg = 1;
                outl = 3;
                depu = 1;
                break;
            case 7:
                roles[1] = 1;
                roles[2] = 3;
                roles[3] = 2;
                reneg = 1;
                outl = 3;
                depu = 2;
                break;
            default:
                roles[1] = 2;
                roles[2] = 3;
                roles[3] = 2;
                reneg = 2;
                outl = 3;
                depu = 2;
                break;
        }
        
        setCharacterNames(); //initalize the list of all of the character names
        
        for(int index = 0; index < getTotalPlayers(); index++) //add all the players to the list
            tableSeating.add(createPlayer(index));
    }
    
    public Player createPlayer(int index)
    {        
        String characterName = getCharacterName(); //set the character name
        
        int role = rand.nextInt(5);
        for(;;) //loops infintely until you can add that role into the game
        {
            if(role == 0 && sher != 0) //if you can add a sheriff
            {
                sher--;
                break; 
            }                     
            if(role == 1 && reneg != 0) //if you can add a renegade
            {
                reneg--;
                break; 
            }         
            if(role == 2 && outl != 0) //if you can add a outlaw
            {
                outl--;
                break; 
            }         
            if(role == 3 && depu != 0) //if you can add a deputy
            {
                depu--;
                break; 
            } 
            
            role = rand.nextInt(5); //nothing could be added so generate a new number
        }
        
        String roleName = "";
        if(role == 0)
            roleName = "Sheriff";
        if(role == 1)
            roleName = "Renegade";
        if(role == 2)
            roleName = "Outlaw";
        if(role == 3)
            roleName = "Deputy";
        
        //set HP based on character name
        int HP = 0;
        switch (characterName){
            case "BART CASSIDY":
                HP = 8;
                break;
            case "BLACK JACK":
                HP = 8;
                break;
            case "CALAMITY JANET":
                HP = 8;
                break;
            case "EL GRINGO":
                HP = 7;
                break;
            case "JESSE JONES":
                HP = 9;
                break;
            case "JOURDONNAIS":
                HP = 7;
                break;
            case "KIT CARLSON":
                HP = 7;
                break;
            case "LUCKY DUKE":
                HP = 8;
                break;
            case "PAUL REGRET":
                HP = 9;
                break;
            case "PEDRO RAMIREZ":
                HP = 8;
                break;
            case "ROSE DOOLAN":
                HP = 9;
                break;
            case "SID KETCHUM":
                HP = 8;
                break;
            case "SLAB THE KILLER":
                HP = 8;
                break;
            case "SUZY LAFAYETTE":
                HP = 8;
                break;
            case "VULTURE SAM":
                HP = 9;
                break;
            case "WILLY THE KID":
                HP = 8;
                break;
        }
        
        if(role == 0)
            HP = HP + 2; //if sheriff increase HP by 2
    
        Player newPlayer = new Player(characterName, roleName, index, HP);
        
        return newPlayer;
    }
    
    public void createDice()
    {
        
    }
    
    public int winCondition()
    {
        if(roles[0] == 0 && roles[1] == 1 && roles[2] == 0 && roles[3] == 0)
            return 1; //everyone else died, single renegade wins
        else if(roles[1] == 0 && roles[2] == 0)
            return 2; //outlaws and renegade(s) died, sheriff wins
        else if(roles[0] == 0)
            return 3; //sheriff died, outlaws win
        else
            return 0; //game is not over
    }
    
    public boolean checkGameOver(Player deadPlayer)
    {
        if(winCondition() != 0)
            return true; //game is over
        else
            return false; //game is not over
    }
    
    public boolean checkPlayerDeath(Player damagedPlayer)
    {
        if(damagedPlayer.getHealth() <= 0)
        {
            String role = checkPlayerRole(damagedPlayer);
            //decrement the current amount of the role of the dead player that exists in the game
            if(role.equals("Sheriff"))
                roles[0]--;
            else if(role.equals("Renegade"))
                roles[1]--;
            else if(role.equals("Outlaw"))
                roles[2]--;
            else
                roles[3]--;
            
            System.out.println("Player " + damagedPlayer.getIndex() + " is dead, their role was " + role);
            
            tableSeating.remove(damagedPlayer); //remove the player from the seating
            return true;
        }
        else
            return false;
    }
    
    public String checkPlayerRole(Player player)
    {
        return player.getRole();
    }
}
