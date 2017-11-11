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
