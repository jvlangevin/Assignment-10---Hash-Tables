package assignment10;

public class BadHashFunctor implements HashFunctor{

	/**
	 * Returns a hash value based on the number of characters in the string.
	 */
	@Override
	public int hash(String item) {
		return item.length();
	}

}
