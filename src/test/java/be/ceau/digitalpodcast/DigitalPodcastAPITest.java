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
