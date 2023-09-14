package com.crud.statistic;

import javax.swing.*;

public class Show {


	//-------------------------------------------------------------//

	public static void main(String[] args) {

		SwingUtilities.invokeLater(Show::createGUI);
	}

	//-------------------------  lancer le frame ------------------------------------//

	public static void createGUI(){

		dataInterface UI = new dataInterface();

		JPanel root = UI.getMainPanel();

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(root);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);


	}

	//-------------------------------------------------------------//
}
