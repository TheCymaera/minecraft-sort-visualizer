package com.heledron.minecraft.sortvisualizerplugin.algorithms.others

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm

private const val giveUpAfter = 1000;

val bogoSort: SortingAlgorithm = fun (array: ArrayEditor, memory: MemoryEditor) {
	var i = 0;
    while (!isSorted(array)) {
		shuffle(array, memory);
        if (i > giveUpAfter) return;
        i++;
    }
}


private fun isSorted(array: ArrayEditor): Boolean {
	for (i in 0 until array.length - 1) {
		if (array[i].greaterThan(array[i + 1])) return false;
	}
	return true;
}