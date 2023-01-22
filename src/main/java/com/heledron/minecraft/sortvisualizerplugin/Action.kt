package com.heledron.minecraft.sortvisualizerplugin

import java.util.*

abstract class EditorAction {
    abstract fun apply(memory: Memory): EditorAction;
    abstract fun reads(): Array<Pointer>;
    abstract fun writes(): Array<Pointer>;
}

class CreateArrayAction(val size: Int) : EditorAction() {
    override fun apply(memory: Memory): EditorAction {
        memory.arrays.add(ArrayList(size));
        return this;
    }

    override fun reads(): Array<Pointer> {
        return emptyArray();
    }

    override fun writes(): Array<Pointer> {
        return emptyArray();
    }
}

class SwapAction(val a: Pointer, val b: Pointer) : EditorAction() {
    override fun apply(memory: Memory): EditorAction {
        val temp = memory.get(b);
        memory.set(b, memory.get(a));
        memory.set(a, temp);
        return this;
    }

    override fun reads(): Array<Pointer> {
        return arrayOf(a, b);
    }

    override fun writes(): Array<Pointer> {
        return arrayOf(a, b);
    }
}

class CopyAction(val a: Pointer, val b: Pointer): EditorAction() {
    override fun apply(memory: Memory): EditorAction {
        memory.set(a, memory.get(b));
        return this;
    }

    override fun reads(): Array<Pointer> {
        return arrayOf(b);
    }

    override fun writes(): Array<Pointer> {
        return arrayOf(a);
    }
}

class SetAction(val pointer: Pointer, val value: Int): EditorAction() {
    override fun apply(memory: Memory): EditorAction {
        memory.set(pointer, value);
        return this;
    }

    override fun reads(): Array<Pointer> {
        return emptyArray();
    }

    override fun writes(): Array<Pointer> {
        return arrayOf(pointer);
    }
}

class ReadAction(vararg pointers: Pointer): EditorAction() {
    val pointers = pointers as Array<Pointer>;
    override fun apply(memory: Memory): EditorAction {
        return this;
    }

    override fun reads(): Array<Pointer> {
        return pointers.clone();
    }

    override fun writes(): Array<Pointer> {
        return emptyArray();
    }
}