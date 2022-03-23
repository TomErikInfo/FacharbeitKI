package main.java.de.erik;

import java.util.Random;

public class Schicht {
	Neuron[] neuronen;
	public Schicht(int anzahlEingang,int anzahlNeuronen, Random r) {
		neuronen = new Neuron[anzahlNeuronen];
		for(int i = 0; i<neuronen.length; i++) {
			neuronen[i] = new Neuron(anzahlEingang, r);
		}
	}
	public double[] klassifiziere(double[] input) {

		double[] ergebnis = new double[neuronen.length];
		for(int i = 0; i<neuronen.length; i++) {
			ergebnis[i] =  neuronen[i].wertung(input);
		}

		return ergebnis ;
	}
	public int length()  {
		return neuronen.length;

	}
	public Neuron[] getNeurons() {
		return neuronen;
	}
	public void updateSchicht(double[] delta, double[] x) {
		for(int i = 0; i< neuronen.length;i++)	{
			neuronen[i].updateWeight(delta[i], x);
		}
	}
}


