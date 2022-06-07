package windows.main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import global.math.Vector3;
import graphics.Canvas.ObjectType;

public class ObjectLayout extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public static JList objectList;
	public static JButton objectSelectBtn = new JButton("Apply");
	public static JButton updateBtn = new JButton("Update");
	public static JLabel rotLbl, posLbl, dimLbl;
	
	public static SliderPanel rotXSlider, rotYSlider, rotZSlider, rotWSlider,
	posXSlider, posYSlider, posZSlider, posWSlider,
	dimXSlider, dimYSlider, dimZSlider, dimWSlider;
	
	public Vector3<Float> getPos(){
		return new Vector3<Float>((float) posXSlider.getValue(),(float) posYSlider.getValue(),(float) posZSlider.getValue());
	}
	
	public Float getPos4() {
		return (float) posWSlider.getValue();
	}
	
	public Vector3<Float> getRot(){
		return new Vector3<Float>((float) rotXSlider.getValue(),(float) rotYSlider.getValue(),(float) rotZSlider.getValue());
	}
	
	public Float getRot4() {
		return (float) rotWSlider.getValue();
	}
	
	public Vector3<Float> getDim(){
		return new Vector3<Float>((float) dimXSlider.getValue(),(float) dimYSlider.getValue(),(float) dimZSlider.getValue());
	}
	
	public Float getDim4() {
		return (float) dimWSlider.getValue();
	}
	
	public ObjectLayout() {
		this.setLayout(new GridLayout(0, 1));

		//Object list 1x2
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(1,2));
		
		objectList = new JList<ObjectType>(ObjectType.values());
		objectSelectBtn.setBackground(Color.black);
		objectSelectBtn.setForeground(Color.white);
		
		listPanel.add(objectList);
		listPanel.add(objectSelectBtn);
		
		this.add(listPanel);
		
		//Position
		posLbl = new JLabel("Position (%)");
		this.add(posLbl);
		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridLayout(3,3));
		
		posXSlider = new SliderPanel("x", 0, 100, 50, updateBtn);
		posYSlider = new SliderPanel("y", 0, 100, 50, updateBtn);
		posZSlider = new SliderPanel("z", 0, 100, 25, updateBtn);
		posWSlider = new SliderPanel("w", 0, 100, 50, updateBtn);
		
		positionPanel.add(posXSlider.get());
		positionPanel.add(posYSlider.get());
		positionPanel.add(posZSlider.get());
//		positionPanel.add(posWSlider.get());
		
		this.add(positionPanel);
		
		//Dimensions
		dimLbl = new JLabel("Dimensions (%)");
		this.add(dimLbl);
		JPanel dimensionPanel = new JPanel();
		dimensionPanel.setLayout(new GridLayout(3,3));
		
		dimXSlider = new SliderPanel("x", 1, 100, 100, updateBtn);
		dimYSlider = new SliderPanel("y", 1, 100, 1, updateBtn);
		dimZSlider = new SliderPanel("z", 1, 100, 1, updateBtn);
		dimWSlider = new SliderPanel("w", 1, 100, 100, updateBtn);
		
		dimensionPanel.add(dimXSlider.get());
		dimensionPanel.add(dimYSlider.get());
		dimensionPanel.add(dimZSlider.get());
//		dimensionPanel.add(dimWSlider.get());
		
		this.add(dimensionPanel);
		
		//Rotation
		rotLbl = new JLabel("Rotation (Deg)");
		this.add(rotLbl);
		JPanel rotationPanel = new JPanel();
		rotationPanel.setLayout(new GridLayout(3,3));
		
		rotXSlider = new SliderPanel("x", 0, 360, 0, objectSelectBtn);
		rotYSlider = new SliderPanel("y", 0, 360, 0, objectSelectBtn);
		rotZSlider = new SliderPanel("z", 0, 360, 0, objectSelectBtn);
		rotWSlider = new SliderPanel("w", 0, 360, 0, objectSelectBtn);
		
		rotationPanel.add(rotXSlider.get());
		rotationPanel.add(rotYSlider.get());
		rotationPanel.add(rotZSlider.get());
//		rotationPanel.add(rotWSlider.get());
		
		this.add(rotationPanel);		
		this.add(updateBtn);
	}
	
	class SliderPanel{
		
		private JPanel panel;
		private JSlider slider;
		private String info;
		public String getInfo() {
			return info;
		}

		public int getMin() {
			return min;
		}

		public int getMax() {
			return max;
		}

		public int getValue() {
			return slider.getValue();
		}
		
		
		public void setValue(int value) {
			this.slider.setValue(value);
			this.slider.updateUI();
		}

		private int min, max;
		
		public SliderPanel(String info, int min, int max, int value, JButton btn){
			this.info = info;
			this.min = min;
			this.max = max;
			
			JLabel infoLbl = new JLabel(info), valueLbl = new JLabel(String.valueOf(value));
			slider = new JSlider(min, max, value);
			slider.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					valueLbl.setText(String.valueOf(slider.getValue()));
					btn.doClick(0);
				}
			});
			
			panel = new JPanel(new GridBagLayout());
			GridBagConstraints panelC = new GridBagConstraints();
			panelC.fill = GridBagConstraints.BOTH;
			panelC.gridwidth = 1;
			
			panelC.gridy = 0;

			panelC.gridx = 0;
			panelC.weightx = 1/3f;
			panel.add(infoLbl, panelC);
			panelC.weightx = 1/3f;
			panelC.gridx = 1;
			panel.add(slider, panelC);
			panelC.weightx = 1/3f;
			panelC.gridx = 2;
			panel.add(valueLbl, panelC);
		}
		
		public JPanel get() {
			return this.panel;
		}
		
		public JSlider getSlider() {
			return this.slider;
		}
		
		public void setPanel(JPanel panel) {
			this.panel = panel;
		}
	}
	
}
