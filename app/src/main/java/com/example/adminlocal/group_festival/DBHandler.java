package com.example.adminlocal.group_festival;

/**
 * Created by adminlocal on 10/12/2017.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "groupInfo";
    // Contacts table name
    private static final String TABLE_GROUP = "groups";
    // Group Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ARTISTE = "artiste";
    private static final String KEY_TEXTE = "texte";
    private static final String KEY_WEB = "web";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_SCENE = "scene";
    private static final String KEY_JOUR = "jour";
    private static final String KEY_HEURE = "heure";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_GROUP + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ARTISTE + " TEXT,"+ KEY_TEXTE + " TEXT,"+
                KEY_WEB + " TEXT,"+ KEY_IMAGE + " TEXT," + KEY_SCENE + " TEXT,"+ KEY_JOUR + " TEXT,"+
                KEY_HEURE + " TEXT"  + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
        // Creating tables again
        onCreate(db);
    }

    public void addGroup (Group group){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            // le group existe deja

            if(updateObject(group) == 0) {
                db.insert(TABLE_GROUP, null, createContentValues(group));
            }
         }
        catch (Exception exc){
        exc.printStackTrace();
        }
    }

    public long updateObject(Group group){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.update(TABLE_GROUP, createContentValues(group), KEY_ID + "=" + group.getId(), null);
    }

    private ContentValues createContentValues(Group group) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, group.getId());
        if ( group.getArtiste() != null ) {
            values.put(KEY_ARTISTE, group.getArtiste());
        }
        if (group.getTexte() != null ){
            values.put(KEY_TEXTE,group.getTexte());
        }
        if (group.getWeb() != null ){
            values.put(KEY_WEB,group.getWeb());
        }
        if (group.getImage() != null ){
            values.put(KEY_IMAGE,group.getImage());
        }
        if (group.getScene() != null ){
            values.put(KEY_SCENE,group.getScene());
        }
        if (group.getJour() != null ){
            values.put(KEY_JOUR,group.getJour());
        }
        if (group.getHeure() != null ){
            values.put(KEY_HEURE,group.getHeure());
        }
//        Log.v("artiste", "test finale=" + values );
        return values;
    }


    // delete every data in the table
    public void deleteGroup() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+ TABLE_GROUP);
    }

    public  List<String> getAllGroupsName() {
        List<String> groupList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + TABLE_GROUP, null );

        try {
            while (cursor.moveToNext()) {
                groupList.add(cursor.getString(1));
            }

        } finally {
            cursor.close();
        }
        return groupList;
    }

    public  List<Group> getAllGroupsDetail() {
        List<Group> groupdetail = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + TABLE_GROUP, null );

        try {
            while (cursor.moveToNext()) {
                Group group = new Group();
                group.setId(cursor.getInt(0));
                group.setArtiste(cursor.getString(1));
                group.setTexte(cursor.getString(2));
                group.setWeb(cursor.getString(3));
                group.setImage(cursor.getString(4));
                group.setScene(cursor.getString(5));
                group.setJour(cursor.getString(6));
                group.setHeure(cursor.getString(7));

                groupdetail.add(group);
            }

        } finally {
            cursor.close();
        }
        return groupdetail;
    }

    public  ArrayList<String> getAllScene() {
        ArrayList<String> groupscene = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + TABLE_GROUP, null );

        try {
            while (cursor.moveToNext()) {
                if(!(groupscene.contains(cursor.getString(5)))) {
                    groupscene.add(cursor.getString(5));
                }
            }

        } finally {
            cursor.close();
        }
        return groupscene;
    }

    public  ArrayList <String> getHeurePassage() {
        ArrayList <String> passagelist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + TABLE_GROUP, null );

        try {
            while (cursor.moveToNext()) {
                String passage = cursor.getString(6) +"  "+cursor.getString(7);
                if(!(passagelist.contains(passage))){
                    passagelist.add(passage);
                }

            }

        } finally {
            cursor.close();
        }
        return passagelist;
    }


    // Getting a group
    public Group getGroup(String artiste) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_GROUP, new String[] { KEY_ID,
                        KEY_ARTISTE, KEY_WEB, KEY_IMAGE,KEY_SCENE,KEY_JOUR,KEY_HEURE}, KEY_ARTISTE + "=?",
                new String[] { String.valueOf(artiste) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        Group data = new Group();
//        Group data = new Group(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),
//                cursor.getString(7));

        return data;
    }
}

