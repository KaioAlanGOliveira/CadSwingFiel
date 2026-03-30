package br.com.kaio.cadswingfiel.ui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

//	private JFormattedTextField txtCpf;

	private JButton btnNovo;
	private JButton btnSalvar;
	private JButton btnAlterar;
	private JButton btnApagar;
	private JButton btnCancelar;
	private JButton btnFechar;

	private JTextField txtValor;
	private JComboBox<Fiel> cbxFiel;

	private boolean modoEdicao = true;
	private JTextField txtCodPg;
	private PagamentoId id;

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
		btnSalvar.addActionListener(e -> {
			try {

				salvar();
			} catch (Exception e2) {

				e2.printStackTrace();
			}
		});
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
		txtValor.setBounds(29, 126, 245, 28);
		raiz.add(txtValor);
		txtValor.setColumns(10);

		cbxFiel = new JComboBox<Fiel>();
		cbxFiel.setBounds(29, 64, 142, 28);
		DefaultComboBoxModel<Fiel> model = new DefaultComboBoxModel<>();
		List<Fiel> fieis = getFieis();
		for (Fiel fiel : fieis) {

			model.addElement(fiel);
		}
		cbxFiel.setModel(model);
		cbxFiel.setSelectedIndex(-1);
		raiz.add(cbxFiel);

		JLabel lblNewLabel = new JLabel("CPF do Fiel");
		lblNewLabel.setBounds(29, 39, 86, 14);
		raiz.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Valor");
		lblNewLabel_1.setBounds(29, 101, 46, 14);
		raiz.add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("CodPG");
		lblNewLabel_3.setBounds(181, 39, 46, 14);
		raiz.add(lblNewLabel_3);

		txtCodPg = new JTextField();
		txtCodPg.setBounds(181, 64, 93, 28);
		raiz.add(txtCodPg);
		txtCodPg.setColumns(10);

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
		this.id = null;
		modoEdicao = true;
		habilitarControles(true);
		cbxFiel.requestFocus();
	}

	private void salvar() throws Exception {

		if (!validarFrm()) {

			return;
		}

		Pagamento pg = getPagamento();
		PagamentoDao dao = new PagamentoDao();

		try {

			if (modoEdicao) {

				dao.adicionar(pg);
				mensagem("Fiel cadastrado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
			} else {
				dao.atualizar(pg);
				mensagem("Fiel atualizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
			}
			habilitarControles(false);
		} catch (Exception ex) {
			mensagem("Erro ao salvar: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void alterar() {

		this.modoEdicao = false;
		cbxFiel.setEnabled(true);
		habilitarControles(true);
	}

	private void apagar() throws Exception {

		int confirm = JOptionPane.showConfirmDialog(dialog, "Deseja realmente apagar este registro?", "Confirmação",
				JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.YES_OPTION) {

			return;
		}

		try {

			Pagamento pg = getPagamento();
			PagamentoDao dao = new PagamentoDao();
			dao.apagar(pg);

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
		txtCodPg.setText("");
	}

	public void habilitarControles(boolean habilitar) {

		txtValor.setEditable(habilitar);

		btnSalvar.setEnabled(habilitar);
		btnCancelar.setEnabled(habilitar);
		btnAlterar.setEnabled(!habilitar);
		btnApagar.setEnabled(!habilitar);
		btnNovo.setEnabled(!habilitar);
		btnFechar.setEnabled(!habilitar);

		txtCodPg.setEnabled(true);
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

	public void carregarDadosParaEdicao(int codPg, String cpf, Double valor, PagamentoId id) {

		this.modoEdicao = false;
		this.id = id;

		for (int i = 0; i < cbxFiel.getItemCount(); i++) {

			Fiel f = cbxFiel.getItemAt(i);
			if (f.getCpf().equals(cpf)) {

				cbxFiel.setSelectedItem(f);
				break;
			}
		}

		txtValor.setText(String.valueOf(valor));
		txtCodPg.setText(String.valueOf(codPg));

		habilitarControles(false);
	}

	private Pagamento getPagamento() throws Exception {

		if (modoEdicao) {

			Pagamento pg;

			if (modoEdicao) {
				
				pg = new Pagamento();

				PagamentoId id = new PagamentoId();

				Fiel fielSelecionado = (Fiel) cbxFiel.getSelectedItem();

				id.setCpf(fielSelecionado.getCpf());
				id.setCodPagamento(Integer.parseInt(txtCodPg.getText()));

				pg.setId(id);

			} else {

				PagamentoDao dao = new PagamentoDao();
				pg = dao.getPagamentoId(this.id);
			}

			try {
				pg.setValor(Double.parseDouble(txtValor.getText()));
			} catch (NumberFormatException e) {
				mensagem("Valor inválido!", JOptionPane.ERROR_MESSAGE);
				return null;
			}

			try {

				pg.setValor(Double.parseDouble(txtValor.getText()));
			} catch (NumberFormatException e) {
				mensagem("Valor inválido!", JOptionPane.ERROR_MESSAGE);
			}

			modoEdicao = true;
			habilitarControles(false);

			return pg;
		} else {

			PagamentoDao dao = new PagamentoDao();
			Pagamento pg = dao.getPagamentoId(id);

			try {

				pg.setValor(Double.parseDouble(txtValor.getText()));
			} catch (NumberFormatException e) {
				mensagem("Valor inválido!", JOptionPane.ERROR_MESSAGE);
			}

			modoEdicao = false;
			habilitarControles(false);

			return pg;
		}
	}
}