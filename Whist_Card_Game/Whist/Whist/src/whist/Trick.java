package whist;
import cards.Card;
import cards.Card.Suit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * Skeleton class for storing information about whist tricks
 * @author ajb
 */
public class Trick{
   public static Suit trumps;
   private Card[] trick;
   private Card leadCard;
   private int leadPlayer;
   private Card topCard;
   
   public Trick(int p){
   leadPlayer = p;
   trick = new Card[BasicGame.NOS_PLAYERS];
   this.topCard = topCard;
   }    //p is the lead player 
   
   public static void setTrumps(Suit s){
       trumps=s;
   }
   
   public Suit getTrumps(){
       return trumps;
   }
   
   public Card getTopCard(){
       return topCard;
   }
    
/**
 * 
 * @return the Suit of the lead card.
 */    
    public Suit getLeadSuit(){
        return leadCard.getSuit(); 
    }
    
    public void setLeadCard(Card cards){
        leadCard = cards;
    }
/**
 * Records the Card c played by Player p for this trick
 * @param c
 * @param p 
 */
    public void setCard(Card c,Player p){

        if(p.getID() == leadPlayer){
            leadCard = c;
        }
       topCard = trick[findWinner()];
    }
/**
 * Returns the card played by player with id p for this trick
 * @param p
 * @return 
 */    
    public Card getCard(Player p){
        return trick[p.getID()];
    }
    
/**
 * Finds the ID of the winner of a completed trick
 */    
    public int findWinner(){
        ArrayList<Card> finalCards = new ArrayList();
        ArrayList<Card> trumpCard = new ArrayList();
        Boolean isTrump = false;
        Card winCard = null;
        
        for(Card c : trick){
            if(c != null){
                if(c.getSuit() == getLeadSuit()){
                    finalCards.add(c);
                }
                if(c.getSuit() == trumps){
                    finalCards.add(c);
                    isTrump = true;
                }
            }
        }
        
        if(isTrump){
            for(Card c : finalCards){
                if(c.getSuit() == trumps){
                    trumpCard.add(c);
                }
            }
            Collections.sort(trumpCard);
            winCard = trumpCard.get(0);
        }
        
        return Arrays.asList(trick).indexOf(winCard);
    }

    Card[] getCards() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
