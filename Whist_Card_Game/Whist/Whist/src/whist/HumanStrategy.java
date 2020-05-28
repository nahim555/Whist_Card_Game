/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whist;

import cards.Card;
import cards.Hand;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Nahim
 */
public class HumanStrategy implements Strategy {
    
    private Hand leadCards = new Hand();
    private Card chosenCard =null;
    private boolean isFirst = true;
    private int id;
    private Trick trick;
    private Hand hand;
    
    public HumanStrategy(int id){
        this.id = id;
    }

    @Override
    public Card chooseCard(Hand h, Trick t) {
        trick = t;
        hand = h;
        
        Scanner sc = new Scanner(System.in);
        boolean validCard = true;
        boolean chooseCard = true;
        boolean answer = true;
        chosenCard = null;
        isFirst = true;
        
        leadCards.getHand().clear();
        
        //check if first player
        for(Card c : t.getCards()){
            if(c != null){
                isFirst = false;
                break;
            }
        }
        
        if(!isFirst){
            //check if cards in hand are trump or lead
            for(Card c : h.getHand()){
                if(c.getSuit() == t.getLeadSuit()){
                    leadCards.addSingleCard(c);
                }
            }
        }
        
        h.sortAscending();
        System.out.println("" + t.toString());
        System.out.println("HAND ");
        System.out.println(PlayerHand());
        
        int handSize = hand.getHand().size()-1;
        System.out.println("Pick a card " + "between 0" + "-" + handSize + " ");
        int acard = 0;
        while(!validCard){
            try{
                acard = sc.nextInt();
            }catch(InputMismatchException e){
                acard = -1;
                 sc.next();
            }
        }
        
        int card = acard;
             
        
        //Card chosen less than 0 or bigger than hand size
        if(card < 0 || card >= hand.getHand().size()){
            validCard = false;
        }
        
        while(!validCard){
            System.out.println("Pick a valid card " + handSize);
            sc.next();
        }
        card = sc.nextInt();
        
        //card is valid if between 0 and handsize
        validCard = !(card < 0 || card >= hand.getHand().size());
        
    
        chosenCard = hand.getHand().get(card);
        
        
        return chosenCard;
    }
    
    public String PlayerHand(){
        StringBuilder st = new StringBuilder();
        ArrayList<Hand> hand = new ArrayList();
        for(int i=0; i<hand.size();i++){
            st.append("(").append(i).append(")").append(hand.get(i).toString()).append("\n");
        }
        return st.toString();
    }

    @Override
    public void updateData(Trick c) {
      trick = c;   
    }
    
}
