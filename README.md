# Digital Podcast API

Implementing the API @ www.digitalpodcast.com in Java

[![Maven Central badge](https://maven-badges.herokuapp.com/maven-central/be.ceau/digitalpodcast-api/badge.svg)](https://mvnrepository.com/artifact/be.ceau/digitalpodcast-api)  [![Javadocs](https://javadoc.io/badge/be.ceau/digitalpodcast-api.svg)](https://javadoc.io/doc/be.ceau/digitalpodcast-api)  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)

Java library implementing API calls at [digitalpodcast](http://www.digitalpodcast.com/)

### Usage

```Java
Stream<Podcast> podcasts = new DigitalPodcastAPI().getTopRated();
```

### Requirements
This library requires Java 8 or higher.

### Maven Central
Include this project directly from Maven Central
```XML
<dependency>
	<groupId>be.ceau</groupId>
	<artifactId>digitalpodcast-api</artifactId>
	<version>${project.version}</version>
</dependency>
```

### GnuPG public key
Verify signature files with my [GnuPG public key](https://www.ceau.be/pubkey.gpg).

### License
Licensed under [the Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.txt).