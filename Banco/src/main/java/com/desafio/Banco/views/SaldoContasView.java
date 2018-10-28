package com.desafio.Banco.views;

import com.desafio.Banco.BancoUI;
import com.desafio.Banco.layouts.SaldoContasLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


public class SaldoContasView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private BancoUI ui = (BancoUI) UI.getCurrent();
	private MenuItem item;
	private SaldoContasLayout saldoContasLayout;
	
	public SaldoContasView() {
		saldoContasLayout = new SaldoContasLayout();
		saldoContasLayout.setMargin(false);
		saldoContasLayout.setSizeFull();
		addComponent(saldoContasLayout);
		setComponentAlignment(saldoContasLayout, Alignment.MIDDLE_CENTER);
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
		saldoContasLayout.update();
	}
}
