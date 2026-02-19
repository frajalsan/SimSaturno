package com.fjas.simsaturno;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SimSaturnoDB";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_DATOS_ORBITALES = "datos_orbitales";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_RADIO_ORBITAL = "radio_orbital";
    public static final String COLUMN_PERIODICIDAD = "periodicidad";

    // ********************************************************************

    public static final String TABLE_INFO_SATELITES = "informacion_satelites";
    public static final String COLUMN_MAGNITUD = "magnitud";
    public static final String COLUMN_DIAMETRO = "diametro";
    public static final String COLUMN_DESCUBRIDOR = "descubridor";
    public static final String COLUMN_ANIO = "anio_descubrimiento";

    // ********************************************************************

    public static final String TABLE_PERIODOS = "periodos_satelites";
    public static final String COLUMN_PERIODO = "periodo_sinodico";

    // ********************************************************************

    public static final String TABLE_OBSERVACIONES = "observaciones";
    public static final String COLUMN_SATELITE = "satelite";
    public static final String COLUMN_OBSERVACION = "observacion";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_HORA = "hora";


    // ********************************************************************



    private static final String CREATE_TABLE_DATOS_ORBITALES =
            "CREATE TABLE " + TABLE_DATOS_ORBITALES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT NOT NULL, " +
                    COLUMN_RADIO_ORBITAL + " REAL NOT NULL, " +
                    COLUMN_PERIODICIDAD + " REAL NOT NULL" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Activar claves foráneas
        db.execSQL("PRAGMA foreign_keys=ON;");

        // ---------------- Tabla principal: información general ----------------
        String CREATE_TABLE_INFO_SATELITES = "CREATE TABLE " + TABLE_INFO_SATELITES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT NOT NULL UNIQUE, " + // clave única
                COLUMN_MAGNITUD + " REAL, " +
                COLUMN_DIAMETRO + " REAL, " +
                COLUMN_DESCUBRIDOR + " TEXT, " +
                COLUMN_ANIO + " INTEGER);";
        db.execSQL(CREATE_TABLE_INFO_SATELITES);

        // ---------------- Tabla de datos orbitales ----------------
        String CREATE_TABLE_DATOS_ORBITALES = "CREATE TABLE " + TABLE_DATOS_ORBITALES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_RADIO_ORBITAL + " REAL NOT NULL, " +
                COLUMN_PERIODICIDAD + " REAL NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_NOMBRE + ") REFERENCES " + TABLE_INFO_SATELITES + "(" + COLUMN_NOMBRE + ") " +
                "ON DELETE CASCADE ON UPDATE CASCADE);";
        db.execSQL(CREATE_TABLE_DATOS_ORBITALES);

        // ---------------- Tabla de periodos ----------------
        String CREATE_TABLE_PERIODOS = "CREATE TABLE " + TABLE_PERIODOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_PERIODO + " REAL, " +
                "fecha_hora TEXT, " +
                "FOREIGN KEY(" + COLUMN_NOMBRE + ") REFERENCES " + TABLE_INFO_SATELITES + "(" + COLUMN_NOMBRE + ") " +
                "ON DELETE CASCADE ON UPDATE CASCADE);";
        db.execSQL(CREATE_TABLE_PERIODOS);

        // ---------------- Tabla de observaciones ----------------
        String CREATE_OBSERVACIONES_TABLE = "CREATE TABLE " + TABLE_OBSERVACIONES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SATELITE + " TEXT NOT NULL, " +
                COLUMN_OBSERVACION + " TEXT, " +
                COLUMN_FECHA + " TEXT, " +
                COLUMN_HORA + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_SATELITE + ") REFERENCES " + TABLE_INFO_SATELITES + "(" + COLUMN_NOMBRE + ") " +
                "ON DELETE CASCADE ON UPDATE CASCADE);";
        db.execSQL(CREATE_OBSERVACIONES_TABLE);

        // ---------------- Inserciones ----------------
        // Insertar datos base de satélites
        db.execSQL("INSERT INTO " + TABLE_INFO_SATELITES + " (" +
                COLUMN_NOMBRE + ", " + COLUMN_MAGNITUD + ", " + COLUMN_DIAMETRO + ", " +
                COLUMN_DESCUBRIDOR + ", " + COLUMN_ANIO + ") VALUES " +
                "('Mimas', 12.9, 396.4, 'William Herschel', 1789)," +
                "('Encélado', 11.7, 504.2, 'William Herschel', 1789)," +
                "('Tethys', 10.2, 1062.2, 'Giovanni Cassini', 1684)," +
                "('Dione', 10.4, 1122.8, 'Giovanni Cassini', 1684)," +
                "('Rhea', 10.0, 1527, 'Giovanni Cassini', 1672)," +
                "('Titán', 8.4, 5149.52, 'Christiaan Huygens', 1655)," +
                "('Japeto', 10.2, 1468.6, 'Giovanni Cassini', 1671);");

        // Insertar datos orbitales
        db.execSQL("INSERT INTO " + TABLE_DATOS_ORBITALES + " (" +
                COLUMN_NOMBRE + ", " + COLUMN_RADIO_ORBITAL + ", " + COLUMN_PERIODICIDAD + ") VALUES " +
                "('Mimas', 185540, 0.942422)," +
                "('Encélado', 238400, 1.370218)," +
                "('Tethys', 294992, 1.887802)," +
                "('Dione', 377654, 2.736916)," +
                "('Rhea', 527367, 4.517503)," +
                "('Titán', 1221803, 15.945448)," +
                "('Japeto', 3561850, 79.331002);");

        // Insertar periodos
        db.execSQL("INSERT INTO " + TABLE_PERIODOS + " (" +
                COLUMN_NOMBRE + ", " + COLUMN_PERIODO + ", fecha_hora) VALUES " +
                "('Mimas', 0.942504558, '2025-10-12 10:05:29')," +
                "('Encélado', 1.370392527, '2025-10-12 14:02:56')," +
                "('Tethys', 1.888133297, '2025-10-12 17:13:07')," +
                "('Dione', 2.737612404, '2025-10-14 01:04:12')," +
                "('Rhea', 4.519400612, '2025-10-12 04:02:43')," +
                "('Titán', 15.96911513, '2025-10-10 05:06:22')," +
                "('Japeto', 79.92029066, '2025-08-30 12:26:38');");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIODOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATOS_ORBITALES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO_SATELITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSERVACIONES);
        onCreate(db);
    }

    public InfoSatelite getInfoSatelite(String nombreSatelite) {
        SQLiteDatabase db = this.getReadableDatabase();
        InfoSatelite info = null;

        String query = "SELECT " + COLUMN_NOMBRE + ", " +
                COLUMN_MAGNITUD + ", " +
                COLUMN_DIAMETRO + ", " +
                COLUMN_DESCUBRIDOR + ", " +
                COLUMN_ANIO +
                " FROM " + TABLE_INFO_SATELITES +
                " WHERE " + COLUMN_NOMBRE + " = ?";

        Cursor c = db.rawQuery(query, new String[]{nombreSatelite});

        if (c.moveToFirst()) {
            String nombre = c.getString(0);
            float magnitud = c.getFloat(1);
            float diametro = c.getFloat(2);
            String descubridor = c.getString(3);
            int anio = c.getInt(4);
            info = new InfoSatelite(nombre, magnitud, diametro, descubridor, anio);
        }

        c.close();
        db.close();
        return info;
    }

    // Obtener el periodo sinódico de un satélite
    public double getPeriodoSatelite(String nombre) {
        double periodo = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PERIODO + " FROM " + TABLE_PERIODOS +
                " WHERE " + COLUMN_NOMBRE + " = ?", new String[]{nombre});
        if (cursor.moveToFirst()) {
            periodo = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return periodo;
    }

    // Obtener el radio orbital de un satélite
    public double getRadioOrbital(String nombre) {
        double radio = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_RADIO_ORBITAL + " FROM " + TABLE_DATOS_ORBITALES +
                " WHERE " + COLUMN_NOMBRE + " = ?", new String[]{nombre});
        if (cursor.moveToFirst()) {
            radio = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return radio;
    }

    // Obtener la fecha de referencia (fecha_hora) de un satélite desde la tabla periodos_satelites
    public Calendar getFechaReferencia(String nombreSatelite) {
        SQLiteDatabase db = this.getReadableDatabase();
        Calendar calendar = null;

        String query = "SELECT fecha_hora FROM " + TABLE_PERIODOS +
                " WHERE " + COLUMN_NOMBRE + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{nombreSatelite});

        if (cursor.moveToFirst()) {
            String fechaHoraStr = cursor.getString(0); // formato: "yyyy-MM-dd HH:mm:ss"
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                Date fecha = sdf.parse(fechaHoraStr);
                calendar = Calendar.getInstance();
                calendar.setTime(fecha);
            } catch (Exception e) {
                e.printStackTrace(); // si la fecha no se puede parsear
            }
        }

        cursor.close();
        db.close();
        return calendar;
    }





}
