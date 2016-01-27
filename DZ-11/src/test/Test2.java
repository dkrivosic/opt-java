package test;

import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Test2 {

	public static void main(String[] args) {
		Runnable job = new Runnable() {
			
			@Override
			public void run() {
				IRNG rng = RNG.getRNG();
				
				for(int i = 0; i < 20; i++) {
					System.out.println(rng.nextInt(-5, 5));
				}
				
			}
		};
		
		EVOThread thread = new EVOThread(job);
		thread.start();
	}
}
