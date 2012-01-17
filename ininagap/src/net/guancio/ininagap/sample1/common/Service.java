package net.guancio.ininagap.sample1.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Service extends Remote {
	int doSomething(int param) throws RemoteException;
}
