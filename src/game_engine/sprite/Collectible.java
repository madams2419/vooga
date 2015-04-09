package game_engine.sprite;

public class Collectible extends Sprite{
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 5138838c563c960daed39788fdea70460ca86d06
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
<<<<<<< HEAD
=======
>>>>>>> a6ac6e16e27cd9ddd8d3cb89042487d1eee2b383
=======
>>>>>>> 000a72cd2ad330dfa4783279defb0d8be34cb353
>>>>>>> 5138838c563c960daed39788fdea70460ca86d06

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
<<<<<<< HEAD
<<<<<<< HEAD


=======
>>>>>>> a6ac6e16e27cd9ddd8d3cb89042487d1eee2b383
=======
=======


>>>>>>> 000a72cd2ad330dfa4783279defb0d8be34cb353
>>>>>>> 5138838c563c960daed39788fdea70460ca86d06

}
