MavenFind
=========

this is command line tool to search library in Maven Central Repository.

[Maven のセントラルリポジトリ](http://search.maven.org/#search|ga|1|) をコマンドラインから検索するためのツール。

bower の search みたいな感覚で検索したかったので。

##How to use
###デフォルト共通動作
- 条件に一致するライブラリの最新バージョンが一覧表示されます。
- 上位 20 件のみが表示されます。

###任意検索
```text
> groovy mvnfind -q commons-lang
com.btmatthews.jaxb2_commons:jaxb2-commons-lang3:1.0.0
com.cedarsoft.commons:lang:6.0.1
com.mysema.commons:mysema-commons-lang:0.2.4
commons-lang:commons-lang:20030203.000129
net.bramp.jackson:jackson-datatype-commons-lang3:0.1
net.sf.staccatocommons:commons-lang:1.2
org.andromda.thirdparty.jaxb2_commons:commons-lang-plugin:2.2
org.apache.commons:commons-lang3:3.3.2
org.apache.directory.studio:org.apache.commons.lang:2.6
org.apache.karaf.demos.deployer.wrap:osgi.commons-lang:2.3.6
org.apache.karaf.eik.plugins:org.apache.commons.lang:2.6
org.apache.servicemix.bundles:org.apache.servicemix.bundles.commons-lang:2.4_6
org.jvnet.jaxb2_commons:jaxb2-commons-lang:2.4
org.kie.modules:org-apache-commons-lang-main:6.2.0.Beta1
org.kie.modules:org-apache-commons-lang3:6.2.0.Beta1
org.mod4j.org.apache.commons:lang:2.1.0
```

`-q` で任意検索。

###グループID などを指定
```text
> groovy mvnfind -g org.apache.commons -a commons-lang3
org.apache.commons:commons-lang3:3.3.2
```

`-g` で `groupId`、 `-a` で `artifactId`、 `-v` で `version` を指定できる。

###全バージョンを表示する
```text
> groovy mvnfind -g org.apache.commons -a commons-lang3 --allVersions
org.apache.commons:commons-lang3:3.0
org.apache.commons:commons-lang3:3.0.1
org.apache.commons:commons-lang3:3.1
org.apache.commons:commons-lang3:3.2
org.apache.commons:commons-lang3:3.2.1
org.apache.commons:commons-lang3:3.3
org.apache.commons:commons-lang3:3.3.1
org.apache.commons:commons-lang3:3.3.2
```

`--allVersions` を指定する。

###表示件数を指定する
```text
> groovy mvnfind -g org.apache.commons --max 5
org.apache.commons:commons-compress:1.8.1
org.apache.commons:commons-csv:1.0
org.apache.commons:commons-dbcp2:2.0.1
org.apache.commons:commons-email:1.3.3
org.apache.commons:commons-math3:3.3
```

`--max` を指定する。



