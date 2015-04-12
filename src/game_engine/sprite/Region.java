package game_engine.sprite;

import game_engine.IAction;

public class Region extends Sprite implements IRegion{

    public Region() {
        super();
      }

      public Region(String name){
          super(name);
      }
    
      public Region(String name, int id){
          super(name,id);
      }
      
      private IAction changePhysics = (params) ->{
          //Change corresponding physics params
      };
      
      public IAction getChangePhysics(){
          return this.changePhysics;
      }
      
      private IAction negatePhysics = (params) ->{
          
      };
      
      public IAction getNegatePhysics(){
          return this.negatePhysics;
      }
      
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void setGravity (double gravity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getGravity () {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setDrag (double drag) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getDrag () {
        // TODO Auto-generated method stub
        return 0;
    }

}
