package com.jorchi.architecture.mvvm.db;

import android.content.Context;

import com.jorchi.architecture.mvvm.AppExecutors;
import com.jorchi.architecture.mvvm.db.converter.DateConverter;
import com.jorchi.architecture.mvvm.db.dao.CommentDao;
import com.jorchi.architecture.mvvm.db.dao.ProductDao;
import com.jorchi.architecture.mvvm.db.entity.CommentEntity;
import com.jorchi.architecture.mvvm.db.entity.ProductEntity;
import com.jorchi.architecture.mvvm.db.entity.ProductFtsEntity;
import com.jorchi.architecture.mvvm.model.Comment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ProductEntity.class, ProductFtsEntity.class, CommentEntity.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase mInstance;
    private static final String DATABASE_NAME = "basic-sample-db";

    public abstract ProductDao productDao();
    public abstract CommentDao commentDao();
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();


    public static AppDatabase getInstance(final Context context, final AppExecutors executors){
        if (mInstance == null){
            synchronized (AppDatabase.class){
                if (mInstance == null){
                    mInstance = buildDatabase(context.getApplicationContext(), executors);
                    mInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public static AppDatabase buildDatabase(final Context context, final AppExecutors executors){
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                executors.diskIO().execute(()->{
                    addDelay();
                    AppDatabase database = AppDatabase.getInstance(context, executors);
                    List<ProductEntity> products = DataGenerator.generateProducts();
                    List<CommentEntity> comments = DataGenerator.generateCommentsForProducts(products);
                    insetData(database, products, comments);
                    database.setDatabaseCreated();
                });
            }
        }).addMigrations(MIGRATION_1_2).build();
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated(){
        return mIsDatabaseCreated;
    }

    private void updateDatabaseCreated(final Context context){
        if (context.getDatabasePath(DATABASE_NAME).exists()){
            setDatabaseCreated();
        }
    }

    private static void addDelay(){
        try {
            Thread.sleep(4000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void insetData(final AppDatabase database, final List<ProductEntity> products,
                                  final List<CommentEntity> comments){
        database.runInTransaction(()->{
            database.productDao().insertAll(products);
            database.commentDao().insertAll(comments);
        });
    }

    private static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `productsFts` " +
                    "USING FTS4(`name` TEXT, `description` TEXT, content= `products`)");
            database.execSQL("INSERT INTO productsFts (`rowid`, `name`, `description`) " +
                    "SELECT `id`, `name`, `description` FROM products");
        }
    };
}
