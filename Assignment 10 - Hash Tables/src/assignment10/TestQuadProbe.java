package assignment10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestQuadProbe {

	final private static int INITIAL_SIZE = 11;
	
	public static void main(String[] args){
		
		
		
		HashFunctor functor = new GoodHashFunctor();
		QuadProbeHashTable test = new QuadProbeHashTable(INITIAL_SIZE, functor);
		
		List<String> largeFile = readFromFile(new File("wordsEn.txt"));
		
		test.addAll(largeFile);
		if(test.contains("radiantly"))
		{
			System.out.println("Item was added and is contained.");
			System.out.println("The number of times this was rehashed: " + test.timesRehashed());
			System.out.println("The number of collisions: " + test.collisionCount());
			System.out.println("Size of table is: " + test.size());
			System.out.println("Length of table is: " + test.tableLength());
			System.out.println("Collisions per word: " + (double)(test.collisionCount())/test.size());
			
		}
		else{
			System.out.println("Something is wrong with add/contains.");
			System.out.println("The number of times this was rehashed: " + test.timesRehashed());
			System.out.println("The number of collisions: " + test.collisionCount());
			System.out.println("Size of table is: " + test.size());
			System.out.println("Length of table is: " + test.tableLength());
			
			for(int i = 0; i < test.tableLength(); i++){
				System.out.println(""+i+" "+ test.getItemByIndex(i));
			}
			
		}
		
	}
	
	private static List<String> readFromFile(File file) {
		ArrayList<String> words = new ArrayList<String>();
		
		try (Scanner fileInput = new Scanner(file)) {
			/*
			 * Java's Scanner class is a simple lexer for Strings and primitive
			 * types (see the Java API, if you are unfamiliar).
			 */

			/*
			 * The scanner can be directed how to delimit (or divide) the input.
			 * By default, it uses whitespace as the delimiter. The following
			 * statement specifies anything other than alphabetic characters as
			 * a delimiter (so that punctuation and such will be ignored). The
			 * string argument is a regular expression that specifies "anything
			 * but an alphabetic character". You need not understand any of this
			 * for the assignment.
			 */
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

		System.out.println("Document is " + words);

		return words;
	}
}
