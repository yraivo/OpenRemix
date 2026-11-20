package remix.command;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.module.exploit.BookControlModule;

public final class BookClientCommand extends AbstractClientCommand {

    private static String[] packedTextFragments;

    public BookClientCommand() {
        super("Book", new String[] {"book"});
        this.addAction(CommandAction.requiringArguments(3, arguments -> configureBook(arguments)));
    }

    private static boolean configureBook(String[] arguments) {
        String option = arguments[1];
        boolean standardFlow = TargetsClientCommand.isStandardTargetFlow();
        StringBuilder value = new StringBuilder();
        for (int index = 2; index < arguments.length; index++) {
            value.append(packedTextFragments[0]).append(arguments[index]);
            if (!standardFlow) {
                break;
            }
        }
        String bookValue = value.toString().replaceFirst(packedTextFragments[1], packedTextFragments[2]);
        if (!applyBookOption(option, bookValue, standardFlow)) {
            return false;
        }

        if (ec.playerController.isInCreativeMode()) {
            giveWrittenBook(standardFlow);
        } else {
            ClientCommandManager.printMessage(
                    "Done! now get a writable book! (make sure to enable bookcontrol in exploits!)");
        }
        return true;
    }

    private static boolean applyBookOption(String option, String value, boolean standardFlow) {
        if (option.equalsIgnoreCase("text")) {
            BookControlModule.bookPageText = value;
        } else if (option.equalsIgnoreCase("hover")) {
            BookControlModule.pageHoverText = value;
        } else if (option.equalsIgnoreCase("command")) {
            BookControlModule.pageClickCommand = value;
        } else {
            return false;
        }
        return standardFlow;
    }

    private static void giveWrittenBook(boolean standardFlow) {
        ItemStack book = new ItemStack(Items.written_book);
        CompoundTag bookData = new CompoundTag();
        NBTTagList pages = new NBTTagList();
        String padding = packedTextFragments[3];
        int page = 0;

        while (page < 11) {
            padding = padding + padding;
            page++;
            if (!standardFlow) {
                break;
            }
        }

        pages.appendTag(
                new NBTTagString(
                        String.format(
                                "{\"clickEvent\":{\"action\":\"run_command\",\"value\":\"%s\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"%s\"}},\"text\":\"%s\"}",
                                BookControlModule.pageClickCommand,
                                BookControlModule.pageHoverText,
                                BookControlModule.bookPageText
                                        + padding)));
        page = 1;

        while (page < 100) {
            pages.appendTag(
                    new NBTTagString(
                            String.format(
                                    "{\"clickEvent\":\"text\":\"%s\"}",
                                    BookControlModule.bookPageText
                                            + padding)));
            page++;
            if (!standardFlow) {
                break;
            }
        }

        bookData.setTag("pages", pages);
        bookData.setString("author", "\u00a74Admin");
        bookData.setByte("resolved", (byte) 1);
        bookData.setString("title", "Game");
        book.setTag(bookData);
        book.addEnchantment(Enchantment.sharpness, 32767);
        ec.getNetworkHandler()
                .sendPacketNoEvent(
                        new C10PacketCreativeInventoryAction(
                                36, book));
        ClientCommandManager.printMessage(
                "Given you the book in your first slot!");
    }

    static {
        remix.command.BookClientCommand.packedTextFragments = new String[6];
        remix.command.BookClientCommand.packedTextFragments[0] = new String(" ");
        remix.command.BookClientCommand.packedTextFragments[1] = new String(" ");
        remix.command.BookClientCommand.packedTextFragments[2] = new String("");
        remix.command.BookClientCommand.packedTextFragments[3] = new String(" ");
    }

    public String getUsage() {
        return "book <text/hover/command> <display text/hover text/command to execute)";
    }
}
