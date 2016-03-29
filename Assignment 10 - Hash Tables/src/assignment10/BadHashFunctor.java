package assignment10;

public class BadHashFunctor implements HashFunctor{

	//this BadHasFunctor returns a value based on the first character
	@Override
	public int hash(String item) {
		return Character.getNumericValue(item.charAt(0));
	}

	public static void main(String[] args){
		String s = "a";
		System.out.println(Character.getNumericValue('z'));
	}
}
