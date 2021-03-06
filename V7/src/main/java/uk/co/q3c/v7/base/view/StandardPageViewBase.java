/*
 * Copyright (C) 2013 David Sowerby
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package uk.co.q3c.v7.base.view;

import javax.inject.Inject;

import uk.co.q3c.v7.base.navigate.V7Navigator;
import uk.co.q3c.v7.base.view.component.UserNavigationTree;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;

public abstract class StandardPageViewBase extends ViewBase {

	private HorizontalSplitPanel layout;
	private final UserNavigationTree navtree;
	private Label label;

	private GridLayout grid;

	@Inject
	protected StandardPageViewBase(V7Navigator navigator, UserNavigationTree navtree) {
		super(navigator);
		this.navtree = navtree;
		buildUI();
	}

	@Override
	protected void buildUI() {
		layout = new HorizontalSplitPanel();
		label = new Label("This is the " + this.getClass().getSimpleName());
		label.setHeight("100px");
		grid = new GridLayout(3, 3);

		grid.addComponent(label, 1, 1);
		grid.setSizeFull();
		grid.setColumnExpandRatio(0, 0.33f);
		grid.setColumnExpandRatio(1, 0.33f);
		grid.setColumnExpandRatio(2, 0.33f);

		grid.setRowExpandRatio(0, 0.4f);
		grid.setRowExpandRatio(1, 0.2f);
		grid.setRowExpandRatio(2, 0.4f);

		label.setSizeFull();
		layout.setSplitPosition(200f, Unit.PIXELS);
		layout.setFirstComponent(navtree);
		layout.setSecondComponent(grid);

	}

	@Override
	public Component getUiComponent() {
		return layout;
	}

	public Label getLabel() {
		return label;
	}

}
