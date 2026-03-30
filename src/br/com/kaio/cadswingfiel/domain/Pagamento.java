package br.com.kaio.cadswingfiel.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamento")
public class Pagamento {

	@EmbeddedId
	private PagamentoId id;

	@Column(name = "valor", nullable = false)
	private Double valor;

	@Column(name = "tipo", nullable = false)
	private Integer tipo;

	public Pagamento() {
	}

	public Pagamento(PagamentoId id, Double valor, Integer tipo) {
		this.id = id;
		this.valor = valor;
		this.tipo = tipo;
	}

	public PagamentoId getId() {
		return id;
	}

	public void setId(PagamentoId id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
}