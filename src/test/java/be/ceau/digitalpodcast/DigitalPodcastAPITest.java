package be.ceau.digitalpodcast;

import org.junit.Test;

public class DigitalPodcastAPITest {

	@Test
	public void test() {
		new DigitalPodcastAPI().getSubscribed().forEach(System.out::println);
	}

}
