package com.desafio.Banco.views;

import com.desafio.Banco.BancoUI;
import com.desafio.Banco.layouts.DepositoLayout;
import com.desafio.Banco.layouts.SaqueLayout;
import com.desafio.Banco.layouts.TransferenciaLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


public class TransacaoView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private BancoUI ui = (BancoUI) UI.getCurrent();
	private MenuItem item;
	private TabSheet tabs;

	public TransacaoView() {
		tabs = new TabSheet();
		SaqueLayout saqueLayout = new SaqueLayout();
		DepositoLayout depositoLayout = new DepositoLayout();
		TransferenciaLayout transferenciaLayout = new TransferenciaLayout();
		tabs.setSizeFull();
		tabs.setSelectedTab(saqueLayout);
		addComponent(tabs);
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
