package assignment10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestChainingHash {
	
	private static final int initialSize = 13;

	public static void main(String[] args) {

		ChainingHashTable cht = new ChainingHashTable(initialSize, new GoodHashFunctor());
		List<String> list = readFromFile(new File("wordsEn.txt"));
		
		cht.addAll(list);
		
		
		if(cht.contains("radish")){
			System.out.println("Contains and add works.");
		}
		else{
			System.out.println("Problems with contains/add.");
		}
		System.out.println("Number of rehashes: " + cht.timesRehashed());
		System.out.println("Number of collisions: " + cht.collisionCount());
		System.out.println("Size of table: " + cht.size());
		System.out.println("Length of storage array: " + cht.tableLength());
		System.out.println("Collisions per word: " + (double)(cht.collisionCount())/cht.size());
		
		System.out.println("Largest list size: " + cht.getLargestListSize());
	}

	private static List<String> readFromFile(File file) {
		ArrayList<String> words = new ArrayList<String>();
		
		try (Scanner fileInput = new Scanner(file)) {

			fileInput.useDelimiter("\\s*[^a-zA-Z]\\s*");

			while (fileInput.hasNext()) {
				String s = fileInput.next();
				if (!s.equals("")) {
					words.add(s.toLowerCase());
				}
			}

		} catch (FileNotFoundException e) {
			System.err.println("File " + file + " cannot be found.");
		}

//		System.out.println("Document is " + words);

		return words;
	}
}
