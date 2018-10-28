package com.desafio.Banco;

import java.io.File;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;

import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.facades.FacadeDados;
import com.desafio.Banco.views.LoginView;
import com.desafio.Banco.views.PrincipalView;
import com.desafio.Banco.views.RelatorioView;
import com.desafio.Banco.views.SaldoContasView;
import com.desafio.Banco.views.UsuariosView;
import com.desafio.Banco.windows.UsuarioWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.UIEvents.PollEvent;
import com.vaadin.event.UIEvents.PollListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

@Theme("BancoTheme")
public class BancoUI extends UI {
	private static final long serialVersionUID = 1L;
	final protected String pastaPrincipal = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	final protected String pastaIcones = pastaPrincipal + "/icones";
	
	protected LoginView loginView;
	protected Navigator navegador;
	protected VerticalLayout conteudo, pagina;
	protected HorizontalLayout cabecalho, botoesCabecalho, rodape;
	protected HorizontalSplitPanel painel;
	protected DtoUsuario usuario;
	protected Label labelCabecalho;
	protected MenuBar menu;
	protected MenuBar.Command comando;
	protected MenuItem itemAnterior;
	protected HashMap<String, String> viewVisiveis = new HashMap<>();
	protected VerticalSplitPanel navegadorEsquerda;
	protected FacadeDados facadeDados = new FacadeDados();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	configurarPagina();
    	
		loginView = new LoginView();
		navegador = new Navigator(this, conteudo);
		navegador.addView("login", loginView);
		navegador.setErrorView(loginView);
        
        pagina = new VerticalLayout(cabecalho, painel, rodape);
		pagina.setExpandRatio(painel, 1);
		pagina.setSizeFull();
		pagina.setSpacing(false);
		pagina.setMargin(false);
		setContent(pagina);

		getPage().setTitle("Banco do Gabriel");

