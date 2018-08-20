package com.androidapps.robertsteele.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androidapps.robertsteele.criminalintent.database.CrimeBaseHelper;
import com.androidapps.robertsteele.criminalintent.database.CrimeCursorWrapper;
import com.androidapps.robertsteele.criminalintent.database.CrimeDBSchema.CrimeTable;
import com.androidapps.robertsteele.criminalintent.database.CrimeDBSchema.CrimeTable.Cols;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Context mcontext;
    private SQLiteDatabase mSQLiteDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public static ContentValues getContentValues(Crime crime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cols.UUID, crime.getmId().toString());
        contentValues.put(Cols.DATE, crime.getmDate().toString());
        contentValues.put(Cols.TITLE, crime.getMtitle());
        contentValues.put(Cols.SOLVED, crime.ismSolved() ? 1 : 0);
        return contentValues;
    }

    public void addCrime(Crime crime) {
        ContentValues contentValues = getContentValues(crime);
        mSQLiteDatabase.insert(CrimeTable.NAME, null, contentValues);
    }

    public void deleteCrime(Crime crime) {
        mSQLiteDatabase.delete(CrimeTable.NAME, Cols.UUID + "= ?", new String[] {crime.getmId().toString()});
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getmId().toString();
        ContentValues contentValues = getContentValues(crime);
        mSQLiteDatabase.update(CrimeTable.NAME, contentValues,
                Cols.UUID + " = ? ", new String[]{uuidString});
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] args) {
        Cursor cursor = mSQLiteDatabase.query(CrimeTable.NAME,
                null,
                whereClause,
                args,
                null,
                null,
                null);
        return new CrimeCursorWrapper(cursor);
    }

    private CrimeLab(Context context) {
        mcontext = context.getApplicationContext();
        mSQLiteDatabase = new CrimeBaseHelper(mcontext).getReadableDatabase();
    }

    public List<Crime> getmCrimes() {
        List<Crime> crimes = new ArrayList<Crime>();

        CrimeCursorWrapper crimeCursorWrapper = queryCrimes(null, null);

        try {
            crimeCursorWrapper.moveToFirst();
            while (!crimeCursorWrapper.isAfterLast()) {
                crimes.add(crimeCursorWrapper.getCrime());
                crimeCursorWrapper.moveToNext();
            }
        } finally {
            crimeCursorWrapper.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID uuid) {
        CrimeCursorWrapper crimeCursorWrapper =
                queryCrimes(Cols.UUID + " = ? ", new String[]{uuid.toString()});
        if (crimeCursorWrapper.getCount() == 0) {
            return null;
        } else {
            try{
                crimeCursorWrapper.moveToFirst();
                return crimeCursorWrapper.getCrime();
            }
            finally {
                crimeCursorWrapper.close();
            }
        }
    }
}
