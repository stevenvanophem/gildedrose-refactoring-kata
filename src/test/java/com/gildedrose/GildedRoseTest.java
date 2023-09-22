package com.gildedrose;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    private final GildedRose app = new GildedRose();

    @AfterEach
    void tearDown() {
        app.clearItems();
    }

    @Test
    @DisplayName("I can add items to the GildedRose")
    void testAddItems() {
        ItemTestFactory.allItems().forEach(app::addItem);
    }

    @ParameterizedTest(name = "Day {1} expected sellIn: {2} and quality: {3}")
    @MethodSource
    @DisplayName("Once the sell by date has passed, Quality degrades twice as fast")
    void testRule1(Item item, int days, int sellIn, int quality) {
        app.addItem(item);
        IntStream.range(0, days).forEach(day -> app.updateQuality());

        assertThat(app.items().toString()).isEqualTo("[" + item.name().value() + ", " + sellIn + ", " + quality + "]");
    }

    private static Stream<Arguments> testRule1() {
        final var name = Item.Name.fromString("Cookies");
        final var sellDate = new Item.SellDate(5);
        final var quality = new Item.Quality(10);
        final var item = new Item(name, sellDate, quality);
        return Stream.of(
            Arguments.of(item, 0, 5, 10),
            Arguments.of(item, 1, 4, 9),
            Arguments.of(item, 2, 3, 8),
            Arguments.of(item, 3, 2, 7),
            Arguments.of(item, 4, 1, 6),
            Arguments.of(item, 5, 0, 5),
            Arguments.of(item, 6, -1, 3),
            Arguments.of(item, 7, -2, 1),
            Arguments.of(item, 8, -3, 0),
            Arguments.of(item, 9, -4, 0),
            Arguments.of(item, 10, -5, 0),
            Arguments.of(item, 11, -6, 0)
        );
    }

    @Test
    @DisplayName("The items degrade correctly after one day")
    void testDegrade() {
        List<Item> items = ItemTestFactory.allItems();
        items.forEach(app::addItem);

        app.updateQuality();

        final String formattedItems = format(app.items());
        final String expectedResult = """
            +5 Dexterity Vest, 9, 19
            Aged Brie, 1, 1
            Elixir of the Mongoose, 4, 6
            Sulfuras, Hand of Ragnaros, 0, 80
            Sulfuras, Hand of Ragnaros, -1, 80
            Backstage passes to a TAFKAL80ETC concert, 14, 21
            Backstage passes to a TAFKAL80ETC concert, 9, 50
            Backstage passes to a TAFKAL80ETC concert, 4, 50
            Conjured Mana Cake, 2, 4
            """;

        assertThat(formattedItems).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("The items degrade correctly after two days")
    void testDegradeDay2() {
        List<Item> items = ItemTestFactory.allItems();
        items.forEach(app::addItem);

        app.updateQuality();
        app.updateQuality();

        final String formattedItems = format(app.items());
        final String expectedResult = """
            +5 Dexterity Vest, 8, 18
            Aged Brie, 0, 2
            Elixir of the Mongoose, 3, 5
            Sulfuras, Hand of Ragnaros, 0, 80
            Sulfuras, Hand of Ragnaros, -1, 80
            Backstage passes to a TAFKAL80ETC concert, 13, 22
            Backstage passes to a TAFKAL80ETC concert, 8, 50
            Backstage passes to a TAFKAL80ETC concert, 3, 50
            Conjured Mana Cake, 1, 2
            """;

        assertThat(formattedItems).isEqualTo(expectedResult);
    }

    private static String format(List<Item> items) {
        String result = items.stream()
            .map(Item::toString)
            .collect(Collectors.joining("\n"));
        return result + "\n";
    }

}
