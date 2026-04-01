package br.com.kaio.cadswingfiel;

import javax.swing.UIManager;

import br.com.kaio.cadswingfiel.persistence.EmFactory;
import br.com.kaio.cadswingfiel.ui.UiPrincipal;

public class CadSwingFiel {

	public static void main(String[] args) {

		try {

			EmFactory.getEm();

			UiPrincipal window = new UiPrincipal();
			window.setVisible(true);

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
