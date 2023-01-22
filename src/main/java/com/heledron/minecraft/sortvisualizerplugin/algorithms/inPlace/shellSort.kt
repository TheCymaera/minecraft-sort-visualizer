package com.heledron.minecraft.sortvisualizerplugin.algorithms.inPlace

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm

val shellSort: SortingAlgorithm = fun (array: ArrayEditor, _: MemoryEditor) {
	var gap = array.length;
	while (gap > 1) {
		gap = (gap.toDouble() / 2).toInt();
		for (i in gap until array.length) {
			var j = i;
			while (j >= gap && array[j - gap].greaterThan(array[j])) {
				array[j - gap].swap(array[j]);
				j -= gap;
			}
		}
	}
}
