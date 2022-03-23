package main.java.de.erik;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class SteuerungsTestklasee {
	public static void main(String[] args) {		
		try {
			List<double[]> trainingData = readData("C:\\Users\\Erik\\Desktop\\FacharbeitKI\\NeuronalesNetzFacharbeit\\src\\main\\resources\\train.txt");
			List<double[]> trainingLabels = readLabels("C:\\Users\\Erik\\Desktop\\FacharbeitKI\\NeuronalesNetzFacharbeit\\src\\main\\resources\\train_label.txt");
			// for static access, uses the class name directly)
			List<double[]> trainingDataKlein = trainingData.subList(0, 40000);
			List<double[]> trainingLabelsKlein = trainingLabels.subList(0, 60000);
			NeuronalesNetz n = new NeuronalesNetz(784, 40, 10) ;
			//			for(double[] t : trainingData) {
			//				double[] klassen = n.klassifiziere(t);
			//				System.out.println(Arrays.toString(klassen));
			//			}
			n.train(10, 0.1f , trainingDataKlein, trainingLabelsKlein);
			System.out.println("Ab jetzt mit testdaten");
			List<double[]> testData = readData("C:\\Users\\Erik\\Desktop\\FacharbeitKI\\NeuronalesNetzFacharbeit\\src\\main\\resources\\test.txt");	
			List<double[]> testLabels = readLabels("C:\\Users\\Erik\\Desktop\\FacharbeitKI\\NeuronalesNetzFacharbeit\\src\\main\\resources\\test_labels.txt");
			long startzeit = System.currentTimeMillis();
			n.testen(testData, testLabels);
			long endzeit = System.currentTimeMillis();
			System.out.println("Laufzeit:"+(endzeit-startzeit));

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

private static List<double[]> readLabels(String filename) throws Exception {
	List<String> labels = FileUtils.readLines(new File(filename), StandardCharsets.US_ASCII);
	List<double[]> result = new ArrayList<>();
	for(String line : labels) {
		double[] vector = new double[10];
		for(int i=0; i<vector.length; i++) {
			if(Integer.parseInt(line)==i) {
				vector[i]=1.0;
			} else {
				vector[i]=0;
			}
		}
		result.add(vector);
	}
	return result;
}

private static List<double[]> readData(String filename) throws IOException {		
	List<String> training = FileUtils.readLines(new File(filename), StandardCharsets.US_ASCII);
	List<double[]> result = new ArrayList<>();
	for(String line : training) {
		String[] splitLine = line.split(",");
		double[] vector = new double[splitLine.length];
		for(int i=0; i<splitLine.length; i++) {
			vector[i] = Double.parseDouble(splitLine[i])>0?1.0 : 0.0;
		}
		result.add(vector);
	}
	return result;
}

}
