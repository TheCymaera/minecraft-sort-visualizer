package com.heledron.minecraft.sortvisualizerplugin.algorithms.inPlace

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm

val bubbleSort: SortingAlgorithm = fun (array: ArrayEditor, _: MemoryEditor) {
	for (i in 0 until array.length) {
		for (j in 0 until array.length - i - 1) {
			if (array[j].greaterThan(array[j + 1])) {
				array[j].swap(array[j + 1]);
			}
		}
	}
}
