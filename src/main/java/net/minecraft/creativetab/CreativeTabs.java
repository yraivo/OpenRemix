package net.minecraft.creativetab;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.BrewingCreativeTab;
import net.minecraft.creativetab.BuildingBlocksCreativeTab;
import net.minecraft.creativetab.CombatCreativeTab;
import net.minecraft.creativetab.DecorationsCreativeTab;
import net.minecraft.creativetab.FoodCreativeTab;
import net.minecraft.creativetab.InventoryCreativeTab;
import net.minecraft.creativetab.MaterialsCreativeTab;
import net.minecraft.creativetab.MiscCreativeTab;
import net.minecraft.creativetab.RedstoneCreativeTab;
import net.minecraft.creativetab.SearchCreativeTab;
import net.minecraft.creativetab.ToolsCreativeTab;
import net.minecraft.creativetab.TransportationCreativeTab;

import java.util.Iterator;
import java.util.List;

public abstract class CreativeTabs {

    private boolean drawTitle;
    public static CreativeTabs tabTools;
    public static CreativeTabs tabAllSearch;

    private int tabIndex;
    public static CreativeTabs tabMisc;
    private ItemStack iconItemStack;
    public static CreativeTabs tabFood;
    public static CreativeTabs tabInventory;
    public static CreativeTabs tabMaterials;
    public static CreativeTabs tabRedstone;
    public static CreativeTabs tabBrewing;
    public static CreativeTabs tabCombat;
    public static CreativeTabs[] creativeTabArray;
    public static CreativeTabs tabTransport;
    private String theTexture = "items.png";
    public static CreativeTabs tabDecorations;
    public static CreativeTabs tabBlock;
    private String tabLabel;
    private EnumEnchantmentType[] enchantmentTypes;
    private boolean hasScrollbar = true;
    private static String[] jl;

    public String getTabLabel() {
        return this.tabLabel;
    }

    public CreativeTabs setRelevantEnchantmentTypes(EnumEnchantmentType... aYsValue) {
        this.enchantmentTypes = aYsValue;
        return this;
    }

    public ItemStack getIconItemStack() {
        if (this.iconItemStack == null) {
            this.iconItemStack = new ItemStack(this.getTabIconItem(), 1, this.getIconItemDamage());
        }

        return this.iconItemStack;
    }

