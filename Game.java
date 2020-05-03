package cs2365_project3;

import java.util.Random;
import java.util.ArrayList;

public class Game {
    Random rand = new Random();

    int sher = 1; //used for game set up as a decrementing counter
    int depu, outl, reneg;  //used for game set up as a decrementing counter
    int roles[] = {sher, reneg, outl, depu};  //used for how many of each role are currently in the game
    int zombie, alive;
    int rolesDoA[] = {zombie, alive};
        
    private ArrayList<String> characterNames; //arraylist that stores all the names of the characters
    
    private int totalPlayers; //total number of players in the game
    private ArrayList<Player> tableSeating = new ArrayList<>(0); //arraylist of all alive players
    private ArrayList<Player> deadList = new ArrayList<>(0); //arraylist of all dead players (expansion only)
    
    boolean DoA = false; //flag to see if dead or alive game mode needs to start
    boolean expansion = false; //flag to see if playing with expansions
    Deck deck = new Deck(); //deck that contains the graveyward cards
    int drawnCount = 0; //total count of cards drawn from graveyard
    
    private int arrowPile; //number of arrows in the pile
    
    ChiefArrow arrow;
    
    public Game(int totalPlayers, boolean expansion)
    {
        this.totalPlayers = totalPlayers;
        this.expansion = expansion;
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
    
    public void addDeadList(Player newPlayer)
    {
        this.deadList.add(newPlayer);
    }
    
    public ArrayList<Player> getDeadList()
    {
        return new ArrayList<>(deadList);
    }
    
    public void setDeadList(ArrayList<Player> newSeating)
    {
        this.deadList = newSeating;
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
        //contains all possible names of characters that can be chosen from
        this.characterNames = new ArrayList<>();
        this.characterNames.add("BLACK JACK");
        this.characterNames.add("CALAMITY JANET");
        this.characterNames.add("JESSE JONES");
        this.characterNames.add("PAUL REGRET");
        this.characterNames.add("SUZY LAFAYETTE");
        this.characterNames.add("VULTURE SAM");
        this.characterNames.add("APACHE KID");
        this.characterNames.add("BILL NOFACE");
        this.characterNames.add("BELLE STAR");
        this.characterNames.add("GREG DIGGER");        
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
        gameSetup(); //setup the game for the first time
        printGameSetup();
        boolean gameOver = false;
        int playerTurnIndex = 0;
        for(Player player : getTableSeating())
        {
            if(player.getRole().equals("Sheriff")) //the sheriff always starts the game
            {
                playerTurnIndex = player.getPlayerIndex();
                break;
            }
        }
        while(!gameOver)
        {
            Turn turn = new Turn(getTableSeating(), getDeadList(), getArrowPile(), playerTurnIndex, roles, rolesDoA, arrow, expansion, DoA); //create a new turn
            turn.playTurn();
            gameOver = turn.getGameOver(); //get if the game is over from turn
            setTableSeating(turn.getTableSeating()); //get the list of alive players from turn
            setDeadList(turn.getDeadList()); //get the list of dead players from turn
            setArrowPile(turn.getArrowStack()); //get the arrow count from turn
            roles = turn.getRoles();
            rolesDoA = turn.getRolesDoA();
            arrow = turn.getChiefArrow();
            if(turn.getPlayerAlive()) //if current player died, don't increase the index
                playerTurnIndex = (playerTurnIndex + 1) % tableSeating.size(); //set the player index to be the next alive player
            if(expansion && !DoA && !gameOver) //if playing the expansion and dead or alive not started, all dead players draw a card at the end of every turn
            {
                for(Player player : getDeadList())
                {
                    int value = deck.drawCard();
                    drawnCount+=value;
                    System.out.println(player.getCharacterName() + " drew a " + value + ". Total pile up to " + drawnCount);
                    if(drawnCount > getTableSeating().size()) //if drawn pile count is greater than players alive, start dead or alive game mode
                    {
                        System.out.println("Conditions met, dead or alive starting");
                        DoA = true;
                        zombieSetup();
                    }
                }
            }
            if(gameOver)
            {
                System.out.print("Game over on condition ");
                switch(turn.getWinCond())
                {
                    case 1: System.out.println("Everybody else died, single Renegade wins.");
                            break;
                    case 2: System.out.println("Outlaw and Renegade(s) died, Sheriff and Deputy wins.");
                            break;
                    case 3: System.out.println("Sheriff died, Outlaws win.");
                            break;
                    case 4: System.out.println("Alive died, Zombies win.");
                            break;
                    case 5: System.out.println("Zombies died, Alive win.");
                            break;
                }
            }
            System.out.println("--------------------------------------------------");
            printGameStatus();
        }
            
    }
    
    public void gameSetup()
    {
        //create a graveyard deck if playing with expansion and create chief arrow
        if(expansion)
        {
            deck.createDeck();
            arrow = new ChiefArrow();
        }

        //set arrow pile
        setArrowPile(9);
        
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
            case "APACHE KID":
                HP = 9;
                break;
            case "BILL NOFACE":
                HP = 9;
                break;
            case "BELLE STAR":
                HP = 8;
                break;
            case "GREG DIGGER":
                HP = 7;
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
    
    public void zombieSetup()
    {
        rolesDoA[0] = getDeadList().size();
        rolesDoA[1] = getTableSeating().size();
        System.out.println("Alive: " + rolesDoA[0] + "\t Dead: " + rolesDoA[1]);
        boolean zombieMaster = false; //used if case of 2 renegades (8 players)
        int totalAlive = getTableSeating().size();
        for(Player player : getTableSeating())
        {
            if(player.getRole().equals("Renegade") && totalPlayers < 8) //if less than 8 players and renegade alive, set as zombie master
                player.setRole("Zombie Master");
            else if(player.getRole().equals("Renegade") && totalPlayers == 8 && zombieMaster == false) //if more than 8 players and 2 renegades alive, set first as zombie master
            {
                player.setRole("Zombie Master");
                zombieMaster = true;
            }
            else
                player.setRole("Alive"); //change all alive people to have the role be alive
        }
        for(Player deadPlayer : getDeadList())
        {
            //easier to create a new player instead of changing values
            Player newPlayer = new Player(deadPlayer.getCharacterName(), "Zombie", deadPlayer.getPlayerIndex(), totalAlive, deadPlayer.getUser());
            tableSeating.add(deadPlayer.getPlayerIndex(), newPlayer);
        }
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
    
    public void printGameStatus()
    {
        System.out.println("There are " + getTableSeating().size() + " players left");
        for(Player player : getTableSeating())
        {
            System.out.println(player.getCharacterName() + " (" + player.getRole() + ") : HP - " + player.getHealth() + ",  Arrows: " + player.getArrowCount());
        }
        
        System.out.println();
    }
}
