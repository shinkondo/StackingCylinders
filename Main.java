//package StackingCylinders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Main {
	List<Coordinate> currentRow;
	
	public Main(double[] numbers) {
		currentRow = new LinkedList<Coordinate>();
		Arrays.sort(numbers);
		for (double xCoordinate: numbers) {
			currentRow.add(new Coordinate(xCoordinate, 1));
		}
	}
	
	private Coordinate getNextCylinder(Coordinate f, Coordinate s) {
		double p, q;
		double r = 2;
		if (Math.abs(f.y - s.y) < Math.pow(10, -12)) {
			p = (f.x + s.x) / 2;
			q = f.y + Math.sqrt(Math.pow(r, 2) - Math.pow(f.x - p, 2));
		} else {
			// q = ap + b where (p, q) is the new coordinate.
			double a = (s.x - f.x) / (f.y - s.y);
			double b = (Math.pow(f.x, 2) - Math.pow(s.x, 2) + Math.pow(f.y, 2) - Math
					.pow(s.y, 2)) / 2 / (f.y - s.y);

			double j = 1 + Math.pow(a, 2);
			double k =  2 * (a * b - a * f.y - f.x);
			double l = Math.pow(f.x, 2) + Math.pow(f.y - b, 2) - Math.pow(r, 2);
			//choose bigger q
			if (a >= 0) {
				p = pformula(j,k,l);
			}
			else {
				p = mformula(j,k,l);
			}
			q = a * p + b;
		}
		//System.out.println(p + "|" + q);
		return new Coordinate(p, q);
	}

	public void execute() {
		while (currentRow.size() != 1) {
			List<Coordinate> nextRow = new LinkedList<Coordinate>();
			for (int i = 0; i < currentRow.size() - 1; i++) {
				nextRow.add(getNextCylinder(currentRow.get(i),
						currentRow.get(i + 1)));
			}
			currentRow = nextRow;
		}
		Coordinate topmost = currentRow.get(0);
		System.out.printf("%.4f %.4f\n", topmost.x, topmost.y);
		
	}
	
	private double roundAt(double x, int digit) {
		double constant = Math.pow(10, digit);
		return Math.round(x * constant) / constant;
	}
	
	/*
	 * return greater one.
	 */
	private double pformula(double a, double b, double c) {
		return (-1 * b + Math.sqrt(Math.pow(b, 2) - 4 * a * c))/(2 * a);
	}
	
	/*
	 * return smaller one.
	 */
	private double mformula(double a, double b, double c) {
		return (-1 * b - Math.sqrt(Math.pow(b, 2) - 4 * a * c))/(2 * a);
	}
	
	public static void readInput() {
		try {

			//BufferedReader bf = new BufferedReader(new FileReader("./src/StackingCylinders/input.txt"));
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			String input;
			while ((input = bf.readLine()) != null) {
				String [] words = input.split(" ");
				int numberOfInput = Integer.parseInt(words[0]);
				if (numberOfInput == 0) {
					break;
				}
				double[] numbers = new double[numberOfInput];
				for (int i = 1; i < words.length; i++) {
					numbers[i - 1] = Double.parseDouble(words[i]);
				}
				Main z = new Main(numbers);
				z.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		Main.readInput();
	}


}

class Coordinate {
	public double x, y;

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
}