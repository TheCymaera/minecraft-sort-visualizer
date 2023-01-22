package com.heledron.minecraft.sortvisualizerplugin.algorithms.inPlace

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor

val quickSort = fun(array: ArrayEditor, _: MemoryEditor) {
	quickSort(array, 0, array.length - 1);
}

// Sorts a (portion of an) array, divides it into partitions, then sorts those
private fun quickSort(A: ArrayEditor, lo: Int, hi: Int) {
	// Ensure indices are in correct order
	if (lo >= hi || lo < 0) return;

	// Partition array and get the pivot index
	val p = partition(A, lo, hi);
	
	// Sort the two partitions
	quickSort(A, lo, p - 1); // Left side of pivot
	quickSort(A, p + 1, hi); // Right side of pivot
}

// Divides array into two partitions
private fun partition(A: ArrayEditor, lo: Int, hi: Int): Int {
	// Temporary pivot index
	var i = lo - 1;

	for (j in lo until hi) {
		// If the current element is less than or equal to the pivot
		if (A[j].lessThan(A[hi])) {
			// Move the temporary pivot index forward
			i += 1;

			// Swap the current element with the element at the temporary pivot index
			A[i].swap(A[j]);
		}
	}
	// Move the pivot element to the correct pivot position (between the smaller and larger elements)
	i += 1;
	A[i].swap(A[hi]);
	return i; // the pivot index
}