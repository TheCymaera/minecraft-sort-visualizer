package com.heledron.minecraft.sortvisualizerplugin

class Pointer(val array: Int, val index: Int) {
}

class Memory() {
    val arrays: ArrayList<ArrayList<Int>> = ArrayList();

    fun clone(): Memory {
        val out = Memory();
        for (array in arrays) out.arrays.add(ArrayList(array));
        return out;
    }

    fun set(pointer: Pointer, value: Int) {
        val array = arrays[pointer.array];
        while (pointer.index >= array.size) array.add(0);
        array[pointer.index] = value;
    }

    fun get(pointer: Pointer): Int {
        return arrays[pointer.array][pointer.index];
    }
}