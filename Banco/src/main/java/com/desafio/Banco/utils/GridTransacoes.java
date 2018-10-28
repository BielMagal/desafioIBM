package com.desafio.Banco.utils;

import java.util.ArrayList;
import java.util.Date;

import com.desafio.Banco.dtos.DtoTransacao;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.DateRenderer;

public class GridTransacoes extends Grid<DtoTransacao> {

	private static final long serialVersionUID = 1L;

	ArrayList<DtoTransacao> items = new ArrayList<>();
	ListDataProvider<DtoTransacao> provider;
	HeaderRow filteringHeader;

	public GridTransacoes() {
		super();
		provider = new ListDataProvider<>(items);
		setDataProvider(provider);
		filteringHeader = appendHeaderRow();
	}

	public Column<DtoTransacao, String> addStringColumn(ValueProvider<DtoTransacao, String> valueProvider, String columnName,
			double widthInPixels) {
		return addColumn(valueProvider).setId(columnName).setWidth(widthInPixels)
				.setCaption(columnName);
	}

	public Column<DtoTransacao, Integer> addIntegerColumn(ValueProvider<DtoTransacao, Integer> valueProvider, String columnName,
			double widthInPixels) {
		return addColumn(valueProvider).setId(columnName).setWidth(widthInPixels)
				.setCaption(columnName);
	}
	
	public Column<DtoTransacao, Date> addDateColumn(ValueProvider<DtoTransacao, Date> valueProvider, String columnName, double widthInPixels) {
		return addColumn(valueProvider, new DateRenderer("%1$td/%1$tm %1$tH:%1$tM")).setId(columnName)
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
