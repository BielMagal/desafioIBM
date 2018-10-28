package com.desafio.Banco.layouts;

import java.io.File;

import com.desafio.Banco.BancoUI;
import com.desafio.Banco.dtos.DtoConta;
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.utils.GridContas;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class SaldoContasLayout extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	final protected String pastaPrincipal = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	final protected String pastaIcones = pastaPrincipal + "/icones";
	FacadeDados facadeDados = new FacadeDados();
	GridContas gridContas;
	BancoUI ui = (BancoUI) UI.getCurrent();
	Button btnUpdate;
	
	public SaldoContasLayout () {
		btnUpdate = new Button("");
		btnUpdate.setIcon(new FileResource(new File(pastaIcones + "/atualizar.png")));
		btnUpdate.setWidth("30px");
		btnUpdate.addStyleName("button-meta");
		btnUpdate.setDescription("Atualizar");
		btnUpdate.addClickListener(this::atualizar);
		configurarGrid();
		addComponents(btnUpdate, gridContas);
		setExpandRatio(gridContas, 1.0f);
		setSizeFull();
		setSpacing(false);
		setMargin(false);
		update();
	}

	private void configurarGrid() {
		gridContas = new GridContas();
		gridContas.addStringColumn(DtoConta::getId,DtoConta.F_ID,150).setWidthUndefined();
		gridContas.addStringColumn(DtoConta::getCpfCliente,DtoConta.F_CPF_CLIENTE,150).setWidthUndefined();
		gridContas.addStringColumn(DtoConta::getNomeCliente,DtoConta.F_NOME_CLIENTE,150).setWidthUndefined();
		gridContas.addDateColumn(DtoConta::getCriacaoData,DtoConta.F_CRIACAO,150).setWidthUndefined();
		gridContas.addStringColumn(DtoConta::getSaldoFormatado,DtoConta.F_SALDO,150).setWidthUndefined();
		
		gridContas.setSortOrder(GridSortOrder.asc(gridContas.getColumn(DtoConta.F_ID)));
		gridContas.criarFiltros();
		gridContas.setSizeFull();
	}

	public void update() {
		gridContas.getItems().clear();
		gridContas.getItems().addAll(facadeDados.getContas());
		gridContas.getProvider().refreshAll();
	}
	
	public void atualizar(ClickEvent e) {
		update();
	}
}