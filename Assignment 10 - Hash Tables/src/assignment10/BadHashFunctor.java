package assignment10;

public class BadHashFunctor implements HashFunctor{


	
	//this BadHasFunctor returns a value based on the first character
	@Override
	public int hash(String item) {
		
		return Character.valueOf(item.charAt(0));
	}

}
