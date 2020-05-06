package cs2365_project3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Turn class is responsible for any running the logic for the turn of
 * the game.
 * Responsibilities include: rolling dice, resolving dice, checking if player
 * died, checking if game over.
 * @author Akhil Kapadia
 */
public class Turn {

    Scanner scan = new Scanner(System.in);
    
    String output = "";

    //Arraylist of all players in the game for this turn.
    private ArrayList<Player> player = new ArrayList();
    //Index of the player's whose turn it is in the arraylist.
    private int currentPlayer;
    //The total amount of arrows left in the stack
    private int arrowStack;
    //Name of current Player from player class.
    private String name;
    
    boolean playerAlive = true;

    //flag to keep track of the game being over
    private boolean gameOver = false;
    //value to check what condition the game is over on
    private int winCond;
    //used to keep track of how many of each role is left alive for checking game over
    private int[] roles = {0,0,0,0}; 
    private int[] rolesDoA = {0, 0};
    Token tokens;
    
    private GameGUI GUI;

    //flag to keep track of if the expansion is being used
    private boolean expansion;
    //flag to keep track of if the dead or alive gamemode has started
    private boolean DoA;

    //arraylist of dead players
    private ArrayList<Player> deadList = new ArrayList(0);
    
    //handler for making ai based decisions
    AIDecisionMaking ai;
    //handler for making dice based actions
    DiceController diceHandler;

    ChiefArrow arrow;
    
    /**
     * 
     * @param tableSeating
     * @param deadList
     * @param arrowPile
     * @param current
     * @param roles
     * @param rolesDoA
     * @param arrow
     * @param tokens
     * @param expansion
     * @param DoA 
     */
    public Turn(ArrayList<Player> tableSeating, ArrayList<Player> deadList, int arrowPile, int current, int[] roles, 
            int[] rolesDoA, ChiefArrow arrow, Token tokens, boolean expansion, boolean DoA)
    {
        this.currentPlayer = current;
        this.player = tableSeating;
        this.deadList = deadList;
        this.arrowStack = arrowPile;
        this.name = player.get(current).getCharacterName();

        this.expansion = expansion;
        this.DoA = DoA;

        this.ai = new AIDecisionMaking();
        int[] test = {5,0,0,0};
        this.diceHandler = new DiceController(test);
        this.roles = roles;
        this.rolesDoA = rolesDoA;
        this.arrow = arrow;
        this.tokens = tokens;
    }
    
    /**
     * Method sets the game gui
     * @param gui 
     */
    public void setGUI(GameGUI gui)
    {
        this.GUI = gui;
    }
    
    /**
     * Method gets game gui 
     * @return 
     */
    public GameGUI getGUI()
    {
        return this.GUI;
    }
    
    public int getIndex()
    {
        return this.currentPlayer;
    }
    
    /**
     * Method gets output
     * @return string output
     */
    public String getOutput()
    {
        //System.out.println("working");
        //System.out.println(output);
        return this.output;
    }

    /**
     * Method returns the amount of arrows left over after the turn ends.
     * @return Integer, Arrows left over after turn ends.
     */
    public int getArrowStack()
    {
        return arrowStack;
    }
    
    /**
    * Method that gets if playing with the expansions.
    * @return Boolean, true if playing with expansions
    */
    public boolean getExpansion()
    {
        return this.expansion;
    }
    
    /**
     * Method returns if player is alive 
     * @return boolean true if player is alive
     */
    public boolean getPlayerAlive()
    {
        return this.playerAlive;
    }
    
    /**
     * Method to return tokens
     * @return tokens
     */
    public Token getTokens()
    {
        return this.tokens;
    }
    
    /**
     * Method to get player name 
     * @return string name
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
    * Method that gets if dead or alive game mode has started.
    * @return Boolean, true if game mode started
    */
    public boolean getDoA()
    {
        return this.DoA;
    }

    /**
    * Method that gets the array of number of each role left in game.
    * @return Integer[], number of each role left in the game
    */
    public int[] getRoles()
    {
        return this.roles;
    }
    
