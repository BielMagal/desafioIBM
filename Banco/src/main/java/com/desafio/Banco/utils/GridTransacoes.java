package com.desafio.Banco.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.desafio.Banco.dtos.DtoTransacao;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.DateRenderer;

public class GridTransacoes extends Grid<DtoTransacao> {

	private static final long serialVersionUID = 1L;

	ArrayList<DtoTransacao> items = new ArrayList<>();
	ListDataProvider<DtoTransacao> provider;
	HeaderRow filteringHeader;
	TextField filtroTransacao, filtroClienteOrigem, filtroContaOrigem, filtroClienteDestino, filtroContaDestino;

	public GridTransacoes() {
		super();
		provider = new ListDataProvider<>(items);
		setDataProvider(provider);
		filteringHeader = appendHeaderRow();
	}

	public void criarFiltros() {
		criarFiltroTransacao();
		criarFiltroClienteOrigem();
		criarFiltroContaOrigem();
		criarFiltroClienteDestino();
		criarFiltroContaDestino();
	}

	public void criarFiltroTransacao() {
		filtroTransacao = new TextField();
		criarFiltro(filtroTransacao, DtoTransacao.F_TIPO_TRANSACAO);
	}

	public void criarFiltroClienteOrigem() {
		filtroClienteOrigem = new TextField();
		criarFiltro(filtroClienteOrigem, DtoTransacao.F_USUARIO_ORIGEM);
	}

	public void criarFiltroContaOrigem() {
		filtroContaOrigem = new TextField();
		BancoUtil.setContaField(filtroContaOrigem);
		criarFiltro(filtroContaOrigem, DtoTransacao.F_CONTA_ORIGEM);
	}

	public void criarFiltroClienteDestino() {
		filtroClienteDestino = new TextField();
		criarFiltro(filtroClienteDestino, DtoTransacao.F_USUARIO_DESTINO);
	}

	public void criarFiltroContaDestino() {
		filtroContaDestino = new TextField();
		BancoUtil.setContaField(filtroContaDestino);
		criarFiltro(filtroContaDestino, DtoTransacao.F_CONTA_DESTINO);
	}

	public void criarFiltro(TextField filtro, String colunaId) {
		filtro.setWidth("100%");
		filtro.setHeight("25px");
		filtro.addValueChangeListener(event -> {
			atualizarFiltros();
		});
		filteringHeader.getCell(colunaId).setComponent(filtro);
	}

	private void atualizarFiltros() {
		provider.clearFilters();
		if (filtroTransacao != null)
			atualizarFiltroTransacao();
		if (filtroClienteOrigem != null)
			atualizarFiltroClienteOrigem();
		if (filtroContaOrigem != null)
			atualizarFiltroContaOrigem();
		if (filtroClienteDestino != null)
			atualizarFiltroClienteDestino();
		if (filtroContaDestino != null)
			atualizarFiltroContaDestino();
	}

	private void atualizarFiltroTransacao() {
		if (filtroTransacao != null && !filtroTransacao.getValue().equals("")) {
			provider.addFilter(transacao -> {
				if (transacao.getTipoTransacaoNome() == null)
					return false;
				String lower = transacao.getTipoTransacaoNome().toLowerCase(Locale.ENGLISH);
				return lower.contains(filtroTransacao.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroClienteOrigem() {
		if (filtroClienteOrigem != null && !filtroClienteOrigem.getValue().equals("")) {
			provider.addFilter(transacao -> {
				if (transacao.getUsuarioOrigem() == null)
					return false;
				String lower = transacao.getUsuarioOrigem().toLowerCase(Locale.ENGLISH);
				return lower.contains(filtroClienteOrigem.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroContaOrigem() {
		if (filtroContaOrigem != null && !filtroContaOrigem.getValue().equals("")) {
			provider.addFilter(conta -> {
				if (conta.getContaOrigem() == null)
					return false;
				return conta.getContaOrigem().contains(filtroContaOrigem.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroClienteDestino() {
		if (filtroClienteDestino != null && !filtroClienteDestino.getValue().equals("")) {
			provider.addFilter(transacao -> {
				if (transacao.getUsuarioDestino() == null)
					return false;
				String lower = transacao.getUsuarioDestino().toLowerCase(Locale.ENGLISH);
				return lower.contains(filtroClienteDestino.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroContaDestino() {
		if (filtroContaDestino != null && !filtroContaDestino.getValue().equals("")) {
			provider.addFilter(conta -> {
				if (conta.getContaDestino() == null)
					return false;
				return conta.getContaDestino().contains(filtroContaDestino.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	public Column<DtoTransacao, String> addStringColumn(ValueProvider<DtoTransacao, String> valueProvider,
			String columnName, double widthInPixels) {
		return addColumn(valueProvider).setId(columnName).setWidth(widthInPixels).setCaption(columnName);
	}

	public Column<DtoTransacao, Integer> addIntegerColumn(ValueProvider<DtoTransacao, Integer> valueProvider,
			String columnName, double widthInPixels) {
		return addColumn(valueProvider).setId(columnName).setWidth(widthInPixels).setCaption(columnName);
	}

	public Column<DtoTransacao, Date> addDateColumn(ValueProvider<DtoTransacao, Date> valueProvider, String columnName,
			double widthInPixels) {
		return addColumn(valueProvider, new DateRenderer("%1$td/%1$tm/%1$tY %1$tH:%1$tM")).setId(columnName)
				.setWidth(widthInPixels).setCaption(columnName);
	}

	public ArrayList<DtoTransacao> getItems() {
		return items;
	}

	public void setItems(ArrayList<DtoTransacao> items) {
		this.items = items;
	}

	public ListDataProvider<DtoTransacao> getProvider() {
		return provider;
	}

	public void setProvider(ListDataProvider<DtoTransacao> provider) {
		this.provider = provider;
	}

}
