package main.java.de.erik;

import java.util.Random;

public class Neuron {	
	double[] weight;

	static final double BETA=0.1;
	/**
	 *
	 * @param numInputs
	 */
	public Neuron(int numInputs, Random randomGenerator) {		
		weight = new double[numInputs]; 
		for(int i=0; i<numInputs; i++) {
			weight[i] = 1 * (randomGenerator.nextDouble()-0.5);
		}
	}

	public double wertung(double[] input) {
		double ergebnis =0;
		for(int i=0; i<input.length; i++) {
			ergebnis+= input[i]* weight[i];
		}

		return f(ergebnis);
	}
	private double f(double x) {
		return  1d/(1d+Math.exp(-x));
		//return 1/(1+Math.pow(2.7182818246, x));
	}
	public double[] getWeights() {
		return weight;
	}
	public void updateWeight(double delta,double[] x) {
		for(int i =0; i< weight.length;i++) {
			weight[i] = weight[i] + BETA*delta*x[i];
		}
	}
}


