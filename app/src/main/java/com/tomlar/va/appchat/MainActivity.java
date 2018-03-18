package com.tomlar.va.appchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener {

    private RecyclerView rcvContent;
    private MessageAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btnSend;
    private EditText edtMessage;

    public static final int MAX_LENG = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvContent = findViewById(R.id.rcvContent);
        btnSend = findViewById(R.id.btn_send);
        edtMessage = findViewById(R.id.edt_message);

        btnSend.setOnClickListener(this);

        // Init RecyclerView
        adapter = new MessageAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        rcvContent.setLayoutManager(mLayoutManager);
        rcvContent.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        String message = edtMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            // display an error.
            Toast.makeText(this, "Please input message", Toast.LENGTH_SHORT).show();
            return;
        }
        splitMessage(message);
    }

    private void splitMessage(String str) {
        String[] items = str.split(" ");
        List<String> wordList = Arrays.asList(items);

        if (str.length() < MAX_LENG) {
            adapter.addMessage(str);
        } else {
            ArrayList<String> chunks = new ArrayList<>();
            int count = 1;                              // Count chunks
            int num = getNumberOfChunk(wordList);      // Number of chunks of a message

            // If the message contains a span of non-whitespace characters longer than 50 characters, display an error.
            if (num == -1) {
                // display an error.
                Toast.makeText(this, "Message is invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            int indexOfChunks = 0;
            for (int i = 0; i < wordList.size(); i++) {
                chunks.add(wordList.get(i));
                indexOfChunks++;
                if (getStringLengthOfChunks(chunks) > MAX_LENG) {
                    // If first word > 50, don't remove item
                    if (i != 0) {
                        indexOfChunks--;
                        chunks.remove(indexOfChunks);
                    }
                    adapter.addMessage(count + "/"
                            + num
                            + " "
                            + toString(chunks));
                    count++;
                    chunks.clear();
                    indexOfChunks = 0;
                    chunks.add(wordList.get(i));
                }

                // Last item
                if (i == wordList.size() - 1) {
                    adapter.addMessage(num + "/"
                            + num
                            + " "
                            + toString(chunks));
                }
            }
        }
        edtMessage.setText("");
    }

    /**
     * Get Length of chunk
     *
     * @param Array
     * @return int
     */
    private int getStringLengthOfChunks(ArrayList<String> arr) {
        String str = "";
        for (int i = 0; i < arr.size(); i++) {
            str += arr.get(i) + " ";
        }
        return str.length() + 4;        // return length of  indicators + message. ex: "1/2 Hello"
    }

    /**
     * Convert Array to String
     *
     * @param Array
     * @return String
     */
    private String toString(ArrayList<String> arr) {
        String str = "";
        for (int i = 0; i < arr.size(); i++) {
            str += arr.get(i) + " ";
        }
        return str;
    }

    /**
     * Get Total chunk of a message when split the message into parts
     * If the message contains a span of non-whitespace characters longer than 50 characters, return -
     *
     * @param Array
     * @return int
     */
    private int getNumberOfChunk(List<String> itemList) {
        int num = 0;
        ArrayList<String> itemMessage = new ArrayList<>();
        int indexOfItemMessage = 0;
        for (int i = 0; i < itemList.size(); i++) {

            if (itemList.get(i).length() > 50) {
                return -1;
            }
            itemMessage.add(itemList.get(i));
            indexOfItemMessage++;
            if (getStringLengthOfChunks(itemMessage) > MAX_LENG) {
                indexOfItemMessage--;
                itemMessage.remove(indexOfItemMessage);
                itemMessage.clear();
                indexOfItemMessage = 0;
                itemMessage.add(itemList.get(i));
                num++;
            }

            // Last item
            if (i == itemList.size() - 1) {
                num++;
            }
        }
        return num;
    }
}
