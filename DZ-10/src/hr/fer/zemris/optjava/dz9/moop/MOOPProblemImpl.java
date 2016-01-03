package hr.fer.zemris.optjava.dz9.moop;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz9.functions.IFunction;

public class MOOPProblemImpl implements MOOPProblem {

	private List<IFunction> objectiveFunctions;
	private int solutionSize;
	
	public MOOPProblemImpl(int solutionSize, IFunction ...funcs) {
		objectiveFunctions = new ArrayList<>();
		for(IFunction f: funcs) {
			objectiveFunctions.add(f);
		}
		this.solutionSize = solutionSize;
	}

	@Override
	public int getNumberOfObjectives() {
		return objectiveFunctions.size();
	}

	@Override
	public void evaluateSolution(double[] solution, double[] objective) {
		int n = getNumberOfObjectives();
		for(int i = 0; i < n; i++) {
			objective[i] = objectiveFunctions.get(i).valueAt(solution);
		}
	}

	@Override
	public double[] evaluateSolution(double[] solution) {
		double[] obj = new double[getNumberOfObjectives()];
		evaluateSolution(solution, obj);
		return obj;
	}

	@Override
	public int getSolutionSize() {
		return solutionSize;
	}
	
	
}
