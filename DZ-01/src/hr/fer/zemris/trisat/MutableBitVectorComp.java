package hr.fer.zemris.trisat;

import java.util.Comparator;

public class MutableBitVectorComp implements Comparator<MutableBitVector> {
	private SATFormulaStats stats;
	
	public MutableBitVectorComp(SATFormulaStats stats) {
		this.stats = stats;
	}

	@Override
	public int compare(MutableBitVector o1, MutableBitVector o2) {
		stats.setAssignment(o1, false);
		double first = stats.getPercentageBonus();
		stats.setAssignment(o2, false);
		double second = stats.getPercentageBonus();
		int ret = 0;
		if(first - second > 0) {
			ret = -1;
		} else if(first - second < 0) {
			ret = 1;
		}
		return ret;
	}

}
