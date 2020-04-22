package cs2365_project3;

import java.util.Random;
import java.util.ArrayList;

public class Game {
    Random rand = new Random();

    int sher = 1; //used for game set up as a decrementing counter
    int depu, outl, reneg;  //used for game set up as a decrementing counter
    int roles[] = {sher, reneg, outl, depu};  //used for how many of each role are currently in the game
    
    ArrayList<Turn> gameResult = new ArrayList<>();
    
    private ArrayList<String> characterNames;
    
    private int totalPlayers;
    private ArrayList<Player> tableSeating = new ArrayList<>(0);
    
    private int arrowPile;
    
    public void setTotalPlayers()
    {
        this.totalPlayers = 5;
    }
    
    public int getTotalPlayers()
    {
        return this.totalPlayers;
    }
    
    public void decrementTotalPlayers()
    {
        this.totalPlayers--;
    }
    
    public void addTableSeating(Player newPlayer)
    {
        this.tableSeating.add(newPlayer);
    }
    
    public ArrayList<Player> getTableSeating()
    {
        return new ArrayList<>(tableSeating);
    }
    
    public void setTableSeating(ArrayList<Player> newSeating)
    {
        this.tableSeating = newSeating;
    }
    
    public void setArrowPile(int totalArrows)
    {
        this.arrowPile = totalArrows;
    }
    
    public int getArrowPile()
    {
        return this.arrowPile;
    }
    
    public void setCharacterNames()
    {
        this.characterNames = new ArrayList<>();
        this.characterNames.add("BLACK JACK");
        this.characterNames.add("CALAMITY JANET");
        this.characterNames.add("JESSE JONES");
        this.characterNames.add("PAUL REGRET");
        this.characterNames.add("SUZY LAFAYETTE");
        this.characterNames.add("VULTURE SAM");
    }
    
    public String getCharacterName()
    {
        //get a random name from the list to return and remove that name from the list
        int randomIndex = rand.nextInt(characterNames.size());
        String name = characterNames.get(randomIndex);
        characterNames.remove(randomIndex);
        return name;
    }
    
    public void playGame()
    {
        gameSetup();
        printGameSetup();
        boolean gameOver = false;
        int playerTurnIndex = 0;
        //ArrayList<Player> tableSeating = getTableSeating();
        for(Player player : getTableSeating())
        {
            if(player.getRole().equals("Sheriff"))
            {
                playerTurnIndex = player.getPlayerIndex();
                break;
            }
        }
        while(!gameOver)
        {
            Turn turn = new Turn(getTableSeating(), getArrowPile(), playerTurnIndex, roles);
            gameResult.add(turn);
            gameOver = turn.getGameOver();
            setTableSeating(turn.getTableSeating());
            setArrowPile(turn.getArrowStack());
            playerTurnIndex = (playerTurnIndex + 1) % tableSeating.size();
            if(gameOver)
                System.out.println("Game over on condition" + turn.getWinCond());
            System.out.println("--------------------------------------------------");
            //printGameSetup();
        }
            
    }
    
    public void gameSetup()
    {
        //set arrow pile
        setArrowPile(9);
        
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
            case "BLACK JACK":
                HP = 8;
                break;
            case "CALAMITY JANET":
                HP = 8;
                break;
            case "JESSE JONES":
                HP = 9;
                break;
            case "PAUL REGRET":
                HP = 9;
                break;
            case "SUZY LAFAYETTE":
                HP = 8;
                break;
            case "VULTURE SAM":
                HP = 9;
                break;
        }
        
        if(role == 0)
            HP = HP + 2; //if sheriff increase HP by 2
        
        boolean User = false;
        if(index == 0)
            User = true; //first player created is always user
    
        Player newPlayer = new Player(characterName, roleName, index, HP, User);
        
        return newPlayer;
    }
        
    public void printGameSetup()
    {
        System.out.println("There are " + getTotalPlayers() + " players in the game.\n");
        
        for(Player player : getTableSeating())
        {
            System.out.println("Name: " + player.getCharacterName());
            System.out.println("Ability: " + player.getAbility());
            System.out.println("Health: " + player.getHealth());
            System.out.println("Role: " + player.getRole());
            System.out.println("Arrows: " + player.getArrowCount());
            System.out.println("Index: " + player.getPlayerIndex());
            System.out.println("");
        }
    }
}
