package com.gildedrose;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class GildedRose {

    private final List<Item> items = new ArrayList<>();

    public GildedRose addItem(Item item) {
        Objects.requireNonNull(item, "item is missing");
        if (this.items.contains(item))
            throw new IllegalArgumentException("item already exists");
        this.items.add(item);
        return this;
    }

    public void clearItems() {
        this.items.clear();
    }

    public GildedRose updateQuality() {
        List<Item> items = this.items.stream()
            .map(Item::degrade)
            .toList();
        this.items.clear();
        this.items.addAll(items);
        return this;
    }

    public List<Item> items() {
        return this.items;
    }

}
