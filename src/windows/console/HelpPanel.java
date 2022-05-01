package windows.console;

import java.awt.Font;
import java.util.Iterator;

import javax.swing.JTextArea;

import windows.console.commands.Commands;

public class HelpPanel extends JTextArea{
	private static Commands commands;
	
	public HelpPanel() {
		commands = new Commands();
		searchCommands("");
	}
	
	public void searchCommands(String cmd) {
		this.setText("Commands:\n");
		for (String key : commands.getCommands().keySet()) {
			if (key.contains(cmd)) {
				String info = "";
				if (commands.getCommands().get(key).getInfo().length() > 0) {
					info = commands.getCommands().get(key).getInfo();
				}
				this.append("\t" + key + " (" + info + ")" + "\n");
			}
		}
	}
}
