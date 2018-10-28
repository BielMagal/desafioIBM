package com.desafio.Banco.utils;

import java.util.ArrayList;
import java.util.Date;

import com.desafio.Banco.dtos.DtoConta;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.DateRenderer;

public class GridContas extends Grid<DtoConta> {

	private static final long serialVersionUID = 1L;

	ArrayList<DtoConta> items = new ArrayList<>();
	ListDataProvider<DtoConta> provider;
	HeaderRow filteringHeader;

	public GridContas() {
		super();
		provider = new ListDataProvider<>(items);
		setDataProvider(provider);
		filteringHeader = appendHeaderRow();
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
