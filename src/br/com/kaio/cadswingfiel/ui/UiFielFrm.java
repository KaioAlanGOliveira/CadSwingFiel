package br.com.kaio.cadswingfiel.ui;

import java.awt.Dimension;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.MaskFormatter;

import br.com.kaio.cadswingfiel.dao.FielDao;
import br.com.kaio.cadswingfiel.domain.Fiel;

public class UiFielFrm {

	private JDialog dialog;
	private JPanel raiz;

	private JTextField txtNome;
	private JFormattedTextField txtCpf;
	private JFormattedTextField txtTelefone;
	private JTextField txtEmail;

	private JButton btnNovo;
	private JButton btnSalvar;
	private JButton btnAlterar;
	private JButton btnApagar;
	private JButton btnCancelar;
	private JButton btnFechar;

	private boolean modoEdicao = true;

	public UiFielFrm() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		raiz = new JPanel();
		raiz.setLayout(null);
		raiz.setPreferredSize(new Dimension(740, 320));

		// ==================== LABELS E CAMPOS ====================

		// Nome
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(28, 86, 80, 20);
		raiz.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(28, 109, 544, 28);
		raiz.add(txtNome);

		// CPF
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(28, 25, 80, 20);
		raiz.add(lblCpf);

		try {
			MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
			maskCpf.setPlaceholderCharacter('_');
			txtCpf = new JFormattedTextField(maskCpf);
		} catch (ParseException e) {
			txtCpf = new JFormattedTextField();
		}
		txtCpf.setBounds(28, 48, 134, 28);
		raiz.add(txtCpf);

		// Telefone
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(28, 149, 80, 20);
		raiz.add(lblTelefone);

		try {
			MaskFormatter maskTel = new MaskFormatter("(##) #####-####");
			maskTel.setPlaceholderCharacter('_');
			txtTelefone = new JFormattedTextField(maskTel);
		} catch (ParseException e) {
			txtTelefone = new JFormattedTextField();
		}
		txtTelefone.setBounds(28, 172, 200, 28);
		raiz.add(txtTelefone);

		// Email
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(250, 149, 80, 20);
		raiz.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(250, 172, 322, 28);
		raiz.add(txtEmail);

		// ==================== BOTÕES ====================

