/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import cards.Card.Rank;
import cards.Card.Suit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Nahim
 */
public class Hand implements Iterable<Card>{
    //cards in a hand
    private ArrayList<Card> hand;
    //keeps track of cards
    private int noofCards;
    
    private static final long serialVersionUID = 300L;
    
    // default constructor
    public Hand(){
        hand = new ArrayList();
        noofCards = hand.size();
        
    }
    //hand constructor
    public Hand(Card[] arrayofCards){
        //adds card array to hand
        hand.addAll(Arrays.asList(arrayofCards));
        noofCards = hand.size();
    }
    
    public int countSuit(Suit countSuit){
        int counter = 0;
        
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).getSuit().equals(countSuit)){
                counter++;
            }
            else{
                counter--;
            }
                
        }
        return counter;
    }
    
    public int countRank(Rank countRank){
        int count = 0;
        
        for(Card c : hand){
            if(c.getRank() == countRank){
                count++;
            }
        }
        return count;
    }
    
    public void addSingleCard(Card aCard){
        //add a single har to this hand
        hand.add(aCard);
        noofCards = hand.size();
    }
    
    public void addCardCollection(Card[] arrayOfCards){
        //add array of cards to hand
        hand.addAll(Arrays.asList(arrayOfCards));
        noofCards = hand.size();
    }
    
    public void addHand(Hand aHand){
        //add hand to this hand
        ArrayList<Card> temp = aHand.hand;
        hand.addAll(temp);
        noofCards = hand.size();
    }
    
    public boolean removeSingleCard(Card removeCard){
        //check hand if empty
        if(!hand.isEmpty()){
            for(int i = 0; i<hand.size(); i++){
                //remove if matches the card in the hand
                if(hand.get(i).equals(removeCard)){
                    hand.remove(i);
                }
            }
            return true;
        }else{
            //return false if it does not match
            return false;
        }
    }
    
    public boolean removeAllCards(Hand collectionOfCards){
        boolean remove = false;
        
        if(!hand.isEmpty() && collectionOfCards.hand.size() <= hand.size()){
            for(Card myCard : hand){
                //check if all passed cards are in this hand
                for(Card card1 : hand){
                    if(card1.equals(myCard)){
                        remove = true;
                    }else{
                        return false;
                    }
            }
            }
        }else{
            remove = false;
        }
        if(remove == true){
            for(int i = 0; i<hand.size(); i++){
                for(int j = 0; j < collectionOfCards.hand.size(); j++){
                    hand.remove(i);
                }
            }
        }
        return remove;
    }
    
    public Card remCardAtPostion(int card){
        Card aCard = null;
        
        //if in a valid position
        if(hand.size() >= card){
            //set card to aCard value
            aCard = hand.get(card);
            //remove it from the hand
            hand.remove(hand.get(card));
            //remove no of cards
            this.noofCards--;
            
        }
        return aCard;
        
    }

    @Override
    public Iterator<Card> iterator() {
        //return list 
        return hand.listIterator();
    }
    
    public void sortAscending(){
        Collections.sort(hand);
    }
    
    public void sortRank(){
        Collections.sort(hand, Card.CompareRank());
    }
    
    public ArrayList<Card> getHand(){
        return this.hand;
    }
   
    
    public boolean hasSuit(Suit suitInHand){
        for(Card c : hand){
            if(c.getSuit() == suitInHand){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        //toString method
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< hand.size(); i++){
            sb.append(hand.get(i)).append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Method to save serialization object
     * @param fileName
     * @throws IOException
     * @throws ClassNotFoundException 
     */
      public void saveFile(String fileName) throws IOException, ClassNotFoundException{
       FileOutputStream in = new FileOutputStream(fileName);
       try(ObjectOutputStream out = new ObjectOutputStream(in)){
           out.writeObject(this);
       }
   }
      
      /**
       * Serialization method to load object
       * @param file
       * @return
       * @throws FileNotFoundException
       * @throws IOException
       * @throws ClassNotFoundException 
       */
       public Hand load(String file) throws FileNotFoundException, IOException, ClassNotFoundException{
       Hand h = null;
       try{
           FileInputStream in = new FileInputStream(file);
           ObjectInputStream fin = new ObjectInputStream(in);
           h = (Hand)fin.readObject();
       }catch(IOException e){
           e.printStackTrace();
       }
       return h;
   }
   
    
    public static void main(String[] args){
        
        //Hand test
        
        //add cards - create new hand
        Hand hand = new Hand();
        Card card = new Card(Rank.ACE,Suit.SPADES);
        hand.addSingleCard(new Card(Rank.EIGHT,Suit.CLOVERS));
        Hand hand2 = new Hand();
        hand2.addHand(hand);
        
        System.out.println(hand.toString());
        System.out.println(hand.countRank(Rank.FOUR));
        System.out.println(hand.countSuit(Suit.CLOVERS));
        System.out.println(hand.noofCards);
        
        //hasSuit
        System.out.println(hand.hasSuit(Suit.DIAMONDS));
        hand.sortAscending();
        
        System.out.println("Sort by Rank: ");
        hand.sortRank();
        System.out.println("Sort by Suit: ");
        
        //remove card
        System.out.println(hand.remCardAtPostion(0));
        System.out.println(hand.removeSingleCard(card));
        
        //print hand
        System.out.println(hand);
        
        
        
        
    }
    
}
