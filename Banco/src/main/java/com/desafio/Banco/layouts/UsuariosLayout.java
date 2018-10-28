package com.desafio.Banco.layouts;

import java.io.File;

import com.desafio.Banco.BancoUI;
import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.utils.GridUsuarios;
import com.desafio.Banco.windows.UsuarioWindow;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class UsuariosLayout extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	final protected String pastaPrincipal = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	final protected String pastaIcones = pastaPrincipal + "/icones";
	FacadeDados facadeDados = new FacadeDados();
	GridUsuarios gridContas;
	BancoUI ui = (BancoUI) UI.getCurrent();
	Button btnUpdate, btnAdicionar;
	
	public UsuariosLayout () {
		btnUpdate = new Button("");
		btnUpdate.setIcon(new FileResource(new File(pastaIcones + "/atualizar.png")));
		btnUpdate.setWidth("30px");
		btnUpdate.addStyleName("button-meta");
		btnUpdate.setDescription("Atualizar");
		btnUpdate.addClickListener(this::atualizar);
		btnAdicionar = new Button("");
		btnAdicionar.setIcon(new FileResource(new File(pastaIcones + "/adicionar.png")));
		btnAdicionar.setWidth("30px");
		btnAdicionar.addStyleName("button-meta");
		btnAdicionar.setDescription("Adicionar usuário");
		btnAdicionar.addClickListener(this::adicionar);
		HorizontalLayout layoutBotoes = new HorizontalLayout(btnUpdate, btnAdicionar);
		configurarGrid();
		addComponents(layoutBotoes, gridContas);
		setExpandRatio(gridContas, 1.0f);
		setSizeFull();
		setSpacing(false);
		setMargin(false);
		update();
	}

	private void configurarGrid() {
		gridContas = new GridUsuarios(this);
		
		gridContas.addStringColumn(DtoUsuario::getCpf,DtoUsuario.F_CPF,150).setWidthUndefined();
		gridContas.addStringColumn(DtoUsuario::getNome,DtoUsuario.F_NOME,150).setWidthUndefined();
		gridContas.addStringColumn(DtoUsuario::getNumConta,DtoUsuario.F_CONTA,150).setWidthUndefined();
		gridContas.addStringColumn(DtoUsuario::getTipoUsuarioDescricao,DtoUsuario.F_TIPO_USUARIO,150).setWidthUndefined();
		gridContas.addStringColumn(DtoUsuario::getEmail,DtoUsuario.F_EMAIL,150).setWidthUndefined();
		gridContas.addStringColumn(DtoUsuario::getEndereco,DtoUsuario.F_ENDERECO,150).setWidthUndefined();
		gridContas.addDateColumn(DtoUsuario::getNascimentoData,DtoUsuario.F_NASCIMENTO,150).setWidthUndefined();
		gridContas.addBotaoReset("Resetar Senha", 150).setWidthUndefined();
		
		gridContas.setSortOrder(GridSortOrder.asc(gridContas.getColumn(DtoUsuario.F_NOME)));
		gridContas.criarFiltros();
		gridContas.setSizeFull();
	}

	public void update() {
		gridContas.getItems().clear();
		gridContas.getItems().addAll(facadeDados.getUsuarios());
		gridContas.getProvider().refreshAll();
	}
	
	public void atualizar(ClickEvent e) {
		update();
	}
	
	public void adicionar(ClickEvent e) {
		UsuarioWindow window = new UsuarioWindow(facadeDados, null);
		ui.adicionarJanela(window);
	}
}