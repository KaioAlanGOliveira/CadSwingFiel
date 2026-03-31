package br.com.kaio.cadswingfiel.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "vw_fieis_pagamento_busca")
public class PagamentoView {

	@EmbeddedId
	private PagamentoId id;
	private double valor;
	private String nome;
	private Integer tipo;

	public PagamentoView() {
		super();
	}

	public PagamentoId getId() {
		return id;
	}

	public void setId(PagamentoId id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
