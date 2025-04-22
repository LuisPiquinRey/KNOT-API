package com.luispiquinrey.apiknot.Entities.DTO;

import java.util.List;

public class PurchaseRequest {
    private List<PurchaseItem> items;


    public List<PurchaseItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItem> items) {
        this.items = items;
    }
}

