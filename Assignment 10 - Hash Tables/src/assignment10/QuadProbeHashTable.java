package assignment10;

import java.util.ArrayList;
import java.util.Collection;

public class QuadProbeHashTable implements Set<String>{

	private int size;
	private HashFunctor hashFunctor;
	private String[] table;
	private int rehashedCount;
	private int collisions;
	
	/** Constructs a hash table of the given capacity that uses the hashing function
     * specified by the given functor.
     */
   public QuadProbeHashTable(int capacity, HashFunctor functor){
	   
	   this.size = 0;
	   this.rehashedCount = 0;
	   this.collisions = 0;
	   if(!isPrime(capacity)){
		   this.table = new String[getNextPrime(capacity)]; //always sets the capacity to be a prime at start
	   }
	   else{
		   this.table = new String[capacity];
	   }
	   this.hashFunctor = functor;
   }
	
	@Override
	public boolean add(String item) {
		
		if(contains(item)){
			return false;
		}
		
		//checks if this.table is half full 
		//rehashes if it is; rehash resizes table to next prime number 
		//and assigns previous items to the new hashTable
		if(this.isTableHalfFull())
		{
			this.rehash(table.length*2);
		}
		
		//sets the initial index
		int initialIndex = hashFunctor.hash(item) % table.length;
		int index = initialIndex;
		int quadValue = 1;
		
		while(this.table[index] != null)
		{
			this.collisions++;
			index = initialIndex;
			
			/*if the index isn't empty the index should increase at a rate
			  of 1, 4, 9, 16, 25 (growing by squares) BUT is then reduced by 
			  modulo table size, so if the table size is 17 (prime #, it would go to index
			  1, 4, 9, 16, 8  */
			index = (index + (quadValue * quadValue)) % table.length;
			quadValue++;
		}
		
		this.table[index] = item;
		size++;
		
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends String> items) {

		int initialSize = size;
		
		if(items.size() > (table.length/2)+this.size())
		{
			this.rehash(items.size()*3);

		}
		
		for(String item : items){
			
				add(item);
			
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
		this.size = 0;
	}

	@Override
	public boolean contains(String item) {
		int initialIndex = hashFunctor.hash(item) % table.length;
		int index = initialIndex;
		int quadValue = 1;
		
		/*if we ever run into a null following the quadratic probing algorithm, 
		 * the item isn't contained. Note: because the number of items should be half
		 * the size of the array, we should hit a null if it's not contained.
		 */
		while(this.table[index] != null)
		{
			//if the item is found, return true;
			if(this.table[index].equals(item))
			{
				System.out.println("If was true");
				return true;
			}
			
			index = initialIndex;
			
			/*if the index isn't empty the index should increase at a rate
			  of 1, 4, 9, 16, 25 (growing by squares) BUT is then reduced by 
			  modulo table size, so if the table size is 17 (prime #, it would go to index
			  1, 4, 9, 16, 8  */
			index = (index + (quadValue * quadValue)) % table.length;
			quadValue++;
		}
		
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
	private static boolean isPrime(int num){
		
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
	private static int getNextPrime(int num){
		
		if(num == 2){
			return 3;
		}
		if(num % 2 == 0){
			num++;
		}
		
		do{
			num += 2;
		}while(!isPrime(num));
		
		return num;
	}
	
	
	/**
	 * Checks to see if the table is half full. Array table should be resized once it's halfway full.
	 * @return boolean based on size of elements vs length of table.
	 */
	private boolean isTableHalfFull(){
		if(size >= table.length / 2){
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
	private void rehash(int firstPrimeAfterThis){
		this.rehashedCount++;
		
		String[] tempHash = new String[getNextPrime(firstPrimeAfterThis)];
		ArrayList<String> valueHolder = new ArrayList<>();
		
		for(int i = 0; i < table.length; i++){
			
			if(table[i] != null){
				valueHolder.add(table[i]);
			}
			
		}
		
		this.clear();
		this.table = tempHash;
		this.addAll(valueHolder);
	
	
	}

	public int timesRehashed(){
		return this.rehashedCount;
	}
	
	public int collisionCount(){
		return this.collisions;
	}
	
	public int tableLength(){
		return this.table.length;
	}
	
	public String getItemByIndex(int index){
		
		return this.table[index];
	}
	
}
