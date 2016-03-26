package assignment10;

import java.util.Collection;

public class ChainingHashTable implements Set<String> {

	private int size;
	private HashFunctor hashFunctor;
	private String[] table;

	public ChainingHashTable(int capacity, HashFunctor functor){

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

		for (String item : items) {
			if (!contains(item)) {
				add(item);
				size++;
			}
		}

		if (size != initialSize) {
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

}
