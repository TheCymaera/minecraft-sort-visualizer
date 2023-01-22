package com.heledron.minecraft.sortvisualizerplugin.algorithms.others

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm

val buildStairs: SortingAlgorithm = fun (array: ArrayEditor, _: MemoryEditor) {
    for (i in 0 until array.length) array[i].set(i + 1);
}