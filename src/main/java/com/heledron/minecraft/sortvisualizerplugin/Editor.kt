package com.heledron.minecraft.sortvisualizerplugin

import java.util.*

class MemoryEditor(val memory: Memory, val changes: MutableList<EditorAction> = ArrayList()) {
    fun getArray(array: Int): ArrayEditor {
        return ArrayEditor(memory, array, changes);
    }

    fun createArray(size: Int): ArrayEditor {
        changes.add(CreateArrayAction(size).apply(memory));
        return ArrayEditor(memory, memory.arrays.size - 1, changes);
    }
}

class ArrayEditor(val memory: Memory, val array: Int, val changes: MutableList<EditorAction>) {
    operator fun get(index: Int): PointerEditor {
        return PointerEditor(memory, Pointer(array, index), changes);
    }

    fun push(item: Int) {
        changes.add(SetAction(Pointer(array, length), item).apply(memory));
    }

    val length get(): Int {
        return memory.arrays[array].size;
    }

    fun toList(): ArrayList<PointerEditor> {
        val out = ArrayList<PointerEditor>();
        for (i in 0 until length) out.add(PointerEditor(memory, Pointer(array, i), changes));
        return out;
    }
}

class PointerEditor(val memory: Memory, val pointer: Pointer, val changes: MutableList<EditorAction>) {
    fun greaterThan(other: PointerEditor): Boolean {
        changes.add(ReadAction(pointer, other.pointer).apply(memory));
        return memory.get(pointer) > memory.get(other.pointer);
    }

    fun greaterThanOrEqualTo(other: PointerEditor): Boolean {
        changes.add(ReadAction(pointer, other.pointer).apply(memory));
        return memory.get(pointer) >= memory.get(other.pointer);
    }

    fun lessThan(other: PointerEditor): Boolean {
        changes.add(ReadAction(pointer, other.pointer).apply(memory));
        return memory.get(pointer) < memory.get(other.pointer);
    }

    fun lessThanOrEqualTo(other: PointerEditor): Boolean {
        changes.add(ReadAction(pointer, other.pointer).apply(memory));
        return memory.get(pointer) <= memory.get(other.pointer);
    }

    fun copy(other: PointerEditor) {
        changes.add(CopyAction(pointer, other.pointer).apply(memory));
    }

    fun swap(other: PointerEditor) {
        changes.add(SwapAction(pointer, other.pointer).apply(memory));
    }

    fun read(): Int {
        changes.add(ReadAction(pointer).apply(memory));
        return memory.get(this.pointer);
    }

    fun set(value: Int) {
        changes.add(SetAction(pointer, value).apply(memory));
    }
}