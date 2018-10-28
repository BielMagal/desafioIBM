package com.desafio.BancoModel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the transacao database table.
 * 
 */
@Entity
@Table(name = "transacao")
@NamedQuery(name = "Transacao.findAll", query = "SELECT t FROM Transacao t")
public class Transacao implements Serializable, EntidadeBase {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="Transacao_Gen", 
	table="id_gen", 
	pkColumnName="id_name", 
	valueColumnName="id_val", 
	pkColumnValue="TransacaoAtualId",
	allocationSize=1,
	initialValue=1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="Transacao_Gen")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	@ManyToOne
	@JoinColumn(name = "conta_origem_id")
	private Conta contaOrigem;

	@ManyToOne
	@JoinColumn(name = "conta_destino_id")
	private Conta contaDestino;

	@ManyToOne
	@JoinColumn(name = "tipo_transacao_id")
	private TiposTransacoes tipoTransacao;
	
	private Double valor;
	
	public Transacao() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Conta getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public TiposTransacoes getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(TiposTransacoes tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}
}