package com.androidapps.robertsteele.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.androidapps.robertsteele.criminalintent.Crime;

import java.util.Date;
import java.util.UUID;

import static com.androidapps.robertsteele.criminalintent.database.CrimeDBSchema.CrimeTable.*;

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(Cols.UUID));
        String crimeTitle = getString(getColumnIndex(Cols.TITLE));
        Long crimeDate = getLong(getColumnIndex(Cols.DATE));
        int isSolved = getInt(getColumnIndex(Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setMtitle(crimeTitle);
        crime.setmDate(new Date(crimeDate));
        crime.setmSolved(isSolved != 0);

        return crime;
    }
}
