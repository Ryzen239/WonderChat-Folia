package me.ryzen23.wonderlandschat.utils.menu.layer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.ryzen23.wonderlandschat.utils.menu.base.BaseMenu;
import me.ryzen23.wonderlandschat.utils.menu.element.Renderable;
import me.ryzen23.wonderlandschat.utils.menu.pagination.PaginableArea;

public class PaginableLayer
extends Renderable {
    private final List<PaginableArea> areas = new ArrayList<PaginableArea>();
    private int page = 1;

    public PaginableLayer(BaseMenu menu) {
        super(menu);
    }

    @Override
    public void forceUpdate() {
        for (PaginableArea area : this.areas) {
            area.forceUpdate(this.menu, this.page);
        }
    }

    public void addArea(PaginableArea ... area) {
        this.areas.addAll(Arrays.asList(area));
    }

    public int getPage() {
        return this.page;
    }

    public void nextPage() {
        if (this.page >= this.getMaxPage()) {
            return;
        }
        ++this.page;
        this.menu.forceUpdate();
    }

    public void previousPage() {
        if (this.page == 1) {
            return;
        }
        --this.page;
        this.menu.forceUpdate();
    }

    public int getMaxPage() {
        int max = 0;
        for (PaginableArea area : this.areas) {
            int areaMax = area.getPageCount();
            if (areaMax <= max) continue;
            max = areaMax;
        }
        return max;
    }
}
