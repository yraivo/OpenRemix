package net.minecraft.entity.monster;

import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.monster.IMobMobSelectorPredicate;
import net.minecraft.entity.monster.IMobVisibleMobSelectorPredicate;

import com.google.common.base.Predicate;

public interface IMob extends IAnimals {
    Predicate mobSelector = new IMobMobSelectorPredicate();
    Predicate VISIBLE_MOB_SELECTOR = new IMobVisibleMobSelectorPredicate();
}
