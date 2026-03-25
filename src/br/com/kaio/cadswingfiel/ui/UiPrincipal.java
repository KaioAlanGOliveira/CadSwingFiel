package br.com.kaio.cadswingfiel.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UiPrincipal {

	private JFrame frame;

	// 🎨 Cores refinadas para Nimbus
	private final Color COR_PRIMARIA = new Color(0, 120, 215);
	private final Color COR_TEXTO =  new Color(60, 60, 60);

	public UiPrincipal() {
		setNimbusLookAndFeel();
		initialize();
	}

	private void setNimbusLookAndFeel() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

			// 🎨 Customizando Nimbus
			UIManager.put("control", new Color(245, 245, 245));
			UIManager.put("nimbusBase", COR_PRIMARIA);
			UIManager.put("nimbusFocus", COR_PRIMARIA);
			UIManager.put("text", COR_TEXTO);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		frame = new JFrame("Sistema de Gestão - Fiel");
		frame.setSize(1100, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		// 🔝 MENU
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(new EmptyBorder(5, 10, 5, 10));
		frame.setJMenuBar(menuBar);

		JMenu mnCadastros = new JMenu("Cadastros");
		mnCadastros.setMnemonic(KeyEvent.VK_C);
		menuBar.add(mnCadastros);

		JMenuItem mntmFiel = new JMenuItem("Fiel");
		mntmFiel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		mntmFiel.addActionListener(e -> {
			UiFielLst ui = new UiFielLst();
			ui.show(frame);
		});
		mnCadastros.add(mntmFiel);

		mnCadastros.addSeparator();

		JMenuItem mntmPagamento = new JMenuItem("Pagamentos");
		mnCadastros.add(mntmPagamento);

		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);

		JMenuItem mntmSobre = new JMenuItem("Sobre o Sistema");
		mnAjuda.add(mntmSobre);

		// 🧱 PAINEL CENTRAL (TELA DE BOAS-VINDAS)
		JPanel panelCentral = new JPanel(new GridBagLayout());
		panelCentral.setBackground(new Color(245, 245, 245));

		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220)),
				new EmptyBorder(40, 60, 40, 60)
		));

		// 🏷️ Título principal
		JLabel lblTitulo = new JLabel("Bem-vindo ao Sistema");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
		lblTitulo.setForeground(COR_PRIMARIA);
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 📝 Subtítulo
		JLabel lblSub = new JLabel("Gerencie fiéis e pagamentos com facilidade");
		lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblSub.setForeground(COR_TEXTO);
		lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 📌 Dica de uso
		JLabel lblDica = new JLabel("Use o menu acima para começar");
		lblDica.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		lblDica.setForeground(Color.GRAY);
		lblDica.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Espaçamento
		card.add(lblTitulo);
		card.add(Box.createRigidArea(new Dimension(0, 15)));
		card.add(lblSub);
		card.add(Box.createRigidArea(new Dimension(0, 25)));
		card.add(lblDica);

		panelCentral.add(card);
		frame.add(panelCentral, BorderLayout.CENTER);

		// 🔻 STATUS BAR
		JLabel lblStatus = new JLabel("  Sistema Ativo | v1.0");
		lblStatus.setBorder(new EmptyBorder(5, 10, 5, 10));
		frame.add(lblStatus, BorderLayout.SOUTH);
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}