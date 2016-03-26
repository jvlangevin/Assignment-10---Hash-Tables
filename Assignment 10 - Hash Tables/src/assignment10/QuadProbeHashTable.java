package assignment10;

import java.util.Collection;

public class QuadProbeHashTable implements Set<String>{

	private int size;
	private HashFunctor hashFunctor;
	private String[] table;
	
	/** Constructs a hash table of the given capacity that uses the hashing function
     * specified by the given functor.
     */
   public QuadProbeHashTable(int capacity, HashFunctor functor){
	   	   
	   table = new String[capacity];
	   hashFunctor = functor;
   }
	
	@Override
	public boolean add(String item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends String> items) {

		int initialSize = size;
		
		for(String item : items){
			if(!contains(item)){
				add(item);
				size++;
			}
		}
		
		if(size != initialSize){
			return true;
		}
		return false;
	}

	@Override
	public void clear() {

		for(int i = 0; i < table.length; i++){
			table[i] = null;
		}
	}

	@Override
	public boolean contains(String item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<? extends String> items) {

		for(String item : items){
			if(!contains(item)){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return size;
	}
	
	/**
	 * 
	 * Returns true if the specified integer is prime, returns false otherwise.
	 */
	private boolean isPrime(int num){
		
		if(num == 2){
			return true;
		}
		if(num <= 1 || num % 2 == 0){
			return false;
		}
		
		for(int i = 3; i*i <= num; i += 2){
			if(num % i == 0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns the next largest prime after the specified number.
	 */
	private int getNextPrime(int num){
		
		if(num % 2 == 0){
			num++;
		}
		
		while(!isPrime(num)){
			num += 2;
		}
		return num;
	}

	public static void main(String[] args){
		
		QuadProbeHashTable q = new QuadProbeHashTable(3, new BadHashFunctor());
	}
}
