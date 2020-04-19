# MavenFind
Maven のセントラルリポジトリのアーティファクトを、コマンドラインから検索できるようにするためのツール。  
The command line tool to find artifacts in Maven Central Repository.

See : https://search.maven.org/classic/#api

## Requirements
- JDK11+

## Installation
**Windows**

```
> gradlew.bat jar
```

**Linux**

```
$ ./gradlew jar
```

---

- `build/libs/mvnfind-X.X.X.jar` が生成されるので、任意のフォルダに配置する
- `build/libs/mvnfind-X.X.X.jar` will be created. Move the jar file into any directory.


## Usage
```
$ java -jar mvnfind-X.X.X.jar --help
```

上記コマンドを実行するか、以下のヘルプテキストを確認してください。
Please execute above command or see following text. 

See : [help.txt](https://github.com/opengl-8080/MavenFind/blob/master/src/main/resources/help.txt)

## Release Notes
- 2020-04-19
    - First Release
