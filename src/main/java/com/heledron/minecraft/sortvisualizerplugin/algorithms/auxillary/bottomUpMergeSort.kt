package com.heledron.minecraft.sortvisualizerplugin.algorithms.auxillary

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm
import kotlin.math.min

val bottomUpMergeSort: SortingAlgorithm = fun (array: ArrayEditor, alloc: MemoryEditor) {
	bottomUpMergeSort(array, alloc.createArray(array.length), array.length);
}


private fun bottomUpMergeSort(array: ArrayEditor, auxArray: ArrayEditor, n: Int) {
	// Each 1-element run in array is already "sorted".
	// Make successively longer sorted runs of length 2, 4, 8, 16... until the whole array is sorted.

	var a = array;
	var b = auxArray;

	var width = 1;
	while (width < n) {
		var i = 0;
		while (i < n) {
			// Merge two runs: A[i:i+width-1] and A[i+width:i+2*width-1] to B[]
			// or copy A[i:n-1] to B[] ( if (i+width >= n) )
			merge(a, i, min(i+width, n), min(i+2*width, n), b);

			i += 2 * width
		}

		// Swap
		var t = a;
		a = b;
		b = t;

		width *= 2;
	}

	if (a != array) copyArray(auxArray, array, array.length);
}

//  Left run is A[iLeft :iRight-1].
// Right run is A[iRight:iEnd-1  ].
private fun merge(a: ArrayEditor, iLeft: Int, iRight: Int, iEnd: Int, b: ArrayEditor) {
	var i = iLeft;
	var j = iRight;
	// While there are elements in the left or right runs...

	for (k in iLeft until iEnd) {
		// If left run head exists and is <= existing right run head.
		if (i < iRight && (j >= iEnd || a[i].lessThan(a[j]))) {
			b[k].copy(a[i]);
			i += 1;
		} else {
			b[k].copy(a[j]);
			j += 1;
		}
	} 
}

private fun copyArray(b: ArrayEditor, a: ArrayEditor, n: Int) {
	for (i in 0 until n) a[i].copy(b[i]);
}