    /**
    * Method that gets the array of number of each role left in game.
    * @return Integer[], number of each role left in the game
    */
    public int[] getRolesDoA()
    {
        return this.rolesDoA;
    }    

    /**
    * Method that decreases the number of given role left in the game.
    * @param index Integer, the role to decrease in the array.
    */
    public void decreaseRole(int index)
    {
        this.roles[index]--;
    }

    /**
    * Method that gets the arraylist of players alive in the game.
    * @return ArrayList, players left alive in the game
    */
    public ArrayList<Player> getTableSeating()
    {
        return new ArrayList<>(player);
    }

    /**
    * Method that gets the arraylist of players dead in the game.
    * @return ArrayList, players left dead in the game
    */
    public ArrayList<Player> getDeadList()
    {
        return new ArrayList<>(deadList);
    }

    public ChiefArrow getChiefArrow()
    {
        return arrow;
    }

    /**
    * Method that sets the game over flag based on the win condition.
    */
    public void setGameOver()
    {
        if(winCondition() != 0)
            this.gameOver = true; //game is over
        else
            this.gameOver = false; //game is not over
    }
    
    /**
    * Method that gets the game over flag.
    * @return gameOver Boolean, true if game is over
    */
    public boolean getGameOver()
    {
        return this.gameOver;
    }

    public void setWinCond()
    {
        this.winCond = winCondition();
    }

    public int getWinCond()
    {
        return this.winCond;
    }
        
    /**
     * Method adds an arrow to the current player if there's enough in the stack.
     * Also updates the stack count, triggers indian attack if no arrows left in stack.
     */
    public void indianArrow()
    {
            if(arrowStack >= 1) //if there is an arrow left to take
            {
                    player.get(currentPlayer).setArrowCount(1); //add an arrow to the player
                    arrowStack--; //decrease arrow stack by one
                    //System.out.println(player.get(currentPlayer).getCharacterName() + " took an arrow, they have " + 
                            //player.get(currentPlayer).getArrowCount() + ". Arrow pile at " + getArrowStack());
                    output = output + player.get(currentPlayer).getCharacterName() + " took an arrow, they have " + 
                            player.get(currentPlayer).getArrowCount() + ". Arrow pile at " + getArrowStack() + "\n";
                    //System.out.println();
                    output = output + "\n";
                    if(arrowStack == 0) //if that taken arrow was last one in pile, start indian attack
                        indianAttack();
            }

    }

    public void indianAttack()
    {
        //System.out.println("Indians Attack!");
        output = output + "Indians Attack!\n";
        if(expansion)
        {
            int maxArrows = 0;
            boolean tie = false;
            Player mostArrows = player.get(0);
            for(Player p: player) //determine which player has the most arrows (no ties)
            {
                if(p.getArrowCount() > maxArrows)
                {
                    mostArrows = p;
                    maxArrows = p.getArrowCount();
                    tie = false;
                }
                else if(p.getArrowCount() == maxArrows)
                    tie = true;
            }

            for(Player p : player)
            {
                if(p.getCharacterName().equals(arrow.UseArrow()) && p.equals(mostArrows) &&  tie == false) //if player has chief arrow and most arrows, take no damage
                    output = output + p.getCharacterName() + " is the Indian Chief. They take no damage.\n";
                    //System.out.println(p.getCharacterName() + " is the Indian Chief. They take no damage.");
                else
                {
                    p.TakeDamage(p.getArrowCount()); //player takes damaged based on number of arrows they have
                    //System.out.println(p.getCharacterName() + " took " + p.getArrowCount() + " damage. Current HP: " + p.getHealth());
                    output = output + p.getCharacterName() + " took " + p.getArrowCount() + " damage. Current HP: " + p.getHealth() + "\n";
                    if(p.getCharacterName().equals(arrow.UseArrow())) //if chief arrow and not most arrows, counts as two arrows
                    {
                        p.TakeDamage(2);
                        output = output + p.getCharacterName() + " had the Chief Arrow, but not most arrows, they take 2 extra damage. Current HP:" + p.getHealth() + "\n";
                        //System.out.println(p.getCharacterName() + " had the Chief Arrow, but not most arrows, they take 2 extra damage. Current HP:" + p.getHealth());
                    }
                }
                p.setArrowCount(-p.getArrowCount()); //reset players arrow count to zero
            }
            arrow.TakeArrow("");
            int size = player.size();  //check for player deaths
            for(int i = 0; i < size; i++)
            {
                if(player.get(i).getHealth() <= 0)
                {
                    checkPlayerDeath(player.get(i));
                    size = player.size();
                    i = 0;
                }
            }
        }
        else
        {
            for(Player p : player)
            {
                p.TakeDamage(p.getArrowCount()); //player takes damaged based on number of arrows they have
                output = output + p.getCharacterName() + " took " + p.getArrowCount() + " damage. Current HP: " + p.getHealth() + "\n";
                //System.out.println(p.getCharacterName() + " took " + p.getArrowCount() + " damage. Current HP: " + p.getHealth());
                p.setArrowCount(-p.getArrowCount()); //reset players arrow count to zero
            }
            int size = player.size();  //check for player deaths
            for(int i = 0; i < size; i++)
            {
                if(player.get(i).getHealth() <= 0)
                {
                    checkPlayerDeath(player.get(i));
                    size = player.size();
                    i = 0;
                }
            }
        }

        arrowStack = 9; //reset arrow pile to 9
        //System.out.println();
        output = output + "\n";
    }
    
