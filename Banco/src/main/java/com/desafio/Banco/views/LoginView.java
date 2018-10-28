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
import com.vaadin.ui.Label;
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
	private Label bancoLabel;
	private Label mensagemLabel;

	public LoginView() {
		bancoLabel = new Label("Banco do Gabriel");
		bancoLabel.setStyleName("msg-banco");
		mensagemLabel = new Label("Utilize seu CPF e Senha para entrar. Se não possuir uma conta entre em contato com um de nossos gerentes.");
		mensagemLabel.setStyleName("msg-inicial");
		mensagemLabel.setWidth("450px");
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
		btnLogin.setWidth("300px");
		VerticalLayout layout1 = new VerticalLayout(bancoLabel, mensagemLabel);
		layout1.setComponentAlignment(bancoLabel, Alignment.TOP_CENTER);
		layout1.setComponentAlignment(mensagemLabel, Alignment.TOP_CENTER);
		layout1.setHeight("220px");
		VerticalLayout layout2 = new VerticalLayout(campoCPF,campoSenha,btnLogin);
		layout2.setComponentAlignment(campoCPF, Alignment.MIDDLE_CENTER);
		layout2.setComponentAlignment(campoSenha, Alignment.MIDDLE_CENTER);
		layout2.setComponentAlignment(btnLogin, Alignment.MIDDLE_CENTER);
		VerticalLayout layout3 = new VerticalLayout(layout1, layout2);
		addComponent(layout3);
		setComponentAlignment(layout3, Alignment.MIDDLE_CENTER);
		setHeight(100, Unit.PERCENTAGE);
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
