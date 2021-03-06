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
package uk.co.q3c.v7.base.shiro;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.fest.assertions.Fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import uk.co.q3c.v7.base.navigate.Sitemap;

import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;

@RunWith(MycilaJunitRunner.class)
@GuiceContext({})
public class DefaultRealmTest {

	String onlyValidPassword = "password";

	DefaultRealm realm;

	LoginAttemptLog attemptLog = new DefaultLoginAttemptLog();

	CredentialsMatcher matcher = new AlwaysPasswordCredentialsMatcher();

	@Mock
	URIPermissionFactory permissionFactory;

	@Mock
	Sitemap sitemap;

	@Before
	public void setup() {
		sitemap = mock(Sitemap.class);
		realm = new DefaultRealm(attemptLog, matcher, sitemap, permissionFactory);
	}

	@Test
	public void realmName() {

		// given

		// when

		// then
		assertThat(realm.getName()).isEqualTo("V7 Default Realm");

	}

	@Test
	public void validPassword() {

		// given
		// when
		AuthenticationInfo info = realm.getAuthenticationInfo(token("fred", onlyValidPassword));

		// then
		assertThat(info).isNotNull();

	}

	@Test
	public void passwordFail() {

		// given
		attemptLog.clearUnsuccessful("fred");
		// when
		AuthenticationInfo info = realm.getAuthenticationInfo(token("fred", "rubbish"));
		// then
		assertThat(info).isNull();

	}

	@Test(expected = ExcessiveAttemptsException.class)
	public void bombsAfter3Fails() {

		// given

		// when
		@SuppressWarnings("unused")
		AuthenticationInfo info = realm.getAuthenticationInfo(token("fred", "rubbish"));
		info = realm.getAuthenticationInfo(token("fred", "rubbish"));
		info = realm.getAuthenticationInfo(token("fred", "rubbish"));
		// then
		// exception expected

	}

	@Test(expected = AccountException.class)
	public void nullUserName() {

		// given
		UsernamePasswordToken tk = token(null, "rubbish");
		// when
		realm.getAuthenticationInfo(tk);
		// then
		// should not get this far
		Fail.fail();

	}

	/**
	 * Has authenticated subject been given permissions for private root
	 */
	@Test
	public void uri() {

		// given
		URIViewPermission viewPermission = mock(URIViewPermission.class);
		when(sitemap.getPrivateRoot()).thenReturn("private");
		when(permissionFactory.createViewPermission("private", true)).thenReturn(viewPermission);
		PrincipalCollection pc = new SimplePrincipalCollection();
		// when
		AuthorizationInfo info = realm.getAuthorizationInfo(pc);
		// then
		assertThat(info).isNotNull();
		assertThat(info.getStringPermissions().contains("uri:view:private:*"));

	}

	private UsernamePasswordToken token(String username, String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		return token;
	}

}