    public void brokenArrow()
    {
        int target = -1;
        if(player.get(currentPlayer).getUser())
        {
            //target = user.chooseBrokenArrow(getGUI());
            if(target > -1)
            {
                player.get(target).setArrowCount(-1);
                //System.out.println("Removing one arrow from " + player.get(target).getCharacterName() 
                //+ " they are at " + player.get(target).getArrowCount() + " arrows left.");
                output = output + "Removing one arrow from " + player.get(target).getCharacterName() 
                + " they are at " + player.get(target).getArrowCount() + " arrows left.\n";
            }
            else
                output = output + "Nobody has arrows, no arrows could be removed \n";
                //System.out.println("Nobody has arrows, no arrows could be removed");
        }
        else
        {
            Player targetPlayer = ai.getHighestFavor(player.get(currentPlayer), "Broken Arrow", player);
            if(targetPlayer != null)
            {
                targetPlayer.setArrowCount(-1);
                //System.out.println("Removing one arrow from " + player.get(target).getCharacterName() 
                //+ " they are at " + player.get(target).getArrowCount() + " arrows left.");
                output = output + "Removing one arrow from " + targetPlayer.getCharacterName() 
                + " they are at " + targetPlayer.getArrowCount() + " arrows left.\n";
            }
        }
            
        //System.out.println();
        output = output + "\n";
            
    }
    
    /**
     * Method to deal on damage to the player who rolls the bullet the displays players new health
     */
    public void bullet()
    {
        player.get(currentPlayer).TakeDamage(1);
        output = output + player.get(currentPlayer).getCharacterName() + " takes 1 damage. Current HP: " + player.get(currentPlayer).getHealth() + "\n";
        //System.out.println(player.get(currentPlayer).getCharacterName() + " takes 1 damage. Current HP: " + player.get(currentPlayer).getHealth());
        checkPlayerDeath(player.get(currentPlayer));
        //System.out.println();
        output = output + "\n";
    }
    
    /**
     * Method to add 1 health to player who rolls whiskey the diplays players new health 
     */
    public void whiskey()
    {
        player.get(currentPlayer).addHealth(1);
        output = output + player.get(currentPlayer).getCharacterName() + " heals 1 HP. Current HP: " + player.get(currentPlayer).getHealth() + "\n";
        //System.out.println(player.get(currentPlayer).getCharacterName() + " heals 1 HP. Current HP: " + player.get(currentPlayer).getHealth());
        //do some stuff with duel tokens here
        //System.out.println();
        output = output + "\n";
    }

