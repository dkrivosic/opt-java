package hr.fer.zemris.optjava.dz9.moop;

public class SharingFunction {
	private double sigmaShare;
	private double alpha;

	public SharingFunction(double sigmaShare, double alpha) {
		this.sigmaShare = sigmaShare;
		this.alpha = alpha;
	}

	/**
	 * Returns sharing function value for two solutions.
	 */
	public double value(double[] vector1, double[] vector2, double[] xmax, double[] xmin) {
		double d = distance(vector1, vector2, xmin, xmax);
		if (d > sigmaShare)
			return 0;
		else
			return 1 - Math.pow(d / sigmaShare, alpha);
	}

	/**
	 * Calculates the distance between two solutions.
	 */
	private double distance(double[] vector1, double[] vector2, double[] xmin, double[] xmax) {
		int n = vector1.length;
		double d = 0;
		for (int i = 0; i < n; i++) {
			if (xmin[i] != xmax[i]) {
				d += Math.pow((vector1[i] - vector2[i]) / (xmax[i] - xmin[i]), 2);
			}
		}
		return Math.sqrt(d);
	}

}
