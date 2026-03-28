package br.com.kaio.cadswingfiel.ui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import br.com.kaio.cadswingfiel.dao.FielDao;
import br.com.kaio.cadswingfiel.dao.PagamentoDao;
//import br.com.kaio.cadswingfiel.dao.PagamentoDao;
import br.com.kaio.cadswingfiel.domain.Fiel;
import br.com.kaio.cadswingfiel.domain.Pagamento;
import br.com.kaio.cadswingfiel.domain.PagamentoId;

public class UiPagamentoFrm {

	private JDialog dialog;
	private JPanel raiz;

	private JFormattedTextField txtCpf;

	private JButton btnNovo;
	private JButton btnSalvar;
	private JButton btnAlterar;
	private JButton btnApagar;
	private JButton btnCancelar;
	private JButton btnFechar;

	private JTextField txtValor;
	private JComboBox<Fiel> cbxFiel;

	private boolean modoEdicao = true;

	public UiPagamentoFrm() {

		inicializarComponentes();
	}

	private void inicializarComponentes() {

		raiz = new JPanel();
		raiz.setLayout(null);
		raiz.setPreferredSize(new Dimension(441, 320));

		// ==================== BOTÕES ====================

		btnNovo = new JButton("Novo");
		btnNovo.setBounds(310, 21, 120, 28);
		btnNovo.addActionListener(e -> novoRegistro());
		raiz.add(btnNovo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(310, 116, 120, 28);
		btnSalvar.addActionListener(e -> salvar());
		raiz.add(btnSalvar);

		btnAlterar = new JButton("Alterar");
		btnAlterar.setBounds(310, 55, 120, 28);
		btnAlterar.addActionListener(e -> alterar());
		raiz.add(btnAlterar);

		btnApagar = new JButton("Apagar");
		btnApagar.setBounds(310, 217, 120, 28);
		btnApagar.addActionListener(e -> {
			try {
				apagar();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		raiz.add(btnApagar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(310, 155, 120, 28);
		btnCancelar.addActionListener(e -> cancelar());
		raiz.add(btnCancelar);

		btnFechar = new JButton("Fechar");
		btnFechar.setBounds(310, 256, 120, 28);
		btnFechar.addActionListener(e -> dialog.dispose());
		raiz.add(btnFechar);

		txtValor = new JTextField();
		txtValor.setBounds(178, 75, 86, 20);
		raiz.add(txtValor);
		txtValor.setColumns(10);

		cbxFiel = new JComboBox<Fiel>();
		cbxFiel.setBounds(29, 74, 120, 22);
		DefaultComboBoxModel<Fiel> model = new DefaultComboBoxModel<>();
		List<Fiel> fieis = getFieis();
		for (Fiel fiel : fieis) {
			model.addElement(fiel);
		}
		cbxFiel.setModel(model);
		cbxFiel.setSelectedIndex(-1);
		raiz.add(cbxFiel);

		habilitarControles(true);
	}

	// ====================== MÉTODOS ======================

	public void show(JFrame framePai) {

		dialog = new JDialog(framePai, "Cadastro de pagamento", true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setContentPane(raiz);
		dialog.pack();
		dialog.setLocationRelativeTo(framePai);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	private void novoRegistro() {
		limparCampos();
//		idAtual = null; // <-- MUITO IMPORTANTE
		habilitarControles(true);
		modoEdicao = true;
		txtCpf.requestFocus();
	}

	private void salvar() {
		
		if (!validarFrm()) {
			return;
		}

		Pagamento pg = getPagamento();
		PagamentoDao dao = new PagamentoDao();

		try {
			if (!modoEdicao) {
				dao.adicionar(pg);
				mensagem("Fiel cadastrado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
			} else {
//				dao.atualizarFiel(fiel);
				mensagem("Fiel atualizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
			}
			habilitarControles(false);
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

		if (txtValor.getText().trim().isEmpty()) {
			mensagem("O campo Nome é obrigatório!", JOptionPane.WARNING_MESSAGE);
			txtValor.requestFocus();
			return false;
		}
		return true;
	}

	public List<Fiel> getFieis() {

		FielDao dao = new FielDao();

		List<Fiel> f = dao.listarFiel();

		return f;
	}

	private void limparCampos() {
		cbxFiel.setSelectedIndex(-1);
		txtValor.setText("");
	}

	public void habilitarControles(boolean habilitar) {

		txtValor.setEditable(habilitar);

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
		txtValor.setText(nome);
//		txtTelefone.setText(telefone);
//		txtEmail.setText(email);

		habilitarControles(false);
//		btnSalvar.setText("Atualizar"); // muda o texto do botão
	}

	private Pagamento getPagamento() {
		
		PagamentoId id = new PagamentoId();
		Fiel selecionado = (Fiel) cbxFiel.getSelectedItem();
		String cpf = selecionado.getCpf();
		id.setCpf(cpf);

		Pagamento pg = new Pagamento();
		pg.setId(id);
		try {
			pg.setValor(Long.parseLong(txtValor.getText()));
		} catch (NumberFormatException e) {
			mensagem("Valor inválido!", JOptionPane.ERROR_MESSAGE);
		}

		modoEdicao = false;
		habilitarControles(false);
		
		return pg;
	}

}