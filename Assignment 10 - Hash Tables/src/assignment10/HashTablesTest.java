package assignment10;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HashTablesTest {

	BadHashFunctor badHashFunctor;
	MediocreHashFunctor mediocreHashFunctor;
	GoodHashFunctor goodHashFunctor;
	QuadProbeHashTable quadProbeHashTable;
	ChainingHashTable chainingHashTable;
	ArrayList<String> stringCollection;
	
	@Before
	public void setUp() throws Exception {
		badHashFunctor = new BadHashFunctor();
		mediocreHashFunctor = new MediocreHashFunctor();
		goodHashFunctor = new GoodHashFunctor();
	}

	@Before
	public void generateStringCollectionFromFile(){
		
		File file = new File("wordsEn.txt");
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

		stringCollection = words;
	}
	
	@Test
	public void testBadHashFunctor() {
		assertEquals(28, badHashFunctor.hash("string"));
	}
	
	@Test
	public void testMediocreHashFunctor(){
		assertEquals(6, mediocreHashFunctor.hash("string"));
	}
	
	@Test
	public void testGoodHashFunctor(){
		assertEquals(7371, goodHashFunctor.hash("string"));
	}
	
	@Test
	public void testSize(){
		quadProbeHashTable = new QuadProbeHashTable(2, badHashFunctor);
		chainingHashTable = new ChainingHashTable(2, badHashFunctor);
		assertEquals(0, quadProbeHashTable.size());
		assertEquals(0, chainingHashTable.size());
	}
	
	@Test
	public void isEmptyOnEmptyTable(){
		quadProbeHashTable = new QuadProbeHashTable(2, badHashFunctor);
		chainingHashTable = new ChainingHashTable(2, badHashFunctor);
		assertEquals(true, quadProbeHashTable.isEmpty());
		assertEquals(true, chainingHashTable.isEmpty());
	}
	
	@Test
	public void isEmptyOnNonEmptyTable(){
		quadProbeHashTable = new QuadProbeHashTable(2, badHashFunctor);
		quadProbeHashTable.add("string");
		chainingHashTable = new ChainingHashTable(2, badHashFunctor);
		chainingHashTable.add("string");
		assertEquals(false, quadProbeHashTable.isEmpty());
		assertEquals(false, chainingHashTable.isEmpty());
	}
	
	@Test
	public void addToQuadProbeHashTableWithNoCollisions(){
		quadProbeHashTable = new QuadProbeHashTable(5, badHashFunctor);
		quadProbeHashTable.add("string");
		assertEquals("string", quadProbeHashTable.getItemByIndex(28 % 5));
	}
	
	@Test
	public void addToQuadProbeHashTableWithOneCollision(){
		quadProbeHashTable = new QuadProbeHashTable(11, badHashFunctor);
		quadProbeHashTable.add("string");
		quadProbeHashTable.add("strong");
		assertEquals("strong", quadProbeHashTable.getItemByIndex(((28 % 11) + (1*1)) % 11));
	}
	
	@Test
	public void addToQuadProbeHashTableWithTwoCollisions(){
		quadProbeHashTable = new QuadProbeHashTable(11, badHashFunctor);
		quadProbeHashTable.add("string");
		quadProbeHashTable.add("strong");
		quadProbeHashTable.add("strangle");
		assertEquals("strangle", quadProbeHashTable.getItemByIndex(((28 % 11) + 2*2) % 11));
	}
	
	@Test
	public void addToQuadProbeHashTableWithThreeCollisions(){
		quadProbeHashTable = new QuadProbeHashTable(11, badHashFunctor);
		quadProbeHashTable.add("string");
		quadProbeHashTable.add("strong");
		quadProbeHashTable.add("strangle");
		quadProbeHashTable.add("sanguine");
		assertEquals("sanguine", quadProbeHashTable.getItemByIndex(((28 % 11) + 3*3) % 11));
	}
	
	@Test
	public void quadProbeAddStringCollectionIsItemContainedGoodHash(){
		
		quadProbeHashTable = new QuadProbeHashTable(11, goodHashFunctor);
		quadProbeHashTable.addAll(stringCollection);
		quadProbeHashTable.add("randomWord");
		
		assertEquals(true, quadProbeHashTable.contains("randomWord"));
		assertEquals(true, quadProbeHashTable.contains("radiantly"));
		assertEquals(true, quadProbeHashTable.containsAll(stringCollection));
		assertEquals((stringCollection.size())+1, quadProbeHashTable.size());
	}
	
	@Test
	public void quadProbeAddStringCollectionIsItemContainedMediocreHash(){
		
		quadProbeHashTable = new QuadProbeHashTable(11, mediocreHashFunctor);
		quadProbeHashTable.addAll(stringCollection);
		quadProbeHashTable.add("randomWord");
		
		assertEquals(true, quadProbeHashTable.contains("randomWord"));
		assertEquals(true, quadProbeHashTable.contains("radiantly"));
		assertEquals(true, quadProbeHashTable.containsAll(stringCollection));
		assertEquals((stringCollection.size())+1, quadProbeHashTable.size());
	}
	
	@Test
	public void quadProbeAddStringCollectionIsItemContainedBadHash(){
		
		quadProbeHashTable = new QuadProbeHashTable(11, badHashFunctor);
		quadProbeHashTable.addAll(stringCollection);
		quadProbeHashTable.add("randomWord");
		
		assertEquals(true, quadProbeHashTable.contains("randomWord"));
		assertEquals(true, quadProbeHashTable.contains("radiantly"));
		assertEquals(true, quadProbeHashTable.containsAll(stringCollection));
		assertEquals((stringCollection.size())+1, quadProbeHashTable.size());
	}
	
	@Test
	public void chainingHashAddFourItemsGoodHash(){
		chainingHashTable = new ChainingHashTable(11, goodHashFunctor);
		
		chainingHashTable.add("this");
		chainingHashTable.add("is");
		chainingHashTable.add("a");
		chainingHashTable.add("word");
		
		assertEquals(true, chainingHashTable.contains("this"));
		assertEquals(true, chainingHashTable.contains("is"));
		assertEquals(true, chainingHashTable.contains("a"));
		assertEquals(true, chainingHashTable.contains("word"));
		assertEquals(4, chainingHashTable.size());
		
	}
	
	@Test
	public void chainingHashAddCollectionContainAllGoodHash(){
		chainingHashTable = new ChainingHashTable(11, goodHashFunctor);
		
		chainingHashTable.addAll(stringCollection);
		

		assertEquals(stringCollection.size(), chainingHashTable.size());
		assertEquals(true, chainingHashTable.contains("radiantly"));
		assertEquals(true, chainingHashTable.containsAll(stringCollection));
		
	}
	
	@Test
	public void chainingHashAddFourItemsBadHash(){
		chainingHashTable = new ChainingHashTable(11, badHashFunctor);
		
		chainingHashTable.add("this");
		chainingHashTable.add("is");
		chainingHashTable.add("a");
		chainingHashTable.add("word");
		
		assertEquals(true, chainingHashTable.contains("this"));
		assertEquals(true, chainingHashTable.contains("is"));
		assertEquals(true, chainingHashTable.contains("a"));
		assertEquals(true, chainingHashTable.contains("word"));
		assertEquals(4, chainingHashTable.size());
		
	}
	
	@Test
	public void chainingHashAddCollectionContainAllBadHash(){
		chainingHashTable = new ChainingHashTable(11, badHashFunctor);
		
		chainingHashTable.addAll(stringCollection);
		

		assertEquals(stringCollection.size(), chainingHashTable.size());
		assertEquals(true, chainingHashTable.contains("radiantly"));
		assertEquals(true, chainingHashTable.containsAll(stringCollection));
		
	}
	
	@Test
	public void chainingHashAddFourItemsMediocreHash(){
		chainingHashTable = new ChainingHashTable(11, mediocreHashFunctor);
		
		chainingHashTable.add("this");
		chainingHashTable.add("is");
		chainingHashTable.add("a");
		chainingHashTable.add("word");
		
		assertEquals(true, chainingHashTable.contains("this"));
		assertEquals(true, chainingHashTable.contains("is"));
		assertEquals(true, chainingHashTable.contains("a"));
		assertEquals(true, chainingHashTable.contains("word"));
		assertEquals(4, chainingHashTable.size());
		
	}
	
	@Test
	public void chainingHashAddCollectionContainAllMedicoreHash(){
		chainingHashTable = new ChainingHashTable(11, mediocreHashFunctor);
		chainingHashTable.addAll(stringCollection);
		
		assertEquals(stringCollection.size(), chainingHashTable.size());
		assertEquals(true, chainingHashTable.contains("radiantly"));
		assertEquals(true, chainingHashTable.containsAll(stringCollection));
		
	}
}
