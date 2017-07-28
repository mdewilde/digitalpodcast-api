package be.ceau.digitalpodcast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.xmlpull.v1.XmlPullParserException;

import be.ceau.opml.Opml;
import be.ceau.opml.Outline;
import be.ceau.opml.Parser;

public class DigitalPodcastAPI {

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

	public Stream<Podcast> getDirectory() {
		return read(DIRECTORY);
	}
	
	public Stream<Podcast> getDirectoryNoAdult() {
		return read(DIRECTORY_NO_ADULT);
	}

	public Stream<Podcast> getDirectoryClean() {
		return read(DIRECTORY_CLEAN);
	}

	public Stream<Podcast> getNew() {
		return read(NEW);
	}

	public Stream<Podcast> getNewNoAdult() {
		return read(NEW_NO_ADULT);
	}

	public Stream<Podcast> getNewClean() {
		return read(NEW_CLEAN);
	}

	public Stream<Podcast> getMostViewed() {
		return read(MOST_VIEWED);
	}

	public Stream<Podcast> getMostViewedNoAdult() {
		return read(MOST_VIEWED_NO_ADULT);
	}

	public Stream<Podcast> getMostViewedClean() {
		return read(MOST_VIEWED_CLEAN);
	}

	public Stream<Podcast> getTopRated() {
		return read(TOP_RATED);
	}

	public Stream<Podcast> getTopRatedNoAdult() {
		return read(TOP_RATED_NO_ADULT);
	}

	public Stream<Podcast> getTopRatedClean() {
		return read(TOP_RATED_CLEAN);
	}

	public Stream<Podcast> getSubscribed() {
		return read(SUBSCRIBED);
	}

	public Stream<Podcast> getSubscribedNoAdult() {
		return read(SUBSCRIBED_NO_ADULT);
	}

	public Stream<Podcast> getSubscribedClean() {
		return read(SUBSCRIBED_CLEAN);
	}

	private Stream<Podcast> read(String link) {

		try {

			URL url = new URL(link);

			try (InputStream in = url.openConnection().getInputStream()) {

				Opml opml = new Parser().parse(in);

				return opml.getBody()
						.getOutlines()
						.stream()
						.flatMap(otl -> extractPodcasts(otl).stream());

			}

		} catch (XmlPullParserException | IOException e) {
			e.printStackTrace();
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
			}
		}
		
		return podcasts;
	}
	
}
