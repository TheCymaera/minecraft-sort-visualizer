package com.heledron.minecraft.sortvisualizerplugin

import com.heledron.minecraft.sortvisualizerplugin.algorithms.SortingAlgorithm
import com.heledron.minecraft.sortvisualizerplugin.algorithms.auxillary.*
import com.heledron.minecraft.sortvisualizerplugin.algorithms.inPlace.*
import com.heledron.minecraft.sortvisualizerplugin.algorithms.others.*
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask


class AlgorithmDetails(key: String, message: String, algorithm: SortingAlgorithm) {
    val key = key;
    val message = message;
    val algorithm = algorithm;
}



val algorithms = arrayOf<AlgorithmDetails>(
        AlgorithmDetails("quickSort", "Quick Sort", quickSort),

        AlgorithmDetails("bottomUpMergeSort", "Bottom Up Merge Sort", bottomUpMergeSort),
        AlgorithmDetails("topDownMergeSort", "Top Down Merge Sort", topDownMergeSort),
        AlgorithmDetails("bucketSort", "Bucket Sort (n=3)", bucketSort),

        AlgorithmDetails("selectionSort", "Selection Sort", selectionSort),
        AlgorithmDetails("heapSort", "Heap Sort", heapSort),
        AlgorithmDetails("bubbleSort", "Bubble Sort", bubbleSort),
        AlgorithmDetails("insertionSort", "Insertion Sort", insertionSort),
        AlgorithmDetails("shellSort", "Shell Sort", shellSort),
);

val specialAlgorithms = arrayOf<AlgorithmDetails>(
        AlgorithmDetails("shuffle", "Shuffling...", shuffle),
        AlgorithmDetails("buildStairs", "Building Stairs...", buildStairs),
        AlgorithmDetails("bogoSort", "Bogo Sort", bogoSort),
        AlgorithmDetails("bozoSort", "Bozo Sort", bozoSort),
);

class SortVisualizerPlugin : JavaPlugin() {
    override fun onEnable() {
        println("========================");
        println("Enabling Plugin");

        val algorithmsByKey = HashMap<String, AlgorithmDetails>();
        for (algorithm in algorithms) algorithmsByKey[algorithm.key] = algorithm;
        for (algorithm in specialAlgorithms) algorithmsByKey[algorithm.key] = algorithm;

        val renderer = BarChartRenderer(Location(this.server.getWorld("world"), 0.0, 50.0, 0.0));

        val memory = Memory();

        fun resize(size: Int) {
            renderer.clear(if (memory.arrays.isNotEmpty()) memory.arrays[0].size else 100);

            memory.arrays.clear();
            memory.arrays.add(ArrayList());
            for (i in 1 .. size) memory.arrays[0].add(i);

            renderer.render(this, "", memory, emptyList());
        }

        resize(44);

        var runningTasks: List<BukkitTask> = emptyList();

        fun runAlgorithm(algorithm: AlgorithmDetails) {
            for (task in runningTasks) task.cancel();

            while (memory.arrays.size > 1) memory.arrays.removeLast();

            val editor = MemoryEditor(memory.clone());
            algorithm.algorithm(editor.getArray(0), editor);

            runningTasks = renderer.render(this, algorithm.message, memory, editor.changes);
        }

        var currentAlgorithm = 0;
        fun sequence() {
            val key = algorithms[currentAlgorithm].key;
            runAlgorithm(algorithmsByKey[key]!!);
            currentAlgorithm = (currentAlgorithm + 1) % algorithms.size;
        }

        val itemKey = NamespacedKey(this, "item");

        getCommand("sortVis")!!.setExecutor(fun (sender, _, _, args): Boolean {
            if (args.isEmpty()) return false;
            var key = args[0];

            if (key == "RESIZE") {
                if (args.size != 2) return false;
                resize(args[1].toInt());
                return true;
            }

            if (key == "EVENT_DURATION") {
                if (args.size != 2) return false;
                renderer.eventDuration = args[1].toInt();
                return true;
            }

            if (args.size != 1) return false;

            if (key == "GIVE_ITEMS") {
                val shuffleItem = ItemStack(Material.GUNPOWDER);
                val shuffleMeta = shuffleItem.itemMeta;
                shuffleMeta.persistentDataContainer.set(itemKey, PersistentDataType.STRING, "shuffle");
                shuffleMeta.displayName(Component.text("Shuffle"));
                shuffleItem.itemMeta = shuffleMeta;

                val seqItem = ItemStack(Material.REDSTONE);
                val seqMeta = seqItem.itemMeta;
                seqMeta.persistentDataContainer.set(itemKey, PersistentDataType.STRING, "sequence");
                seqMeta.displayName(Component.text("Sort"));
                seqItem.itemMeta = seqMeta;

                val bogoItem = ItemStack(Material.CLOCK);
                val bogoMeta = bogoItem.itemMeta;
                bogoMeta.persistentDataContainer.set(itemKey, PersistentDataType.STRING, "bogoSort");
                bogoMeta.displayName(Component.text("Bogo Sort"));
                bogoItem.itemMeta = bogoMeta;

                val bozoItem = ItemStack(Material.DEAD_BUSH);
                val bozoMeta = bozoItem.itemMeta;
                bozoMeta.persistentDataContainer.set(itemKey, PersistentDataType.STRING, "bozoSort");
                bozoMeta.displayName(Component.text("Bozo Sort"));
                bozoItem.itemMeta = bozoMeta;

                val player = sender as Player;
                player.inventory.addItem(shuffleItem);
                player.inventory.addItem(seqItem);
                player.inventory.addItem(bogoItem);
                player.inventory.addItem(bozoItem);
            }

            if (key == "RESET_SEQUENCE") {
                sender.sendMessage("Reset sequence");
                currentAlgorithm = 0;
            }

            if (key == "SEQUENCE") {
                sender.sendMessage("Running: $key");
                sequence();
            }

            if (key == "LIST") {
                sender.sendMessage("Algorithms:");
                for (i in algorithms.indices) {
                    val padding = if (i >= 9) "   " else "    ";
                    sender.sendMessage("${padding}${i + 1}: ${algorithms[i].key}");
                }

                for (algorithm in specialAlgorithms) {
                    sender.sendMessage("    ?: ${algorithm.key}");
                }
                return true;
            }

            if (!algorithmsByKey.containsKey(key)) {
                sender.sendMessage("\"$key\" is not a registered algorithm");
            } else {
                sender.sendMessage("Running: $key");
                runAlgorithm(algorithmsByKey[key]!!);
            }

            return true;
        });

        this.server.pluginManager.registerEvents(object : Listener {
            @EventHandler(priority = EventPriority.HIGH)
            fun on(event: PlayerInteractEvent) {
                if (!(event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR)) return
                val player = event.player
                val item = player.inventory.itemInMainHand

                val itemKind = item.itemMeta.persistentDataContainer.get(itemKey, PersistentDataType.STRING) ?: return;

                when (itemKind) {
                    "sequence" -> {
                        sequence();
                    }
                    "shuffle" -> {
                        runAlgorithm(algorithmsByKey["shuffle"]!!);
                    }
                    "bogoSort" -> {
                        runAlgorithm(algorithmsByKey["bogoSort"]!!);
                    }
                    "bozoSort" -> {
                        runAlgorithm(algorithmsByKey["bozoSort"]!!);
                    }
                }
            }
        }, this)

        println("========================");
    }

    override fun onDisable() {
        println("========================");
        println("Disabling Plugin");
        println("========================");
    }
}