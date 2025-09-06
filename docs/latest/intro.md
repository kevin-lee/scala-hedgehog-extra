---
sidebar_position: 1
id: 'intro'
title: 'Hedgehog Extra'
slug: '/'
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Hedgehog Extra

[![Build Status](https://github.com/kevin-lee/scala-hedgehog-extra/workflows/Build-All/badge.svg)](https://github.com/kevin-lee/scala-hedgehog-extra/actions?workflow=Build-All)
[![Release Status](https://github.com/kevin-lee/scala-hedgehog-extra/workflows/Release/badge.svg)](https://github.com/kevin-lee/scala-hedgehog-extra/actions?workflow=Release)
![GitHub Release Date](https://img.shields.io/github/release-date/kevin-lee/scala-hedgehog-extra?logo=github)

![Maven Central Version](https://img.shields.io/maven-central/v/io.kevinlee/hedgehog-extra-core_3)
[![Latest version](https://index.scala-lang.org/kevin-lee/scala-hedgehog-extra/latest.svg)](https://index.scala-lang.org/kevin-lee/scala-hedgehog-extra)

[![Hits](https://hits.sh/github.com/kevin-lee/scala-hedgehog-extra.svg)](https://hits.sh/github.com/kevin-lee/scala-hedgehog-extra/)
[![codecov](https://codecov.io/gh/kevin-lee/scala-hedgehog-extra/graph/badge.svg?token=eRXmN9YMzk)](https://codecov.io/gh/kevin-lee/scala-hedgehog-extra)

![Hedgehog Extra Logo](/img/hedgehog-extra-320x320.png)

Newtypes and Refinement types for Scala 3

|                      Project | Maven Central                                                                                               | Scala.js | Scala Native |
|-----------------------------:|-------------------------------------------------------------------------------------------------------------|----------|--------------|
|          hedgehog-extra-core | ![Maven Central Version](https://img.shields.io/maven-central/v/io.kevinlee/hedgehog-extra-core_3)          | ✅        | ✅            |
|       hedgehog-extra-refined | ![Maven Central Version](https://img.shields.io/maven-central/v/io.kevinlee/hedgehog-extra-refined_3)       | ✅        | ❌            |
|     hedgehog-extra-refined4s | ![Maven Central Version](https://img.shields.io/maven-central/v/io.kevinlee/hedgehog-extra-refined4s_3)     | ✅        | ✅            |

:::info
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-1.18.0.svg)](https://www.scala-js.org)
[![cats-core Scala version support](https://index.scala-lang.org/kevin-lee/scala-hedgehog-extra/hedgehog-extra-core/latest-by-scala-version.svg?platform=native0.5)](https://index.scala-lang.org/kevin-lee/scala-hedgehog-extra/hedgehog-extra-core)

Hedgehog Extra also supports Scala.js and Scala Native.

:::

## Getting Started

To get `hedgehog-extra` for your project,

### hedgehog-extra-core

<Tabs
groupId="hedgehog-extra"
defaultValue="hedgehog-extra-sbt"
values={[
{label: 'sbt', value: 'hedgehog-extra-sbt'},
{label: 'sbt (with libraryDependencies)', value: 'hedgehog-extra-sbt-lib'},
{label: 'scala-cli', value: 'hedgehog-extra-scala-cli'},
]}>
<TabItem value="hedgehog-extra-sbt">

In `build.sbt`,

```scala
"io.kevinlee" %% "hedgehog-extra-core" % "@VERSION@"
```

or for Scala.js and Scala Native

```scala
"io.kevinlee" %%% "hedgehog-extra-core" % "@VERSION@"
```

  </TabItem>

  <TabItem value="hedgehog-extra-sbt-lib">

In `build.sbt`,

```scala
libraryDependencies += "io.kevinlee" %% "hedgehog-extra-core" % "@VERSION@"
```

or for Scala.js and Scala Native

```scala
libraryDependencies += "io.kevinlee" %%% "hedgehog-extra-core" % "@VERSION@"
```

  </TabItem>

  <TabItem value="hedgehog-extra-scala-cli">

```scala
//> using dep "io.kevinlee::hedgehog-extra-core:@VERSION@"
```

  </TabItem>
</Tabs>

***

### hedgehog-extra-refined4s

For Scala 3, you have the option to use `refined4s` in place of `newtype` and `refined`, along with the support for `refined4s` provided by `hedgehog-extra`.

<Tabs
groupId="hedgehog-extra"
defaultValue="hedgehog-extra-sbt"
values={[
{label: 'sbt', value: 'hedgehog-extra-sbt'},
{label: 'sbt (with libraryDependencies)', value: 'hedgehog-extra-sbt-lib'},
{label: 'scala-cli', value: 'hedgehog-extra-scala-cli'},
]}>
<TabItem value="hedgehog-extra-sbt">

In `build.sbt`,

```scala
"io.kevinlee" %% "hedgehog-extra-refined4s" % "@VERSION@"
```

or for Scala.js and Scala Native

```scala
"io.kevinlee" %%% "hedgehog-extra-refined4s" % "@VERSION@"
```

  </TabItem>

  <TabItem value="hedgehog-extra-sbt-lib">

In `build.sbt`,

```scala
libraryDependencies += "io.kevinlee" %% "hedgehog-extra-refined4s" % "@VERSION@"
```

or for Scala.js and Scala Native

```scala
libraryDependencies += "io.kevinlee" %%% "hedgehog-extra-refined4s" % "@VERSION@"
```

  </TabItem>

  <TabItem value="hedgehog-extra-scala-cli">

```scala
//> using dep "io.kevinlee::hedgehog-extra-refined4s:@VERSION@"
```

  </TabItem>
</Tabs>

***


### hedgehog-extra-refined

<Tabs
groupId="hedgehog-extra"
defaultValue="hedgehog-extra-sbt"
values={[
{label: 'sbt', value: 'hedgehog-extra-sbt'},
{label: 'sbt (with libraryDependencies)', value: 'hedgehog-extra-sbt-lib'},
{label: 'scala-cli', value: 'hedgehog-extra-scala-cli'},
]}>
<TabItem value="hedgehog-extra-sbt">

In `build.sbt`,

```scala
"io.kevinlee" %% "hedgehog-extra-refined" % "@VERSION@"
```

or for Scala.js

```scala
"io.kevinlee" %%% "hedgehog-extra-refined" % "@VERSION@"
```

  </TabItem>

  <TabItem value="hedgehog-extra-sbt-lib">

In `build.sbt`,

```scala
libraryDependencies += "io.kevinlee" %% "hedgehog-extra-refined" % "@VERSION@"
```

or for Scala.js

```scala
libraryDependencies += "io.kevinlee" %%% "hedgehog-extra-refined" % "@VERSION@"
```

  </TabItem>

  <TabItem value="hedgehog-extra-scala-cli">

```scala
//> using dep "io.kevinlee::hedgehog-extra-refined:@VERSION@"
```

  </TabItem>
</Tabs>

***

### All hedgehog-extra modules

<Tabs
groupId="hedgehog-extra"
defaultValue="hedgehog-extra-sbt"
values={[
{label: 'sbt', value: 'hedgehog-extra-sbt'},
{label: 'sbt (with libraryDependencies)', value: 'hedgehog-extra-sbt-lib'},
{label: 'scala-cli', value: 'hedgehog-extra-scala-cli'},
]}>
<TabItem value="hedgehog-extra-sbt">

In `build.sbt`,

```scala
"io.kevinlee" %% "hedgehog-extra-core" % "@VERSION@",
"io.kevinlee" %% "hedgehog-extra-refined" % "@VERSION@",
"io.kevinlee" %% "hedgehog-extra-refined4s" % "@VERSION@",
```

or for Scala.js

```scala
"io.kevinlee" %%% "hedgehog-extra-core" % "@VERSION@",
"io.kevinlee" %%% "hedgehog-extra-refined" % "@VERSION@",
"io.kevinlee" %%% "hedgehog-extra-refined4s" % "@VERSION@",
```

or for Scala Native

```scala
"io.kevinlee" %%% "hedgehog-extra-core" % "@VERSION@",
"io.kevinlee" %%% "hedgehog-extra-refined4s" % "@VERSION@",
```

  </TabItem>

  <TabItem value="hedgehog-extra-sbt-lib">

In `build.sbt`,

```scala
libraryDependencies ++= Seq(
  "io.kevinlee" %% "hedgehog-extra-core" % "@VERSION@",
  "io.kevinlee" %% "hedgehog-extra-refined" % "@VERSION@",
  "io.kevinlee" %% "hedgehog-extra-refined4s" % "@VERSION@",
)
```

or for Scala.js

```scala
libraryDependencies ++= Seq(
  "io.kevinlee" %%% "hedgehog-extra-core" % "@VERSION@",
  "io.kevinlee" %%% "hedgehog-extra-refined" % "@VERSION@",
  "io.kevinlee" %%% "hedgehog-extra-refined4s" % "@VERSION@",
)
```

or for Scala Native

```scala
libraryDependencies ++= Seq(
  "io.kevinlee" %%% "hedgehog-extra-core" % "@VERSION@",
  "io.kevinlee" %%% "hedgehog-extra-refined4s" % "@VERSION@",
)
```

  </TabItem>

  <TabItem value="hedgehog-extra-scala-cli">

```scala
//> using dep "io.kevinlee::hedgehog-extra-core:@VERSION@"
//> using dep "io.kevinlee::hedgehog-extra-refined:@VERSION@"
//> using dep "io.kevinlee::hedgehog-extra-refined4s:@VERSION@"
```

  </TabItem>
</Tabs>

## MORE TO BE ADDED LATER

