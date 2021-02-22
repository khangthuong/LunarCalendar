package com.example.lunarcalendar.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lunarcalendar.model.Tuvi;
import com.example.lunarcalendar.model.TuviCungHD;
import com.example.lunarcalendar.model.Tuvitrondoi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String TAG = "DataBaseHelper";

    public static String DB_NAME_DATAEVENT = "DataEvent.db";
    public static String DB_NAME_DATALICHVIET = "DataLichViet.db";
    public static String DB_NAME_CUNGHD = "horoscope.db";
    public static String DB_NAME_TUVI2021 = "Tuvi2021.db";
    public static String DB_NAME_TUVITRONDOI = "Tuvitrondoi.db";
    public static String DB_NAME_VANKHAN = "Vankhan.db";

    /*private final File DB_FILE_DATAEVENT;
    private final File DB_FILE_DATALICHVIET;
    private final File DB_FILE_CUNGHD;
    private final File DB_FILE_TUVI2021;
    private final File DB_FILE_TUVITRONDOI;
    private final File DB_FILE_VANKHAN;*/
    private File mDBFile;

    /*private SQLiteDatabase mDBDataEvent;
    private SQLiteDatabase mDBDataLichviet;
    private SQLiteDatabase mDBCungHD;
    private SQLiteDatabase mBTuvi2021;
    private SQLiteDatabase mDBTuviTrondoi;
    private SQLiteDatabase mDBVankhan;*/
    private SQLiteDatabase mDataBase;

    private final Context mContext;

    public static String DB_DATAEVENT_ASSET = "DataEvent.sqlite";
    public static String DB_DATALICHVIET_ASSET = "DataLichViet.sqlite";
    public static String DB_CUNGHD_ASSET = "horoscope.sqlite";
    public static String DB_TIVI2021_ASSET = "Tuvi2021.sqlite";
    public static String DB_TUVITRONDOI_ASSET = "Tuvitrondoi.sqlite";
    public static String DB_VANKHAN_ASSET = "Vankhan.sqlite";

    public DataBaseHelper(Context context, String nameDB, String assets) {
        super(context, nameDB, null, 1);
        File pathDB = context.getDatabasePath(nameDB);
        this.mContext = context;

        try {
            createDataBase(assets, pathDB);
        } catch (IOException e) {
            e.printStackTrace();
        }

        selectTuvi("nam", 1990);
    }

    public void createDataBase(String assets, File DB_FILE) throws IOException {
        // If the database does not exist, copy it from the assets.
        boolean mDataBaseExist = checkDataBase(DB_FILE);
        if(!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                // Copy the database from assests
                copyDataBase(assets, DB_FILE);
                Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    // Check that the database file exists in databases folder
    private boolean checkDataBase(File DB_FILE) {
        return DB_FILE.exists();
    }

    // Copy the database from assets
    private void copyDataBase(String assets, File DB_FILE) throws IOException {
        InputStream mInput = mContext.getAssets().open(assets);
        OutputStream mOutput = new FileOutputStream(DB_FILE);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    // Open the database, so we can query it
    public SQLiteDatabase openDataBase(File DB_FILE) throws SQLException {
        SQLiteDatabase mSqLiteDatabase;
        mSqLiteDatabase = SQLiteDatabase.openDatabase(DB_FILE.getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mSqLiteDatabase;
    }

    @Override
    public synchronized void close() {
        if(mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Tuvi selectTuvi(String sex, int yyyy) {
        Tuvi mTuvi = new Tuvi();
        if (mDataBase == null || !mDataBase.getPath().contains("Tuvi2021")) {
            mDataBase = openDataBase(mContext.getDatabasePath(DB_NAME_TUVI2021));
        }
        String strSQL = "";
        if (sex.equals("nam")) {
            strSQL = "SELECT NamSinh, NamSinhNam, InfoTuoiNam, TuoiNam, MangNam, SaoNam, HanNam, VanNienNam, ThienCanNam, DiaChiNam, XuatHanhNam,\n" +
                    "MauSacNam, TongQuatNam, SuNghiepNam, TinhCamNam, SucKhoeNam, DacBietTrongNamNam, CungSaoHanNam\n" +
                    "from TUVI a where a.NamSinh = ?";
        } else {
            strSQL = "SELECT NamSinh, NamSinhNu, InfoTuoiNu, TuoiNu, MangNu, SaoNu, HanNu, VanNienNu, ThienCanNu, DiaChiNu, XuatHanhNu,\n" +
                    "MauSacNu, TongQuatNu, SuNghiepNu, TinhCamNu, SucKhoeNu, DacBietTrongNamNu, CungSaoHanNu\n" +
                    "from TUVI a where a.NamSinh = ?";
        }

        Cursor mCursor = mDataBase.rawQuery(strSQL, new String[] {yyyy + ""});
        if (mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            mTuvi.setNamSinh(mCursor.getInt(0) + "");
            mTuvi.setNamSinhAL(mCursor.getString(1));;
            mTuvi.setInfoTuoi(mCursor.getString(2));;
            mTuvi.setTuoi(mCursor.getString(3));
            mTuvi.setMang(mCursor.getString(4));
            mTuvi.setSao(mCursor.getString(5));
            mTuvi.setHan(mCursor.getString(6));
            mTuvi.setVanNien(mCursor.getString(7));
            mTuvi.setThienCan(mCursor.getString(8));
            mTuvi.setDiaChi(mCursor.getString(9));
            mTuvi.setXuatHanh(mCursor.getString(10));
            mTuvi.setMauSac(mCursor.getString(11));
            mTuvi.setTongQuat(mCursor.getString(12));
            mTuvi.setSuNghiep(mCursor.getString(13));
            mTuvi.setTinhCam(mCursor.getString(14));
            mTuvi.setSucKhoe(mCursor.getString(15));
            mTuvi.setDacBietTrongNam(mCursor.getString(16));
            mTuvi.setCungSaoHan(mCursor.getString(17));
            mTuvi.setSex(sex);
        }
        mDataBase.close();
        return mTuvi;
    }

    public Tuvitrondoi selectTuviTrondoi(int sex, int yyyy) {
        Tuvitrondoi mTuvi = new Tuvitrondoi();
        if (mDataBase == null || !mDataBase.getPath().contains("Tuvitrondoi")) {
            mDataBase = openDataBase(mContext.getDatabasePath(DB_NAME_TUVITRONDOI));
        }
        String strSQL = "select namsinh, tuoi, gender, khon, truc, mang, khac, connha, xuong, tuongtinh, chuy, tongquan, " +
                "cuocsong, tinhduyen, congdanh, tuoihoplaman, chonvochong, tuoidaiky, namkhokhan, gioxuathanh, dienbientungnam" +
                "from TUVI where namsinh = ? AND gender = ?";

        Cursor mCursor = mDataBase.rawQuery(strSQL, new String[] {yyyy + "", sex + ""});
        if (mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            mTuvi.setNamsinh(mCursor.getInt(0) + "");
            mTuvi.setTuoi(mCursor.getString(1));;
            mTuvi.setGender(mCursor.getInt(2));;
            mTuvi.setKhon(mCursor.getString(3));
            mTuvi.setTruc(mCursor.getString(4));
            mTuvi.setMang(mCursor.getString(5));
            mTuvi.setKhac(mCursor.getString(6));
            mTuvi.setConnha(mCursor.getString(7));
            mTuvi.setXuong(mCursor.getString(8));
            mTuvi.setTuongtinh(mCursor.getString(9));
            mTuvi.setChuy(mCursor.getString(10));
            mTuvi.setTongquan(mCursor.getString(11));
            mTuvi.setCuocsong(mCursor.getString(12));
            mTuvi.setTinhduyen(mCursor.getString(13));
            mTuvi.setCongdanh(mCursor.getString(14));
            mTuvi.setTuoihoplaman(mCursor.getString(15));
            mTuvi.setChonvochong(mCursor.getString(16));
            mTuvi.setTuoidaiky(mCursor.getString(17));
            mTuvi.setNamkhokhan(mCursor.getString(18));
            mTuvi.setGioxuathanh(mCursor.getString(19));
            mTuvi.setDienbientungnam(mCursor.getString(20));
        }
        mDataBase.close();
        return mTuvi;
    }

    public TuviCungHD selectTuviCungHD(String horo) {
        TuviCungHD mTuvi = new TuviCungHD();
        if (mDataBase == null || !mDataBase.getPath().contains("horoscope")) {
            mDataBase = openDataBase(mContext.getDatabasePath(DB_NAME_CUNGHD));
        }
        String strSQL = "SELECT horo, mota, dactrung, phamchat, sunghiep, giadinh, tinhcach, tinhyeu from TUVITRONDOI where horo = ?";

        Cursor mCursor = mDataBase.rawQuery(strSQL, new String[] {horo});
        if (mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            mTuvi.setHoro(mCursor.getInt(0) + "");
            mTuvi.setMota(mCursor.getString(1));;
            mTuvi.setDactrung(mCursor.getString(2));;
            mTuvi.setPhamchat(mCursor.getString(3));
            mTuvi.setSunghiep(mCursor.getString(4));
            mTuvi.setGiadinh(mCursor.getString(5));
            mTuvi.setTinhcach(mCursor.getString(6));
            mTuvi.setTinhyeu(mCursor.getString(7));
        }
        mDataBase.close();
        return mTuvi;
    }
}
