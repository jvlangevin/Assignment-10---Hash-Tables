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
	   
	   this.size = 0;
	   this.table = new String[this.getNextPrime(capacity)]; //always sets the capacity to be a prime at start
	   this.hashFunctor = functor;
   }
	
	@Override
	public boolean add(String item) {
		
		//checks if this.table is half full 
		//rehashes if it is; rehash resizes table to next prime number 
		//and assigns previous items to the new hashTable
		if(this.isTableHalfFull())
		{
			this.rehash();
		}
		
		
		//sets the initial index
		int index = (this.hashFunctor.hash(item))%this.table.length;
		int quadValue = 1;
		
		while(this.table[index] != null)
		{
			/*if the index isn't empty the index should increase at a rate
			  of 1, 4, 9, 16, 25 (growing by squares) BUT is then reduced by 
			  modulo table size, so if the table size is 17 (prime #, it would go to index
			  1, 4, 9, 16, 8  */
			index = index + quadValue%this.table.length;
			quadValue = quadValue+1;
			quadValue = quadValue*quadValue;
		}
		
		this.table[index] = item;
		
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

		for(int i = 0; i < this.table.length; i++){
			this.table[i] = null;
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
	
	
	/**
	 * Checks to see if the table is half full. Array table should be resized once it's halfway full.
	 * @return boolean based on size of elements vs length of table.
	 */
	private boolean isTableHalfFull(){
		if(this.size() >= this.table.length){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Function to rehash array.
	 * Steps: 1. Create thisHash and assign this HashTable to it's values.
	 * 		  2. create a tempHash with our new size, which is the next prime number after it's length
	 */
	private void rehash(){
		
		QuadProbeHashTable thisHash = this;
		QuadProbeHashTable tempHash = new QuadProbeHashTable(this.getNextPrime(this.table.length), this.hashFunctor);
		tempHash.addAll(this.table);
		thisHash.clear();
		thisHash = tempHash;
	
	}

	public static void main(String[] args){
		
		QuadProbeHashTable q = new QuadProbeHashTable(3, new BadHashFunctor());
	}
}
