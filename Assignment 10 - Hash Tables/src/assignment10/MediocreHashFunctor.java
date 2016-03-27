package assignment10;

public class MediocreHashFunctor implements HashFunctor{

	//this has returns an index value based on the length of the string
	@Override
	public int hash(String item) {
		
		return item.length();
	}

}
