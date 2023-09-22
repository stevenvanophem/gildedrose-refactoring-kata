package com.gildedrose;

import java.util.List;

public class ItemTestFactory {

    private ItemTestFactory() {
    }

    public static List<Item> allItems() {
        return List.of(
            PlusFiveDexterityVest.ITEM,
            AgedBrie.ITEM,
            ElixirOfTheMongoose.ITEM,
            SulfurasHandOfRagnaros.ITEM,
            ExpiredSulfurasHandOfRagnaros.ITEM,
            new Item(BackstagePasses.NAME, new Item.SellDate(15), new Item.Quality(20)),
            new Item(BackstagePasses.NAME, new Item.SellDate(10), new Item.Quality(49)),
            new Item(BackstagePasses.NAME, new Item.SellDate(5), new Item.Quality(49)),
            ConjuredManaCake.ITEM
        );
    }

    public static class PlusFiveDexterityVest {

        public static final Item.Quality QUALITY = new Item.Quality(20);
        public static final Item.SellDate SELL_DATE = new Item.SellDate(10);
        public static final Item.Name NAME = new Item.Name("+5 Dexterity Vest");
        public static final Item ITEM = new Item(NAME, SELL_DATE, QUALITY);

    }

    public static class AgedBrie {

        public static final Item.Quality QUALITY = new Item.Quality(0);
        public static final Item.SellDate SELL_DATE = new Item.SellDate(2);
        public static final Item.Name NAME = new Item.Name("Aged Brie");
        public static final Item ITEM = new Item(NAME, SELL_DATE, QUALITY);

    }

    public static class ElixirOfTheMongoose {

        public static final Item.Quality QUALITY = new Item.Quality(7);
        public static final Item.SellDate SELL_DATE = new Item.SellDate(5);
        public static final Item.Name NAME = new Item.Name("Elixir of the Mongoose");
        public static final Item ITEM = new Item(NAME, SELL_DATE, QUALITY);

    }

    public static class SulfurasHandOfRagnaros {

        public static final Item.Quality QUALITY = new Item.Quality(80);
        public static final Item.SellDate SELL_DATE = new Item.SellDate(0);
        public static final Item.Name NAME = new Item.Name("Sulfuras, Hand of Ragnaros");
        public static final Item ITEM = new Item(NAME, SELL_DATE, QUALITY);

    }

    public static class ExpiredSulfurasHandOfRagnaros {

        public static final Item.Quality QUALITY = new Item.Quality(80);
        public static final Item.SellDate SELL_DATE = new Item.SellDate(-1);
        public static final Item.Name NAME = new Item.Name("Sulfuras, Hand of Ragnaros");
        public static final Item ITEM = new Item(NAME, SELL_DATE, QUALITY);

    }

    public static class BackstagePasses {

        public static final Item.Name NAME = new Item.Name("Backstage passes to a TAFKAL80ETC concert");

    }

    public static class ConjuredManaCake {

        public static final Item.Quality QUALITY = new Item.Quality(6);
        public static final Item.SellDate SELL_DATE = new Item.SellDate(3);
        public static final Item.Name NAME = new Item.Name("Conjured Mana Cake");
        public static final Item ITEM = new Item(NAME, SELL_DATE, QUALITY);

    }

}
