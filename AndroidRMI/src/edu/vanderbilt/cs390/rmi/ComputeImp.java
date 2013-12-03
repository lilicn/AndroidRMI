package edu.vanderbilt.cs390.rmi;

/**
 * local method to computr pi
 * 
 * @author Li
 * 
 */
public class ComputeImp implements Compute {

	public String getPI(int index) {
		return Pi.computePi(index).toString();
	}

}
