package br.com.kaio.cadswingfiel.ui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private PagamentoId codPagamentoId;
	private ButtonGroup grupo;
	private JRadioButton rbOferta;
	private JRadioButton rbDizimo;

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

//		Buttons

		btnAlterar = new JButton("Alterar");
		btnAlterar.setBounds(310, 55, 120, 28);
		btnAlterar.addActionListener(e -> alterar());
		raiz.add(btnAlterar);

		btnApagar = new JButton("Apagar");
		btnApagar.setBounds(310, 217, 120, 28);
		btnApagar.addActionListener(e -> apagar());
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

//		Label
		JLabel lblCpf = new JLabel("CPF do Fiel");
		lblCpf.setBounds(29, 39, 86, 14);
		raiz.add(lblCpf);

		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(29, 101, 46, 14);
		raiz.add(lblValor);

		JLabel lblCodPg = new JLabel("CodPG");
		lblCodPg.setBounds(181, 39, 46, 14);
		raiz.add(lblCodPg);

		txtCodPg = new JTextField();
		txtCodPg.setBounds(181, 64, 93, 28);
		raiz.add(txtCodPg);
		txtCodPg.setColumns(10);

		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(29, 176, 46, 14);
		raiz.add(lblTipo);

		rbOferta = new JRadioButton("Oferta");
		rbOferta.setBounds(29, 197, 109, 23);
		rbOferta.setActionCommand("0");
		raiz.add(rbOferta);

		rbDizimo = new JRadioButton("Dízimo");
		rbDizimo.setBounds(153, 197, 109, 23);
		rbDizimo.setActionCommand("1");
		raiz.add(rbDizimo);

		grupo = new ButtonGroup();
		grupo.add(rbOferta);
		grupo.add(rbDizimo);

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
		this.codPagamentoId = null;
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

				Integer codPagamento = dao.adicionar(pg).intValue();
				pg.getId().setCodPagamento(codPagamento);
				codPagamentoId = pg.getId(); 
				mensagem("Fiel cadastrado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
			} else {
				pg = dao.atualizar(pg);
				mensagem("Fiel atualizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
			}

			carregarDadosParaEdicao(pg);
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

	private void apagar() {

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
			carregarDadosParaEdicao(codPagamentoId);
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
		grupo.clearSelection();
	}

	public void habilitarControles(boolean habilitar) {

		txtValor.setEditable(habilitar);

		btnSalvar.setEnabled(habilitar);
		btnCancelar.setEnabled(habilitar);
		btnAlterar.setEnabled(!habilitar);
		btnApagar.setEnabled(!habilitar);
		btnNovo.setEnabled(!habilitar);
		btnFechar.setEnabled(!habilitar);

		txtCodPg.setEnabled(false);
		cbxFiel.setEnabled(habilitar);

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

	public void carregarDadosParaEdicao(Pagamento pagamento) {

		this.modoEdicao = false;

		for (int i = 0; i < cbxFiel.getItemCount(); i++) {

			Fiel f = cbxFiel.getItemAt(i);
			if (f.getCpf().equals(pagamento.getId().getCpf())) {

				cbxFiel.setSelectedItem(f);
				break;
			}

		}

		txtValor.setText(String.valueOf(pagamento.getValor()));
		txtCodPg.setText(String.valueOf(pagamento.getId().getCodPagamento()));

		if (pagamento.getTipo() != null) {
			if (pagamento.getTipo() == 0) {
				rbOferta.setSelected(true);
			} else if (pagamento.getTipo() == 1) {
				rbDizimo.setSelected(true);
			}
		}

		habilitarControles(false);
	}

	private Pagamento getPagamento() throws Exception {

		if (modoEdicao) {

			PagamentoId novoId = new PagamentoId();
			Pagamento pg = new Pagamento();

			Fiel fielSelecionado = (Fiel) cbxFiel.getSelectedItem();
			if (fielSelecionado == null) {
				throw new Exception("Selecione um Fiel primeiro!");
			}

			if (grupo.getSelection() == null) {
				throw new Exception("Selecione o tipo do pagamento!");
			}

			novoId.setCpf(fielSelecionado.getCpf());

			try {

				pg.setTipo(Integer.parseInt(grupo.getSelection().getActionCommand()));
				pg.setId(novoId);
				pg.setValor(Double.parseDouble(txtValor.getText()));
			} catch (NumberFormatException e) {
				mensagem("Valor inválido!", JOptionPane.ERROR_MESSAGE);
				throw e;
			}

			return pg;
		} else {

			PagamentoDao dao = new PagamentoDao();
			Pagamento pg = dao.getPagamentoById(codPagamentoId);

			if (grupo.getSelection() == null) {
				
				throw new Exception("Selecione o tipo do pagamento!");
			}
			try {

				pg.setTipo(Integer.parseInt(grupo.getSelection().getActionCommand()));
				pg.setValor(Double.parseDouble(txtValor.getText()));
			} catch (NumberFormatException e) {
				mensagem("Valor inválido!", JOptionPane.ERROR_MESSAGE);
			}

			modoEdicao = false;
			habilitarControles(false);

			return pg;
		}
	}

	public void carregarDadosParaEdicao(PagamentoId id) {
		
		PagamentoDao dao = new PagamentoDao();
		Pagamento pagamento = null;
		
		codPagamentoId = id;
		
		try {
			pagamento = dao.getPagamentoById(codPagamentoId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		carregarDadosParaEdicao(pagamento);
		
	}
}