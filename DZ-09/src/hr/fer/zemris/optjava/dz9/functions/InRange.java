package hr.fer.zemris.optjava.dz9.functions;

public class InRange implements IHardConstraint {
	public double[] xmin;
	public double[] xmax;

	public InRange(double[] xmin, double[] xmax) {
		this.xmin = xmin;
		this.xmax = xmax;
	}

	@Override
	public boolean satisfied(double[] solution) {
		int n = solution.length;
		for (int i = 0; i < n; i++) {
			if (solution[i] < xmin[i] || solution[i] > xmax[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void correctSolution(double[] solution) {
		int n = solution.length;
		for (int i = 0; i < n; i++) {
			if (solution[i] < xmin[i]) {
				solution[i] = xmin[i];
			}
			if (solution[i] > xmax[i]) {
				solution[i] = xmax[i];
			}
		}

	}

}
