# Faebook Importer for Shark

The FacebookImporter for Shark is an extension for the [Shark Framework](http://www.sharksystem.net/). It's enables developers to build Facebook importers for the [SharkKnowledgeBase](http://www.sharksystem.net/javadoc/current/) 
by providing an API for building extendable Facebook importers which requests data from Facebook's  [GraphAPI](https://developers.facebook.com/docs/graph-api) and imports this data
into a Shark KnowledgeBase.

# Features

* Execution environment for Facebook importers
* API binding for Facebook's Graph API (Based on [Spring Social Facebook](http://projects.spring.io/spring-social-facebook/))
* Model Classes for Facebook's Graph API
* Login for Facebook's Graph API
* ContextPoint Data binding
* Example Importer which:
  * imports the currently logged in user as Peer Semantic Tag
  * imports it's friends
  * imports it's family relationships
  * imports it's significant other relationship
  * imports it's public posts

# Structure of the Importer

![API Structure](https://github.com/yveskaufmann/sharkb_fbimporter/blob/development/docu/images/fb_importer_structure.png "API Structure")

# How to build

In order to build the facebook importer you need a installed [Java SE Runtime Environment 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) or alternatively
a installed [Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and optional 
a installed [Gradle](https://gradle.org/)>=2.4 but this only a recommendation the build script could be used without any gradle installation.  

The Facebook importer comes with a [Gradle](https://gradle.org/) build script which is responsible
to build the importer. The build script can be executed by any major IDE which provides a [Gradle integration](https://gradle.org/why/integrates-with-everything/). If your are
a NetBeans user then give [GradleSupport](http://plugins.netbeans.org/plugin/44510/gradle-support) a try.

## Build in a IDE

The major IDEs providing a Gradle integration by default or by plugin. is supported by the most major IDEs  

## Grade Tasks

| Tasks        | Description                                                                         |  
| ------------ |-------------------------------------------------------------------------------------| 
| build        | Builds the importer, creates javadoc and load all required dependencies from central maven repository |
| dependencies | Load all required dependencies from central maven repository.                        |
| javadoc      | Generates the javadoc for the facebook importer.                                    |
| distZip      | Creates a distribution archive which contains the builded importer, created javadoc and os specific scripts for starting the facebook importer. When you only wanna use the example importer than this is choise. The buileded archive could be found inside the build/distributions directory. |
| run          | Build and run the facebook importer.   |
| eclipse      | Generates a eclipse ide project in order to open the source code in eclipse.      |
| idea         | Generates a idea ide project in order to open the source code in idea.      |

