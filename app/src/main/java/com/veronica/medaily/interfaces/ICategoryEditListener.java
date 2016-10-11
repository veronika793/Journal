package com.veronica.medaily.interfaces;

import com.veronica.medaily.dbmodels.Category;

/**
 * Created by Veronica on 10/11/2016.
 */
public interface ICategoryEditListener {
    void categoryEdited(Category category,int position);
}