    /**
     * Method enacts the Bull's Eye x1 dice, and handled the special cases when
     * character's abilities interfere with the dice function.
     * If no special cases, the method determining whether the player to the left
     * or the right has worse favor, and shoots them dealing 1HP damage.
     * 
     * @param jack boolean Used to tell if his ability has been used.
     */
    public void bullsEyex1(int target)
    {
            //Sets the left and right index of currentPlayer. If current is 0, then
            //left is that last person.
            Player targetPlayer;
            int right = (currentPlayer!=player.size()-1) ? currentPlayer+1 : 0 ;
            int left = (currentPlayer!=0) ? currentPlayer - 1 :  player.size()-1;
            int rightx2 = (right != player.size()-1) ? right+1 : 0;
            int leftx2 = (left !=0 ) ? left - 1 :  player.size() -1;

            if(player.get(currentPlayer).getUser())
            {
                    //if ( name.equals("CALAMITY JANET"))
                    //{
                            //target = user.chooseShoot(player.get(left),player.get(right),
                                                                              //player.get(leftx2),player.get(rightx2), getGUI());
                    //}else
                            //target = user.chooseShoot(player.get(left),player.get(right), getGUI());

                player.get(target).TakeDamage(1);
                //System.out.println(player.get(currentPlayer).getCharacterName() + " shot " + player.get(target).getCharacterName() 
                //+ ". Current HP: " + player.get(target).getHealth());
                output = output + player.get(currentPlayer).getCharacterName() + " shot " + player.get(target).getCharacterName() 
                + ". Current HP: " + player.get(target).getHealth() + "\n";
                checkPlayerDeath(player.get(target)); //check since player took damage
            }
            else
            {
                targetPlayer = ai.getHighestFavor(player.get(currentPlayer), "Shoot person two over left or right", player);
                targetPlayer.TakeDamage(1);
                //System.out.println(player.get(currentPlayer).getCharacterName() + " shot " + targetPlayer.getCharacterName() 
                //+ ". Current HP: " + targetPlayer.getHealth());
                output = output + player.get(currentPlayer).getCharacterName() + " shot " + targetPlayer.getCharacterName() 
                + ". Current HP: " + targetPlayer.getHealth() + "\n";
                checkPlayerDeath(targetPlayer); //check since player took damage

            }
            //System.out.println();
            output = output + "\n";

    }

    /**
     * Method enacts the Bull's Eye x2 dice, and handled the special cases when
     * character's abilities interfere with the dice function.
     * If no special cases, the method determining whether the player to the left
     * or the right has worse favor, and shoots them dealing 2HP damage.
     * 
     * @param jack boolean Used to tell if his ability has been used.
     */
    public void bullsEyex2(int target)
    {
            //Sets the left and right index of currentPlayer. If current is 0, then
            //left is that last person.
            Player targetPlayer;
            int right = (currentPlayer!=player.size()-1) ? currentPlayer+1 : 0 ;
            int left = (currentPlayer!=0) ? currentPlayer - 1 :  player.size()-1;
            int rightx2 = (right != player.size()-1) ? right+1 : 0;
            int leftx2 = (left !=0 ) ? left - 1 :  player.size() -1;

            if(player.get(currentPlayer).getUser())
            {
                    //if ( name.equals("CALAMITY JANET"))
                    //{
                            //target = user.chooseShoot(player.get(left),player.get(right),
                                                                              //player.get(leftx2),player.get(rightx2), getGUI());
                    //}
                    //else
                            //target = user.chooseShoot(player.get(left),player.get(right), getGUI());

                player.get(target).TakeDamage(1);
                //System.out.println(player.get(currentPlayer).getCharacterName() + " shot " + player.get(target).getCharacterName() 
                //+ ". Current HP: " + player.get(target).getHealth());
                output = output + player.get(currentPlayer).getCharacterName() + " shot " + player.get(target).getCharacterName() 
                + ". Current HP: " + player.get(target).getHealth() + "\n";
                checkPlayerDeath(player.get(target)); //check since player took damage
            }
            else
            {
                targetPlayer = ai.getHighestFavor(player.get(currentPlayer), "Shoot person two over left or right", player);
                //System.out.println(player.get(currentPlayer).getCharacterName() + " shot " + targetPlayer.getCharacterName() 
                //+ ". Current HP: " + targetPlayer.getHealth());
                output = output + player.get(currentPlayer).getCharacterName() + " shot " + targetPlayer.getCharacterName() 
                + ". Current HP: " + targetPlayer.getHealth() + "\n";
                targetPlayer.TakeDamage(1);
                checkPlayerDeath(targetPlayer); //check since player took damage
            }


            //System.out.println();
            output = output + "\n";

    }

