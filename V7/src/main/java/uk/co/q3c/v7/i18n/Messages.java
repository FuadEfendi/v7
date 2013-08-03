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
package uk.co.q3c.v7.i18n;

import java.util.EnumMap;

/**
 * The base for the resource bundle of {@link Messages}. The separation between them is arbitrary, but helps break down
 * what could other wise be long lists, and only one of them needs to look up parameter values:
 * <ol>
 * <li>{@link Labels} : short, usually one or two words, no parameters, generally used as captions
 * <li>{@link Descriptions} : longer, typically several words, no parameters, generally used in tooltips
 * <li>{@link Messages} : contains parameters Typically used for tooltips. For short labels {@link Labels} is used, and
 * for values containing parameters, Messages is used.
 * 
 * 
 * @author David Sowerby 3 Aug 2013
 * 
 */
public class Messages extends EnumResourceBundle<MessageKeys> {

	private static final EnumMap<MessageKeys, String> map = new EnumMap<MessageKeys, String>(MessageKeys.class);
	// TODO make map unmodifiable
	static {
		map.put(MessageKeys.last_name, "The Last Name or Family Name");
		map.put(MessageKeys.ok, "Confirm this Value is OK");
		map.put(MessageKeys.small_font, "Use a Small Font");
	}

	@Override
	public EnumMap<MessageKeys, String> getMap() {
		return map;
	}

}
