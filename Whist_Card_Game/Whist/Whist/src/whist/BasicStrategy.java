/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whist;

import cards.Card;
import cards.Deck;
import cards.Hand;

/**
 *
 * @author Nahim
 */
public class BasicStrategy implements Strategy {

    int id;
    Trick t;
    Hand trumpCard = new Hand();
    Hand leadCards = new Hand();
    Hand noTrumpOrLeadCards = new Hand();
    
    public BasicStrategy(int id){
        this.id = id;
    }
    
    //discard lowest card
    public Card getLowestCard(){
        if(!leadCards.getHand().isEmpty()){
            leadCards.sortRank();
            return leadCards.getHand().get(leadCards.getHand().size()-1);
        }else if(!noTrumpOrLeadCards.getHand().isEmpty()){
            noTrumpOrLeadCards.sortRank();
            return noTrumpOrLeadCards.getHand().get(noTrumpOrLeadCards.getHand().size()-1);
        }else{
            trumpCard.sortRank();
            return trumpCard.getHand().get(trumpCard.getHand().size()-1);
        }
    }
    
    @Override
    public Card chooseCard(Hand h, Trick t) {
      //this trick equals to passed trick
      this.t = t;
      
      Boolean first = true;
      Boolean partnerTurn = false;
      Boolean partnerWinning = false;
      Boolean topTrump = false;
      int partner = 0;
      
      Card[] trick = t.getCards();
      
      for(Card card : trick){
          if(card != null){
              first = false;
          }
      }
      
      //first player
      if(first){
          h.sortRank();
          return h.getHand().get(0);
      }
      
      //check if cards in hand are trump or lead
      for(Card card : h.getHand()){
          if(card.getSuit() == t.getLeadSuit()){
              leadCards.addSingleCard(card);
          }else if(card.getSuit() == t.trumps){
              trumpCard.addSingleCard(card);
          }else
              noTrumpOrLeadCards.addSingleCard(card);
      }
      
      if(id == 0){
          partner = 2;
      }
      if(id == 1){
          partner = 3;
      }
      if(id ==2){
          partner = 0;
      }else{
          partner = 1;
      }
      
      //check if partner has had turn
      if(trick[partner] != null){
          partnerTurn = true;
      }
      
      //if partner has not has thier turn
      if(partnerTurn){
          if(trick[partner] == t.getTopCard()){
              partnerWinning = true;
          }
      }
      
      //determine top card in trick is a trump
      if(t.getTopCard().getSuit() == t.trumps && t.trumps != t.getLeadSuit()){
          topTrump = true;
      } 
      
      //if partner is winning, discard lowest card
      if(partnerWinning){
          return getLowestCard();
      }else if(leadCards.getHand().get(0).getRank().getCardRank() > t.getTopCard().getRank().getCardRank() && topTrump == false){
        return leadCards.getHand().get(0);
        //play trump card
      }else if(!trumpCard.getHand().isEmpty()){
          trumpCard.sortRank();
          if(topTrump == false){
              return trumpCard.getHand().get(0);
          }//if top card is trump, play a card higher than it
          if(topTrump == true){
              if(trumpCard.getHand().get(0).getRank().getCardRank()> t.getTopCard().getRank().getCardRank()){
                  return trumpCard.getHand().get(0);
              }
          }
      }
      
      return getLowestCard();
      
    }

    @Override
    public void updateData(Trick c) {
       t = c;
    }
    
    public static void main(String[] args){
        BasicStrategy s = new BasicStrategy(0);
        Deck deck = new Deck();
        Hand hand1 = new Hand();
        Trick t = new Trick(0);
        
        for(int i=0; i<13; i++){
            hand1.addSingleCard(deck.deal());
        }
        
        Trick.setTrumps(Card.Suit.DIAMONDS);
        System.out.print("Trump is Diamonds ");
        
        //create player 1
        BasicPlayer player1 = new BasicPlayer(0,hand1,s);
        
        hand1.sortAscending();
        
        //output player 1 hand
        System.out.println(hand1.toString());
        t.setCard(player1.playCard(t), player1);
    }
    
    
    
}
