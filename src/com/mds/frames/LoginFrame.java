package com.mds.frames;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel NameLabel = null;
	private JLabel PassLabel = null;
	private JTextField NameTextField = null;
	private JPasswordField PasswordField = null;
	private JButton LoginButton = null;
	private JButton ClearButton = null;
//	private MainFrame m = null;

	/**
	 * This is the default constructor
	 */
	public LoginFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 400);
		this.setContentPane(getJContentPane());
		
		this.setTitle("Login");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			PassLabel = new JLabel();
			PassLabel.setBounds(new Rectangle(75, 170, 100, 30));
			PassLabel.setText("Password : ");
			NameLabel = new JLabel();
			NameLabel.setBounds(new Rectangle(75, 120, 100, 30));
			NameLabel.setText("Name : ");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(NameLabel, null);
			jContentPane.add(PassLabel, null);
			jContentPane.add(getNameTextField(), null);
			jContentPane.add(getPasswordField(), null);
			jContentPane.add(getLoginButton(), null);
			jContentPane.add(getClearButton(), null);
			
		}
		return jContentPane;
	}

	/**
	 * This method initializes NameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNameTextField() {
		if (NameTextField == null) {
			NameTextField = new JTextField();
			NameTextField.setBounds(new Rectangle(225, 120, 180, 30));
		}
		return NameTextField;
	}

	/**
	 * This method initializes PasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getPasswordField() {
		if (PasswordField == null) {
			PasswordField = new JPasswordField();
			PasswordField.setBounds(new Rectangle(225, 170, 180, 30));
		}
		return PasswordField;
	}

	/**
	 * This method initializes LoginButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLoginButton() {
		if (LoginButton == null) {
			LoginButton = new JButton();
			LoginButton.setBounds(new Rectangle(140, 230, 80, 25));
			LoginButton.setText("Login");
			LoginButton.addActionListener(new ActionListener () {
				public void actionPerformed(ActionEvent e) {
					
					
					if(NameTextField.getText().compareTo("admin")==0)
					{
//						System.out.println("clicked");
						if(String.valueOf(PasswordField.getPassword()).equals("mds"))
						{
						
							MainFrame m = new MainFrame();
//							m.setBounds(0,0,900,600);
							m.setVisible(true);
							closeWindow();
						}
						else
							showMessageDialog("Invalid Username or Password");
					}
				
				}
			});
		}
		return LoginButton;
	}
	
	private void showMessageDialog(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}
	
	private void closeWindow()
	{
		setVisible(false);
		dispose();
	}
	

	/**
	 * This method initializes ClearButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getClearButton() {
		if (ClearButton == null) {
			ClearButton = new JButton();
			ClearButton.setBounds(new Rectangle(240, 230, 80, 25));
			ClearButton.setText("Clear");
			ClearButton.addActionListener(new ActionListener () {
				public void actionPerformed(ActionEvent e) {
					NameTextField.setText("");
					PasswordField.setText("");
				}
			});
		}
		
		return ClearButton;
	}
	
	public static void main(String[] args) {
		
		LoginFrame l = new LoginFrame();
		l.setVisible(true);
	}

}
