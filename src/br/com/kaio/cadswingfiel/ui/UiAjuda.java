package br.com.kaio.cadswingfiel.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class UiAjuda {

	private JDialog dialog;
	private JPanel raiz;

	public UiAjuda() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {

		raiz = new JPanel();
		raiz.setLayout(null);
		raiz.setPreferredSize(new Dimension(700, 450));
		raiz.setBackground(new Color(245, 247, 250));

		JLabel lblTitulo = new JLabel("Ajuda - Cadastro de Fiel");
		lblTitulo.setBounds(20, 15, 400, 35);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitulo.setForeground(new Color(31, 79, 163));
		raiz.add(lblTitulo);

		JLabel lblSubtitulo = new JLabel("Saiba como utilizar a tela de cadastro de fiel");
		lblSubtitulo.setBounds(20, 50, 500, 20);
		lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
		raiz.add(lblSubtitulo);

		JPanel cardCampos = criarCard(20, 90, 650, 130, "Preenchimento dos Campos");

		JTextArea txtCampos = new JTextArea();
		txtCampos.setText("CPF: informe os 11 dígitos do CPF.\n" + "Nome: digite o nome completo do fiel.\n"
				+ "Telefone: informe o telefone no formato (99) 99999-9999.\n"
				+ "E-mail: digite um endereço de e-mail válido.");
		txtCampos.setBounds(15, 35, 610, 80);
		txtCampos.setEditable(false);
		txtCampos.setOpaque(false);
		txtCampos.setLineWrap(true);
		txtCampos.setWrapStyleWord(true);
		txtCampos.setFont(new Font("Arial", Font.PLAIN, 14));
		cardCampos.add(txtCampos);

		JPanel cardBotoes = criarCard(20, 235, 650, 140, "Função dos Botões");

		JTextArea txtBotoes = new JTextArea();
		txtBotoes.setText(
				"Novo: limpa os campos para um novo cadastro.\n" + "Salvar: grava um novo fiel ou atualiza os dados.\n"
						+ "Alterar: habilita a edição do registro carregado.\n" + "Apagar: remove o fiel selecionado.\n"
						+ "Cancelar: cancela a operação atual.\n" + "Fechar: encerra a janela.");
		txtBotoes.setBounds(15, 35, 610, 95);
		txtBotoes.setEditable(false);
		txtBotoes.setOpaque(false);
		txtBotoes.setLineWrap(true);
		txtBotoes.setWrapStyleWord(true);
		txtBotoes.setFont(new Font("Arial", Font.PLAIN, 14));
		cardBotoes.add(txtBotoes);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.setBounds(560, 390, 110, 30);
		btnFechar.addActionListener(e -> dialog.dispose());
		raiz.add(btnFechar);

		raiz.add(cardCampos);
		raiz.add(cardBotoes);
	}

	private JPanel criarCard(int x, int y, int largura, int altura, String titulo) {

		JPanel card = new JPanel();
		card.setLayout(null);
		card.setBounds(x, y, largura, altura);
		card.setBackground(Color.WHITE);
		card.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(210, 210, 210)));

		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setBounds(15, 10, 400, 20);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setForeground(new Color(31, 79, 163));
		card.add(lblTitulo);

		return card;
	}

	public void show(JFrame framePai) {

		dialog = new JDialog(framePai, "Ajuda", true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setContentPane(raiz);
		dialog.pack();
		dialog.setLocationRelativeTo(framePai);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}
}
