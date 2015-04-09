package game_engine.sprite;

public class Collectible extends Sprite{
<<<<<<< HEAD
=======
	private int myCount;

	public Collectible() {
		super();
		myCount = 0;
	}


	public Collectible(String name){
		super(name);
		myCount = 0;
	}

	public Collectible(String name, int id){
		super(name,id);
		myCount = 0;
	}

	public void collect(int amount){
		myCount+=amount;
	}

	public void decrement(int amount){
		myCount -= amount;
	}

	public int getCount(){
		return myCount;
	}

	public void resetCount(){
		myCount = 0;
	}
>>>>>>> 000a72cd2ad330dfa4783279defb0d8be34cb353

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
<<<<<<< HEAD
=======


>>>>>>> 000a72cd2ad330dfa4783279defb0d8be34cb353

}
