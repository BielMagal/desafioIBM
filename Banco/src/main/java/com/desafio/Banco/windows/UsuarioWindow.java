package com.desafio.Banco.windows;

import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.desafio.Banco.dtos.DtoTipoUsuario;
import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.utils.BancoUtil;
import com.vaadin.data.Binder;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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
	ComboBox<DtoTipoUsuario> cbTipos;

	HorizontalLayout layoutBtns;
	Button btnConfirmar, btnCancelar;
	DtoUsuario usuario;
	boolean novoUsuario;
	
	public UsuarioWindow(FacadeDados facadeDados, DtoUsuario usuario) {
		super();
		this.facadeDados = facadeDados;
		if(usuario == null) {
			novoUsuario = true;
			usuario = new DtoUsuario();
		}
		this.usuario = usuario;
		if(novoUsuario)
			this.setCaption("Criar usuário");
		else
			this.setCaption("Configurações");
		this.setWidth("400px");
		btnConfirmar = new Button("Confirmar", this::save);
		btnCancelar = new Button("Cancelar", this::cancel);

		layoutBtns = new HorizontalLayout(btnConfirmar, btnCancelar);
		layoutBtns.setSpacing(true);

		campoCpf = new TextField("CPF");
		campoNome = new TextField("Nome");
		campoNome.setRequiredIndicatorVisible(true);
		campoEmail = new TextField("E-mail");
		campoEmail.setRequiredIndicatorVisible(true);
		campoEndereco = new TextField("Endereço");
		campoEndereco.setRequiredIndicatorVisible(true);
		campoNascimento = new DateField("Data de nascimento");
		campoNascimento.setRequiredIndicatorVisible(true);
		campoNascimento.setDateFormat("dd/MM/yyyy");
		binder = new Binder<>();
		if(!novoUsuario) {
			campoCpf.setValue(usuario.getCpf());
			campoNome.setValue(usuario.getNome());
			campoEmail.setValue(usuario.getEmail());
			campoEndereco.setValue(usuario.getEndereco());
			campoNascimento.setValue(usuario.getLocalDate());
			campoNovaSenha1 = new PasswordField("Nova senha");
			campoNovaSenha2 = new PasswordField("Repita a nova senha");
			campoCpf.setEnabled(false);
			campoSenha = new PasswordField("Senha atual");
			campoSenha.setRequiredIndicatorVisible(true);
			campoConta = new TextField("Conta");
			campoConta.setValue(usuario.getNumConta());
			campoConta.setEnabled(false);
			binder.forField(campoCpf).bind(DtoUsuario::getCpf, DtoUsuario::setCpf);
			binder.forField(campoConta).bind(DtoUsuario::getNumConta, DtoUsuario::setNumConta);
		}else {
			cbTipos = new ComboBox<DtoTipoUsuario>("Tipo usuário");
			cbTipos.setItemCaptionGenerator(DtoTipoUsuario::getDescricao);
			ArrayList<DtoTipoUsuario> lista = facadeDados.getTiposUsuarios();
			cbTipos.setItems(lista);
			if(lista.size()>0) {
				cbTipos.setValue(lista.get(lista.size()-1));
				cbTipos.setEmptySelectionAllowed(false);
			}
			campoNovaSenha1 = new PasswordField("Senha");
			campoNovaSenha2 = new PasswordField("Repita a senha");
			campoCpf.setRequiredIndicatorVisible(true);
			BancoUtil.setCPFFormato(campoCpf);
			campoNovaSenha1.setRequiredIndicatorVisible(true);
			campoNovaSenha2.setRequiredIndicatorVisible(true);
			binder.forField(campoCpf).withValidator(cpf -> (cpf.length() == 14), "preencha o CPF corretamente.").bind(DtoUsuario::getCpf, DtoUsuario::setCpf);
		}

		binder.forField(campoNome).bind(DtoUsuario::getNome, DtoUsuario::setNome);
		binder.forField(campoNascimento).withValidator(data -> {
			if(data == null)
				return false;
			LocalDate now = LocalDate.now();
			int anos = now.getYear() - data.getYear();
			if(now.getDayOfYear() - data.getDayOfYear() < 0)
				anos--;
			if(anos > 150 || anos < 16)
				return false;
			return true;
		}, "Data inválida, idade permitida: 16 à 150 anos").bind(DtoUsuario::getLocalDate,DtoUsuario::setLocalDate);
		binder.forField(campoEmail).withValidator(BancoUtil.getOptionalMailValidator()).bind(DtoUsuario::getEmail, DtoUsuario::setEmail);
		binder.forField(campoEndereco).withValidator(endereco -> !endereco.equals(""), "campo obrigatório").bind(DtoUsuario::getEndereco, DtoUsuario::setEndereco);
		VerticalLayout content;
		if(novoUsuario)
			content = new VerticalLayout(campoCpf, campoNome, campoEmail, campoNascimento, cbTipos, campoEndereco, campoNovaSenha1, campoNovaSenha2, layoutBtns);
		else
			content = new VerticalLayout(campoCpf, campoNome, campoConta, campoEmail, campoNascimento, campoEndereco, campoNovaSenha1, campoNovaSenha2, campoSenha, layoutBtns);
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
			binder.writeBeanIfValid(usuario);
			if(campoNovaSenha1.getValue().length() > 3)
				usuario.setSenha(campoNovaSenha1.getValue());
			if(novoUsuario)
				usuario.setTipoUsuario(cbTipos.getValue());
			facadeDados.salvarUsuario(usuario, novoUsuario);
		} catch (Exception e) {
			e.printStackTrace();
			if(novoUsuario)
				Notification.show("Erro ao criar usuário! Tente novamente");
			else
				Notification.show("Erro ao modificar configurações! Tente novamente");
			return;
		}
		if(novoUsuario)
			Notification.show("Usuário criado com sucesso!");
		else
			Notification.show("Modificações realizadas com sucesso!");
		this.close();
	}

	public boolean validation() {
		if(campoCpf.getValue().length() != 14) {
			Notification.show("CPF inválido.", Notification.Type.WARNING_MESSAGE);
			campoCpf.focus();
			return false;		
		}
		if(novoUsuario && facadeDados.usuarioExiste(campoCpf.getValue())) {
			Notification.show("CPF já cadastrado.", Notification.Type.WARNING_MESSAGE);
			campoCpf.focus();
			return false;	
		}
		if(campoNome.getValue().length() < 3) {
			Notification.show("Nome precisa ter 3 ou mais caracteres.", Notification.Type.WARNING_MESSAGE);
			campoNome.focus();
			return false;		
		}
		if(campoEmail.getValue().equals("")) {
			Notification.show("Campo obrigatório!", Notification.Type.WARNING_MESSAGE);
			campoEmail.focus();
			return false;
		}
		if(campoEndereco.getValue().equals("")) {
			Notification.show("Campo obrigatório!", Notification.Type.WARNING_MESSAGE);
			campoEndereco.focus();
			return false;
		}
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
		if(novoUsuario) {
			if (campoNovaSenha1.getValue().length() < 4 || !campoNovaSenha1.getValue().matches(".*\\d.*")) {
				Notification.show("Mínimo de 4 caracteres sendo pelo menos 1 numérico.", Notification.Type.WARNING_MESSAGE);
				campoNovaSenha1.focus();
				return false;
			}
		}
		if(!novoUsuario && facadeDados.verificarCPFSenha(campoCpf.getValue(), campoSenha.getValue()) == null) {
			Notification.show("Senha inválida.", Notification.Type.WARNING_MESSAGE);
			campoSenha.focus();
			return false;
		}
		if(!binder.isValid()) {
			Notification.show("Preencha todos os campos obrigatórios!");
			campoNascimento.focus();
			return false;
		}
		return true;
	}
}
