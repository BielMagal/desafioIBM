package com.desafio.Banco.layouts;

import org.vaadin.textfieldformatter.NumeralFieldFormatter;

import com.desafio.Banco.BancoUI;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class DepositoLayout extends HorizontalLayout{
	private static final long serialVersionUID = 1L;
	final protected String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	final protected String imgpath = basepath + "/img";
	private BancoUI ui = (BancoUI) UI.getCurrent();
	
	private String tabName;
	TextField contaDestino, valor;
	Button confirmar;
	
	public DepositoLayout () {
		this.tabName = "Deposito";
		contaDestino = new TextField("Conta para depósito");
		valor = new TextField("Valor para depósito");
		new NumeralFieldFormatter(" ", ",", 2).extend(valor);
		confirmar = new Button("Confirmar");
		confirmar.addClickListener(clique -> {
			
		});
		VerticalLayout layout = new VerticalLayout(contaDestino, valor, confirmar);
		addComponent(layout);
		setSizeFull();
		setSpacing(false);
		setMargin(false);
	}

	public String getTabName() {
		return tabName;
	}
}
