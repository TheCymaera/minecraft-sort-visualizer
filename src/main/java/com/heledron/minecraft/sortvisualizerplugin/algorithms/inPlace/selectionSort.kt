package com.heledron.minecraft.sortvisualizerplugin.algorithms.inPlace

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm

val selectionSort: SortingAlgorithm = fun (array: ArrayEditor, _: MemoryEditor) {
	for (current in 0 until array.length) {
		var max = current;
		for (i in current + 1 until array.length) {
			if (array[max].greaterThan(array[i])) max = i;
		}
		array[current].swap(array[max]);
	}
}
