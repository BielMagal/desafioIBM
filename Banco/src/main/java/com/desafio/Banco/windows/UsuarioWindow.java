package com.desafio.Banco.windows;

import java.util.Calendar;

import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.utils.BancoUtil;
import com.vaadin.data.Binder;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class UsuarioWindow extends Window {
	private static final long serialVersionUID = 1L;

	private FacadeDados facadeDados;

	Binder<DtoUsuario> binder;
	DateField campoNascimento;
	TextField campoCpf, campoEmail, campoEndereco, campoConta, campoNome;
	PasswordField campoSenha, campoNovaSenha1, campoNovaSenha2;

	HorizontalLayout layoutBtns;
	Button btnConfirmar, btnCancelar;
	DtoUsuario usuario;
	
	public UsuarioWindow(FacadeDados facadeDados, DtoUsuario usuario) {
		super();
		this.facadeDados = facadeDados;
		this.usuario = usuario;
		this.setCaption("Configurações");
		this.setWidth("400px");
		btnConfirmar = new Button("Confirmar", this::save);
		btnCancelar = new Button("Cancelar", this::cancel);

		layoutBtns = new HorizontalLayout(btnConfirmar, btnCancelar);
		layoutBtns.setSpacing(true);

		campoCpf = new TextField("CPF");
		campoCpf.setValue(usuario.getCpf());
		campoCpf.setEnabled(false);
		campoNome = new TextField("Nome");
		campoNome.setValue(usuario.getNome());
		campoNome.setRequiredIndicatorVisible(true);
		campoConta = new TextField("Conta");
		campoConta.setValue(usuario.getNumConta());
		campoConta.setEnabled(false);
		campoEmail = new TextField("E-mail");
		campoEmail.setValue(usuario.getEmail());
		campoEmail.setRequiredIndicatorVisible(true);
		campoEndereco = new TextField("Endereço");
		campoEndereco.setValue(usuario.getEndereco());
		campoEndereco.setRequiredIndicatorVisible(true);
		campoNovaSenha1 = new PasswordField("Nova senha");
		campoNovaSenha2 = new PasswordField("Repita a nova senha");
		campoSenha = new PasswordField("Senha atual");
		campoSenha.setRequiredIndicatorVisible(true);

		binder = new Binder<>();
		binder.forField(campoCpf).bind(DtoUsuario::getCpf, DtoUsuario::setCpf);
		binder.forField(campoNome).withValidator(nome -> !nome.equals(""), "campo obrigatório").bind(DtoUsuario::getNome,
				DtoUsuario::setNome);
		binder.forField(campoConta).bind(DtoUsuario::getNumConta, DtoUsuario::setNumConta);
		binder.forField(campoEmail).withValidator(BancoUtil.getOptionalMailValidator()).bind(DtoUsuario::getEmail, DtoUsuario::setEmail);
		binder.forField(campoEndereco).withValidator(endereco -> !endereco.equals(""), "campo obrigatório").bind(DtoUsuario::getEndereco, DtoUsuario::setEndereco);
		VerticalLayout content = new VerticalLayout(campoCpf, campoNome, campoConta, campoEmail, campoEndereco, campoNovaSenha1, campoNovaSenha2, campoSenha, layoutBtns);
		content.setComponentAlignment(layoutBtns, Alignment.BOTTOM_RIGHT);
		content.setSpacing(true);
		content.setMargin(true);

		this.setContent(content);
		this.setResizable(false);
		this.setModal(true);
		this.center();
		this.setSizeUndefined();
	}

	public void cancel(Button.ClickEvent event) {
		this.close();
	}

	public void save(Button.ClickEvent event) {
		if (!validation())
			return;
		try {
			Calendar data = Calendar.getInstance();
			data.setTimeInMillis(System.currentTimeMillis());
			binder.writeBeanIfValid(usuario);
			if(campoNovaSenha1.getValue().length() > 3)
				usuario.setSenha(campoNovaSenha1.getValue());
			facadeDados.salvarUsuario(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Erro ao modificar configurações! Tente novamente");
			return;
		}
		Notification.show("Modificações realizadas com sucesso!");
		this.close();
	}

	public boolean validation() {
		if(!campoNovaSenha1.getValue().equals("") || !campoNovaSenha2.getValue().equals("")){
			if(!campoNovaSenha1.getValue().equals(campoNovaSenha2.getValue())) {
				Notification.show("Senhas não coincidem.", Notification.Type.WARNING_MESSAGE);
				campoNovaSenha1.focus();
				return false;
			}
			if (campoNovaSenha1.getValue().length() < 4 || !campoNovaSenha1.getValue().matches(".*\\d.*")) {
				Notification.show("Mínimo de 4 caracteres sendo pelo menos 1 numérico.", Notification.Type.WARNING_MESSAGE);
				campoNovaSenha1.focus();
				return false;
			}
		}
		if(campoEmail.getValue().equals("")) {
			Notification.show("Campo obrigatório!", Notification.Type.WARNING_MESSAGE);
			campoEmail.focus();
			return false;
		}
		if(facadeDados.verificarCPFSenha(campoCpf.getValue(), campoSenha.getValue()) == null) {
			Notification.show("Senha inválida.", Notification.Type.WARNING_MESSAGE);
			campoSenha.focus();
			return false;
		}
		return true;
	}
}
