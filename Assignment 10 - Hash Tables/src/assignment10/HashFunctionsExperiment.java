package assignment10;

import java.util.Random;

public class HashFunctionsExperiment {

	static final int startSize = 500;
	static final int endSize = 10000;
	static final int step = 500;
	static final int initialTableSize = 11;
	static final HashFunctor hashFunctor = new BadHashFunctor();
	
	public static void main(String[] args) {

		System.out.println("N \t Collisions \t Time");
		System.out.println("-------------------------------------------");
		
		for(int N = startSize; N <= endSize; N += step){
			
			long totalTime = 0;
			QuadProbeHashTable qpht = new QuadProbeHashTable(initialTableSize, new BadHashFunctor());
			
			for(int i = 0; i < N; i++){
				
				String randomString = randomString((int)(Math.random() * 10) + 1);
				
				long startTime = System.nanoTime();
				hashFunctor.hash(randomString);
				long endTime = System.nanoTime();
				totalTime += endTime - startTime;
				
				qpht.add(randomString((int)(Math.random() * 10) + 1));
			}
			
			System.out.println(N + "\t " + qpht.collisionCount() + "\t\t " + totalTime);

		}
	}
	
	public static String randomString(int length)
	{
    	String retval = "";
    	Random rand = new Random();
    	for (int i = 0; i < length; i++)
    	{
        	// ASCII values a-z,A-Z are contiguous (52 characters)
        	retval += (char) ('a' + (rand.nextInt(26)));
    	}
    	return retval;
	}

}
