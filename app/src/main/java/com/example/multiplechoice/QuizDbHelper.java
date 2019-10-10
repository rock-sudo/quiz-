package com.example.multiplechoice;





import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.view.View;

        import com.example.multiplechoice.QuizContract.*;
        import androidx.annotation.Nullable;

        import java.security.PrivateKey;
        import java.security.PublicKey;
        import java.util.ArrayList;
        import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="MyQuiz.db";
    private static final int DATABASE_VERSION =1;

    private SQLiteDatabase db;


    public QuizDbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db =db;
        final String SQL_CREATE_QUESTION_TABLE ="CREATE TABLE " + QuestionsTable.TABLE_NAME + "( " + QuestionsTable._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "+QuestionsTable.COLUMN_QUESTION + "TEXT, "
                +QuestionsTable.COLUMN_OPTION1 + "TEXT," +
                QuestionsTable.COLUMN_OPTION2 + "TEXT," +
                QuestionsTable.COLUMN_OPTION3+ "TEXT," +
                QuestionsTable.COLUME_ANSWER_NR + "INTEGER " +  ")";

        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);

    }

    private void fillQuestionsTable(){

        Questions q1 =new Questions("A is correct","A","B","C",1);
        addQuestions(q1);

        Questions q2 =new Questions("B is correct","A","B","C",2);
        addQuestions(q2);
        Questions q3 =new Questions("A is correct","A","B","C",1);
        addQuestions(q3);
        Questions q4 =new Questions("C is correct","A","B","C",3);
        addQuestions(q4);
        Questions q5 =new Questions("B is correct","A","B","C",2);
        addQuestions(q5);


    }

    private void addQuestions(Questions question)
    {
        ContentValues cv =new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);


    }

    public List<Questions> getAllQuestions() {
        List<Questions> questionList = new ArrayList<>();
        db= getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ QuestionsTable.TABLE_NAME ,null);

        if(c.moveToFirst()){
            do{
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUME_ANSWER_NR)));

                questionList.add(question);


            }while(c.moveToNext());

        }

        c.close();
        return questionList;
    }



}
