package com.desafio.Banco.windows;

import java.util.Calendar;

import com.desafio.Banco.dtos.DtoTipoTransacao;
import com.desafio.Banco.dtos.DtoTransacao;
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.layouts.PrincipalLayout;
import com.desafio.Banco.utils.BancoUtil;
import com.desafio.BancoModel.model.Conta;
import com.vaadin.data.Binder;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DepositoWindow extends Window {
	private static final long serialVersionUID = 1L;

	private FacadeDados facadeDados;

	DtoTransacao transacao;
	Binder<DtoTransacao> binder;
	TextField valor;
	HorizontalLayout layoutBtns;
	Button btnConfirmar, btnCancelar;
	PrincipalLayout layout;
	Conta conta;

	public DepositoWindow(FacadeDados facadeDados, PrincipalLayout layout, Conta conta) {
		super();
		this.conta = conta;
		this.layout = layout;
		this.facadeDados = facadeDados;
		this.transacao = new DtoTransacao();
		transacao.setTipoTransacao(new DtoTipoTransacao("Depósito", null, null));
		this.setCaption("Realizar Depósito");
		this.setWidth("400px");
		btnConfirmar = new Button("Confirmar", this::save);
		btnCancelar = new Button("Cancelar", this::cancel);

		layoutBtns = new HorizontalLayout(btnConfirmar, btnCancelar);
		layoutBtns.setSpacing(true);

		valor = new TextField("Valor para depósito");
		BancoUtil.setValueField(valor);
		valor.setRequiredIndicatorVisible(true);

		binder = new Binder<>();
		binder.forField(valor).bind(DtoTransacao::getValorString, DtoTransacao::setValorString);

		VerticalLayout content = new VerticalLayout(valor, layoutBtns);
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
			binder.writeBeanIfValid(transacao);
			transacao.setData(data);
			transacao.setContaDestino(conta.getStringId());
			facadeDados.salvarTransacao(transacao);
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Erro ao efetuar depósito! Tente novamente");
			return;
		}
		Notification.show("Depósito realizado com sucesso!");
		layout.update();
		this.close();
	}

	public boolean validation() {
		if (valor.getValue().equals("") || BancoUtil.stringVirgulaToDouble(valor.getValue()) <= 0) {
			Notification.show("Valor inválido.", Notification.Type.WARNING_MESSAGE);
			valor.focus();
			return false;
		}
		return true;
	}
}
