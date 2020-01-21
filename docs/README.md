
# Java ecosystem's 10 Best Practice pillars for the 2020's
#### (title continued...) for back end / data engineering, relative to Java from '00s, from tech manager's POV, with examples.
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

In 2020's we install Java 11 LTS via SDKMan, and simply run the app via:

```
	java -jar myApp.jar
``` 

Oh, an why be JVM based? Because JVM is better then C++, the closest alternative. (One example of why C++ is bad is that trillion dollar F35 plane is a failure, and it was written in C++)

## #2: Cloud/S3

There is still a lot of organizations that have not moved to the the cloud and the basic cloud service is S3 (aka Object Store or Hadoop). 
S3 providers include AWS, GAE, but also Linode, Wasabi, Vultr, etc. Storing data is very cheap, almost free, so storing PetaBytes and more is common. Here is an example of writing to S3 from Scala, using the Java helper classes:

```
    val s3: BasicS3Util = new BasicS3Util(server, access, secret, bucket)
    new LoadS3().load(s3)

```
And that calls this class:

```
  def load(s3: BasicS3Util): Unit = {
    new SNX().getSNX
    _s3 = s3
    ins() // each one is a million, so 3 million rows inserted
    ins()
    ins()
   }//()

   def ins(): Unit = {
    // 25*40* 1000 = 1 Million rows inserted
    val mCount: Int = 25
    var i: Int = 0
    while (i <= mCount) {
      val t: TimerU = TimerU.start()
      _insBatch(40 * 1000)
      i = i + 1
      println(" in " + t.time())
    }
  }//()

  def _insBatch(count: Int): Unit = {
    var i: Int = 0
    val lst: java.util.List[java.util.Map[String, Object]] = new ArrayList()
    while (i <= count) {
      val row: java.util.Map[String, Object] = new HashMap()
      row.put("name", _faker.name.nameWithMiddle())
      row.put("city", _faker.address.city())
      row.put("ip",   _faker.internet.ipV4Address().toString)
      row.put("date", _faker.date.backward().toGMTString())
      row.put("cc",   _faker.business.creditCardType())
      row.put("dept", _faker.commerce.department())
      row.put("price",_faker.commerce.price())
      lst.add(row) 
      i = i + 1
    }
    _s3.put(prefix, lst)
  }//()

```
The calls helper functions convert the java.util.List of rows to be stored into an InputStream, becuse S3 is implmented like a file system. We can later find our lists via 'path'.

Above uses Vultr's S3 - it is easier to use than AWS. There are a ton of great cloud services, if you want to do a small coding execise later in this article, sign up for http://emailjs.com account.


## #3: DB in RAM Memory

DB's using RAM is a new and revolutionary improvement.

Before the 2020's a DB (SQL, Object, FTS, Graph, etc.) would be stored on an SSD, NVMe, SSD, etc. 
But now cloud providers have machines that have 512 Gigs of RAM and more, and even terabytes of RAM is available. And RAM is much faster than SSDs. 
And if terabytes if to small for your DB? You can cluster you DB cloud containers and combine several DB servers into one any size you need.

Our new friends include Aerospkie, Spark, Clickhouse, Apache Ignite, etc. 

Our old friend REDIS works well, as does SQLite. SQLite works in RAM, or can have temp tables in RAM for materialized views.

This is a a paradigm shift, and requires learning and internalizing. So not only should your cloud VM's be 512 Gig or RAM or more, your local development machine should also have 128Gig of RAM or more. (Some development workstation examples with 128Gig plus or RAM: iMac Pro, System 76 and Digital Tigers).

##### Note: Lots of people use a term Big Data. It is not Big Data if it fits on my $800 laptop with 1TB SSD Driver. There is no need to cluster data that are in terabytes, it fits in RAM of a single box. We need a new term: small data, when you have less than a few terabytes.

### Spark-like Data Engineering

Spark is very popular. I'm going to oversimplify Spark: it lets you take unstructured data from data lakes dumps such as S3, then execute memory SQL operations (via a Spark culstur) and report on the data, using charts. 

We can read from S3 into SQLite and then use the Java Poi library to save to a spreadsheet. From a spreadsheet it is a few clicks to make a chart or a graph. 
Data Engineers used CSV in the past, but using SQLite DB is a big improvement. 


## #4: Tools: Gradle.build, Jitpack, Cloud IDE.

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

##### No XML. The entire code base backing this article is not using any XML. XML is a replant for good programmers, they move to other platforms. Rod Johnson, creator of Spring-Boot has encouraged migration away form XML with some political hints.


Aside, before the '20s we started deploying to cloud. Now our IDE can be in the cloud also, eg: CodeAnywhere.


### Demo Example 1: 

