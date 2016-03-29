package assignment10;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HashTablesTest {

	BadHashFunctor badHashFunctor;
	MediocreHashFunctor mediocreHashFunctor;
	GoodHashFunctor goodHashFunctor;
	QuadProbeHashTable quadProbeHashTable;
	ChainingHashTable chainingHashTable;
	
	@Before
	public void setUp() throws Exception {
		badHashFunctor = new BadHashFunctor();
		mediocreHashFunctor = new MediocreHashFunctor();
		goodHashFunctor = new GoodHashFunctor();
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
		assertEquals(6855, goodHashFunctor.hash("string"));
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

}
