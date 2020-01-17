
# 2020's Best Practice pillars for the Java ecosystem
#### (title continued...) relative to Java from '00s, from tech manager's POV, with examples. Plus a few bad practices
by Vic Cekvenich

Summary: There are 2 radical differences in today's Java ecosystem relative to the prior decade. The listed difference amount to a difference in kind, not a difference in degree. We have achieved in the 2020's an order of magnitude improvement. 

### Background

I am Vic Cekvenich, my claim to fame is writing the first book on Java Struts, a predecessor to Spring Boot. I have been looking at Apache Spark, Akka, etc. and I think we can do better, specially as polyglot. There are few ideas we can leverage from NodeJS's Express library. 
And by better I might easier and faster. So lets review the 2020's Best Practice pillars for the Java ecosystem! 

##### NOTE: If anyone would like to join this 2020 Java ecosystem effort, please do that by contacting me at: vic (at) eml.cc .

## #1. Scala, Kotlin, Groovy
SDKMan, Scala, and Java 11 are incremental improvements for the 2020's Java ecosystem.

Kotlin is JVM default used for Google's Andorid platform. Scala is popular for Data Science. And Groovy is dynamic, like NodeJS and Python.
All 3 are more concise than Java for writing applications, utilities, etc. Of the 3 Scala seems easiest to adopt, you can for example go online and paste Java code and it will be converted to Scala. But when writing a library, it is better to write it in Java, so it can be used by others on the JVM platform. 

In 2020's we install Java 11 LTS via SDKMan, and run simply the app via:

```
	java -jar app1.jar
``` 

Oh, an why be JVM based? Because JVM is better then C++, the closest alternative. (One example of why C++ is bad is that trillion dollar F35 plane is a failure, and it was written in C++)


## #2: DB in RAM Memory

DB's using RAM is a new and revolutionary improvement.

Before the 2020's a DB (SQL, Object, FTS, Graph, etc.) would be stored on an SSD, NVMe, SSD, etc. 
But now cloud providers have machines that have 512 Gigs of RAM and more, and even terabytes of RAM is available. And RAM is much faster than SSDs. 
And if terabytes if to small for your DB? You can cluster you DB cloud containers and combine several DB servers into one any size you need.

We can still use cheap S3 type storage to dump things. But for HFT, AdTech, Data Science and such: we can and should store in RAM. 

Our old friend REDIS works well, as does SQLite. SQLite works in RAM, or can have temp tables in RAM for materialized views.

Our new friends include Aerospkie, Spark, Clickhouse, Apache Ignite, etc. 

This is a a paradigm shift, and requires learning and internalizing. So not only should your cloud VM's be 512 Gig or RAM or more, your local development machine should also have 128Gig of RAM or more. (Some development workstation examples with 128Gig plus or RAM: iMac Pro, System 76 and Digital Tigers).

##### Note: Lots of people use a term Big Data. It is not Big Data if it fits on my $800 laptop with 1TB SSD Driver. There is no need to cluster things that are in terabytes.

## #3: Tools: Gradle.build, Jitpack, Cloud IDE.

Before the '20's, we used to use POM.xml. The improvement is self evident with gradle.build.

Compare the power, here a build.gradle that publishes a jar to Jitpack, so you can use the jar in apps via gradle (or maven). Once published, you can now use your lib anywhere in the JVM ecosystem. It is self evident how  much easier the new way is compared to what we had to deal with publishing with XML to Maven:

```
buildscript {
  repositories {
    maven { url "https://jitpack.io" }
  }
  dependencies {
    classpath 'com.github.jengelman.gradle.plugins:shadow:5.2.0'
  }
}
plugins { id 'com.github.johnrengelman.shadow' version '5.2.0' }

apply plugin: 'com.github.johnrengelman.shadow'
repositories {
	maven { url 'https://jitpack.io' }
    mavenCentral()
}
dependencies { }
archivesBaseName = 'XXX'
shadowJar {
   baseName = 'XXX'
   classifier = '' archiveVersion = ''
}
tasks.build.dependsOn tasks.shadowJar
artifacts { archives shadowJar }
// needs jitpack.yaml in root for java version and gradle for its version
group = 'com.github.jitpack'

```

It is just as easy to publish a jar as it is to publish npm from the NodeJS ecosystem. Also a big win is that it is less likely that you will break the build, as everything including IDE's revolves around the gradle.build.


Aside, before the '20s we started deploying to cloud. Now our IDE can be in the cloud also, eg: CodeAnywhere.


### Demo Example 1: 

Here is an example project folder that includes items we mentioned so far: Scala, gradle.build and SQLite. It is a simple Scala project that uses a Java lib (in the lib folder, but deployed )
 that measures how many records we can insert per second. You can change the code to have SQLite built-in feature to use RAM instead of disk. It leverages a few helper classes that I added to the SNX lib.

- https://github.com/cekvenich/SNX/tree/master/SNX_01


### Next: JAMstack.org

Next few points will touch on something called JAMstack, simplistically it is an  API way of working with (generated) front end, including SPA.

## #4: JAR for 'REST', not WAR; plus Reactive Streams.

#### A quick history lesson
In ancient times, Java would use containers such as Tomcat via WAR files that contained WEB-INF/libs and such. Then it eveloved into using Trustin Lee's Netty project - used by Twiter, Akka, and more. Netty was an async (NIO) network library, just a jar and did not need WAR or container making it easier to maintain. 

Also in 2010's Reactive programming became popular (I won't explain here what Reactive is, it would take more space, but most of the explanation on WWW are poor) and Reactive Streams were added to Java 9 by Doug Lea. Apache http core has a Java library by Oleg Kalnichevski that leverages the Java 9 reactive streams. 

### Demo Example 2: 

