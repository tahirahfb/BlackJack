import java.text.DecimalFormat;
import java.util.*;

public class BlackJack {

    public static void main(String[] args) {
        Scanner playerInput = new Scanner(System.in);

        System.out.println("Welcome to Blackjack!");
        System.out.println("---------------------");
        System.out.println("What is your name?");
        String playerName = playerInput.nextLine();
        DecimalFormat df = new DecimalFormat("0.00");


        // Create the playing deck
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffleDeck();
        System.out.println(playingDeck);
        System.out.println("");

        // Create hands for the player and the dealer - hands are created from methods that are made in the deck class
        Deck playerHand = new Deck();
        Deck dealerHand = new Deck();

        double playerMoney = 100.00;
        
        // Game loops 
        while(playerMoney > 0){
            System.out.println(playerName + ", you have $" + df.format(playerMoney) + " how much would you like to bet? Please bet in increments of 5!");
            double playerBet = playerInput.nextDouble();
            if(!(playerBet % 5 == 0)){
                System.out.println("Bet not a multiple of 5, bet again!");
                System.out.println("");
                continue;
            }

            if(playerBet > playerMoney){
                System.out.println("You've bet more than you currently have, bet again!");
                System.out.println("");
                continue;
            }

            boolean endRound = false;

            playerHand.draw(playingDeck);
            playerHand.draw(playingDeck);

            dealerHand.draw(playingDeck);
            dealerHand.draw(playingDeck);

            while(true){
                System.out.println("Your hand: ");
                System.out.println(playerHand.toString());
                System.out.println("");
                System.out.println("Your hand is valued at: " + playerHand.cardsValue());
                System.out.println("");
                System.out.println("Dealer Hand: " + dealerHand.getCard(0).toString() + " and [Hidden]");
                System.out.println("");
                System.out.println(playerName + ", would you like Hit or Stand? Enter 1 for Hit, 2 for Stand.");
                int hitOrStand = playerInput.nextInt();

                if(hitOrStand == 1){
                    playerHand.draw(playingDeck);
                    System.out.println("You drew: " + playerHand.getCard(playerHand.deckSize() - 1));
                    System.out.println("");
                    if(playerHand.cardsValue() > 21){
                        System.out.println("BUST! Your hand total is: " +  playerHand.cardsValue());
                        playerMoney -= playerBet;
                        endRound = true;
                        break;
                    }
                }

                if(hitOrStand == 2){
                    break;
                }

            }

            System.out.println("");
            System.out.println("Dealer Hand: " + dealerHand.toString());
            if((dealerHand.cardsValue() > playerHand.cardsValue()) && endRound == false){
                System.out.println("");
                System.out.println("Dealer Wins!");
                playerMoney -= playerBet;
                endRound = true;
            }

            while((dealerHand.cardsValue() < 17) && endRound == false){
                dealerHand.draw(playingDeck);
                System.out.println("Dealer Drew: " + dealerHand.getCard(dealerHand.deckSize() -1).toString());
            }
            System.out.println("");
            System.out.println("Dealer's Hand Total: " + dealerHand.cardsValue());
            if((dealerHand.cardsValue() > 21) && endRound == false){
                System.out.println("");
                System.out.println("Dealer Busts! YOU WIN!");
                playerMoney += (playerBet * 2);
                endRound = true;
            }

            if((playerHand.cardsValue() == dealerHand.cardsValue()) && endRound == false){
                System.out.println("");
                System.out.println("Push!");
                endRound = true;
            }

            if((playerHand.cardsValue() > dealerHand.cardsValue() && endRound == false)){
                System.out.println("");
                System.out.println("You WIN!");
                playerMoney += (playerBet * 1.5);
                endRound = true;
                ;
            } else if(endRound == false){
                System.out.println("You Lose!");
                playerMoney -= playerBet;
                endRound = true;
            }

            playerHand.moveAllToDeck(playingDeck);
            dealerHand.moveAllToDeck(playingDeck);
            System.out.println("");
            System.out.println("End of hand!");
            System.out.println("------------");

            
        }

    
        
        System.out.println("Sorry, you're all out of money!" );

        playerInput.close();  
    }
    
}
