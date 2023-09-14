package com.crud.statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class dataInterface {

	private JPanel mainPanel;
	private JLabel title;
	private JTextField textFieldUsername;
	private JTextField textFieldScore;
	private JTextField textFieldRank;
	private JTable jtable;
	private JButton buttonadd;
	private JButton buttonUpdate;
	private JButton buttondelete;
	private JPanel jfiestpanel;
	private JPanel jsecondpanel;
	private JPanel jthirdpanel;
	private JLabel labelusername;
	private JLabel labelscore;
	private JLabel labelrank;
	private JScrollPane jscroll;

	private DefaultTableModel tableModel;
	private ResultSet resultSet;
	private PreparedStatement ps;

	private int nextID; // Manually manage the next available ID

	//------------------------------- Return panel -----------------/-/
	public JPanel getMainPanel() {
		showdata();
		return mainPanel;
	}

	//--------------------------------------------------------------------------//

	public dataInterface() {
		// Initialize the nextID with the maximum ID from the database
		nextID = getNextID();

		buttonadd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String statuser = textFieldUsername.getText();
					String statrank = textFieldRank.getText();
					String statscore = textFieldScore.getText();

					Connection con = connector.ConnectDB();
					String sql = "INSERT INTO statistic (stat_id, stat_username, stat_score, stat_rank) VALUES (?, ?, ?, ?)";
					PreparedStatement ps = con.prepareStatement(sql);

					// Use the next available ID
					ps.setInt(1, nextID);
					ps.setString(2, statuser);
					ps.setString(3, statscore);
					ps.setString(4, statrank);

					ps.executeUpdate();
					showdata();
					JOptionPane.showMessageDialog(null, "Saved!");

					// Increment the nextID for the next record
					nextID++;
					textFieldUsername.setText("");
					textFieldRank.setText("");
					textFieldScore.setText("");

				} catch (Exception err) {
					err.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error!");
				}
			}
		});

		buttonUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(dataInterface::createUpdateGUI);
			}
		});

		buttondelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(dataInterface::createdeleteGUI);
			}
		});
	}

	//-----------------------  create update gui to open delete interface -----------//

	public static void createdeleteGUI(){

		PanelDelete UIdelete = new PanelDelete();

		JPanel root = UIdelete.getDeletepanel();

		JFrame frame = new JFrame();
		frame.setContentPane(root);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	//-----------------------  create update gui to open update interface -----------//

	public static void createUpdateGUI(){

		Updatepanel UIupdate = new Updatepanel();

		JPanel root = UIupdate.Getupdatepanel();

		JFrame frame = new JFrame();
		frame.setContentPane(root);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	//--------------------Show data in the table --------------------------//


	private void showdata(){

		try {
			//column title in table
			Object[] columnTitle = {"Statistic ID" , "Username" ,"Score" , "Rank"};
			tableModel = new DefaultTableModel(null , columnTitle);
			jtable.setModel(tableModel);

			//Connection from database

			Connection connection = connector.ConnectDB();
			Statement st =connection.createStatement();
			tableModel.getDataVector().removeAllElements();

			//initial result white sql query

			resultSet = st.executeQuery("SELECT * FROM statistic");
			while (resultSet.next()){
				Object[] data ={
						resultSet.getString("stat_id"),
						resultSet.getString("stat_username"),
						resultSet.getString("stat_score"),
						resultSet.getString("stat_rank"),

				};
				tableModel.addRow(data);

			}



		}catch (SQLException err){

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	private int getNextID() {
		int maxID = 0;

		try {
			Connection connection = connector.ConnectDB();
			Statement st = connection.createStatement();

			ResultSet resultSet = st.executeQuery("SELECT MAX(stat_id) FROM statistic");
			if (resultSet.next()) {
				maxID = resultSet.getInt(1);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}

		// The next available ID should be one greater than the maximum existing ID
		return maxID + 1;
	}
}

