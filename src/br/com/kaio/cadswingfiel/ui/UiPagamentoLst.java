package br.com.kaio.cadswingfiel.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import br.com.kaio.cadswingfiel.dao.PagamentoDao;
import br.com.kaio.cadswingfiel.domain.PagamentoView;

public class UiPagamentoLst {

	private JPanel raiz;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField txtNome;
	private JTextField txtCpf;

	/**
	 * Create the frame.
	 */
	public UiPagamentoLst() {

		raiz = new JPanel();
		raiz.setLayout(null);
		raiz.setPreferredSize(new Dimension(891, 479));

		JButton btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							UiPagamentoFrm window = new UiPagamentoFrm();
							window.show(null);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnAdd.setBounds(831, 122, 41, 23);
		raiz.add(btnAdd);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 149, 851, 309);
		raiz.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] {"CodPagamento", "CPF", "Nome", "Valor"}));
		table.setDefaultEditor(Object.class, null);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					int linha = table.getSelectedRow();
					if (linha != -1) {
						carregarFielNoFormulario(linha);
					}
				}

			}
		});

		Label label = new Label("Lista");
		label.setFont(new Font("Dialog", Font.ITALIC, 12));
		label.setBounds(23, 122, 62, 22);
		raiz.add(label);

		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBounds(162, 56, 712, 28);
		raiz.add(txtNome);

		Label LblNome = new Label("Nome");
		LblNome.setBounds(162, 28, 62, 22);
		raiz.add(LblNome);

		txtCpf = new JTextField();
		txtCpf.setColumns(10);
		txtCpf.setBounds(28, 56, 122, 28);
		raiz.add(txtCpf);

		Label LblCpf = new Label("CPF");
		LblCpf.setBounds(28, 28, 62, 22);
		raiz.add(LblCpf);

		JButton btnPesq = new JButton("Pesquisar");
		btnPesq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nome = txtNome.getText();
				String cpf = txtCpf.getText();

				PagamentoDao dao = new PagamentoDao();

				try {

					List<PagamentoView> lista = dao.buscarPorFiltro(cpf, nome);
					DefaultTableModel modelo = (DefaultTableModel) table.getModel();
					modelo.setRowCount(0);

					for (PagamentoView fe : lista) {
						Object[] linha = { fe.getId().getCodPagamento(), fe.getId().getCpf(), fe.getNome(),
								fe.getValor()};
						modelo.addRow(linha);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		btnPesq.setBounds(784, 84, 90, 28);
		raiz.add(btnPesq);
	}

	public void carregarFielNoFormulario(int linha) {

		int codPg = Integer.parseInt(table.getValueAt(linha, 0).toString());
		String cpf = (String) table.getValueAt(linha, 1);
		String nome = (String) table.getValueAt(linha, 2);
		double valor = Double.parseDouble(table.getValueAt(linha, 3).toString());
//		String telefone = (String) table.getValueAt(linha, 3);
//		String email = (String) table.getValueAt(linha, 4);
//		, telefone, email

		UiPagamentoFrm frm = new UiPagamentoFrm();
		frm.carregarDadosParaEdicao(codPg, cpf, nome, valor);
		frm.show(null);
	}

	public void showFrm() {

		UiFielFrm window = new UiFielFrm();
		window.show(null);
	}

	public void show(JFrame framePai) {

		JDialog dialog = new JDialog(framePai, "Lista pagamento", true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setContentPane(raiz);
		dialog.pack();
		dialog.setLocationRelativeTo(framePai);
		dialog.setResizable(false);

		dialog.setVisible(true);
	}
}
