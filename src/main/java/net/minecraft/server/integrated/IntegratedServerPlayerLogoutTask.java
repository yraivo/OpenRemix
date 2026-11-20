package net.minecraft.server.integrated;

import net.minecraft.entity.player.EntityPlayerMP;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;

class IntegratedServerPlayerLogoutTask implements Runnable {
    IntegratedServer field_177494_a;

    IntegratedServerPlayerLogoutTask(IntegratedServer byTValue) {
        this.field_177494_a = byTValue;
    }

    @Override
    public void run() {
        String[] astring1 = IntegratedServer.getWorldLoadFlowMarker();
        ArrayList arrayList =
                Lists.newArrayList(this.field_177494_a.getConfigurationManager().playerEntityList);
        Iterator iterator = arrayList.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iterator.next();
            this.field_177494_a.getConfigurationManager().playerLoggedOut(entityPlayerMP);
            if (astring == null) {
                break;
            }
        }
    }
}
