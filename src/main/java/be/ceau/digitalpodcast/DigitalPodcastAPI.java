/*
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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.ceau.opml.OpmlParseException;
import be.ceau.opml.OpmlParser;
import be.ceau.opml.entity.Outline;

/**
 * Implementation of <a href="https://www.digitalpodcast.com/">Digital Podcast</a>
 */
public class DigitalPodcastAPI {

	private static final Logger logger = LoggerFactory.getLogger(DigitalPodcastAPI.class);

	private static final String DIRECTORY = "https://www.digitalpodcast.com/opml/digitalpodcast.opml";
	private static final String DIRECTORY_NO_ADULT = "https://www.digitalpodcast.com/opml/digitalpodcastnoadult.opml";
	private static final String DIRECTORY_CLEAN = "https://www.digitalpodcast.com/opml/digitalpodcastclean.opml";
	private static final String NEW = "https://www.digitalpodcast.com/opml/digitalpodcastnew.opml";
	private static final String NEW_NO_ADULT = "https://www.digitalpodcast.com/opml/digitalpodcastnewnoadult.opml";
	private static final String NEW_CLEAN = "https://www.digitalpodcast.com/opml/digitalpodcastnewclean.opml";
	private static final String MOST_VIEWED = "https://www.digitalpodcast.com/opml/digitalpodcastmostviewed.opml";
	private static final String MOST_VIEWED_NO_ADULT = "https://www.digitalpodcast.com/opml/digitalpodcastmostviewednoadult.opml";
	private static final String MOST_VIEWED_CLEAN = "https://www.digitalpodcast.com/opml/digitalpodcastmostviewedclean.opml";
	private static final String TOP_RATED = "https://www.digitalpodcast.com/opml/digitalpodcasttoprated.opml";
	private static final String TOP_RATED_NO_ADULT = "https://www.digitalpodcast.com/opml/digitalpodcasttopratednoadult.opml";
	private static final String TOP_RATED_CLEAN = "https://www.digitalpodcast.com/opml/digitalpodcasttopratedclean.opml";
	private static final String SUBSCRIBED = "https://www.digitalpodcast.com/opml/digitalpodcastmostsubscribed.opml";
	private static final String SUBSCRIBED_NO_ADULT = "https://www.digitalpodcast.com/opml/digitalpodcastmostsubscribednoadult.opml";
	private static final String SUBSCRIBED_CLEAN = "https://www.digitalpodcast.com/opml/digitalpodcastmostsubscribedclean.opml";

	private final OpmlParser opmlParser = new OpmlParser();
	private final HttpClient httpClient = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(15L))
			.build();

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

			var uri = URI.create(link);

			var request = HttpRequest.newBuilder(uri)
					.GET()
					.build();

			var response = httpClient.send(request, BodyHandlers.ofInputStream());

			var opml = opmlParser.parse(response.body());

			return opml.getBody()
					.getOutlines()
					.stream()
					.flatMap(otl -> extractPodcasts(otl).stream());

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new DigitalPodcastException(e);
		} catch (OpmlParseException | IOException e) {
			throw new DigitalPodcastException(e);
		}

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
