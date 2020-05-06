package cs2365_project3;

import java.util.Random;
import java.util.ArrayList;

/**
 * The Game class is responsible setting up the game and then storing the data
 * as it occurs throughout the game.
 * Responsibilities include: performing game setup, performing dead or alive setup,
 * storing data that comes in from the turn class.
 * @author Jacob Strickland
 * Collaborators: Colin Morrsion
 */
public class Game {
    Random rand = new Random();

    int sher = 1; //used for game set up as a decrementing counter
    int depu, outl, reneg;  //used for game set up as a decrementing counter
    int roles[] = {sher, reneg, outl, depu};  //used for how many of each role are currently in the game
    int zombie, alive; //used to keep track of each when checking game over
    int rolesDoA[] = {zombie, alive}; //used as a decrementing counter
    Token tokens = new Token(); //used to store duel tokens
    private int index; //index of the current player
        
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
    
    /**
     * The constructor for the game class
     * @param totalPlayers Integer, the total players in the game
     * @param expansion Boolean, true if playing with expansions
     */
    public Game(int totalPlayers, boolean expansion)
    {
        this.totalPlayers = totalPlayers;
        this.expansion = expansion;
    }
    
    /**
     * Method to get total number of players in game
     * @return integer, number of players playing
     */
    public int getTotalPlayers()
    {
        return this.totalPlayers;
    }
    
    /**
     * Method to decrease total amount of players if player dies
     */
    public void decrementTotalPlayers()
    {
        this.totalPlayers--;
    }
    
    /**
     * Method to add seating at the table for player
     * @param newPlayer, get new player to add 
     */
    public void addTableSeating(Player newPlayer)
    {
        this.tableSeating.add(newPlayer);
    }
    
    /**
     * Method to get table seating arraylist
     * @return arrayList
     */
    public ArrayList<Player> getTableSeating()
    {
        return new ArrayList<>(tableSeating);
    }
    
    /**
     * Method to set the table seating
     * @param newSeating Arraylist
     */
    public void setTableSeating(ArrayList<Player> newSeating)
    {
        this.tableSeating = newSeating;
    }
    
    /**
     * Method adds a dead player to dead list
     * @param newPlayer Player, the player to be added to the dead list
     */
    public void addDeadList(Player newPlayer)
    {
        this.deadList.add(newPlayer);
    }
    
    /**
     * Method to return list of dead players
     * @return deadList ArrayList
     */
    public ArrayList<Player> getDeadList()
    {
        return new ArrayList<>(deadList);
    }
    
    /**
     * Method to set the dead list 
     * @param newSeating ArrayList, the array list of players to set as the dead list
     */
    public void setDeadList(ArrayList<Player> newSeating)
    {
        this.deadList = newSeating;
    }
    
    /**
     * Method to set the total amount of arrows in arrow pile 
     * @param totalArrows integer 
     */
    public void setArrowPile(int totalArrows)
    {
        this.arrowPile = totalArrows;
    }
    
    /**
     * Method to get how many arrows are in arrow pile
     * @return integer, arrowPile number of arrows in pile
     */
    public int getArrowPile()
    {
        return this.arrowPile;
    }
    
    /**
     * Method to get if expansion selected or not
     * @return boolean expansion, true if selected 
     */
    public boolean getExpansion()
    {
        return this.expansion;
    }
    
    /**
     * Method to get if to play dead or alive game
     * @return boolean true if DoA
     */
    public boolean getDoA()
    {
        return this.DoA;
    }
    
    /**
     * Method to set the index of the current player
     * @param index Integer, the index of the current player
     */
    public void setIndex(int index)
    {
        this.index = index;
    }
    
    /**
     * Method to get the index of the current player
     * @return Integer, the index of the current player
     */
    public int getIndex()
    {
        return this.index;
    }
    
    /**
     * Method to get the total value of cards drawn
     * @return Integer, the value of the total cards drawn so far
     */
    public int getCardPile()
    {
        return this.drawnCount;
    }
    
    /**
     * Method to set all possible character names in an arraylist
     */
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
    
    /**
     * Method to get the character name from list then remove name from list
     * @return string character name 
     */
    public String getCharacterName()
    {
        //get a random name from the list to return and remove that name from the list
        int randomIndex = rand.nextInt(characterNames.size());
        String name = characterNames.get(randomIndex);
        characterNames.remove(randomIndex);
        return name;
    }
    
    /**
     * Method creates a new turn for player whos turn it is
     * @param playerTurnIndex integer 
     * @return a new turn 
     */
    public Turn createTurn(int playerTurnIndex)
    {
        return new Turn(getTableSeating(), getDeadList(), getArrowPile(), playerTurnIndex, roles, rolesDoA, arrow, tokens, expansion, DoA); //create a new turn
    }
    
    /**
     * Method to update the game information after a players turn 
     * @param turn Turn, the turn to update the game with
     * @return Game, the game object
     */
    public Game updateGame(Turn turn)
    {
        setTableSeating(turn.getTableSeating()); //get the list of alive players from turn
        setDeadList(turn.getDeadList()); //get the list of dead players from turn
        setArrowPile(turn.getArrowStack()); //get the arrow count from turn
        roles = turn.getRoles();
        rolesDoA = turn.getRolesDoA();
        arrow = turn.getChiefArrow();
        tokens = turn.getTokens();
        if(turn.getPlayerAlive()) //if current player died, don't increase the index
            setIndex((getIndex() + 1) % tableSeating.size()); //set the player index to be the next alive player
        
        return this;
    }
    
    /**
     * Method for dead player to draw a card from graveyard and check conditions for Dead or alive game. 
     */
    public void deadDraw()
    {
        for(Player player : getDeadList()) //for dead player, draw a card
        {
            int value = deck.drawCard();
            drawnCount+=value;
            //System.out.println(player.getCharacterName() + " drew a " + value + ". Total pile up to " + drawnCount);
            if(drawnCount > getTableSeating().size()) //if drawn pile count is greater than players alive, start dead or alive game mode
            {
                //System.out.println("Conditions met, dead or alive starting");
                DoA = true;
                zombieSetup();
            }
        }
    }
  
    /**
     * Method to set up game and create graveyard deck, arrow pile, and add players to list totalPlayers.
     */
    public void gameSetup()
    {
        //create a graveyard deck if playing with expansion and create chief arrow and create duel system
        if(expansion)
        {
            deck.createDeck();
            arrow = new ChiefArrow();
            tokens.createTokens();
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
    
    /**
     * Method to create player, set role, set health points, and set character name
     * @param index Integer, the index of the current player
     * @return newPlayer Player, the player that was created
     */
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
    
    /**
     * Method to setup zombies if dead or alive requirements are met, sets zombie master if applicable, 
     * and sets all alive players roles to be alive and the dead players roles to dead.
     */
    public void zombieSetup()
    {
        rolesDoA[0] = getDeadList().size();
        rolesDoA[1] = getTableSeating().size();
        //System.out.println("Alive: " + rolesDoA[0] + "\t Dead: " + rolesDoA[1]);
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
        
    /**
     * Test method to print the game setup to make sure players were being being 
     * created properly.
     */
    public void printGameSetup() //used as test method
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
    
    /**
     * Test method to print number of players left in game and print players updated 
     * role, health, and arrows.
     */
    public void printGameStatus() //used a test method
    {
        System.out.println("----------------------------\nThere are " + getTableSeating().size() + " players left");
        for(Player player : getTableSeating())
        {
            System.out.println(player.getCharacterName() + " (" + player.getRole() + ") : HP - " + player.getHealth() + ",  Arrows: " + player.getArrowCount());
        }
        
        System.out.println();
    }
}
