package remix.module.render;

import net.minecraft.client.network.PlayerInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import remix.client.friend.FriendEntry;
import remix.client.friend.FriendManager;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.event.impl.TextRenderEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;







import java.util.ArrayList;



import java.util.List;



import java.util.Map.Entry;







/** NameProtect module: render events and 4 settings. */



public final class NameProtectModule extends ModuleBase {







    private boolean playerListDirty;



    private static String[] packedTextFragments;



    public Setting friendsSetting;



    private Setting othersSetting;



    private List<String> protectedPlayerNames;



    public final Setting skinsSetting = new Setting("Skins", this, true);







    public final Setting capesSetting = new Setting("Capes", this, true);







    public NameProtectModule() {



        super(



                "NameProtect",



                new ModuleDefaults(ModuleCategory.RENDER).visible(false));



        this.friendsSetting = new Setting("Friends", this, true);



        this.othersSetting = new Setting("Others", this, true);



        this.protectedPlayerNames = new ArrayList<>();



        this.setName("Name Protect");



    }










    // region Static initialization and string tables



    static {



        remix.module.render.NameProtectModule.packedTextFragments = new String[3];



        remix.module.render.NameProtectModule.packedTextFragments[0] = new String(" ");



    }







    // endregion







    @Subscribe(mc = TextRenderEvent.class, priority = SubscriberPriority.HIGH)



    public void onTextRender(TextRenderEvent event) {



        String text = event.getText();



        String textValue2 = minecraft.session.getUsername();



        if (text.contains(textValue2)) {



            text = text.replace(textValue2, "You");



        }







        if (this.friendsSetting.asBoolean()) {



            for (Entry entry : FriendManager.friendsMap.entrySet()) {



                String key = (String) entry.getKey();



                if (text.contains((CharSequence) entry.getKey())) {



                    text =



                            text.replace(



                                    key,



                                    ((FriendEntry) entry.getValue())



                                            .getAlias());



                }







                



            }



        }







        if (this.othersSetting.asBoolean() && minecraft.world != null) {



            List protectedPlayerNamesValue = this.protectedPlayerNames;



            if (this.playerListDirty) {



                for (PlayerInfo playerInfo : minecraft.getNetworkHandler().getPlayerInfoMap()) {



                    String name = playerInfo.getGameProfile().getName();



                    if (!name.isEmpty()



                            && !name.equals(packedTextFragments[0])



                            && !protectedPlayerNamesValue.contains(name)) {



                        protectedPlayerNamesValue.add(name);



                    }







                    



                }







                this.playerListDirty = false;



            }







            int i = 0;



            int j = protectedPlayerNamesValue.size();







            while (i < j) {



                String textValue4 = (String) protectedPlayerNamesValue.get(i);



                if (text.contains(textValue4)) {



                    text = text.replace(textValue4, "\u00a7k" /* "§k" */ + textValue4);



                }







                i++;



                



            }



        }







        event.setText(text);



    }







    @Subscribe(mc = PacketEvent.class)



    public void onPacket(PacketEvent event) {



        if (event.getDirection() == PacketDirection.RECEIVED) {



            Packet packet = event.getPacket();



            if (packet instanceof S38PacketPlayerListItem) {



                this.playerListDirty = true;



                return;



            }







            if (packet instanceof S07PacketRespawn && !this.protectedPlayerNames.isEmpty()) {



                this.protectedPlayerNames.clear();



            }



        }



    }



}



