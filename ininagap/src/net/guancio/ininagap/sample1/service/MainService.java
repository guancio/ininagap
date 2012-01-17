package net.guancio.ininagap.sample1.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainService {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		Remote stub = UnicastRemoteObject.exportObject(new ServiceImpl(), 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("//localhost/service", stub);
        System.out.println("ComputeEngine bound");
    }

}
