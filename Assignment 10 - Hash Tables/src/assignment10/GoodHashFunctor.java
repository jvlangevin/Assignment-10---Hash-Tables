package assignment10;

public class GoodHashFunctor implements HashFunctor {

	
	//return value will be the value of the character multiplied by twice the position in the string
	@Override
	public int hash(String item) {
		int index = 0;
		for(int i = 0; i < item.length(); i++){
			index += Character.getNumericValue(item.charAt(i)) * (i*23) - (i*item.length()+Character.getNumericValue(item.charAt(0)));
		}
		return index+item.length();
	}

}
