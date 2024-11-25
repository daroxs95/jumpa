![Frame 3.png](doc/Frame%203.png)

# Jumpa

Real time generative art and processing + hype gradle project template...ish
## How to run

```bash
./gradlew run
```

## How to build

```bash
./gradlew build
```

## Features
- Real time processing
- Minim audio library
- Video playing

## Visualizations

Visuals are intended to be used with DOOMxGUTS music
and custom Doom Eternal gameplay footage.

## ⚠️ MAC USERS ⚠️ (THIS IS SOMEWHAT OUTDATED)
- Testes on apple silicon M3 chip,
building the fat jar and running the app works fine
(probably rosetta is involved), 
but running the app using the gradle task `run` will probably not work.

## Hot reload
- To enable hot reload, run the app with the flag `XX:+AllowEnhancedClassRedefinition`
and manage which effects for example are enabled/disabled in the `Main` class but inside the draw method.
This approach is not fail-proof, but it works for now for some high level updates.
(In intelliJ you can add this flag in the run configuration and the in build menu hit the rebuild module)