package br.com.kaio.cadswingfiel.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;

public class UiPrincipal {

	private JFrame frame;
	private final Color PRIMARY_COLOR = new Color(41, 98, 255);
	private final Color BACKGROUND_COLOR = new Color(248, 249, 250);
	private final Color TEXT_COLOR = new Color(33, 37, 41);
	private final Color SECONDARY_TEXT = new Color(108, 117, 125);
	private final Color BORDER_COLOR = new Color(222, 226, 230);

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
			UIManager.put("control", BACKGROUND_COLOR);
			UIManager.put("nimbusBase", PRIMARY_COLOR);
			UIManager.put("nimbusFocus", PRIMARY_COLOR);
			UIManager.put("nimbusBlueGrey", BACKGROUND_COLOR);
			UIManager.put("text", TEXT_COLOR);
			UIManager.put("MenuBar.background", Color.WHITE);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	private void initialize() {
		
		frame = new JFrame("Sistema de Gestão - Fiel");
		frame.setSize(1100, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BorderLayout());

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

		JMenu mnCadastros = new JMenu("Cadastros");
		mnCadastros.setMnemonic(KeyEvent.VK_C);

		JMenuItem mntmFiel = new JMenuItem("Fiel");
		mntmFiel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		mntmFiel.addActionListener(e -> new UiFielLst().show(frame));

		mnCadastros.add(mntmFiel);
		mnCadastros.addSeparator();

		JMenuItem menuPagamento = new JMenuItem("Pagamentos");
		menuPagamento.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		menuPagamento.addActionListener(e -> new UiPagamentoLst().show(frame));
		mnCadastros.add(menuPagamento);
		
		JMenu mnAjuda = new JMenu("Ajuda");
		JMenuItem menuItem = new JMenuItem("Sobre o Sistema");
		menuItem.addActionListener(e -> new UiAjuda().show(frame));
		mnAjuda.add(menuItem);

		menuBar.add(mnCadastros);
		menuBar.add(mnAjuda);
		frame.setJMenuBar(menuBar);

		JPanel panelCentral = new JPanel(new GridBagLayout());
		panelCentral.setBackground(BACKGROUND_COLOR);

		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(new LineBorder(BORDER_COLOR, 1, true),
				new EmptyBorder(50, 80, 50, 80)));

		JLabel lblTitulo = new JLabel("Bem-vindo ao Sistema");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
		lblTitulo.setForeground(PRIMARY_COLOR);
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblSub = new JLabel("Gerencie fiéis e pagamentos com precisão");
		lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblSub.setForeground(TEXT_COLOR);
		lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblDica = new JLabel("Selecione uma opção no menu superior para navegar");
		lblDica.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblDica.setForeground(SECONDARY_TEXT);
		lblDica.setAlignmentX(Component.CENTER_ALIGNMENT);

		card.add(lblTitulo);
		card.add(Box.createRigidArea(new Dimension(0, 10)));
		card.add(lblSub);
		card.add(Box.createRigidArea(new Dimension(0, 30)));
		card.add(lblDica);

		panelCentral.add(card);
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);

		JLabel lblStatus = new JLabel("  Pronto para uso | Versão 3.0.0");
		lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblStatus.setForeground(SECONDARY_TEXT);
		lblStatus.setPreferredSize(new Dimension(frame.getWidth(), 30));
		lblStatus.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));
		frame.getContentPane().add(lblStatus, BorderLayout.SOUTH);
	}

	public void setVisible(boolean visible) {
		
		frame.setVisible(visible);
	}
}
