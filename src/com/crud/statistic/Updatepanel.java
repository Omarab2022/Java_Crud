package com.crud.statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

public class Updatepanel {

	private JPanel mainupdatepanel;
	private JLabel jtitleupdatepanel;
	private JTextField textFieldUPid;
	private JTextField textFieldUpUsername;
	private JTextField textFieldUpScore;
	private JTextField textFieldUpRank;
	private JButton cancelButton;
	private JButton updateButton;
	private JPanel jpanelSearch;
	private JLabel jlabelsearch;
	private JLabel jlabelsear;
	private JTextField textFieldsearch;
	private JPanel jpaneltablesearch;
	private JTable tablesearch;

	private PreparedStatement ps ;

	DefaultTableModel tableModelupdat;

	ResultSet resultSet = null;

	//---------------------------------------------------------------------------------//

	public JPanel Getupdatepanel(){
		return mainupdatepanel;
	}

	//---------------------------------- action listener for update ------------------//
	Updatepanel(){

		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String statuser;
				String statrank;
				String statscore;
				String userID;
				userID = textFieldUPid.getText();
				statrank = textFieldUpRank.getText();
				statscore = textFieldUpScore.getText();
				statuser = textFieldUpUsername.getText();

				// Check if any of the input fields is empty
				if (userID.isEmpty() || statrank.isEmpty() || statscore.isEmpty() || statuser.isEmpty()) {
					JOptionPane.showMessageDialog(null, "All fields must be filled in.", "Error", JOptionPane.ERROR_MESSAGE);
					return; // Exit the method early
				}

				try {

					ps = connector.ConnectDB().prepareStatement("UPDATE statistic SET stat_username=? , stat_score=? , stat_rank=? WHERE stat_id=?");
					ps.setString(4,userID);
					ps.setString(1,statuser);
					ps.setString(2,statscore);
					ps.setString(3,statrank);

					ps.executeUpdate();

					JOptionPane.showMessageDialog(null , "Update Data Success !");
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

	//---------------------------------- action listener for cancel ------------------//


		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JComponent component = (JComponent) e.getSource();
				Window window = SwingUtilities.getWindowAncestor(component);
				window.dispose();
			}
		});


///----------------  Create key listener for search TextField ------------------------------------------//

		textFieldsearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);

				String key = textFieldsearch.getText();
				System.out.println(key);

				if (!key.isEmpty()) {
					try {
						searchdataupdate(key);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			}
		});
	}
//---------------------------search Function ----------------------------//
	public void searchdataupdate( String key ) throws Exception {

		//column title in table
		Object[] columnTitle = {"Statistic ID" , "Username" ,"Score" , "Rank"};
		tableModelupdat = new DefaultTableModel(null , columnTitle);
		tablesearch.setModel(tableModelupdat);

		//Connection from database

		Connection connection = connector.ConnectDB();
		Statement st =connection.createStatement();
		tableModelupdat.getDataVector().removeAllElements();

		//initial result white sql query

		resultSet = st.executeQuery("SELECT * FROM statistic WHERE stat_username LIKE '%"+key+"%' OR stat_score LIKE '%"+key+"%'OR stat_rank LIKE '%"+key+"%' ");
		while (resultSet.next()){
			Object[] data ={
					resultSet.getString("stat_id"),
					resultSet.getString("stat_username"),
					resultSet.getString("stat_score"),
					resultSet.getString("stat_rank"),

			};
			tableModelupdat.addRow(data);

		}

	}
}
