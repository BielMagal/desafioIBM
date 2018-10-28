package com.desafio.Banco.views;

import com.desafio.Banco.BancoUI;
import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.utils.BancoUtil;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


public class LoginView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private TextField campoCPF;
	private PasswordField campoSenha;
	private Button btnLogin;
	private FacadeDados facadeDados = new FacadeDados();
	private BancoUI ui = (BancoUI) UI.getCurrent();

	public LoginView() {
		campoCPF = new TextField("CPF:");
		campoCPF.setWidth("300px");
		campoCPF.setRequiredIndicatorVisible(true);
		BancoUtil.setCPFFormato(campoCPF);

		campoSenha = new PasswordField("Senha:");
		campoSenha.setWidth("300px");
		campoSenha.setRequiredIndicatorVisible(true);
		campoSenha.setValue("");
		campoSenha.addShortcutListener(new ShortcutListener("Login", ShortcutAction.KeyCode.ENTER, null) {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				login(campoCPF.getValue(), campoSenha.getValue());
			}
		});
		btnLogin = new Button("Entrar", event -> {
			login(campoCPF.getValue(), campoSenha.getValue());
		});
		VerticalLayout layout = new VerticalLayout(campoCPF,campoSenha,btnLogin);
		layout.setMargin(false);
		layout.setSizeUndefined();
		addComponent(layout);
		setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		setSizeFull();
		setMargin(false);
	}

	private void limparCampos() {
		campoCPF.setValue("");
		campoSenha.setValue("");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		ui.setItemSelecionado(null);
		ui.setVisibilidadeMenu(false);
		ui.setDivisaoPainel(0);
		ui.setDivisaoNavegador(100);
		limparCampos();
		campoCPF.focus();
	}
	
	private void login(String cpf, String senha) {
		if (cpf == null || senha == null || senha.equals("") || cpf.equals(""))
			return;
		DtoUsuario usuario = facadeDados.verificarCPFSenha(cpf, senha);
		if (usuario != null) {
			getSession().setAttribute("usuario", usuario);
			ui.montarInterface();
		} else {
			Notification.show("CPF ou SENHA incorretos!", Notification.Type.WARNING_MESSAGE);
			limparCampos();
			campoCPF.focus();
		}

	}
}