    public String getBackgroundImageName() {
        return this.theTexture;
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    public String getTranslatedTabLabel() {
        return "itemGroup." + this.getTabLabel();
    }

    public boolean shouldHidePlayerInventory() {
        return this.hasScrollbar;
    }

    public static void b(String[] astring) {
        jl = astring;
    }

    static {
        b(null);
        net.minecraft.creativetab.CreativeTabs.creativeTabArray = new CreativeTabs[13];
        net.minecraft.creativetab.CreativeTabs.tabBlock = new BuildingBlocksCreativeTab(0, "buildingBlocks");
        net.minecraft.creativetab.CreativeTabs.tabDecorations = new DecorationsCreativeTab(1, "decorations");
        net.minecraft.creativetab.CreativeTabs.tabRedstone = new RedstoneCreativeTab(2, "redstone");
        net.minecraft.creativetab.CreativeTabs.tabTransport = new TransportationCreativeTab(3, "transportation");
        net.minecraft.creativetab.CreativeTabs.tabMisc =
                new MiscCreativeTab(4, "misc")
                        .setRelevantEnchantmentTypes(
                                new EnumEnchantmentType[] {EnumEnchantmentType.ALL});
        net.minecraft.creativetab.CreativeTabs.tabAllSearch =
                new SearchCreativeTab(5, "search")
                        .setBackgroundImageName("item_search.png");
        net.minecraft.creativetab.CreativeTabs.tabFood = new FoodCreativeTab(6, "food");
        net.minecraft.creativetab.CreativeTabs.tabTools =
                new ToolsCreativeTab(7, "tools")
                        .setRelevantEnchantmentTypes(
                                EnumEnchantmentType.DIGGER,
                                EnumEnchantmentType.FISHING_ROD,
                                EnumEnchantmentType.BREAKABLE);
        net.minecraft.creativetab.CreativeTabs.tabCombat =
                new CombatCreativeTab(8, "combat")
                        .setRelevantEnchantmentTypes(
                                new EnumEnchantmentType[] {
                                    EnumEnchantmentType.ARMOR,
                                    EnumEnchantmentType.ARMOR_FEET,
                                    EnumEnchantmentType.ARMOR_HEAD,
                                    EnumEnchantmentType.ARMOR_LEGS,
                                    EnumEnchantmentType.ARMOR_TORSO,
                                    EnumEnchantmentType.BOW,
                                    EnumEnchantmentType.WEAPON
                                });
        net.minecraft.creativetab.CreativeTabs.tabBrewing = new BrewingCreativeTab(9, "brewing");
        net.minecraft.creativetab.CreativeTabs.tabMaterials = new MaterialsCreativeTab(10, "materials");
        net.minecraft.creativetab.CreativeTabs.tabInventory =
                new InventoryCreativeTab(11, "inventory")
                        .setBackgroundImageName("inventory.png")
                        .setNoScrollbar()
                        .setNoTitle();
    }

    public void addEnchantmentBooksToList(
            List list, EnumEnchantmentType... enumEnchantmentTypeArray) {
        String[] astring1 = Material.Fr();
        Enchantment[] aZW = Enchantment.enchantmentsBookList;
        int length = aZW.length;
        String[] astring = astring1;
        int j = 0;

        while (j < length) {
            Enchantment enchantment = aZW[j];
            if (enchantment != null && enchantment.type != null) {
                byte b0 = 0;
                int k = 0;

                while (k < enumEnchantmentTypeArray.length && b0 == 0) {
                    if (enchantment.type == enumEnchantmentTypeArray[k]) {
                        b0 = 1;
                    }

                    k++;
                    if (astring == null) {
                        Entity.setLegacyGlobalFlowMarker(new String[3]);
                        break;
                    }
                }

                if (b0 != 0) {
                    list.add(
                            Items.enchanted_book.getEnchantedItemStack(
                                    new EnchantmentData(enchantment, enchantment.getMaxLevel())));
                }
            }

            j++;
            if (astring == null) {
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    public static String[] GL() {
        return jl;
    }

    public void addDisplayedItems(List<ItemStack> itemStackList) {
        String[] astring1 = Material.Fr();
        Iterator iterator = Item.itemRegistry.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            Item item = (Item) iterator.next();
            if (item != null && item.getCreativeTab() == this) {
                item.getSubItems(item, this, itemStackList);
            }

            if (astring == null) {
                break;
            }
        }

        if (this.getRelevantEnchantmentTypes() != null) {
            this.addEnchantmentBooksToList(itemStackList, this.getRelevantEnchantmentTypes());
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            Material.b(new String[1]);
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public CreativeTabs setBackgroundImageName(String text) {
        this.theTexture = text;
        return this;
    }

    public int getTabPage() {
        return this.tabIndex > 11 ? (this.tabIndex - 12) / 10 + 1 : 0;
    }

    public boolean drawInForegroundOfTab() {
        return this.drawTitle;
    }

    public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
        return this.enchantmentTypes;
    }

    public int getTabColumn() {
        return this.tabIndex > 11 ? (this.tabIndex - 12) % 10 % 5 : this.tabIndex % 6;
    }

    public abstract Item getTabIconItem();

    public CreativeTabs setNoTitle() {
        this.drawTitle = false;
        return this;
    }

    public boolean hasRelevantEnchantmentType(EnumEnchantmentType enumEnchantmentType) {
        String[] astring = Material.Fr();
        if (this.enchantmentTypes == null) {
            return false;
        }

        for (EnumEnchantmentType enumEnchantmentTypeValue : this.enchantmentTypes) {
            if (enumEnchantmentTypeValue == enumEnchantmentType) {
                return true;
            }

            if (astring == null) {
                break;
            }
        }

        return false;
    }

    public CreativeTabs(int tabIndexValue, String text) {
        this.drawTitle = true;
        this.tabIndex = tabIndexValue;
        this.tabLabel = text;
        net.minecraft.creativetab.CreativeTabs.creativeTabArray[tabIndexValue] = this;
    }

    public int getIconItemDamage() {
        return 0;
    }

    public CreativeTabs setNoScrollbar() {
        this.hasScrollbar = false;
        return this;
    }

    public boolean isTopRow() {
        return this.tabIndex > 11 ? (this.tabIndex - 12) % 10 < 5 : this.tabIndex < 6;
    }
}