    /**
     * Method for the beer dice. If current player is at max health, then HP
     * is added to the person with the highest favor. If not at max health 
     * then add to his own. Also handles Jesse Jones ability.
     */
    public void beer(int targetIndex)
    {
        Player targetPlayer = player.get(currentPlayer);
        if(player.get(currentPlayer).getUser())
        {
            for(Player p : player)
            {
                if(p.getPlayerIndex() == targetIndex)
                    targetPlayer = p;
            }
        }
        else
            targetPlayer = ai.getHighestFavor(player.get(currentPlayer), "Beer", player);

        //handle jesse jones (if less than or equal to 4 hp and healing self, double healing)
        if (name.equals("JESSE JONES") && targetPlayer.equals(player.get(currentPlayer)) && player.get(currentPlayer).getHealth() <= 4)
            targetPlayer.addHealth(2);
        else if(!(targetPlayer.getRole().equals("Zombie"))) //zombies cannot be healed
            targetPlayer.addHealth(1);
        if(!(targetPlayer.getRole().equals("Zombie"))) //only print out the message if someone was actually healed
        {
           // System.out.println(player.get(currentPlayer).getCharacterName() + " healed " + targetPlayer.getCharacterName() 
                    //+ ". Current HP: " + targetPlayer.getHealth());
            output = output + player.get(currentPlayer).getCharacterName() + " healed " + targetPlayer.getCharacterName() 
                    + ". Current HP: " + targetPlayer.getHealth() + "\n";
            //System.out.println();
            output = output + "\n";
        }

    }
    
    /**
     * Method for gatling gun to shoot players
     * Paul Regrets special ability is also in here so they can not take any damage
     * After shooting method checks for any deaths then displays whos taken damage
     * and players new health
     */
    public void gatlingGun()
    {
        output = output + "Gatling Gun! \n";
        //System.out.println("Gatling Gun!");
            for(Player obj : player) //each player takes one damage unless paul regret
            {
                    String person = obj.getCharacterName();
                    if(!person.equals(name) || !person.equals("PAUL REGRET"))
                    {
                        obj.TakeDamage(1);
                        //System.out.println(person + " takes 1 damage. Current HP: " + obj.getHealth());   
                        output = output + person + " takes 1 damage. Current HP: " + obj.getHealth() + "\n";
                    }
            }
            int size = player.size();  //check for player deaths
            for(int i = 0; i < size; i++)
            {
                if(player.get(i).getHealth() <= 0)
                {
                    checkPlayerDeath(player.get(i));
                    size = player.size();
                    i = 0;
                }
            }
            //remove arrows from current player and add them to the stack.
            arrowStack += player.get(currentPlayer).getArrowCount();
            player.get(currentPlayer).setArrowCount(-player.get(currentPlayer).getArrowCount());

            //System.out.println();
            output = output + "\n";
    }
    
