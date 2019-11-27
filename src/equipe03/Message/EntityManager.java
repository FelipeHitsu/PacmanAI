package equipe03.Message;

import java.util.HashMap;

public class EntityManager {

    private HashMap<String, Entity> map;

    private static EntityManager instance = null;

    private EntityManager(){map = new HashMap<String, Entity>();}

    public static EntityManager getInstance(){
        if(instance == null)
            instance = new EntityManager();
        return instance;
    }

    public void registerEntity(Entity newEntity){map.put(newEntity.getName(), newEntity);}

    public Entity getEntity(String s){
        Entity entity = map.get(s);
        return entity;
    }

    public void removeEntity(Entity entity){map.remove(entity.getID());}

}
