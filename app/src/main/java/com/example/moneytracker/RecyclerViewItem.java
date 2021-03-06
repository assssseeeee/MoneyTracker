package com.example.moneytracker;

public class RecyclerViewItem {
    String itemPriceTextViewCardViewString;
    String itemDateTextViewCardViewString;
    String itemNameTextViewCardViewString;

    public RecyclerViewItem() {
    }

    public RecyclerViewItem(String itemPriceTextViewCardViewString,
                            String itemDateTextViewCardViewString,
                            String itemNameTextViewCardViewString) {
        this.itemPriceTextViewCardViewString = itemPriceTextViewCardViewString;
        this.itemDateTextViewCardViewString = itemDateTextViewCardViewString;
        this.itemNameTextViewCardViewString = itemNameTextViewCardViewString;
    }

    public String getItemPriceTextViewCardViewString() {
        return itemPriceTextViewCardViewString;
    }

    public String getItemDateTextViewCardViewString() {
        return itemDateTextViewCardViewString;
    }

    public String getItemNameTextViewCardViewString() {
        return itemNameTextViewCardViewString;
    }

}
