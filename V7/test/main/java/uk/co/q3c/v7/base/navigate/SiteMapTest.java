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
package uk.co.q3c.v7.base.navigate;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

import uk.co.q3c.v7.base.view.LoginView;
import uk.co.q3c.v7.base.view.testviews.PublicHomeView;
import uk.co.q3c.v7.demo.i18N.DemoLabelKeys;

public class SiteMapTest {

	@Test
	public void url() {

		// given
		SiteMap map = new SiteMap();
		SiteMapNode grandparent = new SiteMapNode("public", PublicHomeView.class, DemoLabelKeys.home);
		SiteMapNode parent = new SiteMapNode("home", PublicHomeView.class, DemoLabelKeys.home);
		SiteMapNode child = new SiteMapNode("login", LoginView.class, DemoLabelKeys.login);
		map.addChild(grandparent, parent);
		map.addChild(parent, child);
		// when

		// then
		assertThat(map.url(grandparent)).isEqualTo("public");
		assertThat(map.url(parent)).isEqualTo("public/home");
		assertThat(map.url(child)).isEqualTo("public/home/login");
	}

}
