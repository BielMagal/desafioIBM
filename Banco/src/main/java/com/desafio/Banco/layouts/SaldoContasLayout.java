package com.desafio.Banco.layouts;

import com.desafio.Banco.BancoUI;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class SaldoContasLayout extends HorizontalLayout{
	private static final long serialVersionUID = 1L;
	final protected String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	final protected String imgpath = basepath + "/img";
	private BancoUI ui = (BancoUI) UI.getCurrent();
	
	public SaldoContasLayout () {
		VerticalLayout layout = new VerticalLayout();
		addComponent(layout);
		setSizeFull();
		setSpacing(false);
		setMargin(false);
	}
}
