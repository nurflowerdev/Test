package com.nurflower.test.db;


import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.nurflower.test.database.MyDatabase;

import org.junit.After;
import org.junit.Before;

abstract public class DbTest {
    protected MyDatabase db;

    @Before
    public void initDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                MyDatabase.class).allowMainThreadQueries().build();
    }

    @After
    public void closeDb() {
        db.close();
    }

}
