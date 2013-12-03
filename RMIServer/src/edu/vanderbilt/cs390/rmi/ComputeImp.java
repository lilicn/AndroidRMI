package edu.vanderbilt.cs390.rmi;

/**
 * implement for computer interface and it will be called by remote client
 * 
 * @author Li
 * 
 */
public class ComputeImp implements Compute {

	public ComputeImp() {
	}

	public String getPI(int index) {
		return Pi.computePi(index).toString();
	}

}
