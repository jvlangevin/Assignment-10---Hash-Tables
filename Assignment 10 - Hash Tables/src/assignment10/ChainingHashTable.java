package assignment10;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ChainingHashTable implements Set<String> {

	private int size;
	private HashFunctor hashFunctor;
	private LinkedList<String>[] storage;
	private int storageSize;
	private int rehashes;
	private int collisions;
	private static final int maxLoadFactor = 10;

	@SuppressWarnings("unchecked")
	public ChainingHashTable(int capacity, HashFunctor functor){

		storage = (LinkedList<String>[]) new LinkedList[capacity];
		hashFunctor = functor;
	}

	@Override
	public boolean add(String item) {
		
		if(contains(item)){
			return false;
		}
		if(this.getLoadFactor() >= maxLoadFactor){
			this.rehash(getNextPrime(storage.length*2));
		}
		
		int index = hashFunctor.hash(item) % storage.length;
		
		if(storage[index] == null){
			storage[index] = new LinkedList<>();
			storageSize++;
		}
		else{
			collisions++;
		}
		
		storage[index].addLast(item);
		size++;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends String> items) {

		if(items.size()+this.size > storage.length*maxLoadFactor){
			this.rehash(getNextPrime((items.size()+this.size)/maxLoadFactor));
		}
		
		int initialSize = size;

		for (String item : items) {	
				add(item);
		}

		if (size != initialSize) {
			return true;
		}
		return false;
	}

	@Override
	public void clear() {

		for(int i = 0; i < storage.length; i++){
			storage[i] = null;
		}
		this.size = 0;
	}

	@Override
	public boolean contains(String item) {

		int index = hashFunctor.hash(item) % storage.length;
		
		if(storage[index] == null){
			return false;
		}
		
		return storage[index].contains(item);
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
	 * Returns the average size of the lists contained in the hash table.
	 */
	private int getLoadFactor(){
		
		if(storageSize == 0){
			return 0;
		}
		
		int totalListSize = 0;
		
		for(int i = 0; i < storage.length; i++){
			if(storage[i] != null){
				totalListSize += storage[i].size();
			}
		}
		
		int averageListSize = totalListSize / storage.length;
		return averageListSize;
	}
	
	/**
	 * Increases the table's capacity to the specified amount and rehashes the items
	 * contained in the table.
	 * 
	 * @param newStorageSize -- the new size of the hash table
	 */
	@SuppressWarnings("unchecked")
	private void rehash(int newStorageSize){
		
		
		
		LinkedList<String>[] tempHash = (LinkedList<String>[]) new LinkedList[newStorageSize];
		ArrayList<String> valueHolder =  new ArrayList<>();
		
		for(int i = 0; i < storage.length; i++){
			if(storage[i] != null){
				for(String entry : storage[i])
				valueHolder.add(entry);
			}
		}
		
		this.clear();
		storage = tempHash;
		this.addAll(valueHolder);
		rehashes++;
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
	
	public int timesRehashed(){
		return rehashes;
	}
	
	public int collisionCount(){
		return collisions;
	}
	
	public int tableLength(){
		return storage.length;
	}
	
	public int getLargestListSize(){
		int largestListSize =0;
		for(int i = 0; i < storage.length; i++)
		{
			if(storage[i] == null){
				largestListSize = Math.max(largestListSize, 0);
			}
			else{
				largestListSize = Math.max(largestListSize, storage[i].size());
			}
		}
		return largestListSize;
	}
	
	public int getSmallestListSize(){
		int smallestListSize=100;
		for(int i = 0; i < storage.length; i++)
		{
			if(storage[i] == null){
				smallestListSize = Math.min(smallestListSize, 0);
			}
			else{
				smallestListSize = Math.min(smallestListSize, storage[i].size());
			}
		}
		return smallestListSize;
	}
}
