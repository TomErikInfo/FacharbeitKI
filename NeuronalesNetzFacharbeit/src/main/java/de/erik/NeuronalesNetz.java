package main.java.de.erik;

import java.util.List;
import java.util.Random;

public class NeuronalesNetz {

	private Schicht hiddenSchicht;
	private Schicht outputSchicht;

	private static final int SEED = 3;

	public NeuronalesNetz(int inputNeuronen, int hiddenNeuronen, int outputNeuronen) {
		Random r = new Random(SEED);

		hiddenSchicht = new Schicht(inputNeuronen, hiddenNeuronen, r);
		outputSchicht = new Schicht(hiddenNeuronen,outputNeuronen, r);
	}
	public double[] klassifiziere(double[] input) {
		return outputSchicht.klassifiziere(hiddenSchicht.klassifiziere(input)); // die Schichten werden verknüpft
	}
	public void train(int Anzahlepoche, float lernrate,List <double[]>traindata,List <double[]>label) {
		for(int epoch=0; epoch<Anzahlepoche; epoch++)
		{	
			double[] deltaoutput = new double[outputSchicht.length()];
			double[] deltahidden = new double[hiddenSchicht.length()];
			for(int i=0; i<traindata.size(); i++) {
				double[] t = traindata.get(i);
				double[] l = label.get(i);
				double[] y = klassifiziere(t);
				computeDeltaOut(deltaoutput, y, l);
				computeDeltaHidden(deltahidden,hiddenSchicht.klassifiziere(t),deltaoutput);
				outputSchicht.updateSchicht(deltaoutput,hiddenSchicht.klassifiziere(t) );
				hiddenSchicht.updateSchicht(deltahidden, t);
			}
			testen(traindata, label);
		}
	}
	public void testen(List<double[]> data, List<double[]> label) {
		int richtig = 0;
		for(int i = 0; i<data.size();i++) {
//			System.out.println("Antwort Netz:"+klassifiziereMax(data.get(i)));
//			System.out.println("Richtige Antwort:"+Arrays.toString(label.get(i)));
			if(((int) label.get(i)[klassifiziereMax(data.get(i))]) == 1) {
				richtig++;
			}
		}
		System.out.println("Zu "+(100-(((float)richtig/(float)data.size())*100f))+"% falsch");
	}	

	private void computeDeltaHidden(double[] deltahidden, double[] y, double[] deltaoutput) {
		for(int i1=0; i1<deltahidden.length;i1++) {
			double w = 0;
			for(int i2=0; i2<deltaoutput.length;i2++) {
				w+= outputSchicht.getNeurons()[i2].getWeights()[i1]*deltaoutput[i2];
			}
			deltahidden[i1] = y[i1]*(1-y[i1])*w;

		}

	}
	private void computeDeltaOut(double[] deltaoutput, double[] y, double[] l) {
		for(int i=0; i<deltaoutput.length;i++) {
			deltaoutput[i] = (l[i]-y[i])*y[i]*(1-y[i]);
		}
	}
	public int klassifiziereMax(double[] input) {
		double[] n = klassifiziere(input);
		int resultIndex = -1;
		double result = -Double.MAX_VALUE;
		for(int i = 0; i<n.length;i++) {
			if(n[i]>=result) {
				resultIndex = i;
				result = n[i];
			}
		}
		return resultIndex;
	}
}



