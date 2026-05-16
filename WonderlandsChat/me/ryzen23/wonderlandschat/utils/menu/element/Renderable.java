package me.ryzen23.wonderlandschat.utils.menu.element;

import me.ryzen23.wonderlandschat.utils.menu.base.BaseMenu;

public abstract class Renderable {
    protected BaseMenu menu;
    private boolean active = true;

    public Renderable(BaseMenu menu) {
        this.menu = menu;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
        this.forceUpdate();
    }

    public abstract void forceUpdate();
}
