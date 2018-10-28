package com.desafio.Banco.layouts;

import java.io.File;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.joda.time.DateTime;

import com.desafio.Banco.BancoUI;
import com.desafio.Banco.dtos.DtoTipoTransacao;
import com.desafio.Banco.dtos.DtoTransacao;
import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.utils.GridTransacoes;
import com.desafio.Banco.windows.DepositoWindow;
import com.desafio.Banco.windows.SaqueWindow;
import com.desafio.Banco.windows.TransferenciaWindow;
import com.desafio.BancoModel.model.Conta;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PrincipalLayout extends HorizontalLayout{
	private static final long serialVersionUID = 1L;
	final protected String pastaPrincipal = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	final protected String pastaIcones = pastaPrincipal + "/icones";
	TextArea areaDados;
	FacadeDados facadeDados = new FacadeDados();
	DtoUsuario usuario;
	Button btnSaque, btnDeposito, btnTransferencia, btnGerar;
	NumberFormat formatar = NumberFormat.getCurrencyInstance();
	GridTransacoes gridTransacoes;
	BancoUI ui = (BancoUI) UI.getCurrent();
	DateField fieldInicio, fieldFim;
	
	public PrincipalLayout (DtoUsuario usuario) {
		this.usuario = usuario;
		areaDados = new TextArea();
		HorizontalLayout superiorLayout = criarLayoutSuperior();
		configurarGrid();
		VerticalLayout principalLayout = new VerticalLayout(superiorLayout, gridTransacoes);
		principalLayout.setComponentAlignment(superiorLayout, Alignment.TOP_CENTER);
		principalLayout.setComponentAlignment(gridTransacoes, Alignment.TOP_CENTER);
		principalLayout.setExpandRatio(superiorLayout, 0.35f);
		principalLayout.setExpandRatio(gridTransacoes, 0.65f);
		principalLayout.setSizeFull();
		addComponent(principalLayout);
		setSizeFull();
		setSpacing(false);
		setMargin(false);
		update();
	}

	private void atualizarAreaDados() {
		Conta conta = facadeDados.getConta(Integer.valueOf(usuario.getNumConta()));
		areaDados.setValue("Cliente: " + usuario.getNome()
				+ "\nConta: " + conta.getId()
				+ "\nSaldo: " + formatar.format(conta.getSaldo()));
	}
	
	private HorizontalLayout criarLayoutSuperior() {
		btnSaque = new Button("Realizar Saque");
		btnSaque.addClickListener(click -> {
			SaqueWindow window = new SaqueWindow(facadeDados, this, facadeDados.getConta(Integer.valueOf(usuario.getNumConta())));
			ui.adicionarJanela(window);
		});
		btnDeposito = new Button("Realizar Deposito");
		btnDeposito.addClickListener(click -> {
			DepositoWindow window = new DepositoWindow(facadeDados, this, facadeDados.getConta(Integer.valueOf(usuario.getNumConta())));
			ui.adicionarJanela(window);
		});
		btnTransferencia = new Button("Realizar Transferência");
		btnTransferencia.addClickListener(click -> {
			TransferenciaWindow window = new TransferenciaWindow(facadeDados, this, facadeDados.getConta(Integer.valueOf(usuario.getNumConta())));
			ui.adicionarJanela(window);
		});
		HorizontalLayout transacoes = new HorizontalLayout(btnSaque, btnDeposito, btnTransferencia);
		transacoes.setComponentAlignment(btnSaque, Alignment.TOP_CENTER);
		transacoes.setComponentAlignment(btnDeposito, Alignment.TOP_CENTER);
		transacoes.setComponentAlignment(btnTransferencia, Alignment.TOP_CENTER);

		fieldInicio = new DateField("Início");
		fieldInicio.setValue(LocalDate.now().minusDays(7));
		fieldFim = new DateField("Fim");
		fieldFim.setValue(LocalDate.now());
		btnGerar = new Button("");
		btnGerar.setIcon(new FileResource(new File(pastaIcones + "/gerar.png")));
		btnGerar.setWidth("30px");
		btnGerar.addStyleName("button-meta");
		btnGerar.setDescription("Gerar extrato");
		btnGerar.addClickListener(this::gerar);
		HorizontalLayout extrato = new HorizontalLayout(fieldInicio, fieldFim, btnGerar);
		extrato.setComponentAlignment(btnGerar, Alignment.MIDDLE_LEFT);
		
		VerticalLayout opcoes = new VerticalLayout(transacoes, extrato);
		HorizontalLayout layout = new HorizontalLayout(areaDados, opcoes);
		layout.setComponentAlignment(areaDados, Alignment.MIDDLE_LEFT);
		layout.setComponentAlignment(opcoes, Alignment.MIDDLE_CENTER);
		layout.setMargin(false);
		return layout;
	}

	private void configurarGrid() {
		gridTransacoes = new GridTransacoes();
		gridTransacoes.addDateColumn(DtoTransacao::getData,DtoTransacao.F_DATA,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getTipoTransacaoNome,DtoTransacao.F_TIPO_TRANSACAO,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getUsuarioOrigem,DtoTransacao.F_USUARIO_ORIGEM,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getContaOrigem,DtoTransacao.F_CONTA_ORIGEM,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getUsuarioDestino,DtoTransacao.F_USUARIO_DESTINO,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getContaDestino,DtoTransacao.F_CONTA_DESTINO,150).setWidthUndefined();
		gridTransacoes.addStringColumn(DtoTransacao::getValorFormatado,DtoTransacao.F_VALOR,150).setWidthUndefined();
		
		gridTransacoes.setSortOrder(GridSortOrder.desc(gridTransacoes.getColumn(DtoTransacao.F_DATA)));	
		gridTransacoes.setStyleGenerator(cell -> {
			DtoTipoTransacao tt = cell.getTipoTransacao();
			if(!tt.utilizaOrigem() || (tt.utilizaDestino() && !cell.getContaOrigem().equals(usuario.getNumConta())))
				return "positivo";
			return "negativo";
		});
		gridTransacoes.setSizeFull();
	}

	public void update() {
		gridTransacoes.getItems().clear();
		DateTime inicio = new DateTime(Date.from(fieldInicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()).withTimeAtStartOfDay();
		DateTime fim = new DateTime(Date.from(fieldFim.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()).withTimeAtStartOfDay().plusDays(1);
		gridTransacoes.getItems().addAll(facadeDados.getTransacoesPorDataConta(inicio.toDate(), fim.toDate(), Integer.valueOf(usuario.getNumConta())));
		gridTransacoes.getProvider().refreshAll();
		atualizarAreaDados();
	}
	
	public void gerar(ClickEvent e) {
		update();
	}
}