    public void duel()
    {
        int targetIndex;
        Player targetPlayer = player.get(currentPlayer);
        if(player.get(currentPlayer).getUser())
        {
            //targetIndex = user.chooseDuel(getGUI());
            for(Player p : player)
            {
                //if(p.getPlayerIndex() == targetIndex)
                    targetPlayer = p;
            }
        }
        else
            targetPlayer = ai.getDuel(player.get(currentPlayer), player);
        
        for(;;)
        {
            Dice dice = new Dice(0, true, 4);
            dice.rollDice();
            String value = dice.getDiceString();
            output = output + player.get(currentPlayer).getCharacterName() + " rolled " + value + "\n";
            //System.out.println(player.get(currentPlayer).getCharacterName() + " rolled " + value);
            if(!value.equals("Duel Guns"))
            {
                int token = tokens.getToken();
                targetPlayer.addTokenList(token);
                targetPlayer.TakeDamage(1);
                switch(token)
                {
                    case 0:
                        output = output + "They take a beer token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth() + "\n";
                        //System.out.println("They take a beer token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth());
                        break;
                    case 1:
                        output = output + "They take a shoot one over token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth() + "\n";
                        //System.out.println("They take a shoot one over token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth());
                        break;
                    case 2:
                        output = output + "They take a shoot two over token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth() + "\n";
                        //System.out.println("They take a shoot two over token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth());
                        break;
                    case 3:
                        output = output + "They take a dynamite token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth() + "\n";
                        //System.out.println("They take a dynamite token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth());
                        break;
                }
                break;
            }
            else
            {
                dice.rollDice();
                value = dice.getDiceString();
                output = output + player.get(currentPlayer).getCharacterName() + " rolled " + value + "\n";
                //System.out.println(player.get(currentPlayer).getCharacterName() + " rolled " + value);
                if(!value.equals("Duel Guns"))
                {
                    int token = tokens.getToken();
                    player.get(currentPlayer).addTokenList(token);
                    player.get(currentPlayer).TakeDamage(1);
                    switch(token)
                    {
                        case 0:
                            output = output + "They take a beer token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth() + "\n";
                            //System.out.println("They take a beer token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth());
                            break;
                        case 1:
                            output = output + "They take a shoot one over token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth() + "\n";
                            //System.out.println("They take a shoot one over token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth());
                            break;
                        case 2:
                            output = output + "They take a shoot two over token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth() + "\n";
                            //System.out.println("They take a shoot two over token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth());
                            break;
                        case 3:
                            output = output + "They take a dynamite token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth() + "\n";
                            //System.out.println("They take a dynamite token and 1 damage. Current HP: " + player.get(currentPlayer).getHealth());
                            break;
                    }
                    break;
                }
            }
        }
        
    }

    /**
     * Method determining roll of dice for this turn. Sets an arrayList of dice 
     * indexes to what the final roll should look like for this turn.
     */
    public void AIDiceRoll()
    {
            int count = 1;
            //Check if player is the User or an AI
            int[] test = {5, 0, 0, 0};
            int choice = ai.ExpansionDice(player.get(currentPlayer));
            if(getExpansion())
            {
                if(choice == 1)
                {
                    test[0] = 3;
                    test[1] = 2;
                }
                else if(choice == 2)
                {
                    test[0] = 2;
                    test[1] = 2;
                    test[2] = 1;
                }
                else if(choice == 3)
                {
                    test[0] = 2;
                    test[1] = 2;
                    test[3] = 1;
                }

                //diceHandler = new DiceController(test);
            }
            diceHandler.rollAllDice();
            output = output + "Roll " + count + ": \n";
            //System.out.println("Roll " + count + ": ");
            output = output + diceHandler.printAllDice();
            count++;
            //need to handle arrows as they come up
            if(getExpansion())
            {
                for(Dice dice : diceHandler.getDiceArray())
                {
                    if(name.equals("APACHE KID") && arrow.UseArrow().equals(""))
                        arrow.TakeArrow(name);
                    else if(!name.equals("BILL NOFACE"))
                    {                                
                        if(arrow.UseArrow().equals("") && dice.getDiceString().equals("Arrow"))
                            arrow.TakeArrow(name);
                        else if(dice.getDiceString().equals("Arrow"))
                            indianArrow();
                    }
                }

                for(Dice dice : diceHandler.getDiceArray())
                {
                    if(dice.getDiceString().equals("Broken Arrow"))
                        brokenArrow();
                }

                for(Dice dice : diceHandler.getDiceArray())
                {
                    if(dice.getDiceString().equals("Bullet"))
                        bullet();
                }
            }
            else
            {
                for(Dice dice : diceHandler.getDiceArray())
                {
                    if(dice.getDiceString().equals("Arrow") && !name.equals("BILL NOFACE"))
                        indianArrow();                             
                }
            }

                while(playerAlive && player.get(currentPlayer).CanReroll())
                {
                    diceHandler.setDiceArray(ai.RerollHandler(player.get(currentPlayer), diceHandler.getDiceArray(), player));
                    diceHandler.rollAllDice();
                    output = output + "Roll " + count + ": \n";
                    //System.out.println("Roll " + count + ": ");
                    output = output + diceHandler.printAllDice();
                    count++;
                    player.get(currentPlayer).usedReroll(); 
                    //need to handle arrows as they come up
                    if(getExpansion())
                    {
                        for(Dice dice : diceHandler.getDiceArray())
                        {
                            if(name.equals("APACHE KID") && arrow.UseArrow().equals(""))
                                arrow.TakeArrow(name);
                            else if(!name.equals("BILL NOFACE"))
                            {                                
                                if(arrow.UseArrow().equals("") && dice.getDiceString().equals("Arrow"))
                                    arrow.TakeArrow(name);
                                else if(dice.getDiceString().equals("Arrow"))
                                    indianArrow();
                            }
                        }
                        
                        for(Dice dice : diceHandler.getDiceArray())
                        {
                            if(dice.getDiceString().equals("Broken Arrow"))
                                brokenArrow();
                        }
                        
                        for(Dice dice : diceHandler.getDiceArray())
                        {
                            if(dice.getDiceString().equals("Bullet"))
                                bullet();
                        }
                    }
                    else
                    {
                        for(Dice dice : diceHandler.getDiceArray())
                        {
                            if(dice.getDiceString().equals("Arrow") && !name.equals("BILL NOFACE"))
                                indianArrow();                             
                        }
                    }
                    //if three dynamite, cant reroll anymore
                    if(diceHandler.checkFrequency("Dynamite") >= 3)
                        player.get(currentPlayer).setRerolls(0);
                }
            
                       
    }