		btnNovo = new JButton("Novo");
		btnNovo.setBounds(590, 25, 120, 28);
		btnNovo.addActionListener(e -> novoRegistro());
		raiz.add(btnNovo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(590, 120, 120, 28);
		btnSalvar.addActionListener(e -> salvar());
		raiz.add(btnSalvar);

		btnAlterar = new JButton("Alterar");
		btnAlterar.setBounds(590, 59, 120, 28);
		btnAlterar.addActionListener(e -> alterar());
		raiz.add(btnAlterar);

		btnApagar = new JButton("Apagar");
		btnApagar.setBounds(590, 221, 120, 28);
		btnApagar.addActionListener(e -> apagar());
		raiz.add(btnApagar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(590, 159, 120, 28);
		btnCancelar.addActionListener(e -> cancelar());
		raiz.add(btnCancelar);

		btnFechar = new JButton("Fechar");
		btnFechar.setBounds(590, 260, 120, 28);
		btnFechar.addActionListener(e -> dialog.dispose());
		raiz.add(btnFechar);

		habilitarControles(true); // inicia desabilitado
	}

	// ====================== MÉTODOS ======================

	public void show(JFrame framePai) {
		dialog = new JDialog(framePai, "Cadastro de Fiel", true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setContentPane(raiz);
		dialog.pack();
		dialog.setLocationRelativeTo(framePai);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	private void novoRegistro() {
		limparCampos();
		habilitarControles(true);
		modoEdicao = true;
		txtCpf.requestFocus();
	}

	private void salvar() {
		if (!validarFrm()) {
			return;
		}

		Fiel fiel = fiel();
		FielDao dao = new FielDao();

		try {
			if (modoEdicao) {
				dao.adicionarFiel(fiel);
				mensagem("Fiel cadastrado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
			} else {
				 dao.atualizarFiel(fiel);
				mensagem("Fiel atualizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
			}
			habilitarControles(false);
			limparCampos();
		} catch (Exception ex) {
			mensagem("Erro ao salvar: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void alterar() {
		modoEdicao = false;
		mensagem("Alterado ...", JOptionPane.INFORMATION_MESSAGE);
		habilitarControles(true);
	}

	private void apagar() {
		mensagem("Funcionalidade de Apagar em desenvolvimento...", JOptionPane.INFORMATION_MESSAGE);
	}

	private void cancelar() {
		limparCampos();
		habilitarControles(false);
	}

	public boolean validarFrm() {
		String cpfLimpo = txtCpf.getText().trim().replace(".", "").replace("-", "").replace("/", "");

		String telefoneLimpo = txtTelefone.getText().trim().replace("(", "").replace(")", "").replace("-", "")
				.replace(" ", "");

		if (cpfLimpo.isEmpty()) {
			mensagem("O campo CPF é obrigatório!", JOptionPane.WARNING_MESSAGE);
			txtCpf.requestFocus();
			return false;
		}
		if (cpfLimpo.length() != 11) {
			mensagem("CPF deve conter 11 dígitos!", JOptionPane.WARNING_MESSAGE);
			txtCpf.requestFocus();
			return false;
		}

		if (txtNome.getText().trim().isEmpty()) {
			mensagem("O campo Nome é obrigatório!", JOptionPane.WARNING_MESSAGE);
			txtNome.requestFocus();
			return false;
		}

		if (telefoneLimpo.isEmpty()) {
			mensagem("O campo Telefone é obrigatório!", JOptionPane.WARNING_MESSAGE);
			txtTelefone.requestFocus();
			return false;
		}

		if (txtEmail.getText().trim().isEmpty()) {
			mensagem("O campo E-mail é obrigatório!", JOptionPane.WARNING_MESSAGE);
			txtEmail.requestFocus();
			return false;
		}

		return true;
	}

	public Fiel fiel() {
		Fiel fiel = new Fiel();
		fiel.setCpf(txtCpf.getText().trim());
		fiel.setNome(txtNome.getText().trim());
		fiel.setTelefone(txtTelefone.getText().trim());
		fiel.setEmail(txtEmail.getText().trim());
		return fiel;
	}

	private void limparCampos() {
		txtCpf.setText("");
		txtNome.setText("");
		txtTelefone.setText("");
		txtEmail.setText("");
	}

	public void habilitarControles(boolean habilitar) {
		txtCpf.setEditable(habilitar);
		txtNome.setEditable(habilitar);
		txtTelefone.setEditable(habilitar);
		txtEmail.setEditable(habilitar);

		btnSalvar.setEnabled(habilitar);
		btnCancelar.setEnabled(habilitar);
		btnAlterar.setEnabled(!habilitar);
		btnApagar.setEnabled(!habilitar);
		btnNovo.setEnabled(!habilitar);
	}

	public void mensagem(String msg, int tipo) {
		String titulo = switch (tipo) {
		case JOptionPane.ERROR_MESSAGE -> "Erro";
		case JOptionPane.WARNING_MESSAGE -> "Atenção";
		case JOptionPane.INFORMATION_MESSAGE -> "Sucesso";
		default -> "Mensagem";
		};
		JOptionPane.showMessageDialog(dialog, msg, titulo, tipo);
	}

	public void carregarDadosParaEdicao(Long id, String cpf, String nome) {
//		this.idAtual = id; // crie o atributo private Long idAtual;
		this.modoEdicao = false;

		txtCpf.setText(cpf);
		txtNome.setText(nome);

		habilitarControles(true);
		btnSalvar.setText("Atualizar"); // muda o texto do botão
	}
}