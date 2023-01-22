package com.heledron.minecraft.sortvisualizerplugin.algorithms

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor


typealias SortingAlgorithm = (array: ArrayEditor, memory: MemoryEditor) -> Unit;