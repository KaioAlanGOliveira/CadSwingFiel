package br.com.kaio.cadswingfiel.ui;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.MaskFormatter;

public class UiFielFrm {
	private JDialog dialog;
	private JPanel raiz;

	private JTextField txtNome;
	private JTextField txtCpf;
	private JTextField txtTelefone;
	private JTextField txtEmail;

	private JButton btnAlterar;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnApagar;
	private JButton btnFechar;

	private final Label label_1 = new Label("Cadastrar pagamento");

	/**
	 * Create the frame.
	 */
	public UiFielFrm() {

		raiz = new JPanel();
		raiz.setLayout(null);
		raiz.setPreferredSize(new Dimension(740, 305));
		label_1.setBounds(0, 0, 140, 22);
		raiz.add(label_1);

		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBounds(160, 90, 412, 28);
		raiz.add(txtNome);

		Label LblNome = new Label("Nome");
		LblNome.setBounds(160, 62, 62, 22);
		raiz.add(LblNome);
		try {
			MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
			maskCpf.setPlaceholderCharacter('_');

			txtCpf = new JFormattedTextField(maskCpf);
			txtCpf.setBounds(28, 88, 122, 28);
			txtCpf.setColumns(14);
			raiz.add(txtCpf);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		Label LblCpf = new Label("CPF");
		LblCpf.setBounds(28, 62, 62, 22);
		raiz.add(LblCpf);

		JLabel lblNewLabel = new JLabel("Telefone");
		lblNewLabel.setBounds(28, 138, 62, 14);
		raiz.add(lblNewLabel);

		try {
			MaskFormatter maskTel = new MaskFormatter("(##) #####-####"); // ou "(##) ####-####" para fixo
			maskTel.setPlaceholderCharacter('_');
			txtTelefone = new JFormattedTextField(maskTel);
			txtTelefone.setBounds(28, 163, 214, 28);
			raiz.add(txtTelefone);
		} catch (ParseException e) {
		}

		txtEmail = new JTextField();
		txtEmail.setBounds(252, 163, 320, 28);
		raiz.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setBounds(252, 138, 46, 14);
		raiz.add(lblNewLabel_1);

		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAlterar.setBounds(615, 29, 107, 28);
		raiz.add(btnAlterar);

		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFechar.setBounds(615, 253, 107, 28);
		raiz.add(btnFechar);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitarControles(false);
			}
		});
		btnSalvar.setBounds(616, 93, 106, 28);
		raiz.add(btnSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelar.setBounds(615, 124, 107, 28);
		raiz.add(btnCancelar);

		btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnApagar.setBounds(615, 189, 107, 28);
		raiz.add(btnApagar);

	}

	public void show(JFrame framePai) {
		dialog = new JDialog(framePai, "Cadastro", true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setContentPane(raiz);
		dialog.pack();
		dialog.setLocationRelativeTo(framePai);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub

	}

	public void habilitarControles(boolean ativo) {

		// Botões de ação
		btnSalvar.setEnabled(ativo);
		btnCancelar.setEnabled(ativo);

		// Botões de navegação
		btnAlterar.setEnabled(!ativo);
		btnApagar.setEnabled(!ativo);
		btnFechar.setEnabled(!ativo);

		// Campos de texto (usando setEditable para manter a cor branca mas impedir
		// digitação)
		txtCpf.setEditable(ativo);
		txtNome.setEditable(ativo);
		txtTelefone.setEditable(ativo);
		txtEmail.setEditable(ativo);
	}
}
