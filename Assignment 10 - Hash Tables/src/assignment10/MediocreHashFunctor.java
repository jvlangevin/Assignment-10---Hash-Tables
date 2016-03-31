package assignment10;

public class MediocreHashFunctor implements HashFunctor{

	/**
	 * Returns a hash value based on the sum of the integer values
	 * of each character in the string.
	 */
	@Override
	public int hash(String item) {

		int sum = 0;
		for(int i = 0; i < item.length(); i++){
			sum += Character.getNumericValue(item.charAt(i));
		}
		return sum;
	}

}
