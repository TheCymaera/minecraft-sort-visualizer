package com.heledron.minecraft.sortvisualizerplugin.algorithms.inPlace

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm

val heapSort: SortingAlgorithm = fun (array: ArrayEditor, _: MemoryEditor) {
	for (i in array.length - 1 downTo 0) {
		heapify(array, i);
	}

	for (i in array.length - 1 downTo 0) {
		array[0].swap(array[i]);
		heapify(array, 0, i - 1);
	}
}

private fun heapify(array: ArrayEditor, index: Int, end: Int = array.length - 1) {
	var largest = index;
	val left = 2 * index + 1;
	val right = 2 * index + 2;

	if (left <= end && array[left].greaterThan(array[largest])) {
		largest = left;
	}
	if (right <= end && array[right].greaterThan(array[largest])) {
		largest = right;
	}
	if (largest != index) {
		array[index].swap(array[largest]);
		heapify(array, largest, end);
	}
}