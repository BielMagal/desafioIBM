package com.desafio.Banco.layouts;

import java.text.NumberFormat;

import com.desafio.Banco.BancoUI;
import com.desafio.Banco.dtos.DtoTransacao;
import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.facades.FacadeTransacao;
import com.desafio.Banco.facades.FacadeUsuario;
import com.desafio.Banco.utils.GridTransacoes;
import com.desafio.Banco.windows.DepositoWindow;
import com.desafio.Banco.windows.SaqueWindow;
import com.desafio.Banco.windows.TransferenciaWindow;
import com.desafio.BancoModel.model.Conta;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PrincipalLayout extends HorizontalLayout{
	private static final long serialVersionUID = 1L;
	final protected String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	final protected String imgpath = basepath + "/img";
	TextArea areaDados;
	FacadeUsuario facadeUsuario = new FacadeUsuario();
	FacadeTransacao facadeTransacoes = new FacadeTransacao();
	DtoUsuario usuario;
	Button btnSaque, btnDeposito, btnTransferencia;
	NumberFormat formatar = NumberFormat.getCurrencyInstance();
	GridTransacoes gridTransacoes;
	BancoUI ui = (BancoUI) UI.getCurrent();
	
	public PrincipalLayout (DtoUsuario usuario) {
		this.usuario = usuario;
		areaDados = new TextArea();
		atualizarAreaDados();
		HorizontalLayout superiorLayout = criarLayoutSuperior();
		VerticalLayout extratoLayout = criarLayoutExtrato();
		extratoLayout.setSizeFull();
		VerticalLayout principalLayout = new VerticalLayout(superiorLayout, extratoLayout);
		principalLayout.setExpandRatio(superiorLayout, 0.2f);
		principalLayout.setExpandRatio(extratoLayout, 0.8f);
		principalLayout.setComponentAlignment(superiorLayout, Alignment.TOP_CENTER);
		principalLayout.setComponentAlignment(extratoLayout, Alignment.MIDDLE_CENTER);
		principalLayout.setSizeFull();
		addComponent(principalLayout);
		setSizeFull();
		setSpacing(true);
		setMargin(true);
	}

	private void atualizarAreaDados() {
		Conta conta = facadeUsuario.getConta(Integer.valueOf(usuario.getNumConta()));
		areaDados.setValue("Cliente: " + usuario.getNome()
				+ "\nConta: " + conta.getId()
				+ "\nSaldo: " + formatar.format(conta.getSaldo()));
	}
	
	private HorizontalLayout criarLayoutSuperior() {
		btnSaque = new Button("Realizar Saque");
		btnSaque.addClickListener(click -> {
			SaqueWindow window = new SaqueWindow(facadeTransacoes, this, facadeTransacoes.getConta(Integer.valueOf(usuario.getNumConta())));
			ui.adicionarJanela(window);
		});
		btnDeposito = new Button("Realizar Deposito");
		btnDeposito.addClickListener(click -> {
			DepositoWindow window = new DepositoWindow(facadeTransacoes, this, facadeTransacoes.getConta(Integer.valueOf(usuario.getNumConta())));
			ui.adicionarJanela(window);
		});
		btnTransferencia = new Button("Realizar Transferência");
		btnTransferencia.addClickListener(click -> {
			TransferenciaWindow window = new TransferenciaWindow(facadeTransacoes, this, facadeTransacoes.getConta(Integer.valueOf(usuario.getNumConta())));
			ui.adicionarJanela(window);
		});
		HorizontalLayout layout = new HorizontalLayout(areaDados, btnSaque, btnDeposito, btnTransferencia);
		layout.setComponentAlignment(areaDados, Alignment.TOP_LEFT);
		layout.setComponentAlignment(btnSaque, Alignment.TOP_CENTER);
		layout.setComponentAlignment(btnDeposito, Alignment.TOP_CENTER);
		layout.setComponentAlignment(btnTransferencia, Alignment.TOP_CENTER);
		layout.setMargin(true);
		return layout;
	}

	private VerticalLayout criarLayoutExtrato() {
		HorizontalLayout opcoes = new HorizontalLayout();
		gridTransacoes = new GridTransacoes();
		gridTransacoes.addDateColumn(DtoTransacao::getData,DtoTransacao.F_DATA,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getTipoTransacaoNome,DtoTransacao.F_TIPO_TRANSACAO,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getUsuarioOrigem,DtoTransacao.F_USUARIO_ORIGEM,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getContaOrigem,DtoTransacao.F_CONTA_ORIGEM,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getUsuarioDestino,DtoTransacao.F_USUARIO_DESTINO,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getContaDestino,DtoTransacao.F_CONTA_DESTINO,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getValorFormatado,DtoTransacao.F_VALOR,150).setWidthUndefined();
		
		gridTransacoes.setSortOrder(GridSortOrder.desc(gridTransacoes.getColumn(DtoTransacao.F_DATA)));	
		gridTransacoes.setSizeFull();
		gridTransacoes.setCaption("Transações");
		VerticalLayout layout = new VerticalLayout(opcoes, gridTransacoes);
		layout.setExpandRatio(opcoes, 0.2f);
		layout.setExpandRatio(gridTransacoes, 0.8f);
		layout.setSizeFull();
		return layout;
	}


	public void update() {
		gridTransacoes.getItems().clear();
		gridTransacoes.getItems().addAll(facadeTransacoes.getTransacoesUsuario());
		gridTransacoes.getProvider().refreshAll();
		atualizarAreaDados();
	}
	
}
