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
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.utils.GridTransacoes;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class RelatorioLayout extends HorizontalLayout{
	private static final long serialVersionUID = 1L;
	final protected String pastaPrincipal = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	final protected String pastaIcones = pastaPrincipal + "/icones";
	FacadeDados facadeDados = new FacadeDados();
	Button btnGerar;
	NumberFormat formatar = NumberFormat.getCurrencyInstance();
	GridTransacoes gridTransacoes;
	BancoUI ui = (BancoUI) UI.getCurrent();
	DateField fieldInicio, fieldFim;
	
	public RelatorioLayout () {
		HorizontalLayout superiorLayout = criarLayoutSuperior();
		configurarGrid();
		VerticalLayout relatorioLayout = new VerticalLayout(superiorLayout, gridTransacoes);
		relatorioLayout.setComponentAlignment(superiorLayout, Alignment.TOP_CENTER);
		relatorioLayout.setComponentAlignment(gridTransacoes, Alignment.TOP_CENTER);
		relatorioLayout.setExpandRatio(superiorLayout, 0.2f);
		relatorioLayout.setExpandRatio(gridTransacoes, 0.8f);
		relatorioLayout.setSizeFull();
		gridTransacoes.setSizeFull();
		addComponent(relatorioLayout);
		setSizeFull();
		setSpacing(false);
		setMargin(false);
		update();
	}

	private HorizontalLayout criarLayoutSuperior() {
		fieldInicio = new DateField("Início");
		fieldInicio.setValue(LocalDate.now().minusDays(7));
		fieldInicio.setDateFormat("dd/MM/yyyy");
		fieldFim = new DateField("Fim");
		fieldFim.setValue(LocalDate.now());
		fieldFim.setDateFormat("dd/MM/yyyy");
		btnGerar = new Button("");
		btnGerar.setIcon(new FileResource(new File(pastaIcones + "/gerar.png")));
		btnGerar.setWidth("30px");
		btnGerar.addStyleName("button-meta");
		btnGerar.setDescription("Gerar relatório");
		btnGerar.addClickListener(this::gerar);
		HorizontalLayout opcoes = new HorizontalLayout(fieldInicio, fieldFim, btnGerar);
		opcoes.setComponentAlignment(btnGerar, Alignment.MIDDLE_LEFT);
		return opcoes;
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
			if(!tt.utilizaOrigem())
				return "positivo";
			else if(!tt.utilizaDestino())
				return "negativo";
			else
				return "neutro";
		});
	}

	public void update() {
		gridTransacoes.getItems().clear();
		DateTime inicio = new DateTime(Date.from(fieldInicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()).withTimeAtStartOfDay();
		DateTime fim = new DateTime(Date.from(fieldFim.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()).withTimeAtStartOfDay().plusDays(1);
		gridTransacoes.getItems().addAll(facadeDados.getTransacoesPorDataConta(inicio.toDate(), fim.toDate(), null));
		gridTransacoes.getProvider().refreshAll();
	}
	
	public void gerar(ClickEvent e) {
		update();
	}
}