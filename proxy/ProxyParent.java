package gtv.proxy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ProxyParent {

	private final String[] allowedExtensions = new String[] { "avi", "mov" };
	private int total, running;
	private boolean shown = false;
	
	private String source, dest, cluster, setting;
	private ProxyFrame pf;
	
	public ProxyParent(String source, String dest, String cluster, String setting, ProxyFrame pf) {
		this.source = source;
		this.dest = dest;
		this.cluster = cluster;
		this.setting = setting;
		this.pf = pf;
		
		start();
	}
	
	private void start() {		
		String command = "/Applications/Compressor.app/Contents/MacOS/Compressor -clustername %CLUSTER% -jobpath %ORIGIN% " +
				"-settingpath \"" + setting + "\" -destinationpath %DEST%";
		command = command.replaceFirst("%CLUSTER%", "\"" + cluster + "\"");
		
		File directory = new File(source);
		if(!directory.isDirectory()) {
			JOptionPane.showMessageDialog(pf, "Source folder is not a directory.", "Directory error", JOptionPane.ERROR_MESSAGE);
		}
		
		File proxyFile = new File(dest);
		
		if( ! proxyFile.isDirectory()) {
			if( ! proxyFile.mkdirs()) {
				JOptionPane.showMessageDialog(pf, "Error creating proxy directories.", "Directory error", JOptionPane.ERROR_MESSAGE);
			}
		}

		List<File> files = new ArrayList<File>();
		
		for(File f : directory.listFiles()) {
			String extension = f.getName().substring(f.getName().lastIndexOf('.') + 1);
			if(!f.isHidden() && !f.isDirectory() && isAllowedExtension(extension)) {
				files.add(f);
			}
		}
		
		total = files.size();
		
		for(File f : files) {
			new ProxyChild(command.replaceFirst("%ORIGIN%", "\"" + f.getAbsolutePath() + "\"")
					.replaceFirst("%DEST%", "\"" + proxyFile.getAbsolutePath() + "/" + f.getName() + "\""),
					this)
			.run();
		}
	}
	
	private boolean isAllowedExtension(String ext) {
		for(String s : allowedExtensions) {
			if(s.equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}

	synchronized void sent() {
		synchronized(this) {
			running++;
			int sum = (int) Math.round(((double) running / total) * 100);
			pf.setProgress(sum);
			if(sum == 100 && !shown) {
				JOptionPane.showMessageDialog(pf, "Sent to Compressor.", "Sent to Compressor", JOptionPane.INFORMATION_MESSAGE);
				shown = true;
			}
		}
	}
	
}
