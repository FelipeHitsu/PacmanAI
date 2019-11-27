package equipe03.Message;

import equipe03.StateMachine;
import pacman.*;

import java.util.List;

public abstract class Entity extends GhostPlayer {

    private String name;

    protected StateMachine stateMachine;

    public int count = 0;

    public List<Move> moveG;

    public State stateG;

    public Game gameG;

    public int ghostIndexG;

    public int moveIndex = 0;

    public int minOffSetX = 10;

    public int maxOffSetX = 13;

    public int offSetY = 15;

    public List<Location> pacManHistory;

    public boolean isStalking = false;

    public Entity(String name){
        this.name = name;

        EntityManager.getInstance().registerEntity(this);
    }

    public String getName(){return name;}

    public Entity getID(){return this;}

    public boolean handleMessage(Message msg){
        return stateMachine.handleMessage(msg);
    }

}
