package com.heledron.minecraft.sortvisualizerplugin.algorithms.auxillary

import com.heledron.minecraft.sortvisualizerplugin.ArrayEditor
import com.heledron.minecraft.sortvisualizerplugin.MemoryEditor
import com.heledron.minecraft.sortvisualizerplugin.algorithms.inPlace.quickSort

private const val k = 3;
val bucketSort = fun(array: ArrayEditor, alloc: MemoryEditor) {
	val buckets: ArrayList<ArrayEditor> = ArrayList();
	for (i in 0 until k) buckets.add(alloc.createArray(0));

	val max = array.toList().maxOfOrNull { i -> i.read() } ?: 0;

	for (i in 0 until array.length) {
		val value = array[i].read();
		val percent = value.toDouble() / max;
		val bucketIndex = if (value === max) k-1 else (k * percent).toInt();
		buckets[bucketIndex].push(value);
	}

	for (bucket in buckets) quickSort(bucket, alloc);

	var n = 0;
	for (bucket in buckets) {
		for (item in bucket.toList()) {
			array[n].copy(item);
			n += 1;
		}
	}
}