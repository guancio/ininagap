package net.guancio.ininagap;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

import net.guancio.ininagap.exceptions.SandboxException;
import net.guancio.ininagap.sample1.service.MainService;

public class Session {
	Map<Class, Object> services = new HashMap<Class, Object>();
	Process process = null;
	
	public Session() throws RemoteException {
		java.rmi.registry.LocateRegistry.createRegistry(1099);
	}

	public <T> T getService(Class<T> class1) {
		if (services.containsKey(class1))
			return (T) services.get(class1);
		Object proxy = Proxy.newProxyInstance(
				class1.getClassLoader(),
				new Class[]{class1},
				new Invoker());
		services.put(class1, proxy);
		return (T) proxy;
	}
	
	protected class Invoker implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			int attempt=1;
			int max = 3;
			while (attempt <= max) {
				System.out.println("Trying method " + method.getName() + "with arg:" + args + " attempt:" + attempt);
				
				attempt++;
				
				Registry registry = LocateRegistry.getRegistry();

				Object srv = null;
				try {
					srv = registry.lookup("//localhost/service");
				} catch (AccessException e2) {
					System.out.println("  Failed to lookup the service: AccessException");
				} catch (RemoteException e2) {
					System.out.println("  Failed to lookup the service: RemoteException");
				} catch (NotBoundException e2) {
					System.out.println("  Failed to lookup the service: NotBoundException");
				}
				if (srv == null) {
					System.out.println(" Starting the service");
					try {
						this.startService();
					} catch (IOException e) {
						System.out.println("    Failed to start the service");
					} catch (InterruptedException e) {
						System.out.println("    Failed to start the service");
					}
					continue;
				}
				try {
					return method.invoke(srv, args);
				} catch (Exception e2) {
					System.out.println("  Failed to lookup the service: NotBoundException");
				}
				System.out.println(" Starting the service");
				try {
					this.startService();
				} catch (IOException e) {
					System.out.println("    Failed to start the service");
				} catch (InterruptedException e) {
					System.out.println("    Failed to start the service");
				}
				continue;
			}
			throw new SandboxException();
		}

		private void startService() throws IOException, InterruptedException {
			process = null;
			ProcessBuilder pb = new ProcessBuilder("java", "-cp", "/media/data/Sources/opens/ininagap/ininagap/bin", MainService.class.getName());
			Map<String, String> env = pb.environment();
			env.put("VAR1", "myValue");
			env.remove("OTHERVAR");
			env.put("VAR2", env.get("VAR1") + "suffix");
			process  = pb.start();
			
			Thread.sleep(5000);
		}
	}

	public void close() {
		if (process == null)
			return;
		
		process.destroy();
	}

}
