package uk.co.q3c.basic;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.q3c.basic.guice.uiscope.UIKey;
import uk.co.q3c.basic.guice.uiscope.UIKeyProvider;
import uk.co.q3c.basic.guice.uiscope.UIScope;
import uk.co.q3c.basic.guice.uiscope.UIScoped;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Scope;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;
import com.vaadin.util.CurrentInstance;

/**
 * A Vaadin UI provider which supports the use of Guice scoped UI (see {@link UIScoped}). If you do not need UIScope,
 * then just extend from UIProvider directly
 * 
 * Subclasses should implement getUIClass(UIClassSelectionEvent event) to provide logic for selecting the UI class.
 * 
 * @author David Sowerby
 * 
 */
public abstract class ScopedUIProvider extends UIProvider {
	private static Logger log = LoggerFactory.getLogger(ScopedUIProvider.class);
	private final UIKeyProvider mainwindowKeyProvider;
	private final Injector injector;

	@Inject
	protected ScopedUIProvider(Injector injector, UIKeyProvider mainwindowKeyProvider) {
		super();
		this.mainwindowKeyProvider = mainwindowKeyProvider;
		this.injector = injector;
	}

	@Override
	public UI createInstance(UICreateEvent event) {
		return createInstance(event.getUIClass());

	}

	public UI createInstance(Class<? extends UI> uiClass) {
		UIKey instanceKey = mainwindowKeyProvider.get();
		// hold the key while UI is created
		CurrentInstance.set(UIKey.class, instanceKey);
		// create the UI
		ScopedUI ui = (ScopedUI) injector.getInstance(uiClass);
		Map<Class<? extends Annotation>, Scope> x = injector.getScopeBindings();
		UIScope uiScope = (UIScope) x.get(UIScoped.class);
		ui.setScope(uiScope);
		log.debug("returning instance of " + ui.getClass().getName() + " with key " + instanceKey);
		ui.setInstanceKey(instanceKey);
		return ui;
	}

}
