package com.bymankind.jsonrecyclerview;

/**
 * Created by Server-Panduit on 4/12/2017.
 */

public class SetterGetter {
    private String order_invoice_number, name, item, picture;
    private int order_id;

    public SetterGetter(int order_id, String order_invoice_number, String name, String item, String picture){
        this.order_id = order_id ;
        this.order_invoice_number = order_invoice_number;
        this.name = name;
        this.item = item;
        this.picture = picture;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public String getOrder_invoice_number() {
        return order_invoice_number;
    }

    public String getPicture() {
        return picture;
    }

}
