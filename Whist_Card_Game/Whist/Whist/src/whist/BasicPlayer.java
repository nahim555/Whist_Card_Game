/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whist;

import cards.Card;
import cards.Hand;
import java.util.Collections;

/**
 *
 * @author Nahim
 */
class BasicPlayer implements Player {
    
    private Hand h;
    private Strategy s;
    private Trick t;
    private int id;

    BasicPlayer(int id, Hand h, Strategy s) {
        this.h = h;
        this.s = s;
        this.id = id;
    }

    @Override
    public void dealCard(Card c) {
        h.addSingleCard(c);
    }

    @Override
    public void setStrategy(Strategy s) {
        this.s = s;
    }

    /**
     * players card played based on the trick t passed
     * @param t
     * @return 
     */
    @Override
    public Card playCard(Trick t) {
        Card card = s.chooseCard(this.h, t);
        Collections.sort(h.getHand());
        System.out.println("Card played " + card);
        this.h.removeSingleCard(card);
        return card;
    }
    @Override
    public void viewTrick(Trick t) {
        s.updateData(t);
    }

    @Override
    public void setTrumps(Card.Suit s) {
       Trick.trumps = s;
    }

    @Override
    public int getID() {
       return id;
    }
    
}
