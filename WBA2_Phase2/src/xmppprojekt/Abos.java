/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmppprojekt;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.jivesoftware.smack.XMPPException;

/**
 * 
 * @author Steveboo
 */
public class Abos extends javax.swing.JFrame {

	/**
	 * Creates new form Abos
	 */
	private Hauptmenue haupt;
	private Xmpptest xmpp;
	public String serienName;

	public Abos(Hauptmenue haupt, Xmpptest xmpp) {
		this.xmpp = xmpp;
		this.haupt = haupt;
		initComponents();
		setVisible(true);
		xmpp.refreshLists(addAboList, subscriptionsList);

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

		jLabel1 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		subscriptionsList = new javax.swing.JList();
		aboDeleteButton = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		addAboList = new javax.swing.JList();
		jLabel3 = new javax.swing.JLabel();
		aboAddButton = new javax.swing.JButton();
		abosToMainButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel1.setText("Abos Bearbeiten");

		subscriptionsList.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4",
					"Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jScrollPane1.setViewportView(subscriptionsList);

		aboDeleteButton.setText("Abonnement Entfernen");
		aboDeleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aboDeleteButtonActionPerformed(evt);
			}
		});

		jLabel2.setText("Vorhandene Abonnements");

		addAboList.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4",
					"Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jScrollPane2.setViewportView(addAboList);

		jLabel3.setText("Abonnement Hinzufügen");

		aboAddButton.setText("Abonnement Hinzufügen");
		aboAddButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aboAddButtonActionPerformed(evt);
			}
		});

		abosToMainButton.setText("Hauptmenü");
		abosToMainButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				abosToMainButtonActionPerformed(evt);
			}
		});

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
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addComponent(jLabel1)
												.addComponent(
														aboDeleteButton,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														150, Short.MAX_VALUE)
												.addComponent(jLabel2)
												.addComponent(
														jScrollPane1,
														javax.swing.GroupLayout.Alignment.TRAILING))
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(82, 82,
																		82)
																.addComponent(
																		abosToMainButton))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(18, 18,
																		18)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jLabel3)
																				.addComponent(
																						aboAddButton)
																				.addComponent(
																						jScrollPane2,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						150,
																						javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(171, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel1)
												.addComponent(abosToMainButton))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel3)
												.addComponent(jLabel2))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jScrollPane1)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		aboDeleteButton))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jScrollPane2,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		224,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		aboAddButton)))
								.addGap(5, 5, 5)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void aboDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_aboDeleteButtonActionPerformed
		// TODO add your handling code here:
		xmpp.aboDeleteButton(this, addAboList, subscriptionsList);
	}// GEN-LAST:event_aboDeleteButtonActionPerformed

	private void aboAddButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_aboAddButtonActionPerformed
		xmpp.addAboButton(this, addAboList, subscriptionsList);
	}// GEN-LAST:event_aboAddButtonActionPerformed

	private void abosToMainButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_abosToMainButtonActionPerformed
		this.dispose();
		haupt.setVisible(true);

	}// GEN-LAST:event_abosToMainButtonActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton aboAddButton;
	private javax.swing.JButton aboDeleteButton;
	private javax.swing.JButton abosToMainButton;
	private javax.swing.JList addAboList;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JList subscriptionsList;
	// End of variables declaration//GEN-END:variables
}