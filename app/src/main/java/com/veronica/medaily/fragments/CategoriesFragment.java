package com.veronica.medaily.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.veronica.medaily.R;
import com.veronica.medaily.listeners.RecyclerClickListener;
import com.veronica.medaily.adapters.CategoriesAdapter;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.dialogs.CategoriesPreviewDialog;
import com.veronica.medaily.dialogs.EditCategoryDialog;
import com.veronica.medaily.helpers.NotificationHandler;
import com.veronica.medaily.listeners.ICategoryEditedListener;
import com.veronica.medaily.loaders.CategoriesLoader;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class CategoriesFragment extends BaseFragment implements android.widget.SearchView.OnQueryTextListener,ICategoryEditedListener {

    private DrawerLayout drawerLayout;
    private SearchView mSearchViewCategories;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private CategoriesFragment categoriesFragment;
    private ItemTouchHelper itemTouchHelper;
    private NotificationHandler notificationHandler;
    private List<Category> userCategories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        categoriesFragment = this;
        notificationHandler = new NotificationHandler(getContext());
        initializeItemTouchHelper();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories,container,false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_categories);
        mSearchViewCategories = (SearchView) view.findViewById(R.id.search_view_categories);
        mSearchViewCategories.setOnQueryTextListener(this);
        mSearchViewCategories.setIconified(true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_categories);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerClickListener(getContext(), mRecyclerView ,new RecyclerClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {

                        if(userCategories.get(position).getNotes().size()>0) {
                            NotesFragment notesFragment = new NotesFragment();
                            Bundle bundle = new Bundle();
                            bundle.putLong("category_id", userCategories.get(position).getId());
                            notesFragment.setArguments(bundle);
                            placeFragment(notesFragment);
                        }else{
                            notificationHandler.toastNeutralNotificationBottom("This category is empty");
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        boolean isOpen = drawerLayout.isDrawerOpen(GravityCompat.START);

                        if(!isOpen) {
                            EditCategoryDialog editCategoryDialog = new EditCategoryDialog(getContext(),userCategories.get(position),categoriesFragment,position);
                            editCategoryDialog.show();
                        }

                    }

                    @Override
                    public void onDoubleTab(View view, int position) {
                        CategoriesPreviewDialog detailsDialog = new CategoriesPreviewDialog(getContext(),userCategories.get(position));
                        detailsDialog.show();
                    }
                })
        );
        //note item touch helper after gesture detector ! ..
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        try {
            userCategories = new CategoriesLoader(progressBar,mCurrentUser,mRecyclerView).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initializeItemTouchHelper() {
        itemTouchHelper = new ItemTouchHelper(

                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int elementPosition = viewHolder.getAdapterPosition();
                        Category categoryToBeRemoved = userCategories.get(elementPosition);
                        List<Note> notesToBeRemoved = categoryToBeRemoved.getNotes();
                        int count = notesToBeRemoved.size();
                        for (int i = 0; i < count; i++) {
                            //if there are any reminders set for the note delete them also
                            if(!notesToBeRemoved.get(i).getNoteReminders().isEmpty()){
                                NoteReminder.delete(notesToBeRemoved.get(i).getNoteReminders().get(0));
                            }
                            Note.delete(notesToBeRemoved.get(i));
                        }
                        CategoriesAdapter categoriesAdapter = (CategoriesAdapter) mRecyclerView.getAdapter();
                        categoriesAdapter.deleteCategory(elementPosition);
                        Category.delete(categoryToBeRemoved);
                    }
                }
        );
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        CategoriesAdapter categoriesAdapter = (CategoriesAdapter) mRecyclerView.getAdapter();
        if(categoriesAdapter!=null) {
            categoriesAdapter.filter(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        CategoriesAdapter categoriesAdapter = (CategoriesAdapter) mRecyclerView.getAdapter();
        // on back pressed adapter is null check s
        if(categoriesAdapter!=null) {
            categoriesAdapter.filter(newText);
        }
        return true;
    }

    @Override
    public void categoryEdited(Category category,int position) {
            userCategories.set(position,category);
            CategoriesAdapter categoriesAdapter = (CategoriesAdapter) mRecyclerView.getAdapter();
            categoriesAdapter.updateCategories(userCategories);
    }
}
