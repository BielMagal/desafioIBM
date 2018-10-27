package com.desafio.Banco.views;

import com.desafio.Banco.BancoUI;
import com.desafio.Banco.layouts.RelatorioLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


public class RelatorioView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private BancoUI ui = (BancoUI) UI.getCurrent();
	private MenuItem item;

	public RelatorioView() {
		RelatorioLayout relatorioLayout = new RelatorioLayout();
		relatorioLayout.setMargin(false);
		relatorioLayout.setSizeUndefined();
		addComponent(relatorioLayout);
		setComponentAlignment(relatorioLayout, Alignment.MIDDLE_CENTER);
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
	}
}
