package com.veronica.myjournal.interfaces;

import java.util.List;

/**
 * Created by Veronica on 10/5/2016.
 */
public interface ICRUDDbOperation<TItem,TBindModelItem> {

    List<TItem> getAll();

    boolean insert(TBindModelItem model);

    boolean update(TItem model);

    boolean delete(int id);

    long itemsCount();
}

