# Sort Visualizer Plugin
## Demos
- [New Video](https://www.youtube.com/watch?v=Lvd03mSjYZ0)
- [Old Video](https://www.youtube.com/watch?v=7b5-G1oVg7o)

## Usage
The presentation is rendered at (0, 50, 0) in the Overworld, facing the positive X direction.

Get items used to control the visualizer.
```
/sortVis GIVE_ITEMS
```

Run a specific algorithm.
```
/sortVis {ALGORITHM_NAME}
```

Change the size of the array, where {INT} is the number of elements.
```
/sortVis RESIZE {INT}
```

Change the presentation speed, where {INT} is the number of game ticks between each step.
```
/sortVis EVENT_DURATION {INT}
```

## Customization
To add an algorithm:
- Find an existing algorithm in the `algorithms` folder for reference.
- Create a new file, and declare a function using the reference.
- Register the algorithm in the `algorithms` array inside `SortVisualizerPlugin.kt`.
```kotlin
val algorithms = arrayOf<AlgorithmDetails>(
	...
	AlgorithmDetails("myAlgorithm", "My Algorithm", myAlgorithm),
);
```

