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
 * The base for the resource bundle of {@link Labels}. The separation between them is arbitrary, but helps break down
 * what could other wise be long lists, and only one of them needs to look up parameter values:
 * <ol>
 * <li>{@link Labels} : short, usually one or two words, no parameters, generally used as captions
 * <li>{@link Descriptions} : longer, typically several words, no parameters, generally used in tooltips
 * <li>{@link Messages} : contains parameters Typically used for tooltips. For short labels {@link Labels} is used, and
 * for values containing parameters, Messages is used.
 * 
 * @see LabelKey
 * @author David Sowerby 9 Feb 2013
 * 
 */
public class Labels extends EnumResourceBundle<LabelKey> {

	private static final EnumMap<LabelKey, String> map = new EnumMap<LabelKey, String>(LabelKey.class);
	// TODO make map unmodifiable
	static {
		map.put(LabelKey.Ok, "Ok");
		map.put(LabelKey.Cancel, "Cancel");
		map.put(LabelKey.First_Name, "First Name");
		map.put(LabelKey.Last_Name, "Last Name");
		map.put(LabelKey.Small, "Small");

	}

	@Override
	public EnumMap<LabelKey, String> getMap() {
		return map;
	}

}
