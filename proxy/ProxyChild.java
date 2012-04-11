package gtv.proxy;

import java.io.IOException;

public class ProxyChild extends Thread {
	
	private String command;
	private ProxyParent pp;
	
	public ProxyChild(String command, ProxyParent pp) {
		this.command = command;
		this.pp = pp;
	}
	
	public void run() {
		try {
			new ProcessBuilder("/bin/sh", "-c", command).start();
			pp.sent();
		}
		catch (IOException e) {
			System.out.println("Runtime exec fail in " + this);
			e.printStackTrace();
		}
	}
	
}
