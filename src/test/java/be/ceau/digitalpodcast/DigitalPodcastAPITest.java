package be.ceau.digitalpodcast;

import org.junit.Assert;
import org.junit.Test;

public class DigitalPodcastAPITest {

	@Test
	public void test() {
		long count = new DigitalPodcastAPI().getMostViewed()
			.peek(p-> Assert.assertNotNull("getMostViewed() stream can not contain null", p))
			.peek(p-> Assert.assertNotNull("podcast getName can not return null", p.getName()))
			.peek(p-> Assert.assertNotNull("podcast getUrl can not return null", p.getUrl()))
			.count();
		Assert.assertTrue("there should be at least one result", count > 0);
	}

}
