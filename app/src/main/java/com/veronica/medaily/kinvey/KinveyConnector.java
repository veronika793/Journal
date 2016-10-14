package com.veronica.medaily.kinvey;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyDeleteCallback;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.Query;
import com.kinvey.java.core.KinveyClientCallback;
import com.kinvey.java.model.KinveyDeleteResponse;
import com.veronica.medaily.Constants;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.dbmodels.User;
import com.veronica.medaily.services.RecallAlarmsService;

import java.util.List;


public class KinveyConnector implements KinveyUserCallback{

    public static String APP_KEY_KINVEY  = "kid_ByYhrHG0";
    public static String APP_SECRET_KINVEY = "a8f1ea64b34b4b94814dfcbde32ff594";

    private static KinveyConnector mInstance;
    private static Context mContext;

    private Client myKinveyClient;
    private User mCurrentUser;

    public static void setupContext(Context context){
        if(mContext==null){
            mContext = context;
        }
    }

    public synchronized static KinveyConnector getInstance(){
        if(mInstance==null){
            mInstance = new KinveyConnector();
        }
        return mInstance;
    }

    private KinveyConnector(){
        final Client client = new Client.Builder(APP_KEY_KINVEY, APP_SECRET_KINVEY,mContext).build();
        client.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {

            }
            public void onSuccess(Boolean b) {

            }
        });

        myKinveyClient = client;

    }

    public void setupCurrentUser(User user){
        mCurrentUser = user;
    }

    public void importData(){
        logoutUser();
        createUser();
        loginUser();
        importCategories();
        importNotes();
    }

    private void importNotes(){
        Query q = myKinveyClient.query();
        q.equals("authorEmail",mCurrentUser.getEmail());
        AsyncAppData<NoteEntitiy> myNotes = myKinveyClient.appData("notesCollection", NoteEntitiy.class);
        myNotes.get(q, new KinveyListCallback<NoteEntitiy>() {
            @Override
            public void onSuccess(NoteEntitiy[] noteEntitiys) {
                Note.deleteAll(Note.class," user=? ", String.valueOf(mCurrentUser.getId()));
                NoteReminder.deleteAll(Note.class, "user=? ",String.valueOf(mCurrentUser.getId()));

                for (NoteEntitiy fetchedNote : noteEntitiys) {
                    String categoryName = fetchedNote.getCategoryName();
                    List<Category> categories = Category.find(Category.class, " name=? ",categoryName);
                    if(categories.size()>0){

                        Note note = new Note(categories.get(0),mCurrentUser,fetchedNote.getTitle(),fetchedNote.getContent(),fetchedNote.getCreatedOnDate(),fetchedNote.getPhotoUri(),fetchedNote.getReminderDate());
                        note.save();
                        if(fetchedNote.getReminderDate()!=null){

                            NoteReminder reminder = new NoteReminder(mCurrentUser,note,note.getCategory(),fetchedNote.getReminderDate());
                            reminder.save();
                        }
                    }
                }
                Intent notificationService = new Intent(mContext, RecallAlarmsService.class);
                notificationService.setAction(Constants.ACTION_BOOT_COMPLETED);
                mContext.startService(notificationService);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void importCategories(){
        Query q = myKinveyClient.query();
        q.equals("authorEmail",mCurrentUser.getEmail());
        AsyncAppData<CategoryEntity> myCategories = myKinveyClient.appData("categoriesCollection", CategoryEntity.class);
        myCategories.get(q, new KinveyListCallback<CategoryEntity>() {
            @Override
            public void onSuccess(CategoryEntity[] categoryEntities) {
                Category.deleteAll(Category.class," user=? ", String.valueOf(mCurrentUser.getId()));
                for (CategoryEntity fetchedCategory : categoryEntities) {
                    int color = fetchedCategory.getColor();
                    Category newCategory = new Category(mCurrentUser,fetchedCategory.getName(),fetchedCategory.getDescription(),color);
                    newCategory.save();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    public void exportData(){
        logoutUser();
        createUser();
        loginUser();
        exportCategories();
        exportNotes();

    }

    private void exportNotes(){

        deletePreviousNotes();
        List<Note> notes = mCurrentUser.getNotes();
        for (Note note : notes) {

            NoteEntitiy noteEntitiy = new NoteEntitiy();
            noteEntitiy.setId(note.getId().toString());
            noteEntitiy.setTitle(note.getTitle());
            noteEntitiy.setContent(note.getContent());
            noteEntitiy.setAuthorEmail(note.getUser().getEmail());
            noteEntitiy.setCategoryName(note.getCategory().getName());
            noteEntitiy.setCreatedOnDate(note.getCreatedOnDate());

            noteEntitiy.setReminderDate(note.getReminderDate());
            noteEntitiy.setPhotoUri(note.getPhotoUri());

            AsyncAppData<NoteEntitiy> myNotes = myKinveyClient.appData("notesCollection",NoteEntitiy.class);
            myNotes.save(noteEntitiy, new KinveyClientCallback<NoteEntitiy>() {
                @Override
                public void onSuccess(NoteEntitiy noteEntitiy) {

                }

                @Override
                public void onFailure(Throwable e) {

                }

            });
        }
    }

    private void exportCategories(){
        deletePreviousCategories();

        List<Category> userCategories = mCurrentUser.getCategories();

        for (Category category : userCategories) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(category.getId().toString());
            categoryEntity.setName(category.getName());
            categoryEntity.setDescription(category.getDescription());
            categoryEntity.setAuthorEmail(mCurrentUser.getEmail());
            categoryEntity.setColor(category.getColor());

            AsyncAppData<CategoryEntity> myCategories = myKinveyClient.appData("categoriesCollection",CategoryEntity.class);
            myCategories.save(categoryEntity, new KinveyClientCallback<CategoryEntity>() {
                @Override
                public void onSuccess(CategoryEntity categoryEntity) {

                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        }

    }

    private void createUser(){
        myKinveyClient.user().create(mCurrentUser.getEmail(), mCurrentUser.getPassword(), new KinveyUserCallback() {
            @Override
            public void onSuccess(com.kinvey.java.User user) {
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
        myKinveyClient.user().put("user_id", mCurrentUser.getId());
        myKinveyClient.user().put("user_password", mCurrentUser.getPassword());
        myKinveyClient.user().put("user_pic", mCurrentUser.getPhotoUri());
        myKinveyClient.user().put("user_name", mCurrentUser.getName());
    }

    private void loginUser(){
        myKinveyClient.user().login(mCurrentUser.getEmail(),mCurrentUser.getPassword(), new KinveyUserCallback() {
            @Override
            public void onSuccess(com.kinvey.java.User user) {
            }
            @Override
            public void onFailure(Throwable t) {}

        });
    }

    @Override
    public void onSuccess(com.kinvey.java.User user) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    private void deletePreviousNotes() {
        Query q = myKinveyClient.query();
        q.equals("authorEmail",mCurrentUser.getEmail());
        AsyncAppData<NoteEntitiy> myevents = myKinveyClient.appData("notesCollection", NoteEntitiy.class);
        myevents.delete(q, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse kinveyDeleteResponse) {
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
    }

    private void deletePreviousCategories(){
        Query q = myKinveyClient.query();
        q.equals("authorEmail",mCurrentUser.getEmail());
        AsyncAppData<CategoryEntity> categoryEntity = myKinveyClient.appData("categoriesCollection", CategoryEntity.class);
        categoryEntity.delete(q, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse kinveyDeleteResponse) {
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    public void logoutUser(){
        myKinveyClient.user().logout().execute();
    }
}
