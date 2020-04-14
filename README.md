# SEP-Project

Project for Software Engineering Practice module; I refactored a Java client-server program using:

* Command design pattern
* Functional expressions
* Object-oriented design principles (i.e encapsulation, documentation, reduced duplication)
* JUnit testing
* Static code analysis
* Test coverage tools
* Version control

## Deployment

The client can be ran from an IDE capable of compiling Java, the main arguments can be set via the project configuration.
Required arguments are : user name, host name and port number. E.g. Aston localhost 8888.
The server can be concurrently ran via a command line by navigating to the root project folder, and entering '-cp build\classes sep.seeter.server.Server [port number]'.

## Built With

* [NetBeans IDE 8.2](https://netbeans.org/community/releases/82/) - The IDE used.
* [JaCoCo](https://www.eclemma.org/jacoco/) - Java Code Coverage library.
* [FindBugs™](http://findbugs.sourceforge.net/) - Java static code analysis tool.
* [EasyPmd](http://plugins.netbeans.org/plugin/57270/easypmd) - NetBeans Java static code analysis plugin.
* [Lizard](http://www.lizard.ws/) - Code complexity analyser.

## Authors

* **Raymond Hu** - *Initial project* - [Dr Raymond Hu](https://bit.ly/3a8AMHT)
* **Aston Turner** - *All modifications* - [Aston13](https://github.com/Aston13)

## Acknowledgments

* Raymond Hu for setting up this assignment and providing me with a platform to further my learning.
