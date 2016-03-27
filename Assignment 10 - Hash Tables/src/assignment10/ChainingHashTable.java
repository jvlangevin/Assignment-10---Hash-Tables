package assignment10;

import java.util.Collection;
import java.util.LinkedList;

public class ChainingHashTable implements Set<String> {

	private int size;
	private HashFunctor hashFunctor;
	private LinkedList<String>[] storage;
	private int storageSize;

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
		
		int index = hashFunctor.hash(item) % storage.length;
		
		if(storage[index] == null){
			storage[index] = new LinkedList<>();
			storageSize++;
		}
		
		storage[index].addLast(item);
		size++;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends String> items) {

		int initialSize = size;

		for (String item : items) {
			if (!contains(item)) {
				add(item);
			}
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
		
		int totalListSize = 0;
		
		for(int i = 0; i < storage.length; i++){
			if(storage[i] != null){
				totalListSize += storage[i].size();
			}
		}
		
		int averageListSize = totalListSize / storageSize;
		return averageListSize;
	}
	
}
