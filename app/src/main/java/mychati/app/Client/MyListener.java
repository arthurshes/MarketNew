package mychati.app.Client;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyListener {
    private ValueEventListener valueEventListener;
    private Query query;

    public MyListener(ValueEventListener valueEventListener, Query query) {
        this.valueEventListener = valueEventListener;
       this.query=query;
    }
    public void unsubscribe(){
      query.removeEventListener(valueEventListener);
    }
}
