package hr.fer.zemris.optjava.rng.provimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;
import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

public class ThreadLocalRNGProvider implements IRNGProvider {
	private ThreadLocal<IRNG> threadLocal = new ThreadLocal<>(); 
	
	@Override
	public IRNG getRNG() {
		if(threadLocal.get() == null) {
			IRNG rng = new RNGRandomImpl();
			threadLocal.set(rng);
		}
		return threadLocal.get();
	}

}
