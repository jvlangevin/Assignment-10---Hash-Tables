package assignment10;

public class TestQuadProbe {

	final private static int INITIAL_SIZE = 11;
	
	public static void main(String[] args){
		
		
		
		HashFunctor functor = new GoodHashFunctor();
		QuadProbeHashTable test = new QuadProbeHashTable(INITIAL_SIZE, functor);
		
		test.add("test1");
		test.add("test2");
		test.add("test3");
		test.add("test4");
		test.add("test5");
		test.add("test6");
		test.add("test7");
		if(test.contains("test7"))
		{
			System.out.println("Item was added and is contained.");
			System.out.println("The number of times this was rehashed: " + test.timesRehashed());
			System.out.println("The number of collisions: " + test.collisionCount());
			System.out.println("Size of table is: " + test.size());
			
		}
		else{
			System.out.println("Something is wrong with add/contains.");
		}
		
	}
}
