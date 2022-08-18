<img align="right" src="logos/tofu-mascot.png" height="170px" style="padding-left: 20px"/>

# Glass

[![Build & Release](https://github.com/tofu-tf/tofu/workflows/Scala%20CI/badge.svg)](https://github.com/tofu-tf/tofu/actions?query=workflow%3A%22Scala+CI%22)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/tf.tofu/tofu-core-ce3_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/tf.tofu/tofu-core_2.13)
[![Discord Chat](https://img.shields.io/discord/657318688025739283.svg)](https://discord.gg/qPD5GGH)
[![Zulip](https://img.shields.io/badge/zulip-join_chat-brightgreen.svg)](https://chat.zulip.org)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)

**Glass is a modern optic library, formely known as tofu-optics** 

# Quick Start

To use the whole utils pack just add to your `build.sbt`: 

```scala
libraryDependencies += "tf.tofu" %% "glass" % "latest version in badge"
```

Of course, you can also specify an exact list of `glass` modules that you want to add to your dependencies (used in place
of `"glass"`):

* `glass-core` for optics core (Optics typeclasses)
* `glass-interop` for optics interop with [Monocle](https://github.com/julien-truffaut/Monocle)
* `glass-macro` for macro optics generators

<img align="right" src="logos/tofu-logo.png" height="100px" style="padding-left: 5px"/>


# Adopters

Proud user of Glass? Feel free to [add your company!](https://github.com/tofu-tf/glass/edit/master/README.md)

<a href="https://tinkoff.ru/"><img width="40%" src="logos/yandex-travel-logo.svg?sanitize=true" /></a>

<a href="https://vivid.money/"><img width="40%" src="logos/vivid.svg?sanitize=true" /></a>

<a href="https://tele2.ru/"><img width="40%" src="logos/tele2-ru-logo.svg?sanitize=true" /></a>

<a href="https://konfy.care/"><img width="40%" src="logos/konfy-logo.svg?sanitize=true" /></a>

<a href="https://www.raiffeisen.ru/en/"><img width="40%" src="logos/raiffeisen-logo.svg?sanitize=true" alt="Raiffeisen Bank Russia"/></a>

<a href="https://www.rms.com/"><img width="15%" src="logos/rms-logo.svg?sanitize=true" alt="Risk Management Solutions" /></a>

# Contributing

Please note we use the following labels for automated release descriptions:
  * `chore` if your PR does not change any types and runtime semantics
  * `fix` if your PR merely fixes incorrect behavior

## Formatting
  We have an automated check for style conformance. You can run `sbt checkfmt` before PR.
  If you have any trouble during this check, just run `sbt fmt` and commit again.
  
# Copyright
Copyright the maintainers, 2019-2022

Logos made with love by [@impurepics](https://twitter.com/impurepics)
