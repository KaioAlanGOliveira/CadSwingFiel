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

public class UiPagamentoFrm {

	private JDialog dialog;
	private JPanel raiz;

	private JTextField txtNome;
	private JFormattedTextField txtCpf;
	private Long idAtual;
	private JFormattedTextField txtTelefone;
	private JTextField txtEmail;

	private JButton btnNovo;
	private JButton btnSalvar;
	private JButton btnAlterar;
	private JButton btnApagar;
	private JButton btnCancelar;
	private JButton btnFechar;

	private boolean modoEdicao = true;

	public UiPagamentoFrm() {
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		raiz = new JPanel();
		raiz.setLayout(null);
		raiz.setPreferredSize(new Dimension(740, 320));

		// ==================== LABELS E CAMPOS ====================

		// Nome
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(150, 59, 422, 20);
		raiz.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(150, 82, 422, 28);
		raiz.add(txtNome);

		// CPF
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(28, 59, 80, 20);
		raiz.add(lblCpf);

		try {
			MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
			maskCpf.setPlaceholderCharacter('_');
			txtCpf = new JFormattedTextField(maskCpf);
		} catch (ParseException e) {
			txtCpf = new JFormattedTextField();
		}
		txtCpf.setBounds(28, 82, 112, 28);
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
		txtTelefone.setBounds(28, 172, 112, 28);
		raiz.add(txtTelefone);

		// Email
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(150, 149, 80, 20);
		raiz.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(150, 172, 422, 28);
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
		btnApagar.addActionListener(e -> {
			try {
				apagar();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		raiz.add(btnApagar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(590, 159, 120, 28);
		btnCancelar.addActionListener(e -> cancelar());
		raiz.add(btnCancelar);

		btnFechar = new JButton("Fechar");
		btnFechar.setBounds(590, 260, 120, 28);
		btnFechar.addActionListener(e -> dialog.dispose());
		raiz.add(btnFechar);

		habilitarControles(true);

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
		idAtual = null; // <-- MUITO IMPORTANTE
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
		txtCpf.setEnabled(false);
		habilitarControles(true);
	}

	private void apagar() throws Exception {

		String cpfLimpo = txtCpf.getText().trim().replace(".", "").replace("-", "").replace("/", "");

		if (cpfLimpo.isEmpty() || cpfLimpo.length() != 11) {
			mensagem("Informe um CPF válido para apagar!", JOptionPane.WARNING_MESSAGE);
			txtCpf.requestFocus();
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(dialog, "Deseja realmente apagar este registro?", "Confirmação",
				JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			FielDao dao = new FielDao();
			dao.removerFiel(cpfLimpo);

			mensagem("Fiel apagado com sucesso!", JOptionPane.INFORMATION_MESSAGE);

			limparCampos();
			habilitarControles(false);

		} catch (Exception e) {
			mensagem("Erro ao apagar: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cancelar() {
		if (!modoEdicao) {
			limparCampos();
			habilitarControles(false);
		} else {
			dialog.setVisible(false);
		}
	}

	public boolean validarFrm() {
		String cpfLimpo = txtCpf.getText().trim().replace(".", "").replace("-", "").replace("/", "");

		String telefoneLimpo = txtTelefone.getText().trim().replace("(", "").replace(")", "").replace("-", "")
				.replace(" ", "");

		if (!cpfLimpo.matches("\\d{11}")) {
			mensagem("CPF inválido!", JOptionPane.WARNING_MESSAGE);
			return false;
		}

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

		String cpfLimpo = txtCpf.getText().trim().replace(".", "").replace("-", "").replace("/", "");

		String telefoneLimpo = txtTelefone.getText().trim().replace("(", "").replace(")", "").replace("-", "")
				.replace(" ", "");

		Fiel fiel = new Fiel();
		fiel.setId(idAtual);
		fiel.setCpf(cpfLimpo);
		fiel.setNome(txtNome.getText().trim());
		fiel.setTelefone(telefoneLimpo);
		fiel.setEmail(txtEmail.getText().trim());

		return fiel;
	}

	private void limparCampos() {
		txtCpf.setText("");
		txtNome.setText("");
		txtTelefone.setText("");
		txtEmail.setText("");
		idAtual = null;
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
		this.idAtual = id; // crie o atributo private Long idAtual;
		this.modoEdicao = false;

		txtCpf.setText(cpf);
		txtNome.setText(nome);
//		txtTelefone.setText(telefone);
//		txtEmail.setText(email);

		habilitarControles(false);
//		btnSalvar.setText("Atualizar"); // muda o texto do botão
	}
}