package com.heledron.minecraft.sortvisualizerplugin.algorithms.others

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm
import kotlin.math.floor

val shuffle: SortingAlgorithm = fun (array: ArrayEditor, _: MemoryEditor) {
	for (i in 0 until array.length) {
		val j = floor(Math.random() * (i + 1)).toInt();
		array[i].swap(array[j]);
	}
}