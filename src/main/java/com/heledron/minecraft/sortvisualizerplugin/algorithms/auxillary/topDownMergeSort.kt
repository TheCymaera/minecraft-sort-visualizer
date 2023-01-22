package com.heledron.minecraft.sortvisualizerplugin.algorithms.auxillary

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm

val topDownMergeSort: SortingAlgorithm = fun (array: ArrayEditor, alloc: MemoryEditor) {
	topDownMergeSort(array, alloc.createArray(array.length), array.length);
}

private fun copyArray(output: ArrayEditor, iBegin: Int, iEnd: Int, input: ArrayEditor) {
	for (i in iBegin until iEnd) output[i].copy(input[i]);
}

// Array A[] has the items to sort; array B[] is a work array.
private fun topDownMergeSort(a: ArrayEditor, b: ArrayEditor, n: Int) {
	copyArray(b, 0, n, a);    // one time copy of A[] to B[]
	splitMerge(b, 0, n, a);   // sort data from B[] into A[]
}

private fun splitMerge(b: ArrayEditor, iBegin: Int, iEnd: Int, a: ArrayEditor) {
	if (iEnd - iBegin <= 1) return;
	val iMiddle = (iEnd + iBegin) / 2;

	// recursively sort both runs from array A[] into B[]
	splitMerge(a, iBegin,  iMiddle, b);  // sort the left  run
	splitMerge(a, iMiddle,    iEnd, b);  // sort the right run
	merge(b, iBegin, iMiddle, iEnd, a);
}

//  Left source half is A[ iBegin:iMiddle-1].
// Right source half is A[iMiddle:iEnd-1   ].
// Result is            B[ iBegin:iEnd-1   ].
private fun merge(a: ArrayEditor, iBegin: Int, iMiddle: Int, iEnd: Int, b: ArrayEditor) {
	var i = iBegin;
	var j = iMiddle;

	// While there are elements in the left or right runs...
	for (k in iBegin until iEnd) {
		// If left run head exists and is <= existing right run head.
		if (i < iMiddle && (j >= iEnd || a[i].lessThanOrEqualTo(a[j]))) {
			b[k].copy(a[i]);
			i++;
		} else {
			b[k].copy(a[j]);
			j++;
		}
	}
}


