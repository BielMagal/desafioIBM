package com.desafio.Banco.views;

import com.desafio.Banco.BancoUI;
import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.layouts.PrincipalLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


public class PrincipalView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private BancoUI ui = (BancoUI) UI.getCurrent();
	private MenuItem item;
	private PrincipalLayout principalLayout;
	
	public PrincipalView(DtoUsuario usuario) {
		principalLayout = new PrincipalLayout(usuario);
		principalLayout.setMargin(false);
		principalLayout.setSizeFull();
		addComponent(principalLayout);
		setComponentAlignment(principalLayout, Alignment.MIDDLE_CENTER);
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
		principalLayout.update();
	}
}
