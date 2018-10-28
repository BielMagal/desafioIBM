package com.desafio.Banco.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.vaadin.dialogs.ConfirmDialog;

import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.layouts.UsuariosLayout;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.DateRenderer;

public class GridUsuarios extends Grid<DtoUsuario> {

	private static final long serialVersionUID = 1L;

	ArrayList<DtoUsuario> items = new ArrayList<>();
	ListDataProvider<DtoUsuario> provider;
	HeaderRow filteringHeader;
	UsuariosLayout layout;
	TextField filtroCPF, filtroNome, filtroConta, filtroTipoUsuario, filtroEmail, filtroEndereco;
	
	public GridUsuarios(UsuariosLayout layout) {
		super();
		this.layout = layout;
		provider = new ListDataProvider<>(items);
		setDataProvider(provider);
		filteringHeader = appendHeaderRow();
	}
	
	public void criarFiltros() {
		criarFiltroCPF();
		criarFiltroNome();
		criarFiltroConta();
		criarFiltroTipoUsuario();
		criarFiltroEmail();
		criarFiltroEndereco();
	}
	
	public void criarFiltroCPF() {
		filtroCPF = new TextField();
		BancoUtil.setCPFFormato(filtroCPF);
		criarFiltro(filtroCPF, DtoUsuario.F_CPF);
	}
	
	public void criarFiltroNome() {
		filtroNome = new TextField();
		criarFiltro(filtroNome, DtoUsuario.F_NOME);
	}
	
	public void criarFiltroConta() {
		filtroConta = new TextField();
		BancoUtil.setContaField(filtroConta);
		criarFiltro(filtroConta, DtoUsuario.F_CONTA);
	}
	
	public void criarFiltroTipoUsuario() {
		filtroTipoUsuario = new TextField();
		criarFiltro(filtroTipoUsuario, DtoUsuario.F_TIPO_USUARIO);
	}
	
	public void criarFiltroEmail() {
		filtroEmail = new TextField();
		criarFiltro(filtroEmail, DtoUsuario.F_EMAIL);
	}
	
	public void criarFiltroEndereco() {
		filtroEndereco = new TextField();
		criarFiltro(filtroEndereco, DtoUsuario.F_ENDERECO);
	}

	public void criarFiltro(TextField filtro, String colunaId) {
		filtro.setWidth("100%");
		filtro.setHeight("25px");
		filtro.addValueChangeListener(event -> {
			atualizarFiltros();
		});
		filteringHeader.getCell(colunaId).setComponent(filtro);
	}
	
	private void atualizarFiltros() {
		provider.clearFilters();
		if(filtroCPF != null)
			atualizarFiltroCPF();
		if(filtroNome != null)
			atualizarFiltroNome();
		if(filtroConta != null)
			atualizarFiltroConta();
		if(filtroTipoUsuario != null)
			atualizarFiltroTipoUsuario();
		if(filtroEmail != null)
			atualizarFiltroEmail();
		if(filtroEndereco != null)
			atualizarFiltroEndereco();
	}

	private void atualizarFiltroCPF() {
		if (filtroCPF != null && !filtroCPF.getValue().equals("")) {
			provider.addFilter(usuario -> {
				if (usuario.getCpf() == null)
					return false;
				return usuario.getCpf().contains(filtroCPF.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroNome() {
		if (filtroNome != null && !filtroNome.getValue().equals("")) {
			provider.addFilter(usuario -> {
				if (usuario.getNome() == null)
					return false;
				String lower = usuario.getNome().toLowerCase(Locale.ENGLISH);
				return lower.contains(filtroNome.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroConta() {
		if (filtroConta != null && !filtroConta.getValue().equals("")) {
			provider.addFilter(usuario -> {
				if (usuario.getNumConta() == null)
					return false;
				return usuario.getNumConta().contains(filtroConta.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroTipoUsuario() {
		if (filtroTipoUsuario!= null && !filtroTipoUsuario.getValue().equals("")) {
			provider.addFilter(usuario -> {
				if (usuario.getTipoUsuarioDescricao() == null)
					return false;
				String lower = usuario.getTipoUsuarioDescricao().toLowerCase(Locale.ENGLISH);
				return lower.contains(filtroTipoUsuario.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroEmail() {
		if (filtroEmail != null && !filtroEmail.getValue().equals("")) {
			provider.addFilter(usuario -> {
				if (usuario.getEmail() == null)
					return false;
				String lower = usuario.getEmail().toLowerCase(Locale.ENGLISH);
				return lower.contains(filtroEmail.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}

	private void atualizarFiltroEndereco() {
		if (filtroEndereco != null && !filtroEndereco.getValue().equals("")) {
			provider.addFilter(usuario -> {
				if (usuario.getEndereco() == null)
					return false;
				String lower = usuario.getEndereco().toLowerCase(Locale.ENGLISH);
				return lower.contains(filtroEndereco.getValue().toLowerCase(Locale.ENGLISH));
			});
		}
	}
	
	public Column<DtoUsuario, String> addStringColumn(ValueProvider<DtoUsuario, String> valueProvider, String columnName,
			double widthInPixels) {
		return addColumn(valueProvider).setId(columnName).setWidth(widthInPixels)
				.setCaption(columnName);
	}

	public Column<DtoUsuario, Integer> addIntegerColumn(ValueProvider<DtoUsuario, Integer> valueProvider, String columnName,
			double widthInPixels) {
		return addColumn(valueProvider).setId(columnName).setWidth(widthInPixels)
				.setCaption(columnName);
	}
	
	public Column<DtoUsuario, Date> addDateColumn(ValueProvider<DtoUsuario, Date> valueProvider, String columnName, double widthInPixels) {
		return addColumn(valueProvider, new DateRenderer("%1$td/%1$tm/%1$tY")).setId(columnName)
				.setWidth(widthInPixels).setCaption(columnName);
	}
	
	public ArrayList<DtoUsuario> getItems() {
		return items;
	}

	public void setItems(ArrayList<DtoUsuario> items) {
		this.items = items;
	}

	public ListDataProvider<DtoUsuario> getProvider() {
		return provider;
	}

	public void setProvider(ListDataProvider<DtoUsuario> provider) {
		this.provider = provider;
	}
	
	public Column<DtoUsuario, String> addBotaoReset(String name, double width) {
		ButtonRenderer<DtoUsuario> btn = new ButtonRenderer<DtoUsuario>(clickEvent -> {
			DtoUsuario u = (DtoUsuario) clickEvent.getItem();
			ConfirmDialog.show(UI.getCurrent(), "Reset de senha", "Confirmar reset da senha do cliente " + u.getNome() + " para \"abc123\"?", "Sim", "Não", dialog -> {
				if (dialog.isConfirmed()) {
					new FacadeDados().resetarSenha(u);
					Notification.show("Senha resetada!");
					layout.update();
				}
			}).getCancelButton().focus();
		});
		return addColumn(visit -> name, btn).setWidth(width);
	}

}
