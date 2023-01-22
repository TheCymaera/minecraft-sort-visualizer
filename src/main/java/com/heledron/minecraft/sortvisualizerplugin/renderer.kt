package com.heledron.minecraft.sortvisualizerplugin

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector


class BarChartRenderer(val position: Location) {
    var xStride = Vector(1,0,0);
    var yStride = Vector(0,1,0);

    var eventDuration = 1;

    var blankBlock = Material.BARRIER;
    var block = Material.WHITE_CONCRETE;
    var auxBlock = Material.LIGHT_GRAY_CONCRETE;
    var readBlock = Material.LIME_CONCRETE;
    var writeBlock = Material.RED_CONCRETE;
    var readAndWriteBlock = Material.YELLOW_CONCRETE;

    var highlightSound = Sound.BLOCK_NOTE_BLOCK_HARP;

    fun render(plugin: JavaPlugin, name: String, memory: Memory, changes: List<EditorAction>): List<BukkitTask> {
        renderFrame(this, memory, emptyArray(), emptyArray());

        val out = ArrayList<BukkitTask>();
        for (i in changes.indices) {
            val action = changes[i];

            out.add(plugin.server.scheduler.runTaskLater(plugin,fun () {
                action.apply(memory);
                renderFrame(this, memory, action.reads(), action.writes());
                messageAll(name);
            }, ((i + 1) * this.eventDuration).toLong()));
        }

        out.add(plugin.server.scheduler.runTaskLater(plugin,fun () {
            renderFrame(this, memory, emptyArray(), emptyArray());
            messageAll("");
        }, ((changes.size + 1) * this.eventDuration).toLong()));

        return out;
    }

    fun clear(size: Int) {
        for (x in 0 until size) {
            for (y in 0 until size) {
                val pos = position.clone();
                pos.add(xStride.clone().multiply(x));
                pos.add(yStride.clone().multiply(y));
                pos.block.type = blankBlock;
            }
        }
    }

    private fun messageAll(message: String) {
        for (player in Bukkit.getServer().onlinePlayers) {
            player.sendActionBar(Component.text(message));
        }
    }
}




private fun renderFrame(settings: BarChartRenderer, memory: Memory, reads: Array<Pointer>, writes: Array<Pointer>) {
    val arrayHeight = memory.arrays[0].size;

    for (pointer in listOf(*reads, *writes)) {
        val value = memory.get(pointer);
        val pitch = value.toFloat() / arrayHeight * 1.5f + 0.5f;
        for (player in settings.position.world.players) {
            player.playSound(player, settings.highlightSound, 1.0f, pitch)
        }
    }




    for (a in memory.arrays.indices) {
        val array = memory.arrays[a];

        val yStride = settings.yStride.clone().multiply(1.0f / memory.arrays.size);

        val barPos = settings.position.clone();
        barPos.add(yStride.clone().multiply(arrayHeight * (memory.arrays.size - 1 - a)));

        for (x in 0 until array.size) {
            val pointer = Pointer(a, x);
            val value = array[x];

            val isRead = listContainsPointer(reads, pointer);
            val isWrite = listContainsPointer(writes, pointer);

            val material = if (isRead && isWrite) settings.readAndWriteBlock;
            else if (isRead) settings.readBlock;
            else if (isWrite) settings.writeBlock
            else if (a > 0) settings.auxBlock;
            else settings.block;

            val pos = barPos.clone();

            pos.add(yStride.clone().multiply(arrayHeight));
            for (i in value until arrayHeight) {
                pos.subtract(yStride);
                pos.block.type = settings.blankBlock;
            }

            for (i in 0 until value) {
                pos.subtract(yStride);
                pos.block.type = material;
            }

            barPos.add(settings.xStride);
        }

        for (x in array.size until memory.arrays[0].size) {
            val pos = barPos.clone();

            for (i in 0 until arrayHeight) {
                pos.block.type = settings.blankBlock;
                pos.add(yStride);
            }

            barPos.add(settings.xStride);
        }
    }
}

private fun listContainsPointer(list: Array<Pointer>, pointer: Pointer): Boolean {
    for (other in list) {
        if (pointer.array == other.array && pointer.index == other.index) return true;
    };
    return false;
}
