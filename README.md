<!-- PROJECT LOGO -->
# Judger Project of the Knight Online Judge 

<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]


A judger of Knight Online Judge which is written in Kotlin to judge the submission code of the online judge.

<!-- ABOUT THE PROJECT -->
## About The Project

This project is from the 30 days challenge which is held by iThome. About the tutorial articles, you can see them [here](https://ithelp.ithome.com.tw/articles/10233368). (in Traditional Chinese) 

### Built With
* [Kotlin](http://kotlinlang.org/)
* [Exposed](https://github.com/JetBrains/Exposed)
* [Docker](http://docker.com)
* [PostgreSQL](http://postgresql.org/)
* [Redis](http://redis.io)

<!-- GETTING STARTED -->
## Getting Started

First, you have to set up the [backend project](https://github.com/sinmaplewing/knight-online-judge-backend-server).

Second, you need to add a file `/main/resources/hikari.properties` to set up the connection setting with the PostgreSQL server. The content in it is:

```
dataSourceClassName=org.postgresql.ds.PGSimpleDataSource
dataSource.user=......
dataSource.password=......
dataSource.databaseName=......
dataSource.portNumber=......
dataSource.serverName=......
```

Third, you need to install Docker to use container to judge the code. You need to pull these images:

1. zenika/kotlin
2. gcc
3. python

Last, run this project with the command `gradlew run`.

<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/sinmaplewing/knight-online-judge-judger/issues) for a list of proposed features (and known issues).

<!-- CONTRIBUTING -->
## Contributing

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<!-- CONTACT -->
## Contact

Maplewing - [Website](https://knightzone.studio) - sinmaplewing@gmail.com

Project Link: [https://github.com/sinmaplewing/knight-online-judge-judger](https://github.com/sinmaplewing/knight-online-judge-judger)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/sinmaplewing/knight-online-judge-judger
[contributors-url]: https://github.com/sinmaplewing/knight-online-judge-judger/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/sinmaplewing/knight-online-judge-judger
[forks-url]: https://github.com/sinmaplewing/knight-online-judge-judger/network/members
[stars-shield]: https://img.shields.io/github/stars/sinmaplewing/knight-online-judge-judger
[stars-url]: https://github.com/sinmaplewing/knight-online-judge-judger/stargazers
[issues-shield]: https://img.shields.io/github/issues/sinmaplewing/knight-online-judge-judger
[issues-url]: https://github.com/sinmaplewing/knight-online-judge-judger/issues
[license-shield]: https://img.shields.io/github/license/sinmaplewing/knight-online-judge-judger
[license-url]: https://github.com/sinmaplewing/knight-online-judge-judger/blob/master/LICENSE.txt