package libapp;

public class Dane {
	
	protected static double kat, predkosc, zasieg;
	
	protected static double calculate() {
		if(zasieg<=0){
			System.err.println("Cos poszlo nie tak przy obliczaniu zasiegu");
			System.exit(0);
		}
		return zasieg;
	}
}