Here is an example project folder that includes items we mentioned so far: Scala, gradle.build and SQLite. It is a simple Scala project that uses a Java lib (in the lib folder, but deployed )
 that measures how many records we can read from S3 and insert per second. You can change the code to have SQLite built-in feature to use RAM DB instead of disk. It leverages a few helper classes that I added to the SNX lib.

- https://github.com/cekvenich/SNX/tree/master/SNX_01


### Next: JAMstack.org

Next few points will touch on something called JAMstack, simplistically it is an  API way of working with (generated) front end, including SPA.

## #5: JAR for Services/'REST', with Reactive Streams

#### A quick history lesson
In ancient times, Java would use containers such as Tomcat via WAR files that contained WEB-INF/libs and such. Then it eveloved into using Trustin Lee's Netty project - used by Twiter, Akka, and more. Netty was an async (NIO) network library, just a jar and did not need WAR or container making it easier to maintain. 

Also in 2010's Reactive programming became popular (I won't explain here what Reactive is, it would take more space, but most of the explanation on WWW are poor) and Reactive Streams were added to Java 9 by Doug Lea. Apache http core has a Java library by Oleg Kalnichevski that leverages the Java 9 reactive streams. 

### Demo Example 2: 

Here is an example Scala project folder that uses Apache http core library v5 that emits a simple 'REST' GET JSON response. It also acts as a simple HTTP Server, so you can write an index.html that uses **fetch()**, we don't use Ajax anymore. There is an HTML page that calls fetch() to GET a JSON response. The example is synchronous, but the library used supports reactive streams. 

- https://github.com/cekvenich/SNX/tree/master/SNX_02


## #6: Stress/Load testing

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

## #7: Cloud Devops

In the past you may put nginx in front of your REST services, but now we use Cloud to front the services. We use a CDN. CDN provides https offloading, early TLS handshake, HTTP/3(udp based), etc.  Also it helps with security since the endpoint IP of the services is not exposed to the WWW. 

CDN also helps with Blue-Green deployments, and even gradual deployment, where you deploy to Canada or APAC first, and a day later to EU and Americas once you know there are no stability issues with a new push.


## #8: Client side API, Client side ViewModel

Today from a browser we write **fetch()**. Question: Who should write that fetch() command that runs in the browser on the client side? 2020's the answer is: Back end engineer. The APIs are done by the back end team going forward. Front end has to worry about UX, CSS design, CSS frameworks and back end team supports them. Those API calls to fetch: the fetch() commands are writen in a ViewModel class (JavaScript/TypeScript support classes that can transpile to ES5/IE11 via tools like PrePros.io and such). You write a ViewModel per page/screen, just like before when you wrote the ViewModel server side.

This is where a git 'uni repo' comes in, the ViewModel is tied to the View and the ViewModel API are tied to the server side services. The bank end team has to follow the View changes and map to it. The front end developer just uses the ViewModel, and does not touch the internal fetch() code. 
This way it is the back end developer that is responsible for TLS and user authentication for each API call, it is their lib. The front end developers can't do transport layer well. And back end team handles all the user authentication and data security. 

Of course, same goes for IOS(ObjectiveC)) and Andorid(Java) apps. Back end engineers must be polyglot and write and support the ViewModel and API calls cross platform. You can even do fancy things related to topology aware client, such as calling EU, USA or APAC back end as needed or more. And you have more power, for example if you change the encoding and not bother the front end devs. They use the ViewModel and are not aware of the implementation. You still document the 'REST' calls, but write the ViewModel as well.

This is similar to AWS Amplify or Google FiereBase, where you just download the .js libs and are not aware how the back end is implemented. Even if you implement a 3rd party backendless services like the 2 mentioned, you need a back end engineer to do the back ups and write the ViewModel.

Here is server code snippet using Apache http core:

```
  var _routes: Map[String, IRoute] = routes
  var _docRoot: String = docRoot

  def handle(req: ClassicHttpRequest, resp: ClassicHttpResponse, context: HttpContext) {
      // browser and CDN cache:
      resp.setHeader("Cache-Control", "public, max-age=" + 1 + ", s-max-age=" + 1)

      var PATH: String = getPath(req)
      // check if File in docRoot, else serve an SSR Route
      var file: File = new File(this._docRoot + PATH)
      if (file.exists() && !file.isDirectory()) {
        serveAFile(file, resp, context)
        return
      }

      // above is file, but now API route not found?
      if (!_routes.containsKey(PATH)) {
        var outgoingEntity: StringEntity = new StringEntity("no such resource " + PATH)
        err(resp, outgoingEntity)
        return
      }

	  //else we have an API route registered
      var r = _routes.get("/API1")
      var params = getParams(req)
      var ret = r.ret(params)
      var outgoingEntity = new StringEntity(ret)
      good(resp, outgoingEntity)

```

