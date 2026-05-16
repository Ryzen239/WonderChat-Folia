package me.ryzen23.wonderlandschat.utils.menu.pagination;

import java.util.ArrayList;
import java.util.List;
import me.ryzen23.wonderlandschat.utils.menu.base.BaseMenu;
import me.ryzen23.wonderlandschat.utils.menu.button.Button;
import me.ryzen23.wonderlandschat.utils.menu.element.MenuElement;
import me.ryzen23.wonderlandschat.utils.menu.selection.Selection;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PaginableArea {
    private final List<Integer> slots;
    private MenuElement emptyElement = new Button(new ItemStack(Material.AIR));
    private final List<MenuElement> elements = new ArrayList<MenuElement>();

    public PaginableArea(Selection selection) {
        this.slots = selection.getSlots();
    }

    public PaginableArea(Selection selection, MenuElement emptyElement) {
        this(selection);
        this.emptyElement = emptyElement;
    }

    public void setEmptyElement(MenuElement emptyElement) {
        this.emptyElement = emptyElement;
    }

    public void addElement(MenuElement element) {
        this.elements.add(element);
    }

    public void forceUpdate(BaseMenu menu, int page) {
        int startIdx = (page - 1) * this.slots.size();
        int endIdx = startIdx + this.slots.size();
        for (int index = startIdx; index < endIdx; ++index) {
            int slot = this.slots.get(index - startIdx);
            if (index >= this.elements.size()) {
                menu.setElement(slot, this.emptyElement);
                continue;
            }
            menu.setElement(slot, this.elements.get(index));
        }
    }

    public int getPageCount() {
        return (int)Math.ceil((double)this.elements.size() / (double)this.slots.size());
    }
}
