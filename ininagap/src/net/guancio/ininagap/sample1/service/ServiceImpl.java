package net.guancio.ininagap.sample1.service;

import java.rmi.RemoteException;

import net.guancio.ininagap.sample1.common.Service;

public class ServiceImpl implements Service {
	@Override
	public int doSomething(int param) throws RemoteException {
		if (param < 0) {
			System.out.println("Service quit...");
			System.exit(0);
		}
		return param+1;
	}

}
