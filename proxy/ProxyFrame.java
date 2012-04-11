package gtv.proxy;

import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;

public class ProxyFrame extends JFrame {
	
	private static final long serialVersionUID = 1853004747809290421L;
	private JTextField cluster;
	private JProgressBar progressBar;
	private String source, dest;
	
	public ProxyFrame() {
		setTitle("GTV HD Raw to Proxy");
		setSize(536, 137);
		getContentPane().setLayout(null);
		
		JLabel lblCluster = new JLabel("Cluster:");
		lblCluster.setBounds(284, 12, 61, 16);
		getContentPane().add(lblCluster);
		
		cluster = new JTextField();
		cluster.setBounds(345, 6, 166, 28);
		getContentPane().add(cluster);
		cluster.setColumns(10);
		
		JLabel lblSetting = new JLabel("Setting:");
		lblSetting.setBounds(284, 40, 61, 16);
		getContentPane().add(lblSetting);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setBounds(345, 36, 166, 27);
		for(String s : ProxySettings.getNames()) {
			comboBox.addItem(s);
		}
		getContentPane().add(comboBox);
		
		JButton btnSourceFolder = new JButton("Source folder");
		btnSourceFolder.setBounds(6, 7, 117, 29);
		btnSourceFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.setProperty("apple.awt.fileDialogForDirectories", "true");
				FileDialog d = new FileDialog(ProxyFrame.this);
				d.setMode(FileDialog.LOAD);
				d.setVisible(true);
				System.setProperty("apple.awt.fileDialogForDirectories", "false");
				ProxyFrame.this.setSource(d.getDirectory() + d.getFile());
			}
		});
		getContentPane().add(btnSourceFolder);
		
		JButton btnDestFolder = new JButton("Dest folder");
		btnDestFolder.setBounds(135, 7, 117, 29);
		btnDestFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.setProperty("apple.awt.fileDialogForDirectories", "true");
				FileDialog d = new FileDialog(ProxyFrame.this);
				d.setMode(FileDialog.LOAD);
				d.setVisible(true);
				System.setProperty("apple.awt.fileDialogForDirectories", "false");
				ProxyFrame.this.setDest(d.getDirectory() + d.getFile());
			}
		});
		getContentPane().add(btnDestFolder);
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(12, 43, 234, 20);
		getContentPane().add(progressBar);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(6, 75, 117, 29);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				progressBar.setValue(0);
				if(source == null) {
					JOptionPane.showMessageDialog(ProxyFrame.this, "You didn't select a source folder.", "No source folder selected", JOptionPane.ERROR_MESSAGE);
				}
				else if(dest == null) {
					JOptionPane.showMessageDialog(ProxyFrame.this, "You didn't select a destination folder.", "No destination folder selected", JOptionPane.ERROR_MESSAGE);
				}
				else if(cluster.getText() == null || cluster.getText().equals("")) {
					JOptionPane.showMessageDialog(ProxyFrame.this, "You didn't select a cluster.", "No cluster selected", JOptionPane.ERROR_MESSAGE);
				}
				else {
					new ProxyParent(source, dest, cluster.getText(), ProxySettings.getLocationFromName((String) comboBox.getSelectedItem()), ProxyFrame.this);
				}
			}
		});
		getContentPane().add(btnStart);
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(135, 75, 117, 29);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		getContentPane().add(btnClose);
		
		JButton btnDefaultCluster = new JButton("Default cluster");
		btnDefaultCluster.setBounds(394, 75, 117, 29);
		btnDefaultCluster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				cluster.setText("This Computer");
			}
		});
		getContentPane().add(btnDefaultCluster);
		
		JButton btnOpenBatchmon = new JButton("Batch Mon");
		btnOpenBatchmon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					Desktop.getDesktop().open(new File("/Applications/Utilities/Batch Monitor.app"));
				}
				catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(ProxyFrame.this, "Failed to open Batch Monitor.", "Batch Monitor error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnOpenBatchmon.setBounds(265, 75, 117, 29);
		getContentPane().add(btnOpenBatchmon);
		
		setVisible(true);
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public void setDest(String dest) {
		this.dest = dest;
	}
	
	public void setProgress(int progress) {
		progressBar.setValue(progress);
	}

	public static void main(String[] args) {
		new ProxyFrame();
	}
}