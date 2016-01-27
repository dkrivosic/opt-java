package hr.fer.zemris.optjava.dz11;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.ga.algorithm.ParallelChildrenCreationGA;
import hr.fer.zemris.optjava.ga.generic.GASolution;
import hr.fer.zemris.optjava.ga.impl.Evaluator;
import hr.fer.zemris.optjava.rng.RNG;

public class Runner2 {

	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length != 7) {
			System.err.println("7 arguments expected:\nPath to original PNG image\n"
					+ "Number of rectangles used for image approximation\nPopulation size\n"
					+ "Max generations\nMinimum satisfiable fitness\nPath to text file with parameters\n"
					+ "Path to generated image");
			System.exit(0);
		}

		GrayScaleImage originalImage = GrayScaleImage.load(new File(args[0]));
		int rectanglesCount = Integer.parseInt(args[1]);
		int populationSize = Integer.parseInt(args[2]);
		int maxGenerations = Integer.parseInt(args[3]);
		double minFitness = Double.parseDouble(args[4]);

		Properties parameters = new Properties();
		ClassLoader classLoader = RNG.class.getClassLoader();
		InputStream inStream = classLoader.getResourceAsStream(args[5]);
		parameters.load(inStream);

		int colChange = Integer.parseInt(parameters.getProperty("color-change"));
		int posChange = Integer.parseInt(parameters.getProperty("position-change"));
		int taskSize = Integer.parseInt(parameters.getProperty("task-size"));
		double pMutation = Double.parseDouble(parameters.getProperty("p-mutation"));

		File targetImage = new File(args[6]);

		ParallelChildrenCreationGA algorithm = new ParallelChildrenCreationGA(populationSize, rectanglesCount, originalImage,
				maxGenerations, taskSize, colChange, posChange, minFitness, pMutation);

		
		GASolution<int[]> solution = algorithm.run();

		GrayScaleImage image = new GrayScaleImage(originalImage.getWidth(), originalImage.getHeight());
		Evaluator evaluator = new Evaluator(originalImage);
		image = evaluator.draw(solution, image);
		image.save(targetImage);
	}

}
