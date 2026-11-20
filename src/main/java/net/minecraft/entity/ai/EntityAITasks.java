package net.minecraft.entity.ai;

import net.minecraft.profiler.Profiler;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class EntityAITasks {
    private static Logger logger;

    private int tickCount;

    private List taskEntries = Lists.newArrayList();
    private Profiler theProfiler;
    private int tickRate;
    private List executingTaskEntries = Lists.newArrayList();

    private boolean method_1c83bcaf42c48f3a8cde(
            EntityAITasksEntityAITaskEntry entityAITasksEntityAITaskEntry) {
        String text = EntityAIBase.cJ();
        Iterator iterator = this.taskEntries.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            EntityAITasksEntityAITaskEntry entityAITasksEntityAITaskEntryValue =
                    (EntityAITasksEntityAITaskEntry) iterator.next();
            if (entityAITasksEntityAITaskEntryValue != entityAITasksEntityAITaskEntry) {
                if (entityAITasksEntityAITaskEntry.priority
                        >= entityAITasksEntityAITaskEntryValue.priority) {
                    if (!this.areTasksCompatible(
                                    entityAITasksEntityAITaskEntry,
                                    entityAITasksEntityAITaskEntryValue)
                            && this.executingTaskEntries.contains(
                                    entityAITasksEntityAITaskEntryValue)) {
                        return false;
                    }
                } else if (!entityAITasksEntityAITaskEntryValue.action.isInterruptible()
                        && this.executingTaskEntries.contains(
                                entityAITasksEntityAITaskEntryValue)) {
                    return false;
                }
            }

            if (textValue == null) {
                break;
            }
        }

        return true;
    }

    private boolean areTasksCompatible(
            EntityAITasksEntityAITaskEntry entityAITasksEntityAITaskEntry,
            EntityAITasksEntityAITaskEntry entityAITasksEntityAITaskEntryValue) {
        return (entityAITasksEntityAITaskEntry.action.getMutexBits()
                        & entityAITasksEntityAITaskEntryValue.action.getMutexBits())
                == 0;
    }

    static {
        net.minecraft.entity.ai.EntityAITasks.logger = LogManager.getLogger();
    }

    public void removeTask(EntityAIBase entityAIBase) {
        String text = EntityAIBase.cJ();
        Iterator iterator = this.taskEntries.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            EntityAITasksEntityAITaskEntry entityAITasksEntityAITaskEntry =
                    (EntityAITasksEntityAITaskEntry) iterator.next();
            EntityAIBase bjl = entityAITasksEntityAITaskEntry.action;
            if (bjl == entityAIBase) {
                if (this.executingTaskEntries.contains(entityAITasksEntityAITaskEntry)) {
                    bjl.resetTask();
                    this.executingTaskEntries.remove(entityAITasksEntityAITaskEntry);
                }

                iterator.remove();
            }

            if (textValue == null) {
                break;
            }
        }
    }

    private boolean method_a81e8b2c2ee3dd1bdae5(
            EntityAITasksEntityAITaskEntry entityAITasksEntityAITaskEntry) {
        return entityAITasksEntityAITaskEntry.action.continueExecuting();
    }

    public EntityAITasks(Profiler bjkValue) {
        this.tickRate = 3;
        this.theProfiler = bjkValue;
    }

    public void addTask(int i, EntityAIBase entityAIBase) {
        this.taskEntries.add(new EntityAITasksEntityAITaskEntry(this, i, entityAIBase));
    }

    public void onUpdateTasks() {
        String text;
        label59:
        {
            String textValue = EntityAIBase.cJ();
            this.theProfiler.startSection("goalSetup");
            text = textValue;
            if (this.tickCount++ % this.tickRate == 0) {
                Iterator iterator = this.taskEntries.iterator();

                while (true) {
                    if (!iterator.hasNext()) {
                        break label59;
                    }

                    EntityAITasksEntityAITaskEntry entityAITasksEntityAITaskEntry =
                            (EntityAITasksEntityAITaskEntry) iterator.next();
                    boolean flag =
                            this.executingTaskEntries.contains(entityAITasksEntityAITaskEntry);
                    if (flag) {
                        if (this.method_1c83bcaf42c48f3a8cde(entityAITasksEntityAITaskEntry)
                                && this.method_a81e8b2c2ee3dd1bdae5(entityAITasksEntityAITaskEntry)
                                && text != null) {
                            continue;
                        }

                        entityAITasksEntityAITaskEntry.action.resetTask();
                        this.executingTaskEntries.remove(entityAITasksEntityAITaskEntry);
                    }

                    if (this.method_1c83bcaf42c48f3a8cde(entityAITasksEntityAITaskEntry)
                            && entityAITasksEntityAITaskEntry.action.shouldExecute()) {
                        entityAITasksEntityAITaskEntry.action.startExecuting();
                        this.executingTaskEntries.add(entityAITasksEntityAITaskEntry);
                    }

                    if (text == null) {
                        break;
                    }
                }
            }

            Iterator iteratorValue = this.executingTaskEntries.iterator();

            while (iteratorValue.hasNext()) {
                EntityAITasksEntityAITaskEntry entityAITasksEntityAITaskEntryValue =
                        (EntityAITasksEntityAITaskEntry) iteratorValue.next();
                if (!this.method_a81e8b2c2ee3dd1bdae5(entityAITasksEntityAITaskEntryValue)) {
                    entityAITasksEntityAITaskEntryValue.action.resetTask();
                    iteratorValue.remove();
                    if (text == null) {
                        break;
                    }
                }
            }
        }

        this.theProfiler.endSection();
        this.theProfiler.startSection("goalTick");

        for (java.util.Iterator<?> __recoveryIterator171_30215 =
                        (this.executingTaskEntries).iterator();
                (__recoveryIterator171_30215).hasNext(); ) {
            EntityAITasksEntityAITaskEntry entityAITasksEntityAITaskEntryValue2 =
                    (EntityAITasksEntityAITaskEntry) (__recoveryIterator171_30215).next();
            entityAITasksEntityAITaskEntryValue2.action.updateTask();
            if (text == null) {
                break;
            }
        }

        this.theProfiler.endSection();
    }
}
