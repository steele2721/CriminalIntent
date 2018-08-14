package com.androidapps.robertsteele.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.androidapps.robertsteele.criminalintent.database.CrimeBaseHelper;
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

    public void updateCrime(Crime crime) {
        String uuidString = crime.getmId().toString();
        ContentValues contentValues = getContentValues(crime);
        mSQLiteDatabase.update(CrimeTable.NAME, contentValues,
                Cols.UUID + " = ? ", new String[] { uuidString });
    }

    private CrimeLab(Context context) {
        mcontext = context.getApplicationContext();
        mSQLiteDatabase = new CrimeBaseHelper(mcontext).getReadableDatabase();
    }

    public List<Crime> getmCrimes() {
        return new ArrayList<Crime>();
    }

    public Crime getCrime(UUID uuid) {
        return null;
    }
}
