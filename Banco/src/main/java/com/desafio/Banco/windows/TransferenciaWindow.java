package com.desafio.Banco.windows;

import java.util.Calendar;

import org.vaadin.textfieldformatter.CustomStringBlockFormatter;
import org.vaadin.textfieldformatter.CustomStringBlockFormatter.Options;
import org.vaadin.textfieldformatter.NumeralFieldFormatter;

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

public class TransferenciaWindow extends Window {
	private static final long serialVersionUID = 1L;

	private FacadeDados facadeDados;

	DtoTransacao transacao;
	Binder<DtoTransacao> binder;
	TextField contaDestino, valor;
	HorizontalLayout layoutBtns;
	Button btnConfirmar, btnCancelar;
	PrincipalLayout layout;
	Conta conta;
	
	public TransferenciaWindow(FacadeDados facadeDados, PrincipalLayout layout, Conta conta) {
		super();
		this.conta = conta;
		this.layout = layout;
		this.facadeDados = facadeDados;
		this.transacao = new DtoTransacao();
		transacao.setTipoTransacao(new DtoTipoTransacao("Transferência", null, null));
		this.setCaption("Realizar Transferencia");
		this.setWidth("400px");
		btnConfirmar = new Button("Confirmar", this::save);
		btnCancelar = new Button("Cancelar", this::cancel);

		layoutBtns = new HorizontalLayout(btnConfirmar, btnCancelar);
		layoutBtns.setSpacing(true);

		contaDestino = new TextField("Conta para transferencia");
		contaDestino.setRequiredIndicatorVisible(true);
		Options options = new Options();
		options.setBlocks(9);
		options.setNumericOnly(true);
		new CustomStringBlockFormatter(options).extend(contaDestino);
		valor = new TextField("Valor para transferência");
		new NumeralFieldFormatter("", ",", 2).extend(valor);
		valor.setRequiredIndicatorVisible(true);

		binder = new Binder<>();
		binder.forField(contaDestino).bind(DtoTransacao::getContaDestino, DtoTransacao::setContaDestino);
		binder.forField(valor).bind(DtoTransacao::getValorString, DtoTransacao::setValorString);

		VerticalLayout content = new VerticalLayout(contaDestino, valor, layoutBtns);
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
			transacao.setContaOrigem(conta.getStringId());
			facadeDados.salvarTransacao(transacao);
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Erro ao efetuar transferência! Tente novamente");
			return;
		}
		Notification.show("Transferência realizado com sucesso!");
		layout.update();
		this.close();
	}

	public boolean validation() {
		Conta c = facadeDados.getConta(conta.getId());
		if (contaDestino.getValue().equals("") || !facadeDados.contaValida(contaDestino.getValue())) {
			Notification.show("Conta inválida.", Notification.Type.WARNING_MESSAGE);
			contaDestino.focus();
			return false;
		}
		if (contaDestino.getValue().equals(c.getStringId())) {
			Notification.show("Conta de origem e destino são iguais..", Notification.Type.WARNING_MESSAGE);
			contaDestino.focus();
			return false;
		}
		if (valor.getValue().equals("") || BancoUtil.stringVirgulaToDouble(valor.getValue()) <= 0) {
			Notification.show("Valor inválido.", Notification.Type.WARNING_MESSAGE);
			valor.focus();
			return false;
		}
		if(BancoUtil.fixCasasDecimais(c.getSaldo()) < BancoUtil.stringVirgulaToDouble(valor.getValue())){
			Notification.show("Saldo insuficiente.", Notification.Type.WARNING_MESSAGE);
			valor.focus();
			return false;			
		}
		return true;
	}
}
