package client;

import j3d.abstraction.universe.ASharedUniverse;

import java.rmi.Naming;

public class Client {

	public static ASharedUniverse getSharedUniverse(String serverHostName, String serverRMIPort, String sharedWorldName) {
		ASharedUniverse sharedWorld = null;
		try {
			sharedWorld = (ASharedUniverse) Naming.lookup("//"
					+ serverHostName + ":" + serverRMIPort + "/"
					+ sharedWorldName);
		} catch (Exception e) {
			System.out.println("probleme liaison CentralManager");
			e.printStackTrace();
			System.exit(1);
		}
		return sharedWorld;
	}
}
