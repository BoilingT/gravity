package windows.console;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import global.WindowHandler;
import windows.console.commands.Commands;

public class ConsoleWindow {
	private WindowHandler output = new WindowHandler(400, 500, "Console");
	private Commands commands = new Commands();
	
	private JTextArea textArea = new JTextArea();
	private JScrollPane textAreaScrollPane = new JScrollPane();
	
	private HelpPanel helpArea = new HelpPanel();
	private JScrollPane helpAreaScrollPane = new JScrollPane();
	
	private JTextArea inputArea = new JTextArea();
	private JScrollPane inputAreaScrollPane = new JScrollPane();
	
	private static ConsoleWindow console;
	
	public static ConsoleWindow getInstance() {
		if (console == null) {
			console = new ConsoleWindow();
		}
		
		return console;
	}
	
	public void init(Object[] objects) {
		output.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		output.getContentPane().setLayout(new GridLayout(0,1));
		
		textArea.setEditable(false);
		textAreaScrollPane.setViewportView(textArea);
		textAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		textAreaScrollPane.setMinimumSize(new Dimension(100, 100));
		textAreaScrollPane.setMaximumSize(new Dimension(100, 100));
		
		output.getContentPane().add(textAreaScrollPane);
		
		//((DefaultCaret)textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		inputAreaScrollPane.setViewportView(inputArea);
		inputAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		inputAreaScrollPane.setMinimumSize(new Dimension(100, 100));
		inputAreaScrollPane.setMaximumSize(new Dimension(100, 100));
		output.getContentPane().add(inputAreaScrollPane);
		
		DocumentListener l = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		inputArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
//				if (inputArea.getText().length() > 0) {
//					if(!helpAreaScrollPane.isVisible()) {
//						helpArea = new HelpPanel();
//						helpArea.setEditable(false);
//						helpAreaScrollPane.setViewportView(helpArea);
//						output.getContentPane().add(helpAreaScrollPane);
//						helpAreaScrollPane.setVisible(true);
//						output.getContentPane().revalidate();
//					}
//					
//				}else if (inputArea.getText().length() <= 0){
//					if(helpAreaScrollPane.isVisible()) {
//						helpArea = null;
//						output.getContentPane().remove(output.getContentPane().add(helpAreaScrollPane));
//						helpAreaScrollPane.setVisible(false);
//						output.getContentPane().revalidate();
//					}
//				}
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					enterPressed();
				}
				String term = parseCommand(inputArea.getText().trim())[0];
				if (helpArea != null) {
					helpArea.searchCommands(term);
					if (commands.getCommands().containsKey(term)) {
						inputArea.setFont(new Font(inputArea.getFont().getFamily(), Font.BOLD, 19));
					}else {
						inputArea.setFont(new Font(inputArea.getFont().getFamily(), Font.PLAIN, 14));
					}
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		helpArea.setEditable(false);
		helpAreaScrollPane.setViewportView(helpArea);
		helpAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		helpAreaScrollPane.setMinimumSize(new Dimension(100, 50));
		helpAreaScrollPane.setMaximumSize(new Dimension(100, 50));
		output.getContentPane().add(helpAreaScrollPane);
				
		output.Show();
	}
	
	private void enterPressed() {
		String[] inputVals = parseCommand(inputArea.getText().trim());
		String cmd = inputVals[0].trim();
		String args = inputVals.length > 1 ? inputVals[1].trim() : "";
		if (cmd.length() > 0) {
			if (commands.getCommands().containsKey(cmd)) {
				commands.run(cmd, args);
				cout("Command: " + inputArea.getText().trim());
				inputArea.setText("");
			}else {
				inputArea.setText(cmd);
			}
		}
	}
	
	private String[] parseCommand(String str) {
		return str.split(" ", 2);
	}
	
	public void cout(String msg) {
		textArea.append(msg + "\n");
		try {
			textArea.setCaretPosition(textArea.getText().indexOf(msg.trim()));			
		} catch (Exception e) {
			return;
		}
	}
}
