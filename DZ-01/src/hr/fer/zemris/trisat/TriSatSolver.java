package hr.fer.zemris.trisat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.parser.Parser;
import hr.fer.zemris.trisat.algorithms.Algorithm;
import hr.fer.zemris.trisat.algorithms.BruteForce;
import hr.fer.zemris.trisat.algorithms.HillClimbing;
import hr.fer.zemris.trisat.algorithms.StatAlgorithm;

public class TriSatSolver {

	public static void main(String[] args) throws IOException {
		if(args.length != 2) {
			System.err.println("2 arguments expected");
			System.exit(0);
		}
		
		int choice = Integer.parseInt(args[0]);
		String path = args[1];
		Parser parser = new Parser();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
		
		String line = reader.readLine();
		while(!line.startsWith("%")) {
			parser.parseLine(line);
			line = reader.readLine();
		}
		
		reader.close();
		SATFormula  formula = parser.generateFormula();
		Algorithm algorithm = null;
		
		switch(choice) {
		case 1:
			algorithm = new BruteForce(formula);
			break;
		case 2:
			algorithm = new HillClimbing(formula);
			break;
		case 3:
			algorithm = new StatAlgorithm(formula);
			break;
		default:
			System.out.println("Choose algorithm 1-3");
			System.exit(0);
			break;
				
		}
		algorithm.run();
		
		
	}

}