And here is .js file loaded in a browser that calls above service:

```
	class UserVM {
	   constructor(cb) {
		  fetch('http://localhost:8888/API1')
		  .then((response) => {
		    return response.json()
		  })
		  .then((myJson) => {
		   cb(myJson)
		  })
	   }
	}//class
```


## #9: Switch to E2E testing (with CI/CD).

E2E is end to end testing, and it is better than our older methods. Before, we were dogmatic about Unit testing, and we can still use Unit testing where needed, for example libraries. But E2E testing is required.

With E2E you automate the testing of the end point, in this case you test the ViewModel/API.  If the ViewModel works, then everything integrates and everything must work! So in addition to stress/load testing of the server service, you must do ViewModel/API testing of Browser, Android and IOS. 
Also, you can still do some unit tests where you think they are needed, but it is no longer exposed to up to management, management will just check on E2E.


E2E is a radical difference and requires time to internalize. You should do the lab here that does a simple E2E. 
E2E has 5 components: 

- 1) ViewModel/API javascript, one per each page/screen of the application.
- 2) A running http server that provides services to that View/Model API; and a running http file server that runs the html ages so we can server them.
In our case our lib will use Apache http core to do both, serve REST and serve html files.
- 3) QUnit(or similar) index.html page that in the browser tests the ViewModel/API .js. We can run this test manually by opening the test pages in a browser. Your E2E tests are written in .js to run in the browser.
- 4) Selenium Chormedriver (v4), in Java will open a 'browser' and call a .js function to start the 'QUnit' test. It will then report the test results, for example via email. So you will have a CLI Scala script that runs the http server and runs a Selenium in a thread that call to the .js testing function.
- 5) CI/CD Scala server that is running at all times in the cloud. It gets called by webhooks to do E2E, load testing, staging deployments, etc. This is something you have to write. 

### Lab Example: E2E Test ( test the client side API/ViewModel)

I will now show the Selenium code since this is classic code (and the full source code example code is available in this git project).

But I'll show you the QUnit javascript/typescript code on the client called by Selenium, that tests the ViewModel:

```
class TestVM1 {
   constructor () {
      depp.define({'vm1':'/api/UserVM.js'})
      depp.require(['vm1'], function(){

      QUnit.test( "hello tests", function( assert ) {
         TestVM1._done1 = assert.async()
         TestVM1._assert = assert
         console.log('in test:')
         new UserVM(function(json){
            console.log(json)
            _WDcb(json)
            TestVM1._assert.ok(true) //passed. Should check json, but...
            TestVM1._done1()
         })
      })//tests
      })//req
   }//()
}//class

// setup the webdrive callback
var _WDcb
function webDriverFoo(WDcb) {
   _WDcb = WDcb
   console.log('start tests')
   var pro = loadQunit()
   pro.then(function(){
      QUnit.config.autostart = false

      console.log('qunit loaded')
   
      //QUnit.start()
      new TestVM1()
   })//pro
}

```



## #10 Bonus: SSR (Server Side Rendering) with Pug

We used to use PHP, ASP, JSP for SSR, heck 1/3 of WWW is PHP.
But here is what we use in NodeJS, something more moderm:


![](expressjs.png)

And in Java/Scala we can do this:

```
   var _render: JadeConfiguration = new JadeConfiguration()

  def Pug(): String = {
    val cwd: String = System.getProperty("user.dir")
    val loader: TemplateLoader = new FileTemplateLoader(cwd + "/routes/", "UTF-8")
    _render.setTemplateLoader(loader)
    _render.setCaching(false)
    val template1: JadeTemplate = _render.getTemplate("index.pug")
    
    val model1: java.util.Map[String, Object] = new java.util.HashMap[String, Object]()
    model1.put("city", "Bremen")
    //binding
    val html: String = _render.renderTemplate(template1, model1)
    html
  }
```

Are you not impressed yet? OK, let me teach you Pug in 15 seconds:
- https://html2pug.now.sh

2020's we use templating engines, for example eBay uses Marko and here is other examples on staticgen.com. Pug is a good one, so when you need SSR, then use Pug.


The End

----


### Scope 

The scope of the best practices is limited to what an average engineer can learn then in 1 day, and a Sr engineer can learn them in 1/2 of a day.
It was influenced Spring-boot, Spark and Akka

The major changes are that you must use 128gig to 8tb of RAM, you must do stress testing, and the ViewModel is now client side.


## Conclusion

Are you an experienced Java tech leader?
If so:

- I listed some good particles. Is there another?

- And most important: is there anything I should remove or correct!


Reach out to me please and help me. vic(at) eml.cc



