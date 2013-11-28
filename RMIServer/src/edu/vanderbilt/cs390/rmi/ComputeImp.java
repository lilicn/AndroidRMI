package edu.vanderbilt.cs390.rmi;

public class ComputeImp implements Compute {

	public ComputeImp() {
	}

	public String getPI(int index) {
		return Pi.computePi(index).toString();
	}

}
