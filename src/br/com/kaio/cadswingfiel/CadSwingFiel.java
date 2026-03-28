package br.com.kaio.cadswingfiel;

import java.awt.EventQueue;

import javax.swing.UIManager;

import br.com.kaio.cadswingfiel.ui.UiPrincipal;

public class CadSwingFiel {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiPrincipal window = new UiPrincipal();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		});
	}

}
