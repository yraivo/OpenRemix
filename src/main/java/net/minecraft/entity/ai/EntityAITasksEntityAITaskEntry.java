package net.minecraft.entity.ai;

class EntityAITasksEntityAITaskEntry {
    public int priority;
    public EntityAIBase action;
    EntityAITasks field_75732_c;

    public EntityAITasksEntityAITaskEntry(
            EntityAITasks bjmValue, int priorityValue, EntityAIBase bjlValue) {
        this.field_75732_c = bjmValue;
        this.priority = priorityValue;
        this.action = bjlValue;
    }
}
