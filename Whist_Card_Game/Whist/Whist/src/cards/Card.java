/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
/*
 *
 * @author Nahim
 */
public class Card implements Serializable, Comparable<Card>,Comparator<Card> {
    
    private static final long serialVersionUID = 100L;
    private Rank rank;
    private Suit suit;
   
    /**
     * Enum to hold Rank and suit values for cards
     */
    public enum Rank {
        
        TWO(2,1),THREE(3,2),FOUR(4,3),FIVE(5,4),SIX(6,5),SEVEN(7,6),EIGHT(8,7),NINE(9,8),TEN(10,9),JACK(10,10),QUEEN(10,11),
        KING(10,12),ACE(11,13);
        
        private int cardValue;
        private int cardRank;
        public static Rank[] values = values();
        
        
        private Rank(int value, int cardRank)
        {
            this.cardValue = value;
            this.cardRank = cardRank;
        }
        
        //getNext method - returns next enum value
        public Rank getNext()
        {
            return values()[(ordinal()+1)%values().length];
        }
        
        //returns previous
        public Rank getPrevious(){
            return values()[(ordinal()-1)%values.length];
        }
        
        public int getValue()
        {
            return cardValue;
        }
        
        public int getCardRank(){
            return cardRank;
        }
    
    };
    
    /**
     * Enumeration to store Suit values
     */
    public enum Suit
    {
        CLOVERS, DIAMONDS, HEARTS, SPADES;
        
        public static Suit[] values = values();
        
        /**
         * method that returns random suit
         * @return value of suit
         */
        public static Suit getRandomSuit()
        {
            Random random = new Random();
            int randomSuit = new Random().nextInt(values.length);
            //return Suit.values()[random.nextInt(Suit.values().length)];
            return values[randomSuit];
        }
       
    };
    
   
    /**
     * Default Constructor
     * @param rank
     * @param suit 
     */
    public Card(Rank rank, Suit suit)
    {
        this.rank = rank;
        this.suit = suit;
    }
    
    /**
     * Test constructor
     */
    public Card()
    {
        
    }
    
    /**
     * 
     * @return random suit 
     */
    public Suit getRandomSuit()
        {
            Random random = new Random();
            
            return Suit.values()[random.nextInt(Suit.values().length)];
        }
    
    /**
     * Method to set Rank and value
     * @param rank 
     */
    public void setRank(int rank)
    {
   
        this.rank.getValue();
    }
    
    /**
     * 
     * @return rank
     */
    public Rank getRank()
    {
        return rank;
    }
    
    /**
     * Method to set suit
     * @param suit 
     */
    public void setSuit(Suit suit)
    {
        this.suit = suit;
    } 
    
    /**
     * 
     * @return suit
     */
    public Suit getSuit()
    {
        return suit;
    }
    
       // implement natural order
    @Override
    public int compareTo(Card o) {
        int rankCompare = rank.compareTo(o.rank);
        return rankCompare != 0 ? rankCompare : suit.compareTo(o.suit);
    }

    @Override
    public int compare(Card o1, Card o2) {
        int n = o1.compareTo(o2);
        if(n == 1 || n == -2){
            return -1;
        }
        return 0;
    }
    
    /**
     * Sort in Ascending order
     * @param one
     * @param two
     * @return 
     */
    public  int compareTo(Card one, Card two){
        if(this.rank.compareTo(two.rank) < 0){
            return -1;
        }
        else if(this.rank.compareTo(two.rank) == 0){
            if(this.suit.compareTo(two.suit) < 0){
                return -2;
            }
        else if(this.suit.compareTo(two.suit) == 0){
            return 0;
        }
        else if(this.suit.compareTo(two.suit) > 0){
            return 2;
        }
        }
    
        return 1;
    }
    
