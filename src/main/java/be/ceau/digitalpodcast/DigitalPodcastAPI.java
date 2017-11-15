package be.ceau.digitalpodcast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.ceau.opml.OpmlParseException;
import be.ceau.opml.OpmlParser;
import be.ceau.opml.entity.Opml;
import be.ceau.opml.entity.Outline;

public class DigitalPodcastAPI {

	private static final Logger logger = LoggerFactory.getLogger(DigitalPodcastAPI.class);

	private static final String DIRECTORY = "http://www.digitalpodcast.com/opml/digitalpodcast.opml";
	private static final String DIRECTORY_NO_ADULT = "http://www.digitalpodcast.com/opml/digitalpodcastnoadult.opml";
	private static final String DIRECTORY_CLEAN = "http://www.digitalpodcast.com/opml/digitalpodcastclean.opml";
	private static final String NEW = "http://www.digitalpodcast.com/opml/digitalpodcastnew.opml";
	private static final String NEW_NO_ADULT = "http://www.digitalpodcast.com/opml/digitalpodcastnewnoadult.opml";
	private static final String NEW_CLEAN = "http://www.digitalpodcast.com/opml/digitalpodcastnewclean.opml";
	private static final String MOST_VIEWED = "http://www.digitalpodcast.com/opml/digitalpodcastmostviewed.opml";
	private static final String MOST_VIEWED_NO_ADULT = "http://www.digitalpodcast.com/opml/digitalpodcastmostviewednoadult.opml";
	private static final String MOST_VIEWED_CLEAN = "http://www.digitalpodcast.com/opml/digitalpodcastmostviewedclean.opml";
	private static final String TOP_RATED = "http://www.digitalpodcast.com/opml/digitalpodcasttoprated.opml";
	private static final String TOP_RATED_NO_ADULT = "http://www.digitalpodcast.com/opml/digitalpodcasttopratednoadult.opml";
	private static final String TOP_RATED_CLEAN = "http://www.digitalpodcast.com/opml/digitalpodcasttopratedclean.opml";
	private static final String SUBSCRIBED = "http://www.digitalpodcast.com/opml/digitalpodcastmostsubscribed.opml";
	private static final String SUBSCRIBED_NO_ADULT = "http://www.digitalpodcast.com/opml/digitalpodcastmostsubscribednoadult.opml";
	private static final String SUBSCRIBED_CLEAN = "http://www.digitalpodcast.com/opml/digitalpodcastmostsubscribedclean.opml";

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #DIRECTORY}
	 */
	public Stream<Podcast> getDirectory() {
		return read(DIRECTORY);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #DIRECTORY_NO_ADULT}
	 */
	public Stream<Podcast> getDirectoryNoAdult() {
		return read(DIRECTORY_NO_ADULT);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #DIRECTORY_CLEAN}
	 */
	public Stream<Podcast> getDirectoryClean() {
		return read(DIRECTORY_CLEAN);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #NEW}
	 */
	public Stream<Podcast> getNew() {
		return read(NEW);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #NEW_NO_ADULT}
	 */
	public Stream<Podcast> getNewNoAdult() {
		return read(NEW_NO_ADULT);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #NEW_CLEAN}
	 */
	public Stream<Podcast> getNewClean() {
		return read(NEW_CLEAN);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #MOST_VIEWED}
	 */
	public Stream<Podcast> getMostViewed() {
		return read(MOST_VIEWED);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #MOST_VIEWED_NO_ADULT}
	 */
	public Stream<Podcast> getMostViewedNoAdult() {
		return read(MOST_VIEWED_NO_ADULT);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #MOST_VIEWED_CLEAN}
	 */
	public Stream<Podcast> getMostViewedClean() {
		return read(MOST_VIEWED_CLEAN);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #TOP_RATED}
	 */
	public Stream<Podcast> getTopRated() {
		return read(TOP_RATED);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #TOP_RATED_NO_ADULT}
	 */
	public Stream<Podcast> getTopRatedNoAdult() {
		return read(TOP_RATED_NO_ADULT);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #TOP_RATED_CLEAN}
	 */
	public Stream<Podcast> getTopRatedClean() {
		return read(TOP_RATED_CLEAN);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #SUBSCRIBED}
	 */
	public Stream<Podcast> getSubscribed() {
		return read(SUBSCRIBED);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #SUBSCRIBED_NO_ADULT}
	 */
	public Stream<Podcast> getSubscribedNoAdult() {
		return read(SUBSCRIBED_NO_ADULT);
	}

	/**
	 * @return a {@link Stream} over any {@link Podcast} found at {@value #SUBSCRIBED_CLEAN}
	 */
	public Stream<Podcast> getSubscribedClean() {
		return read(SUBSCRIBED_CLEAN);
	}

	private Stream<Podcast> read(String link) {

		try {

			URL url = new URL(link);

			try (InputStream in = url.openConnection().getInputStream()) {

				Opml opml = new OpmlParser().parse(in);

				return opml.getBody()
						.getOutlines()
						.stream()
						.flatMap(otl -> extractPodcasts(otl).stream());

			}

		} catch (OpmlParseException | IOException e) {
			logger.error("read(String {})", link, e);
		}

		return Stream.empty();

	}

	private List<Podcast> extractPodcasts(Outline outline) {

		List<Podcast> podcasts = outline.getSubElements()
				.stream()
				.flatMap(otl -> extractPodcasts(otl).stream())
				.collect(Collectors.toList());

		String type = outline.getAttributes().get("type");
		if ("link".equals(type)) {
			String name = outline.getAttributes().get("text");
			String url = outline.getAttributes().get("url");
			if (name != null && url != null) {
				podcasts.add(new Podcast(name, url));
			} else {
				logger.warn("extractPodcasts(Outline) name or url null in {}", outline.getAttributes());
			}
		}

		return podcasts;
	}

}
