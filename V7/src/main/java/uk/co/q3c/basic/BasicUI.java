package uk.co.q3c.basic;

import javax.inject.Inject;

import com.google.inject.name.Named;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.VerticalLayout;

@PreserveOnRefresh
@Theme("chameleon")
public class BasicUI extends ScopedUI implements UriFragmentChangedListener {

	private final ViewProvider viewProvider;

	private Navigator navigator;

	private final String title;

	private final HeaderBar headerBar;

	private final FooterBar footerBar;

	@Inject
	protected BasicUI(HeaderBar headerBar, FooterBar footerBar, @Named(A.title) String title, ViewProvider viewProvider) {
		super();
		this.title = title;
		this.viewProvider = viewProvider;
		this.footerBar = footerBar;
		this.headerBar = headerBar;

	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle(title);
		getPage().addUriFragmentChangedListener(this);

		VerticalLayout viewArea = doLayout();
		navigator = new Navigator(this, viewArea);

		// Only one provider needed because GuiceViewProvider does the work to select the View class from the view name
		navigator.addProvider(viewProvider);

		// Navigate to the start view
		navigator.navigateTo("view1");
	}

	protected VerticalLayout doLayout() {
		// viewArea is the layout where Views will be placed
		VerticalLayout viewArea = new VerticalLayout();
		// viewArea.addComponent(new View1());
		viewArea.setSizeUndefined();
		viewArea.setWidth("100%");

		VerticalLayout screenLayout = new VerticalLayout(headerBar, viewArea, footerBar);
		screenLayout.setSizeFull();
		screenLayout.setExpandRatio(viewArea, 1);
		setContent(screenLayout);
		return viewArea;

	}

	@Override
	public void uriFragmentChanged(UriFragmentChangedEvent event) {

	}

	@Override
	public Navigator getNavigator() {
		return navigator;
	}

	public HeaderBar getHeaderBar() {
		return headerBar;
	}

	public FooterBar getFooterBar() {
		return footerBar;
	}

}