    /**
     * Method to get the max card in list
     * @param cards
     * @return 
     */
    public static Card max(List<Card> cards){
        Card max = cards.get(0);
        Iterator<Card> it = cards.iterator();
        
        while(it.hasNext()){
            int compare = max.compareTo(it.next());
            if(compare == 1){
                max = it.next();
            }
        }
        return max;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.rank);
        hash = 19 * hash + Objects.hashCode(this.suit);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Card)){
            return false;
        }
        Card c = (Card)obj;
        return this.compareTo(c) == 0;
    }
    
    
    /**
     * Nested class sort descending order of Rank
     */
    public static class CompareDescending implements Comparator<Card>{

        @Override
        public int compare(Card o1, Card o2) {
            int rank = o1.compareTo(o2);
            if(rank == 0)
            {
               // return o1.suit.ordinal() - o2.suit.ordinal();#
                return -1;
            }
            else{
                return 0;
            }
        }
            
}
   /**
    * Nested class sort into ascending order by suit then rank
    */
   public class CompareRank implements Comparator<Card>{
       
        @Override
        public int compare(Card t, Card t1) {
            if(t.rank.ordinal() > t1.rank.ordinal()){
                return 1;
        }
            else if(t.rank.ordinal() < t1.rank.ordinal()){
                return -1;
            }else{
                return 0;
            }
        
    }
    

}
    /**
     * Returns a ArrayList of cards passing a comparator based on 
     * being greater than the card passed through the parameter.
     * @param card
     * @param c
     * @param compare
     * @param acard
     * @return 
     */
    public ArrayList<Card> chooseGreater(ArrayList<Card> card, Card c,Comparator<Card> compare, Card acard){
        Iterator<Card> iterator = card.iterator();
        ArrayList<Card> newList = new ArrayList();
        
        while(iterator.hasNext()){
            Card c1 = iterator.next();
            if(compare.compare(acard, c1) == -1){
                newList.add(c);
        }
        }
        return newList;
    }
    
    
    /**
     * Uses two comparators and lambda expression to call ChooseGreater method with 
     * a list of cards. Lambda implements logic that Card A is greater than Card B
     * if Rank of A is greater than Rank B or if the ranks are equal, than Card A is greater than B if the 
     * suit is greater than B
     * @param list
     * @param card
     * @return 
     */
    public ArrayList<Card> selectTest(ArrayList<Card> list, Card card){
        Comparator<Card> Rank = new CompareRank();
        Comparator<Card> descending = new CompareDescending();
        ArrayList<Card> aList = new ArrayList();
        
        list.forEach(cards ->{
            if(Rank.compare(card, card) == -1){
                aList.add(cards);
            }
            else if(card.getRank() == cards.getRank()){
                if(descending.compare(card, card) == -1){
                    aList.add(card);
                }
            }
        });
        
        return aList;
    }
    
    
    /**
     * Method to save Serialisation object
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
     * Method to load serialisation Card object
     * @param filename
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
   public Card load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException{
       try{
           FileInputStream in = new FileInputStream(filename);
           Card l = null;
           try{
               ObjectInputStream fin = new ObjectInputStream(in);
               l = (Card)fin.readObject();
           }catch(IOException e){
           }
           return l;
       }catch(IOException e){
       }
       
       return null;
   }
       
       
   
 
    
    /**
     * toString to return cards 
     * @return 
     */
    @Override
    public String toString(){
        StringBuilder st = new StringBuilder();
        st.append(this.rank).append(" ").append(this.suit);
        return st.toString();
    }
    
    public static void main(String[] args){
        //Test Card.java methods
        
        //Add cards to hand
        ArrayList<Card> cards = new ArrayList();
        cards.add(new Card(Rank.ACE,Suit.SPADES));
        cards.add(new Card(Rank.QUEEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.EIGHT,Suit.HEARTS));
        cards.add(new Card(Rank.JACK, Suit.CLOVERS));
        
        Card ACEOFSPADE = new Card(Rank.ACE,Suit.SPADES);
       
        
        //sorted list in ascending order
        System.out.println(cards + "\n");
        
        //print by rank
        Collections.sort(cards,Card.CompareRank());
        
        ArrayList<Card> choosegreater = chooseGreater(cards,compareRank());
        System.out.println(choosegreater);
    }
    
}
