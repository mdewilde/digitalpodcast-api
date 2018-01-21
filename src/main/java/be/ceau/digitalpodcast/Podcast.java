/*
	Copyright 2018 Marceau Dewilde <m@ceau.be>
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
		https://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package be.ceau.digitalpodcast;

import java.util.Locale;
import java.util.Objects;

/**
 * A podcast, as returned in a Digital Podcast OPML file.
 */
public class Podcast {

	private final String name;
	private final String url;

	public Podcast(String name, String url) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(url);
		this.name = name.trim();
		this.url = url.trim().toLowerCase(Locale.ENGLISH);
	}

	/**
	 * @return the name of this {@link Podcast}, never {@code null}
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the url of this {@link Podcast}, never {@code null}
	 */
	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("Podcast [name=")
				.append(name)
				.append(", url=")
				.append(url)
				.append("]")
				.toString();
	}

}
