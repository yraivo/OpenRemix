package remix.input;

import remix.client.friend.FriendManager;
import remix.event.InputType;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.command.ClientCommandManager;
import remix.input.KeyAction;
import remix.input.ModuleToggleKeyAction;

public final class FriendMiddleClickKeyAction extends KeyAction {
    public FriendMiddleClickKeyAction(InputType inputType) {
        super(new BoundKeyTask<Object>("friend_task", (target, keyCode) -> true,
                target -> togglePointedPlayer()), inputType);
    }

    private static void togglePointedPlayer() {
        boolean legacyKeyFlow = ModuleToggleKeyAction.getLegacyModuleToggleFlowState() != null;
        MinecraftClient client = MinecraftClient.getMinecraft();
        RayTraceResult hit = client.objectMouseOver;
        if (hit == null || hit.typeOfHit != RayHitType.ENTITY
                || !(hit.entityHit instanceof PlayerEntity) || hit.entityHit == client.player) {
            return;
        }

        String playerName = hit.entityHit.getName();
        RemixClient.INSTANCE.getClientServices();
        FriendManager friends = ClientServices.friendManager;
        if (!friends.isFriend(playerName)) {
            friends.addFriend(playerName);
            RemixClient.INSTANCE.getClientServices();
            ClientCommandManager.printMessage(String.format("Added %s as a friend!", playerName));
            if (!legacyKeyFlow) {
                return;
            }
        }

        friends.removeFriend(playerName);
        RemixClient.INSTANCE.getClientServices();
        ClientCommandManager.printMessage(String.format("Removed %s from friends!", playerName));
    }
}
