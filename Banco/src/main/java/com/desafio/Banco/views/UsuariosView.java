package com.desafio.Banco.views;

import com.desafio.Banco.BancoUI;
import com.desafio.Banco.layouts.UsuariosLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


public class UsuariosView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private BancoUI ui = (BancoUI) UI.getCurrent();
	private MenuItem item;
	private UsuariosLayout usuarioLayout;
	
	public UsuariosView() {
		usuarioLayout = new UsuariosLayout();
		usuarioLayout.setMargin(false);
		usuarioLayout.setSizeFull();
		addComponent(usuarioLayout);
		setComponentAlignment(usuarioLayout, Alignment.MIDDLE_CENTER);
		setSizeFull();
		setMargin(false);
	}
	
	public void setMenuItem(MenuItem item) {
		this.item = item;		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		item.setStyleName("highlight");
		ui.setItemSelecionado(item);
		ui.setDivisaoPainel(0);
		ui.setDivisaoNavegador(100);
		usuarioLayout.update();
	}
}
