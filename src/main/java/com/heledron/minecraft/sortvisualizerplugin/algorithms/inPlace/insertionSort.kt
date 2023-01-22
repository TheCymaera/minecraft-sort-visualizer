package com.heledron.minecraft.sortvisualizerplugin.algorithms.inPlace

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm

val insertionSort: SortingAlgorithm = fun (array: ArrayEditor, _: MemoryEditor) {
	for (i in 1 until array.length) {
		var j = i;
		while (j > 0 && array[j - 1].greaterThan(array[j])) {
			array[j - 1].swap(array[j]);
			j--;
		}
	}
}