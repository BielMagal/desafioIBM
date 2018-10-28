package com.desafio.Banco.utils;

import java.util.ArrayList;
import java.util.Date;

import org.vaadin.dialogs.ConfirmDialog;

import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.layouts.UsuariosLayout;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.DateRenderer;

public class GridUsuarios extends Grid<DtoUsuario> {

	private static final long serialVersionUID = 1L;

	ArrayList<DtoUsuario> items = new ArrayList<>();
	ListDataProvider<DtoUsuario> provider;
	HeaderRow filteringHeader;
	UsuariosLayout layout;
	
	public GridUsuarios(UsuariosLayout layout) {
		super();
		this.layout = layout;
		provider = new ListDataProvider<>(items);
		setDataProvider(provider);
		filteringHeader = appendHeaderRow();
	}

	public Column<DtoUsuario, String> addStringColumn(ValueProvider<DtoUsuario, String> valueProvider, String columnName,
			double widthInPixels) {
		return addColumn(valueProvider).setId(columnName).setWidth(widthInPixels)
				.setCaption(columnName);
	}

	public Column<DtoUsuario, Integer> addIntegerColumn(ValueProvider<DtoUsuario, Integer> valueProvider, String columnName,
			double widthInPixels) {
		return addColumn(valueProvider).setId(columnName).setWidth(widthInPixels)
				.setCaption(columnName);
	}
	
	public Column<DtoUsuario, Date> addDateColumn(ValueProvider<DtoUsuario, Date> valueProvider, String columnName, double widthInPixels) {
		return addColumn(valueProvider, new DateRenderer("%1$td/%1$tm/%1$tY %1$tH:%1$tM")).setId(columnName)
				.setWidth(widthInPixels).setCaption(columnName);
	}
	
	public ArrayList<DtoUsuario> getItems() {
		return items;
	}

	public void setItems(ArrayList<DtoUsuario> items) {
		this.items = items;
	}

	public ListDataProvider<DtoUsuario> getProvider() {
		return provider;
	}

	public void setProvider(ListDataProvider<DtoUsuario> provider) {
		this.provider = provider;
	}
	
	public Column<DtoUsuario, String> addBotaoReset(String name, double width) {
		ButtonRenderer<DtoUsuario> btn = new ButtonRenderer<DtoUsuario>(clickEvent -> {
			DtoUsuario u = (DtoUsuario) clickEvent.getItem();
			ConfirmDialog.show(UI.getCurrent(), "Reset de senha", "Confirmar reset da senha do cliente " + u.getNome() + " para \"abc123\"?", "Sim", "Não", dialog -> {
				if (dialog.isConfirmed()) {
					new FacadeDados().resetarSenha(u);
					Notification.show("Senha resetada!");
					layout.update();
				}
			}).getCancelButton().focus();
		});
		return addColumn(visit -> name, btn).setWidth(width);
	}

}