		if (getPage().getUriFragment() == null || getPage().getUriFragment().equals("")) {
			getSession().setAttribute("usuario", null);
			usuario = null;
		}
		if (getSession().getAttribute("usuario") != null) {
			usuario = (DtoUsuario) getSession().getAttribute("usuario");
			montarInterface();
		} else {
			navegador.navigateTo("login");
		}
		addPollListener(new PollListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void poll(PollEvent event) {
			}
		});
    }

	private void configurarPagina() {
    	configurarCabecalho();
    	configurarRodape();
    	configurarPainel();		
	}

	private void configurarCabecalho() {
		labelCabecalho = new Label("");
		labelCabecalho.setStyleName("bem-vindo");
		Button btnConfiguracao = new Button("", new FileResource(new File(pastaIcones + "/configuracao.png")));
		btnConfiguracao.addClickListener(this::gerenciarUsuario);
		btnConfiguracao.addStyleName("bigbutton");

		Button btnSair = new Button("", new FileResource(new File(pastaIcones + "/sair.png")));
		btnSair.addClickListener(this::sair);
		btnSair.addStyleName("bigbutton");

		botoesCabecalho = new HorizontalLayout(btnConfiguracao, btnSair); // linkLogpyx
		botoesCabecalho.setSpacing(true);

		menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setHeight("30px");
		menu.markAsDirty();
		comando = new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem itemEscolhido) {
				String textoItem = itemEscolhido.getText();
				String url = viewVisiveis.get(textoItem);
				if (itemAnterior != null && itemAnterior != itemEscolhido) {
					itemAnterior.setStyleName(null);
					navegador.navigateTo(url);
				}
				itemAnterior = itemEscolhido;
			}
		};

		cabecalho = new HorizontalLayout(labelCabecalho, menu, botoesCabecalho);
		cabecalho.setId("cabecalho");
		cabecalho.setWidth("100%");
		cabecalho.addStyleName("cabecalho-rodape");
		cabecalho.setSpacing(true);
		cabecalho.setComponentAlignment(labelCabecalho, Alignment.MIDDLE_LEFT);
		cabecalho.setComponentAlignment(menu, Alignment.MIDDLE_CENTER);
		cabecalho.setComponentAlignment(botoesCabecalho, Alignment.MIDDLE_RIGHT);
		cabecalho.setExpandRatio(menu, 1.0f);
	}

    protected void configurarRodape() {
		rodape = new HorizontalLayout();
		rodape.setId("rodape");
		rodape.setHeight(25, Unit.PIXELS);
		rodape.setWidth("100%");
		rodape.addStyleName("cabecalho-rodape");
	}
	
	protected void configurarPainel() {
		navegadorEsquerda = new VerticalSplitPanel();
		navegadorEsquerda.markAsDirty();
		navegadorEsquerda.setSizeFull();

		conteudo = new VerticalLayout();
		conteudo.setId("conteudo");
		conteudo.markAsDirty();
		conteudo.setSizeFull();
		conteudo.setMargin(false);

		painel = new HorizontalSplitPanel(navegadorEsquerda, conteudo);
		painel.markAsDirty();
		painel.setSizeFull();
	}

    public void montarInterface() {
    	removerViews();
		DtoUsuario usuario = (DtoUsuario) getSession().getAttribute("usuario");
		menu.removeItems();
		menu.setVisible(false);
		if(usuario != null) {
			PrincipalView principalView = new PrincipalView(usuario);
			principalView.setMenuItem(menu.addItem("Principal", null, comando));
			viewVisiveis.put("Principal", "principal");
			navegador.addView("principal", principalView);
			if(usuario.usuarioGerente()) {
				RelatorioView relatorioView = new RelatorioView();
				relatorioView.setMenuItem(menu.addItem("Relatório de transações", null, comando));
				viewVisiveis.put("Relatório de transações", "relatorio");
				navegador.addView("relatorio", relatorioView);
				SaldoContasView saldoContasView = new SaldoContasView();
				saldoContasView.setMenuItem(menu.addItem("Saldos de contas", null, comando));
				viewVisiveis.put("Saldos de contas", "saldo");
				navegador.addView("saldo", saldoContasView);
				UsuariosView usuariosView = new UsuariosView();
				usuariosView.setMenuItem(menu.addItem("Gerenciar Usuários", null, comando));
				viewVisiveis.put("Gerenciar Usuários", "usuarios");
				navegador.addView("usuarios", usuariosView);
				menu.setVisible(true);
			}
			labelCabecalho.setCaption("Olá " + usuario.getNome() + ", bem vindo!");
			navegador.navigateTo("principal");
			navegador.setErrorView(principalView);
		}else {
			navegador.addView("login", loginView);
			navegador.setErrorView(loginView);
			labelCabecalho.setCaption("");
			navegador.navigateTo("login");
		}
	} 
    
	protected void removerViews() {
		navegador.destroy();
		navegador = new Navigator(this, conteudo);
	}
    
	protected void gerenciarUsuario(Button.ClickEvent event) {
		DtoUsuario usuario = (DtoUsuario) getSession().getAttribute("usuario");
		if(usuario != null) {
			UsuarioWindow window = new UsuarioWindow(facadeDados, usuario);
			this.adicionarJanela(window);
		}
	}
	
	protected void sair(Button.ClickEvent event) {
		getSession().setAttribute("usuario", null);
		montarInterface();
	}
	

	public void setVisibilidadeMenu(boolean visivel) {
		menu.setVisible(visivel);
	}

	public void setItemSelecionado(MenuItem item) {
		if (item == null)
			for (MenuItem menuItem : menu.getItems()) {
				menuItem.setStyleName(null);
			}
		itemAnterior = item;
	}

	public void setDivisaoPainel(int porcentagem) {
		painel.setSplitPosition(porcentagem, Unit.PERCENTAGE);
	}

	public void setDivisaoNavegador(int porcentagem) {
		navegadorEsquerda.setSplitPosition(porcentagem, Unit.PERCENTAGE);
	}

	public void adicionarJanela(Window window) {
		this.access(new Runnable() {
			@Override
			public void run() {
				if (!window.isModal()) {
					window.setModal(true);
				}
				addWindow(window);
				window.focus();
			}
		});
	}
	
	@WebServlet(urlPatterns = "/*", name = "BancoUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = BancoUI.class, productionMode = true)
    public static class BancoUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
    }
}
