package edu.nr.lib.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class AndroidConnection {

	public static final int defaultPort = 1768;
	private static final String defaultIpAddress = "localhost";
	
	
	double distance;
	double turnAngle;
	
	boolean goodToGo = false;

	public void run() {
		Socket clientSocket;
		try {
			clientSocket = new Socket(defaultIpAddress, defaultPort);
			clientSocket.setSoTimeout(100); //We will only wait for 100 ms before timing out
			try {
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				outToServer.writeBytes("check\n");
				String responseSentence = inFromServer.readLine();
				if(responseSentence == null) {
					System.out.println("Had socket timeout exception");
					clientSocket.close();
					distance = 0;
					turnAngle = 0;
					goodToGo = false;
				} else {
					System.out.println("FROM SERVER: " + responseSentence);
					goodToGo = true;
					int x = responseSentence.indexOf(':');
					if (x > 0) {
						String left = responseSentence.substring(0, x);
					    String right = responseSentence.substring(x);
					    try {
					    	distance = Double.valueOf(left);
					    	turnAngle = Double.valueOf(right);
						    System.out.println("Angle: " + turnAngle + " Distance: " + distance);
					    } catch (NumberFormatException e) {
					    	System.err.println("Coudln't parse number from Jetson. Recieved Message: " + responseSentence);
					    }
					}
				}
			} catch (SocketTimeoutException e) {
				System.out.println("Had socket timeout exception");
				clientSocket.close();
				distance = 0;
				turnAngle = 0;
				goodToGo = false;
			} 
		} catch (UnknownHostException e) {
			System.out.println("Unknown host to connect to");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public double getTurnAngle() {
		return turnAngle;
	}

	public double getDistance() {
		return distance;
	}
	
	public boolean goodToGo() {
		return goodToGo;
	}
}