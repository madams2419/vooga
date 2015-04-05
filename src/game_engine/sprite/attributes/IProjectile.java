package game_engine.sprite.attributes;

import game_engine.sprite.Sprite;

public interface IProjectile {

       public void setProjectile(Sprite sprite);
       
       public void setNumProjectiles(int number);
       
       public void getNumProjectiles();
       
       public void shootProjectile();
}
