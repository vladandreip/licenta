package caompany.com.licenta;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.nio.charset.Charset;
import java.util.ArrayList;

import caompany.com.licenta.cursuri.Curs;
import caompany.com.licenta.networking.ClassRequest;
import caompany.com.licenta.networking.DeleteClassRequest;
import caompany.com.licenta.networking.GetClassRequest;
import caompany.com.licenta.swipe.SwipeDismissListViewTouchListener;
import retrofit2.Response;

public class ClassActivity extends AppCompatActivity implements NfcAdapter.OnNdefPushCompleteCallback,
        NfcAdapter.CreateNdefMessageCallback{
    //The array lists to hold our messages
    Curs curs;
    String header;
    private ArrayList<String> messagesReceivedArray = new ArrayList<>();

    //Text boxes to add and display our messages
    private ArrayList<String> languagesarraylist;
    ArrayAdapter<String> language_adapter;
    private TextInputLayout courseNameWrapper;
    private TextView txtReceivedMessages;
    private ListView listViewCourse;
    Context mContext;

    public void addCourse(View view) {
        //String newMessage = txtBoxAddMessage.getText().toString();
        String courseName = courseNameWrapper.getEditText().getText().toString();
        if(!verifyCredentials(courseName)){
            courseNameWrapper.setError("Nume invalid!");
        }

    }


    private  void updateTextViews() {

        txtReceivedMessages.setText("Messages Received:\n");
        //Populate our list of messages we have received
        if (messagesReceivedArray.size() > 0) {
            for (int i = 0; i < messagesReceivedArray.size(); i++) {
                txtReceivedMessages.append(messagesReceivedArray.get(i));
                txtReceivedMessages.append("\n");
            }
        }
    }

    //Save our Array Lists of Messages for if the user navigates away
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("lastMessagesReceived",messagesReceivedArray);
    }

    //Load our Array Lists of Messages for when the user navigates back
    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        messagesReceivedArray = savedInstanceState.getStringArrayList("lastMessagesReceived");
    }

    private NfcAdapter mNfcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        mContext = this;
        if(getIntent().getExtras() != null){
            Log.d("TOKEN", "onCreate: " + getIntent().getExtras().getString("x-auth"));
            header = getIntent().getExtras().getString("header");
        }
        init();
        //listViewCourse.setAdapter(language_adapter);
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listViewCourse,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Toast.makeText(mContext,"Bum",Toast.LENGTH_LONG).show();

                                    //delete request for course
                                    DeleteClassRequest deleteClassRequest = new DeleteClassRequest();
                                    deleteClassRequest.tryRequest(header, curs.getCurses().get(position).get_id());


                                    languagesarraylist.remove(position);
                                    language_adapter.notifyDataSetChanged();

                                }

                            }
                        });
        listViewCourse.setOnTouchListener(touchListener);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter != null) {
            //Handle some NFC initialization here
            //This will refer back to createNdefMessage for what it will send
            mNfcAdapter.setNdefPushMessageCallback(this, this);

            //This will be called if the message is sent successfully
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        else {
            Toast.makeText(this, "NFC not available on this device",
                    Toast.LENGTH_SHORT).show();
        }
        courseNameWrapper = findViewById(R.id.course_name_wrapper);

        courseNameWrapper.setHint("Numele cursului");
        Button btnAddMessage = findViewById(R.id.btn_new_course);
        txtReceivedMessages = findViewById(R.id.textView);
        btnAddMessage.setText("Add Message");
        updateTextViews();
    }
    /*
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        //This will be called when another NFC capable device is detected.
        if (messagesToSendArray.size() == 0) {
            return null;
        }
        //We'll write the createRecords() method in just a moment
        NdefRecord[] recordsToAttach = createRecords();
        //When creating an NdefMessage we need to provide an NdefRecord[]
        return new NdefMessage(recordsToAttach);
    }*/



    /*
    public NdefRecord[] createRecords() {
        NdefRecord[] records = new NdefRecord[messagesToSendArray.size() + 1];
        //To Create Messages Manually if API is less than
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            for (int i = 0; i < messagesToSendArray.size(); i++){
                byte[] payload = messagesToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));
                NdefRecord record = new NdefRecord(
                        NdefRecord.TNF_WELL_KNOWN,      //Our 3-bit Type name format
                        NdefRecord.RTD_TEXT,            //Description of our payload
                        new byte[0],                    //The optional id for our Record
                        payload);                       //Our payload for the Record

                records[i] = record;
            }
        }
        //Api is high enough that we can use createMime, which is preferred.
        else {
            for (int i = 0; i < messagesToSendArray.size(); i++){
                byte[] payload = messagesToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));

                NdefRecord record = NdefRecord.createMime("text/plain",payload);
                records[i] = record;
            }
        }
        records[messagesToSendArray.size()] =
                NdefRecord.createApplicationRecord(getPackageName());
        return records;
    }*/
    private void handleNfcIntent(Intent NfcIntent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction())) {
            Parcelable[] receivedArray =
                    NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(receivedArray != null) {
                messagesReceivedArray.clear();
                NdefMessage receivedMessage = (NdefMessage) receivedArray[0];
                NdefRecord[] attachedRecords = receivedMessage.getRecords();

                for (NdefRecord record:attachedRecords) {
                    String string = new String(record.getPayload());
                    //Make sure we don't pass along our AAR (Android Application Record)
                    if (string.equals(getPackageName())) { continue; }
                    messagesReceivedArray.add(string);
                }
                Toast.makeText(this, "Received " + messagesReceivedArray.size() +
                        " Messages", Toast.LENGTH_LONG).show();
                updateTextViews();
            }
            else {
                Toast.makeText(this, "Received Blank Parcel", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onNewIntent(Intent intent) {
        handleNfcIntent(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        updateTextViews();
        handleNfcIntent(getIntent());
    }
    public boolean verifyCredentials(String credential){
        return credential.length() > 0;
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        //return null;
    }
    public void init(){
        listViewCourse = findViewById(R.id.lv_courses);
        languagesarraylist = new ArrayList<>();
        GetClassRequest getClassRequest = new GetClassRequest(){
            @Override
            public void onSuccess(Response<JsonElement> response) {
                Log.d("ceva", "onSuccess: ");
                Gson gson = new Gson();
                try {
                    curs = gson.fromJson(response.body(), Curs.class);
                    int size = curs.getCurses().size();
                    for(int i=0; i<size; i++){
                        Log.d("MATERIE", "onSuccess: " + curs.getCurses().get(i).getText());
                        languagesarraylist.add(curs.getCurses().get(i).getText());
                    }
                    language_adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_activated_1,languagesarraylist);
                    listViewCourse.setAdapter(language_adapter);
                    //language_adapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        getClassRequest.tryRequest(header);

        //adding few data to arraylist
        //languagesarraylist.add("SQL");
        //languagesarraylist.add("JAVA");
        //languagesarraylist.add("JAVA SCRIPT ");
        //languagesarraylist.add("C#");
        //languagesarraylist.add("PYTHON");
        //languagesarraylist.add("C++");
        //languagesarraylist.add("IOS");
        //languagesarraylist.add("ANDROID");
        //languagesarraylist.add("PHP");
        //languagesarraylist.add("LARAVEL");


        //language_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,languagesarraylist);
    }
}