/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmppprojekt;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.XMPPException;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * 
 * @author Steveboo
 */
public class Login extends javax.swing.JFrame {

	private String username;
	private String password;
	private Xmpptest xmpp;
	private Hauptmenue haupt;

	/**
	 * Creates new form Login
	 */
	public Login() {
		initComponents();
		setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jToggleButton1 = new javax.swing.JToggleButton();
		loginButton = new javax.swing.JButton();
		usernameTextfield = new javax.swing.JTextField();
		passwordTextfield = new javax.swing.JPasswordField();
		registerButton = new javax.swing.JButton();
		label1 = new java.awt.Label();
		label2 = new java.awt.Label();

		jToggleButton1.setText("jToggleButton1");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		loginButton.setText("Login");
		loginButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginButtonActionPerformed(evt);
			}
		});

		passwordTextfield
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						passwordTextfieldActionPerformed(evt);
					}
				});

		registerButton.setText("Register");
		registerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				registerButtonActionPerformed(evt);
			}
		});

		label1.setText("Username");

		label2.setText("Password");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														usernameTextfield,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														103,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														label1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														loginButton,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														103,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(35, 35, 35)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														label2,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														passwordTextfield,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														103, Short.MAX_VALUE)
												.addComponent(
														registerButton,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														label1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														label2,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														usernameTextfield,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														passwordTextfield,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(loginButton)
												.addComponent(registerButton))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_loginButtonActionPerformed
		xmpp = new Xmpptest();
		xmpp.connect(usernameTextfield, passwordTextfield);
		haupt = new Hauptmenue(xmpp, this);
		this.setVisible(false);
	}// GEN-LAST:event_loginButtonActionPerformed

	private void passwordTextfieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_passwordTextfieldActionPerformed
		xmpp = new Xmpptest();
		xmpp.connect(usernameTextfield, passwordTextfield);
		haupt = new Hauptmenue(xmpp, this);
		this.setVisible(false);
	}// GEN-LAST:event_passwordTextfieldActionPerformed

	private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_registerButtonActionPerformed
		// xmpp.register(usernameTextfield, passwordTextfield);
	}// GEN-LAST:event_registerButtonActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JToggleButton jToggleButton1;
	private java.awt.Label label1;
	private java.awt.Label label2;
	private javax.swing.JButton loginButton;
	private javax.swing.JPasswordField passwordTextfield;
	private javax.swing.JButton registerButton;
	private javax.swing.JTextField usernameTextfield;
	// End of variables declaration//GEN-END:variables
}