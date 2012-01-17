package net.guancio.ininagap.sample1.client;

import java.rmi.RemoteException;

import net.guancio.ininagap.Session;
import net.guancio.ininagap.sample1.common.Service;

public class MainClient {
	private static Process p = null;

	public static void main(String[] args) throws RemoteException {
		Session session = new Session();
		Service service = session.getService(Service.class);
		
		try {
			System.out.println(service.doSomething(5));
			System.out.println(service.doSomething(5));
			System.out.println(service.doSomething(5));
			System.out.println(service.doSomething(5));
			System.out.println(service.doSomething(5));
		} catch (Exception e) {
			System.out.println("Execution failed");
		}
		try {
			System.out.println(service.doSomething(-1));
		} catch (Exception e) {
			System.out.println("Execution failed");
		}
		try {
			System.out.println(service.doSomething(5));
			System.out.println(service.doSomething(5));
			System.out.println(service.doSomething(5));
			System.out.println(service.doSomething(5));
			System.out.println(service.doSomething(5));
		} catch (Exception e) {
			System.out.println("Execution failed");
		}
		
		session.close();
	}
}
