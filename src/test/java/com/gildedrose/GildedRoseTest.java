package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    @Test
    @DisplayName("The items degrade correctly after one day")
    void testDegrade() {
        List<Item> items = provideItems();

        new GildedRose(items).updateQuality();

        final String formattedItems = format(items);
        final String expectedResult = """
            +5 Dexterity Vest, 9, 19
            Aged Brie, 1, 1
            Elixir of the Mongoose, 4, 6
            Sulfuras, Hand of Ragnaros, 0, 80
            Sulfuras, Hand of Ragnaros, -1, 80
            Backstage passes to a TAFKAL80ETC concert, 14, 21
            Backstage passes to a TAFKAL80ETC concert, 9, 50
            Backstage passes to a TAFKAL80ETC concert, 4, 50
            Conjured Mana Cake, 2, 5
            """;

        assertThat(formattedItems).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("The items degrade correctly after two days")
    void testDegradeDay2() {
        List<Item> items = provideItems();

        GildedRose app = new GildedRose(items);
        app.updateQuality();
        app.updateQuality();

        final String formattedItems = format(items);
        final String expectedResult = """
            +5 Dexterity Vest, 8, 18
            Aged Brie, 0, 2
            Elixir of the Mongoose, 3, 5
            Sulfuras, Hand of Ragnaros, 0, 80
            Sulfuras, Hand of Ragnaros, -1, 80
            Backstage passes to a TAFKAL80ETC concert, 13, 22
            Backstage passes to a TAFKAL80ETC concert, 8, 50
            Backstage passes to a TAFKAL80ETC concert, 3, 50
            Conjured Mana Cake, 1, 4
            """;

        assertThat(formattedItems).isEqualTo(expectedResult);
    }

    private static String format(List<Item> items) {
        String result = items.stream()
            .map(Item::toString)
            .collect(Collectors.joining("\n"));
        return result + "\n";
    }

    private static List<Item> provideItems() {
        return List.of(
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Aged Brie", 2, 0),
            new Item("Elixir of the Mongoose", 5, 7),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6)
        );
    }

}
