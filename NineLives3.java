package NineLives;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NineLives3 {



// method main
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("**********Welcome to Nine Lives!**********");

    // whether to show instructions
            System.out.println("\nHave you played this game before? (Yes/No)");
            String SkipInstructions = input.next();

            boolean GoToInstructions = false;
            GoToInstructions = !(SkipInstructions.equalsIgnoreCase("y")
            || SkipInstructions.equalsIgnoreCase("yes"));
        
            if(GoToInstructions) {

                Instructions();
            }
    
    // whether to start game
        System.out.println("\nStart the game? (Yes/No)");
        String PlayInput = input.next();
        
    // game started
        while(!(PlayInput.equalsIgnoreCase("n")
        || PlayInput.equalsIgnoreCase("no"))) {

            boolean StartGame = false;
            StartGame = PlayInput.equalsIgnoreCase("y")
            || PlayInput.equalsIgnoreCase("yes");

            if(StartGame){
                
                TheGame(input);

                System.out.println("\nPlay again? (Yes/No)");
            }

            else {
                System.out.println("\nNot understood...");
                System.out.println("\nStart the game? (Yes/No)");
            }

            PlayInput = input.next();
        }

    // game not started

        System.out.println("\nSee you next time!");

        input.close();
    }



// method with game instructions
    static void Instructions() {

        System.out.println("\nWell, there will be a secret word for you to guess");
        Pause(2);
        System.out.println("The system will give you a hint on how many letters it contains");
        Pause(2);
        System.out.println("Every time you should enter one letter you think the word might contain");
        Pause(2);
        System.out.println("One letter at a time");
        Pause(1);
        System.out.println("NOT the whole word!");
        Pause(1);
        System.out.println("NOT THE WHOLE WORDDD!!!");
        Pause(2);
        System.out.println("\n@@@  @@@  @@@  @@@  @@@@@@@   @@@@@@@@  @@@@@@@    @@@@@@   @@@@@@@   @@@@@@    @@@@@@   @@@@@@@    @@@@@@    @@@@@@   @@@  ");
        System.out.println("@@@  @@@  @@@@ @@@  @@@@@@@@  @@@@@@@@  @@@@@@@@  @@@@@@@   @@@@@@@  @@@@@@@@  @@@@@@@@  @@@@@@@@  @@@@@@@@  @@@@@@@@  @@@  ");
        System.out.println("@@!  @@@  @@!@!@@@  @@!  @@@  @@!       @@!  @@@  !@@         @@!    @@!  @@@  @@!  @@@  @@!  @@@  @@!  @@@  @@!  @@@  @@!  ");
        System.out.println("!@!  @!@  !@!!@!@!  !@!  @!@  !@!       !@!  @!@  !@!         !@!    !@!  @!@  !@!  @!@  !@!  @!@      @!@       @!@   !@   ");
        System.out.println("@!@  !@!  @!@ !!@!  @!@  !@!  @!!!:!    @!@!!@!   !!@@!!      @!!    @!@  !@!  @!@  !@!  @!@  !@!     !!@       !!@    @!@  ");
        System.out.println("!@!  !!!  !@!  !!!  !@!  !!!  !!!!!:    !!@!@!     !!@!!!     !!!    !@!  !!!  !@!  !!!  !@!  !!!    !!@       !!@     !!!  ");
        System.out.println("!!:  !!!  !!:  !!!  !!:  !!!  !!:       !!: :!!        !:!    !!:    !!:  !!!  !!:  !!!  !!:  !!!                           ");
        System.out.println(":!:  !:!  :!:  !:!  :!:  !:!  :!:       :!:  !:!      !:!     :!:    :!:  !:!  :!:  !:!  :!:  !:!    !:!       !:!     :!:  ");
        System.out.println("::::: ::   ::   ::   :::: ::   :: ::::  ::   :::  :::: ::      ::    ::::: ::  ::::: ::   :::: ::     ::        ::      ::  ");
        System.out.println(" : :  :   ::    :   :: :  :   : :: ::    :   : :  :: : :       :      : :  :    : :  :   :: :  :     :::       :::     :::  ");
        Pause(3);
        System.out.println("\nGood.");
        Pause(1);
        System.out.println("You have 9 lives to start with");
        Pause(1);
        System.out.println("A life will be taken if you miss");
        Pause(1);
        System.out.println("You win the game if you guessed all the letters of the secret word");
        Pause(1);
        System.out.print("That's it, enough of explanations, let's try it out");
        Pause(1);
        System.out.print(".");
        Pause(1);
        System.out.print(".");
        Pause(1);
        System.out.println(".");
        Pause(1);
    }



