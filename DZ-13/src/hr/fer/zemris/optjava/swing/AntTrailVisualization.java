package hr.fer.zemris.optjava.swing;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.event.ActionEvent;

import hr.fer.zemris.optjava.gp.actions.ActionType;
import hr.fer.zemris.optjava.gp.algorithm.AntEvaluator;
import hr.fer.zemris.optjava.gp.ant.Ant;
import hr.fer.zemris.optjava.gp.ant.Direction;
import hr.fer.zemris.optjava.gp.helpers.World;
import hr.fer.zemris.optjava.observers.Observer;

import java.awt.BorderLayout;

public class AntTrailVisualization implements Observer {

	public JFrame frame;
	private int height;
	private int width;
	private JButton[][] map;
	private Color backgroundColor = Color.LIGHT_GRAY;
	private Color antColor = Color.BLACK;
	private int currentX;
	private int currentY;
	private Direction facing;
	private Queue<ActionType> actions;
	private int score = 0;
	private int movesCount = 0;

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public AntTrailVisualization(String filename, Ant ant, AntEvaluator eval) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		String line = reader.readLine();
		String[] tmp = line.split("x");
		this.width = Integer.parseInt(tmp[0]);
		this.height = Integer.parseInt(tmp[1]);
		this.map = new JButton[height][width];
		for (int i = 0; i < height; i++) {
			line = reader.readLine();
			tmp = line.split("");
			for (int j = 0; j < width; j++) {
				map[i][j] = new JButton();
				map[i][j].setEnabled(false);
				if (tmp[j].equals("1")) {
					map[i][j].setBackground(Color.GREEN);
				} else {
					map[i][j].setBackground(backgroundColor);
				}
			}
		}
		reader.close();

		actions = new LinkedList<>();
		ant.reset();
		ant.attach(this);
		eval.evaluate(ant);

		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 250, 205));
		frame.getContentPane().setLayout(new BorderLayout());

		JPanel gl = new JPanel(new GridLayout(height, width));
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				gl.add(map[i][j]);
			}
		}
		this.currentX = 0;
		this.currentY = 0;
		facing = Direction.RIGHT;
		map[currentY][currentX].setBackground(antColor);
		frame.getContentPane().add(gl, BorderLayout.CENTER);

		JPanel buttonsPanel = new JPanel(new FlowLayout());
		JButton nextStep = new JButton("Next step");
		JTextArea scoreboard = new JTextArea();
		scoreboard.setFont(new Font("Verdana", Font.BOLD, 13));
		scoreboard.setText("Score: 0");
		JTextArea moves = new JTextArea();
		moves.setFont(new Font("Verdana", Font.BOLD, 13));
		moves.setText("Moves: 0");
		buttonsPanel.add(nextStep);
		buttonsPanel.add(scoreboard);
		buttonsPanel.add(moves);
		frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		nextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (actions.isEmpty()) {
					return;
				}

				ActionType action = actions.poll();
				while (action != ActionType.Move) {
					if (action == null) {
						return;
					}
					if (action == ActionType.Left) {
						turnLeft();
					} else if (action == ActionType.Right) {
						turnRight();
					}
					action = actions.poll();
				}
				move();
				scoreboard.setText("Score: " + score);
				moves.setText("Moves: " + movesCount);
			}
		});

	}

	@Override
	public void update(ActionType type) {
		actions.add(type);

	}

	/**
	 * Ant turns to the left by 90 degrees.
	 */
	public void turnLeft() {
		movesCount++;
		if (facing == Direction.RIGHT) {
			facing = Direction.UP;
		} else if (facing == Direction.UP) {
			facing = Direction.LEFT;
		} else if (facing == Direction.LEFT) {
			facing = Direction.DOWN;
		} else {
			facing = Direction.RIGHT;
		}
	}

	/**
	 * Ant turns to the right by 90 degrees.
	 */
	public void turnRight() {
		movesCount++;
		if (facing == Direction.RIGHT) {
			facing = Direction.DOWN;
		} else if (facing == Direction.UP) {
			facing = Direction.RIGHT;
		} else if (facing == Direction.LEFT) {
			facing = Direction.UP;
		} else {
			facing = Direction.LEFT;
		}
	}

	/**
	 * Ant move one space in direction it is facing.
	 */
	public void move() {
		movesCount++;
		map[currentY][currentX].setBackground(AntTrailVisualization.this.backgroundColor);
		if (facing == Direction.LEFT) {
			currentX--;
			if (currentX < 0) {
				currentX += World.getWidth();
			}
		} else if (facing == Direction.RIGHT) {
			currentX = (currentX + 1) % World.getWidth();
		} else if (facing == Direction.UP) {
			currentY--;
			if (currentY < 0) {
				currentY += World.getHeight();
			}
		} else {
			currentY = (currentY + 1) % World.getHeight();
		}
		if (map[currentY][currentX].getBackground() != AntTrailVisualization.this.backgroundColor) {
			score++;
		}
		map[currentY][currentX].setBackground(AntTrailVisualization.this.antColor);

	}

}
