package ch.uzh.ifi.seal.soprafs19.godCards;

import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.model.Field;
import ch.uzh.ifi.seal.soprafs19.model.Worker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class NormalPlayer {
    @Id
    @GeneratedValue
    private long godCardId;

    @OneToOne(mappedBy = "godCard")
    private Player player;

    protected Field move(Worker worker){
        //implement something
        return(new Field());
    }

    protected Field build(Worker worker){
        //implement something
        return(new Field());
    }

    protected boolean checkWin(Player player){
        //implement something
        return(false);
    }

}