    /**
     * Handles the actual play of the turn.
     */
    public void resolveDice()
    {
            //setDiceRoll();
            
            if(playerAlive)
            {

                player.get(currentPlayer).setRerolls(2); //reset rerolls to 2
                diceHandler.setDiceArray(diceHandler.sortDiceArray()); //sort the dice array in order of resolve

                //resolve the dice in correct order, arrows are already handled as they are rolled
                if(diceHandler.checkFrequency("Dynamite") >= 3)
                {
                    player.get(currentPlayer).TakeDamage(1);
                    //System.out.println("Dynamite! Take 1 damage, current HP: " + player.get(currentPlayer).getHealth());
                    output = output + "Dynamite! Take 1 damage, current HP: " + player.get(currentPlayer).getHealth() + "\n";
                    checkPlayerDeath(player.get(currentPlayer)); //check since player took damage
                    if(gameOver)
                    {
                        winCond = winCondition();
                        return; //breakout since game is over
                    }
                }

                if(player.get(currentPlayer).getCharacterName().equals("SUZY LAFAYETTE")) //add suzy ability, if no shoot dice at end of turn, plus 2 hp
                {
                    //System.out.println("No shoot dice rolled, plus 2 HP");
                    output = output + "No shoot dice rolled, plus 2 HP";
                    if(diceHandler.checkFrequency("Shoot person one over left or right") == 0 
                            && diceHandler.checkFrequency("Shoot person two over left or right") == 0)
                        player.get(currentPlayer).addHealth(2);
                }

                for(Dice dice : diceHandler.getDiceArray())
                {
                    String diceString = dice.getDiceString();
                    if(diceString.equals("Whiskey Bottle"))
                    {
                        whiskey();
                        if(name.equals("GREG DIGGER"))
                            whiskey();
                    }
                    else if(diceString.equals("Shoot person one over left or right"))
                    {
                        bullsEyex1(0);
                        if(gameOver)
                        {
                            winCond = winCondition();
                            return; //breakout since game is over
                        }
                    }
                    else if(diceString.equals("Shoot person one over left or right twice"))
                    {
                        bullsEyex1(0);
                        if(gameOver)
                        {
                            winCond = winCondition();
                            return; //breakout since game is over
                        }
                        if(gameOver)
                        {
                            winCond = winCondition();
                            return; //breakout since game is over
                        }
                    }
                    else if(diceString.equals("Shoot person two over left or right"))
                    {
                        bullsEyex2(0);
                        if(gameOver)
                        {
                            winCond = winCondition();
                            return; //breakout since game is over
                        }
                    }
                    else if(diceString.equals("Shoot person two over left or right twice"))
                    {
                        bullsEyex2(0);
                        if(gameOver)
                        {
                            winCond = winCondition();
                            return; //breakout since game is over
                        }
                        bullsEyex2(0);
                        if(gameOver)
                        {
                            winCond = winCondition();
                            return; //breakout since game is over
                        }
                    }
                    else if(diceString.equals("Beer"))
                        beer(0);
                    else if(diceString.equals("Double Beer"))
                    {
                        beer(0);
                        beer(0);
                    }


                }

                int gatlingTotal = diceHandler.checkFrequency("Gatling") + (2*diceHandler.checkFrequency("Double Gatling"));
                if(gatlingTotal >= 3)
                {
                    gatlingGun();
                    if(gameOver)
                    {
                        winCond = winCondition();
                        return; //breakout since game is over
                    }
                }
                
                for(Dice dice : diceHandler.getDiceArray())
                {
                    if(dice.getDiceString().equals("Duel Guns"))
                        duel();
                }
            }
            
            //tokens.addToken(player.get(currentPlayer).getTokenList());
            //player.get(currentPlayer).wipeTokenList();
    }
    /**
     * Method to check game requirements to see if there is a winner(s)
     * @return integers related to who won, 0 game is not over
     */
    public int winCondition()
    {
        if(getDoA())
        {
            int[] roleArray = getRolesDoA();
            if(roleArray[1] == 0 || (roleArray[0] == 0 && roleArray[1] == 0))
                return 4; //alive are dead, zombie win
            else if(roleArray[0] == 0)
                return 5; //zombie are dead and alive are alive, alive win
            else
                return 0; //game is not over
        }
        else
        {
            int[] roleArray = getRoles();
            if(roleArray[0] == 0 && roleArray[1] == 1 && roleArray[2] == 0 && roleArray[3] == 0)
                return 1; //everyone else died, single renegade wins
            else if(roleArray[1] == 0 && roleArray[2] == 0)
                return 2; //outlaws and renegade(s) died, sheriff wins
            else if(roleArray[0] == 0)
                return 3; //sheriff died, outlaws win
            else
                return 0; //game is not over
        }
    }
    /**
     * Method to check if player is dead and if so displays whos died and their roe.
     * Vulture Sams special ability is also in here
     * @param damagedPlayer
     * @return 
     */
    public boolean checkPlayerDeath(Player damagedPlayer)
    {
        if(damagedPlayer.getHealth() <= 0)
        {
            String role = damagedPlayer.getRole();
            //decrement the current amount of the role of the dead player that exists in the game
            if(role.equals("Sheriff"))
                decreaseRole(0);
            else if(role.equals("Renegade"))
                decreaseRole(1);
            else if(role.equals("Outlaw"))
                decreaseRole(2);
            else
                decreaseRole(3);

            //System.out.println("Player " + damagedPlayer.getPlayerIndex() + " is dead, their role was " + role);
            output = output + "Player " + damagedPlayer.getPlayerIndex() + " is dead, their role was " + role + "\n";
            
            //if(damagedPlayer.equals(player.get(currentPlayer)))
                    //playerAlive = false;
            
            tokens.addToken(damagedPlayer.getTokenList());
            damagedPlayer.wipeTokenList();

            deadList.add(damagedPlayer);
            player.remove(damagedPlayer); //remove the player from the seating
            setGameOver();

            for(Player p: getTableSeating()) //vulture sam ability
            {
                if(p.getCharacterName().equals("VULTURE SAM"))
                {
                    //System.out.println("Someone died, VULTURE SAM plus 2 HP");
                    p.addHealth(2);
                }                    
            }

            return true;
        }
        else
            return false;
    }
}