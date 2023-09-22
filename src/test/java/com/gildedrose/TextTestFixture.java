package com.gildedrose;

import java.util.List;

public class TextTestFixture {

    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        List<Item> items = ItemTestFactory.allItems();
        GildedRose app = new GildedRose();
        items.forEach(app::addItem);

        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();
            app.updateQuality();
        }
    }

}