// method with the game

    static void TheGame(Scanner input) {

        String SecretWord = "";
        SecretWord = RandomWord();

        int Lives = 9;
        char GuessLetter = 0;
        String GuessHistory = "";
        SecretWord = SecretWord.toUpperCase();

        System.out.println("\nWell, I thought of a word, can you guess what it is?");
        System.out.println("Lives: " + Lives);
        System.out.print("Hint:");

    // first hint
        String Hint = "";
        for(int i = 0; i < SecretWord.length(); ++i) {

            Hint = Hint + "_";
        }

        PrintWithSpaces(Hint);

        while(Lives > 0) {

            System.out.println();
            System.out.println("Guess a letter");

            GuessLetter = input.next().charAt(0);
            GuessLetter = Character.toUpperCase(GuessLetter);

            if(!(GuessHistory.contains(GuessLetter + ""))) {
                GuessHistory = GuessHistory + GuessLetter;
            }

            boolean IsLetterGuessed = SecretWord.contains(GuessLetter + "");
                
    // if guessed
            if(IsLetterGuessed) {

                System.out.println("Good, the word does contain " + GuessLetter);
                System.out.println("Lives: " + Lives);
                System.out.print("Hint:");

    // hint
                for(int i = 0; i < SecretWord.length(); ++i) {
                        
                    if(SecretWord.charAt(i) == GuessLetter) {

                        Hint = Hint.substring(0, i) + GuessLetter + Hint.substring(i + 1);
                    }
                }

                PrintWithSpaces(Hint);

                System.out.print("History:");
                PrintWithSpaces(GuessHistory);

    // if won
                if(SecretWord.equals(Hint)) {
                        
                    System.out.println("\nCongratulations, you won!");
                    return;
                }
            }

    // if not guessed
            else {

                System.out.println("Nope! The letter is not there");

                --Lives;
                System.out.println("Lives: " + Lives);

                System.out.print("Hint:");
                PrintWithSpaces(Hint);

                System.out.print("History:");
                PrintWithSpaces(GuessHistory);
            }
        }

    // no lives left
        System.out.println("\nGAME OVER");
        System.out.println("It was: " + SecretWord);
    }



// method that returns a random noun of 4 or more letters
    static String RandomWord() {

        String[] Array100Words = {
            "girl", "word", "mind", "side", "back", "room", "head", "hand", 
            "line", "road", "home", "road", "face", "book", "time", "kind", 
            "night", "city", "door", "work", "south", "town", "north", "east", 
            "west", "plan", "food", "light", "right", "state", "price", "area", 
            "water", "family", "mother", "father", "health", "school", "person", 
            "church", "death", "study", "stage", "queen", "music", "space", 
            "nature", "police", "change", "model", "logic", "topic", "record", 
            "right", "place", "moment", "reason", "system", "effort", "action", 
            "option", "partner", "brother", "garden", "result", "culture", "dinner", 
            "support", "service", "traffic", "budget", "safety", "concept", "image", 
            "impact", "channel", "function", "prison", "summer", "animal", "doctor", 
            "journey", "project", "nature", "energy", "teacher", "opinion", "journey", 
            "capital", "statement", "solution", "children", "support", "contract", 
            "officer", "material", "vehicle", "fashion", "manager", "research"
            };
        
        int Random0To99Number = (int)(Math.random() * Array100Words.length);
        String RandomWord = Array100Words[Random0To99Number];
        return RandomWord;
    }



// method used to print words with spaces between letters
    static void PrintWithSpaces(String OriginalString) {

        for(int i = 0; i < OriginalString.length(); ++i) {

            System.out.print(" ");
            System.out.print(OriginalString.charAt(i));
        }

        System.out.println();
    }



// method that pauses the system for a number of secends
    static void Pause(int seconds) {

        try {
            TimeUnit.SECONDS.sleep(seconds);
        }

        catch (InterruptedException e) {
        }
    }
}