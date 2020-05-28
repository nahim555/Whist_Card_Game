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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Nahim
 */
public class Deck implements Iterable<Card>, Serializable {
   int fixedSize = 52;
   private List<Card> cards = Arrays.asList(new Card[fixedSize]);
   private int cardsLeft;
   private static final long serialVersionUID = 49;
   
   
   /**
    * Deck constructor - creates list of cards and fills deck with cards 
    */
   public Deck(){
       cards = new ArrayList();
       for(Card.Suit suit : Card.Suit.values()){
           for(Card.Rank rank : Card.Rank.values()){
               cards.add(new Card(rank,suit));
           }
       shuffle();
       
       }
  
   }
   
   /**
    * Method to shuffle deck using Collections
    */
   public void shuffle(){
       Collections.shuffle(cards);
       
   }
   
   /**
    * Method to deal deck 
    * @return 
    */
   public Card deal(){
       Card top = null;
       //if card is available
       if(cards.size() > 0){
           //get top card
           top = this.cards.get(0);
           //remove from deck
           this.cards.remove(top);
           //cardsleft--
           this.cardsLeft--;
       }else{
           //make a new deck
           newDeck();
   }
       return top;
   }
   
   /**
    * 
    * @return size of hand list
    */
   public int size(){
       return this.cards.size();
   }
   
   /**
    * Method to create new deck
    */
   public void newDeck(){
       cards.clear();
       Deck newDeck = new Deck();
       newDeck.shuffle();
       this.cards = newDeck.cards;
       
       //this.cardsLeft = 52;
       shuffle();
       
   }
   
   /**
    * Method to save the deck in SpadeIterator order
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
   
   public Deck load(String file) throws FileNotFoundException, IOException, ClassNotFoundException{
       Deck d = null;
       try{
           FileInputStream in = new FileInputStream(file);
           ObjectInputStream fin = new ObjectInputStream(in);
           d = (Deck)fin.readObject();
       }catch(IOException e){
           e.printStackTrace();
       }
       return d;
   }
   
   /**
    * write file 
    * @param out
    * @throws IOException 
    */
   public void write(ObjectOutputStream out) throws IOException{
       out.defaultWriteObject();
       Card c;
       Iterator<Card> iterator = SpadeIterator();
       ArrayList<Card> list = new ArrayList();
       
       while(iterator.hasNext()){
           c = iterator.next();
           System.out.println(c.toString());
           list.add(c);
       }
       out.writeObject(list);
       
   }
   
   public Card getCard(int c){
       return cards.get(c);
   }


    public void Reverse(List<Card> cards){
        this.cards = cards;
    }
    
   @Override
    public Iterator<Card> iterator(){
        return new DeckIterator(cards);
        
    }
    
  
   /**
    * Nested class to traverse Deck in order cards are delt
    */
   public class DeckIterator implements Iterator<Card>{
       
       private List<Card> cards;
       private Deck deck;
       private int position;
      
       DeckIterator(List<Card> cards){
           this.cards = new ArrayList();
           this.deck = deck;
           this.position = cards.size();
       }

       // return postion
        @Override
        public boolean hasNext() {
           return position > 0;
        }

        @Override
        public Card next() {
             while(hasNext()){
                 Card spade = cards.remove(position--);
                 if(spade.getSuit().compareTo(Card.Suit.SPADES) == 0)
                     return spade;
             }
             return deck.getDeck().get(position);
        }
        
       @Override
        public void remove(){
            cards.remove(position);
        }
        
   }
   
   public List<Card> getDeck(){
        return cards;
    }
   
   /**
    * Iterator that traverses spades in the deck
    * @return 
    */
   public Iterator<Card> SpadeIterator(){
       return new SpadeIterator(cards);
   }
   
   private class SpadeIterator implements Iterator<Card>{
       
       private Deck d;
       private Card c;
       int position = 0;
       
       
       public SpadeIterator(List<Card> cards){
           cards = new ArrayList();
       }

        @Override
        public boolean hasNext() {
           return position < size();
        }

        @Override
        public Card next() {
            position++;
            if(hasNext()){
                for(Card c : d){
                    if(c.getSuit().equals(Card.Suit.SPADES)){
                        cards.add(c);
                    }
                }
            }
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
       
   }
   
   public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException{
       //Deck test
       
       Deck deck = new Deck();
       
        Iterator<Card> it2 = deck.iterator();
        while(it2.hasNext()){
           System.out.println(it2.next().toString());
       }
       
       //test serialization
       Iterator<Card> it = deck.iterator();
       
       while(it.hasNext()){
           System.out.println(it.next() + "");
   }
       try{
           FileOutputStream fout = new FileOutputStream("deck.ser");
           ObjectOutputStream out = new ObjectOutputStream(fout);
           out.writeObject(deck);
           out.close();
       }catch(IOException ex){
           ex.printStackTrace();
       }
       
       it = deck.SpadeIterator();
       while(it.hasNext()){
           System.out.println(it.next().toString() + "");
       }
       
       deck.newDeck();
       
       try{
           FileInputStream fin = new FileInputStream("deck.ser");
           ObjectInputStream in = new ObjectInputStream(fin);
           deck = (Deck)in.readObject();
           in.close();
       }catch(IOException ex){
           ex.printStackTrace();
       }
       
      
       

}
   
}