Here is an example Scala project folder that uses Apache http core library v5 that emits a simple 'REST' GET JSON response. It also acts as a simple HTTP Server, so you can write an index.html that uses **fetch()**, we don't use Ajax anymore. There is an HTML page that calls fetch() to GET a JSON response. The example is synchronous, but the library used supports reactive streams. 

- https://github.com/cekvenich/SNX/tree/master/SNX_02


## #5: Stress/Load testing

Java now comes with a built in HTTP Client. In the past we might have setup jMeter or Grinder, but now writing a test script the uses the built in Java HTTP Client wrapped in executor pool makes it just as easy to script GETs and POSTs. 

```
	public String GET(String uri) throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		return response.body();
	}
```

There is a helper class that wraps this method in an executor to simulate appropriate load. You need to write a CLI Scala script to start the http services, runs the stress test, and then report the results (for example email the results). 
Stress/Load testing is **not optional**. It can be scripted into your CI process to catch any performance regressions. (Github has hooks that can transparently call our little Apache http core lib and  email us the performance results as well as use the Jitpack API as needed). 

## #6: Cloud Devops

In the past you may put nginx in front of your REST services, but now we use Cloud to front the services. We use a CDN. CDN provides https offloading, early TLS handshake, HTTP/3(udp based), etc.  Also it helps with security since the endpoint IP of the services is not exposed to the WWW. 

CDN also helps with Blue-Green deployments, and even gradual deployment, where you deploy to Canada or APAC first, and a day later to EU and Americas once you know there are no stability issues with a new push.


## #7: Client side API, Client side ViewModel

Today from a browser we write **fetch()** to. Question: Who should write that fetch() command that runs in the browser on the client side? 2020's the answer is: Back end engineer. The APIs are done by the back end team going forward. Front end has to worry about UX, CSS design, CSS frameworks and back end team supports them. Those API calls to fetch: the fetch() commands are writen in a ViewModel class (JavaScript/TypeScript support classes that can transpile to ES5/IE11 via tools like PrePros.io and such). You write a ViewModel per page/screen, just like before when you wrote the ViewModel server side.

This is where a git 'uni repo' comes in, the ViewModel is tied to the View and the ViewModel API are tied to the server side services. The bank end team has to follow the View changes and map to it. The front end developer just uses the ViewModel, and does not touch the internal fetch() code. 
This way it is the back end developer that is responsible for TLS and user authentication for each API call, it is their lib. The front end developers can't do transport layer well. And back end team handles all the user authentication and data security. 

Of course, same goes for IOS(ObjectiveC)) and Andorid(Java) apps. Back end engineers must be polyglot and write and support the ViewModel and API calls cross platform. You can even do fancy things related to topology aware client, such as calling EU, USA or APAC back end as needed or more. And you have more power, for example if you change the encoding and not bother the front end devs. They use the ViewModel and are not aware of the implementation. You still document the 'REST' calls, but write the ViewModel as well.

This is similar to AWS Amplify or Google FiereBase, where you just download the .js libs and are not aware how the back end is implemented. Even if you implement a 3rd party backendless services like the 2 mentioned, you need a back end engineer to do the back ups and write the ViewModel.

Note: I keep using REST in quotes, you don't have to do strict REST, but you must be able to write a stress test against it.









## #8: No more unit tests, no TDD, no BDD: Time for E2E testing with CI/CD

E2E is end to end testing, and it is better than our older methods. With E2E you automate the testing of the end point, in this case you test the ViewModel/API.  If the ViewModel works, then everything integrates and everything must work! So in addition to stress/load testing of the server service, you must do ViewModel/API testing of Browser, Android and IOS. 
Also, you can still do some unit tests where you think they are needed, but it is no longer exposed to up to management, management will just check on E2E.


E2E is a radical difference and requires time to internalize. You should do the lab here that does a simple E2E. 
E2E has 5 components: 

- 1) ViewModel/API javascript, one per each page/screen of the application.
- 2) A running http server that provides services to that View/Model API; and a running http file server that runs the html ages so we can server them.
In our case our lib will use Apache http core to do both, serve REST and serve html files.
- 3) QUnit(or similar) index.html page that in the browser tests the ViewModel/API .js. We can run this test manually by opening the test pages in a browser. Your E2E tests are written in .js to run in the browser.
- 4) Selenium Chormedriver (v4), in Java will open a 'browser' and call a .js function to start the 'QUnit' test. It will then report the test results, for example via email. So you will have a CLI Scala script that runs the http server and runs a Selenium in a thread that call to the .js testing function.
- 5) CI/CD Scala server that is running at all times in the cloud. It gets called by webhooks to do E2E, load testing, staging deployments, etc. This is something you have to write. 

### Lab Example 1: E2E Test ( test the API/ViewModel)

Here is the Qunit

Here is the call to QUnit from Selenium.


## #9: SSR (Server Side Rendering) with Pug


### Like NodeJS's Express
PHP ASP JSP


## #10: Spark-like Data Processing

# Bad smells

## A. Hitchen's Razor & bombastic executive adjectives


#### Fake Big Data 


#### 30X faster. MemSQL



#### Impossible to overstate


## B. 'No XML Assholes'

#### Channeling Linus

#### Rod Johnson hints to reduce XML

#### Alternatives: Front-End, Python, Node.js, Go

#### Logging in Java

## C. Interruptions, like Slack

At home, checking work email.

#### Rengnogled 

## Conclusion

Are you an experienced Java tech leader?
If so:

- I listed 12 good particles. Is there a 13th?

- I listed 2 bad practices. Is the a 3rd?

- And most important: is there anything I should remove!

Reach out to me please and help me. vic(at) eml.cc


#### Forced sharding

#### Blue-Green Deployments

#### To master new way you must absorb the tools


Scope 1 day,  half day