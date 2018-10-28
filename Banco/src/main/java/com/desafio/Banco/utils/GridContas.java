package com.desafio.Banco.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.desafio.Banco.dtos.DtoConta;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.DateRenderer;

public class GridContas extends Grid<DtoConta> {

	private static final long serialVersionUID = 1L;

	ArrayList<DtoConta> items = new ArrayList<>();
	ListDataProvider<DtoConta> provider;
	HeaderRow filteringHeader;
	TextField filtroConta, filtroCPF, filtroNome;

	public GridContas() {
		super();
		provider = new ListDataProvider<>(items);
		setDataProvider(provider);
		filteringHeader = appendHeaderRow();
	}
	
	public void criarFiltros() {
		criarFiltroConta();
		criarFiltroCPF();
		criarFiltroNome();
	}
	
	public void criarFiltroConta() {
		filtroConta = new TextField();
		BancoUtil.setContaField(filtroConta);
		criarFiltro(filtroConta, DtoConta.F_ID);
	}
	
	public void criarFiltroCPF() {
		filtroCPF = new TextField();
		BancoUtil.setCPFFormato(filtroCPF);
		criarFiltro(filtroCPF, DtoConta.F_CPF_CLIENTE);
	}
	
	public void criarFiltroNome() {
		filtroNome = new TextField();
		criarFiltro(filtroNome, DtoConta.F_NOME_CLIENTE);
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
		if(filtroConta != null)
			atualizarFiltroConta();
		if(filtroCPF != null)
			atualizarFiltroCPF();
		if(filtroNome != null)
			atualizarFiltroNome();
	}

	private void atualizarFiltroConta() {
		if (filtroConta != null && !filtroConta.getValue().equals("")) {
			provider.addFilter(conta -> {
				if (conta.getId() == null)
					return false;
				return conta.getId().contains(filtroConta.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroCPF() {
		if (filtroCPF != null && !filtroCPF.getValue().equals("")) {
			provider.addFilter(conta -> {
				if (conta.getCpfCliente() == null)
					return false;
				return conta.getCpfCliente().contains(filtroCPF.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroNome() {
		if (filtroNome != null && !filtroNome.getValue().equals("")) {
			provider.addFilter(conta -> {
				if (conta.getNomeCliente() == null)
					return false;
				String lower = conta.getNomeCliente().toLowerCase(Locale.ENGLISH);
				return lower.contains(filtroNome.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	public Column<DtoConta, String> addStringColumn(ValueProvider<DtoConta, String> valueProvider, String columnName,
			double widthInPixels) {
		return addColumn(valueProvider).setId(columnName).setWidth(widthInPixels)
				.setCaption(columnName);
	}

	public Column<DtoConta, Integer> addIntegerColumn(ValueProvider<DtoConta, Integer> valueProvider, String columnName,
			double widthInPixels) {
		return addColumn(valueProvider).setId(columnName).setWidth(widthInPixels)
				.setCaption(columnName);
	}
	
	public Column<DtoConta, Date> addDateColumn(ValueProvider<DtoConta, Date> valueProvider, String columnName, double widthInPixels) {
		return addColumn(valueProvider, new DateRenderer("%1$td/%1$tm/%1$tY %1$tH:%1$tM")).setId(columnName)
				.setWidth(widthInPixels).setCaption(columnName);
	}
	
	public ArrayList<DtoConta> getItems() {
		return items;
	}

	public void setItems(ArrayList<DtoConta> items) {
		this.items = items;
	}

	public ListDataProvider<DtoConta> getProvider() {
		return provider;
	}

	public void setProvider(ListDataProvider<DtoConta> provider) {
		this.provider = provider;
	}

}
