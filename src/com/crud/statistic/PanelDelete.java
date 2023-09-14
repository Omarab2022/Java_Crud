package com.crud.statistic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PanelDelete {
	private JPanel deletepanel;
	private JLabel titledelete;
	private JPanel jpanelid;
	private JPanel jpanelbutton;
	private JTextField textFieldIDtodelete;
	private JButton cancelButton;
	private JButton deleteButton;

	public PreparedStatement ps ;


	public JPanel getDeletepanel(){
		return deletepanel;
	}

	PanelDelete(){


		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String userId;
				userId = textFieldIDtodelete.getText();

				if (userId.isEmpty() ) {
					JOptionPane.showMessageDialog(null, " fields must be filled in.", "Error", JOptionPane.ERROR_MESSAGE);
					return; // Exit the method early
				}

				try {

					ps = connector.ConnectDB().prepareStatement("DELETE FROM statistic WHERE stat_id=?");
					ps.setString(1,userId);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null , "Deleted Data with Success !");
					JComponent component = (JComponent) e.getSource();
					Window window = SwingUtilities.getWindowAncestor(component);
					window.dispose();
					Show.createGUI();

				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}


			}
		});

		//----------cancel button

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComponent component = (JComponent) e.getSource();
				Window window = SwingUtilities.getWindowAncestor(component);
				window.dispose();
			}
		});
	}


}
