package com.example.patienttrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
//Newly added


public class SecondActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Button logout;

    private TextView textView;
    private WifiManager wifiManager;
    private ListView listView;
    private Button buttonScan;
    private int size = 0;
    private List<android.net.wifi.ScanResult> results;
    private ArrayList<String> arrayList = new ArrayList<>(), Ap = new ArrayList<>(), LocationList = new ArrayList<>();
    private ArrayList<Double> Dis = new ArrayList<>();
    private HashMap<String, Integer> Count =  new HashMap<String, Integer>();
    private ArrayAdapter adapter;
    private android.os.Handler mHandler = new android.os.Handler();
    private ArrayList<Integer[]> outerArray = new ArrayList<Integer[]>();
    private int[] Now = new int[6];
    private double[] DisIndx = new double[1000];
    private boolean[] vst = new boolean[1000];

    String s = "\0", Loc = "\0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //AP List
        Ap.add("CSE Office-3");
        Ap.add("CSE Office-2");
        Ap.add("CSE Head Office");
        Ap.add("OSLAB");
        Ap.add("TP-LINK_80D4");
        Ap.add("CSE Networklab");

        //Location List
        LocationList.add("Student's Room");
        LocationList.add("Dining Room");
        LocationList.add("Corridor - 1");
        LocationList.add("Corridor - 2");
        LocationList.add("Corridor - 3");
        LocationList.add("Corridor - 4");
        LocationList.add("Corridor - 5");
        LocationList.add("Corridor - 6");
        LocationList.add("Ashfak Sir's Room");
        Count.put("Student's Room", 0);
        Count.put("Dining Room", 0);
        Count.put("Corridor - 1", 0);
        Count.put("Corridor - 2", 0);
        Count.put("Corridor - 3", 0);
        Count.put("Corridor - 4", 0);
        Count.put("Corridor - 5", 0);
        Count.put("Corridor - 6", 0);
        Count.put("Ashfak Sir's Room", 0);
        //Initializing Current Ap's Strength
        for(int i = 0; i < 6; i++) {
            Now[i] = 0;
        }

        //Data Set Insertion
        //Student's Room
        Integer[] Data1 = {-55, -83, -74, -86, 0, 0};
        outerArray.add(Data1);
        Integer[] Data2 = {-52, -75, -75, 0, 0, 0};
        outerArray.add(Data2);
        Integer[] Data3 = {-54, -67, -72, -79, 0, 0};
        outerArray.add(Data3);
        Integer[] Data4 = {-65, -75, -76, 0, 0, 0};
        outerArray.add(Data4);
        Integer[] Data5 = {-58, -70, -74, 0, 0, 0};
        outerArray.add(Data5);
        Integer[] Data6 = {-61, -75, 73, 0, 0, 0};
        outerArray.add(Data6);
        Integer[] Data7 = {-48, -67, -70, 0, 0, 0};
        outerArray.add(Data7);
        Integer[] Data8 = {-66, -73, -72, 0, 0, 0};
        outerArray.add(Data8);
        Integer[] Data9 = {-54, -77, -80, 0, 0, 0};
        outerArray.add(Data9);
        Integer[] Data10 = {-58, -73, -73, 0, 0, 0};
        outerArray.add(Data10);
        Integer[] Data11 = {-56, -66, -81, 0, 0, 0};
        outerArray.add(Data11);
        Integer[] Data12 = {-53, -78, -81, 0, 0, 0};
        outerArray.add(Data12);
        Integer[] Data13 = {-59, -73, -75, -78, 0, 0};
        outerArray.add(Data13);
        Integer[] Data14 = {-60, -73, -73, -79, 0, 0};
        outerArray.add(Data14);

        //Dining Room
        Integer[] Data15 = {-61, -86, -82, 0, 0, 0};
        outerArray.add(Data15);
        Integer[] Data16 = {-64, -85, -80, 0, 0, 0};
        outerArray.add(Data16);
        Integer[] Data17 = {-65, 0, -84, 0, 0, 0};
        outerArray.add(Data17);
        Integer[] Data18 = {-58, 0, -78, 0, 0, 0};
        outerArray.add(Data18);
        Integer[] Data19 = {-65, -82, -87, 0, 0, 0};
        outerArray.add(Data19);
        Integer[] Data20 = {-69, -86, -81, 0, 0, 0};
        outerArray.add(Data20);
        Integer[] Data21 = {-69, -87, -85, 0, 0, 0};
        outerArray.add(Data21);
        Integer[] Data22 = {-73, -83, 0, -82, 0, 0};
        outerArray.add(Data22);
        Integer[] Data23 = {-72, -83, -85, 0, 0, 0};
        outerArray.add(Data23);
        Integer[] Data24 = {-67, -87, -85, 0, 0, 0};
        outerArray.add(Data24);
        Integer[] Data25 = {-67, -84, -76, -82, 0, 0};
        outerArray.add(Data25);
        Integer[] Data26 = {-67, -84, -86, -81, 0, 0};
        outerArray.add(Data26);
        Integer[] Data27 = {-64, -83, -84, 0, 0, 0};
        outerArray.add(Data27);
        Integer[] Data28 = {-64, -84, -84, 0, 0, 0};
        outerArray.add(Data28);

        //Corridor - 1
        Integer[] Data29 = {-57, -82, -78, 0, 0, 0};
        outerArray.add(Data29);
        Integer[] Data30 = {-59, -81, -78, 0, 0, 0};
        outerArray.add(Data30);
        Integer[] Data31 = {-61, -85, -79, 0, 0, 0};
        outerArray.add(Data31);
        Integer[] Data32 = {-59, -83, -83, 0, 0, 0};
        outerArray.add(Data32);
        Integer[] Data33 = {-57, -86, -86, 0, 0, 0};
        outerArray.add(Data33);
        Integer[] Data34 = {-59, -82, -81, 0, 0, 0};
        outerArray.add(Data34);
        Integer[] Data35 = {-54, -79, -77, 0, 0, 0};
        outerArray.add(Data35);
        Integer[] Data36 = {-55, -83, -78, 0, 0, 0};
        outerArray.add(Data36);
        Integer[] Data37 = {-52, -74, -76, 0, 0, 0};
        outerArray.add(Data37);
        Integer[] Data38 = {-56, -74, -77, 0, 0, 0};
        outerArray.add(Data38);
        Integer[] Data39 = {-51, -70, -75, 0, 0, 0};
        outerArray.add(Data39);
        Integer[] Data40 = {-43, -78, -82, 0, 0, 0};
        outerArray.add(Data40);
        Integer[] Data41 = {-48, -79, -81, 0, 0, 0};
        outerArray.add(Data41);
        Integer[] Data42 = {-35, -75, -69, 0, 0, 0};
        outerArray.add(Data42);
        Integer[] Data43 = {-45, -75, -76, 0, 0, 0};
        outerArray.add(Data43);

        //Corridor - 2
        Integer[] Data44 = {0, -51, -82, -76, -78, 0};
        outerArray.add(Data44);
        Integer[] Data45 = {0, -59, -90, -77, -75, 0};
        outerArray.add(Data45);
        Integer[] Data46 = {0, -57, -79, 0, -79, 0};
        outerArray.add(Data46);
        Integer[] Data47 = {0, -63, -85, 0, -75, 0};
        outerArray.add(Data47);
        Integer[] Data48 = {0, -58, -84, 0, -80, 0};
        outerArray.add(Data48);
        Integer[] Data49 = {0, -52, -87, -82, -73, 0};
        outerArray.add(Data49);
        Integer[] Data50 = {0, -54, -71, -80, -79, 0};
        outerArray.add(Data50);
        Integer[] Data51 = {0, -59, -88, 0, -80, 0};
        outerArray.add(Data51);
        Integer[] Data52 = {0, -66, -82, 0, -77, 0};
        outerArray.add(Data52);
        Integer[] Data53 = {0, -56, -81, 0, -81, 0};
        outerArray.add(Data53);
        Integer[] Data54 = {-84, -61, 0, 0, -80, 0};
        outerArray.add(Data54);
        Integer[] Data55 = {0, -53, 0, -80, -82, 0};
        outerArray.add(Data55);
        Integer[] Data56 = {0, -54, -86, 0, -79, 0};
        outerArray.add(Data56);
        Integer[] Data57 = {0, -59, -88, -74, -79, 0};
        outerArray.add(Data57);
        Integer[] Data58 = {0, -59, -74, 0, -80, 0};
        outerArray.add(Data58);
        Integer[] Data59 = {0, -55, -84, 0, -86, 0};
        outerArray.add(Data59);
        Integer[] Data60 = {0, -57, -85, 0, -73, 0};
        outerArray.add(Data60);
        Integer[] Data61 = {0, -53, -86, 0, -79, 0};
        outerArray.add(Data61);
        Integer[] Data62 = {-87, -57, -82, 0, 0, 0};
        outerArray.add(Data62);
        Integer[] Data63 = {0, -61, -89, 0, -78, 0};
        outerArray.add(Data63);
        Integer[] Data64 = {0, -59, -83, -76, -88, 0};
        outerArray.add(Data64);
        Integer[] Data65 = {-74, -57, -78, -73, -83, 0};
        outerArray.add(Data65);
        Integer[] Data66 = {-74, -54, -83, 0, -82, 0};
        outerArray.add(Data66);
        Integer[] Data67 = {-74, -50, -83, 0, -82, 0};
        outerArray.add(Data67);
        Integer[] Data68 = {0, -56, -80, 0, -83, 0};
        outerArray.add(Data68);
        Integer[] Data69 = {-75, -62, -81, 0, -86, 0};
        outerArray.add(Data69);
        Integer[] Data70 = {0, -41, -83, 0, -82, 0};
        outerArray.add(Data70);
        Integer[] Data71 = {0, -43, -80, -79, -83, 0};
        outerArray.add(Data71);
        Integer[] Data72 = {0, -50, -73, 0, -77, 0};
        outerArray.add(Data72);
        Integer[] Data73 = {0, -46, -76, -78, 0, 0};
        outerArray.add(Data73);
        Integer[] Data74 = {-74, -52, -78, 0, -74, 0};
        outerArray.add(Data74);
        Integer[] Data75 = {-78, -46, -73, 0, 0, 0};
        outerArray.add(Data75);
        Integer[] Data76 = {0, -42, -88, -76, -82, 0};
        outerArray.add(Data76);
        Integer[] Data77 = {-76, -58, -69, -76, -83, 0};
        outerArray.add(Data77);
        Integer[] Data78 = {0, -54, -83, 0, -76, 0};
        outerArray.add(Data78);
        Integer[] Data79 = {0, -52, -72, 0, -84, 0};
        outerArray.add(Data79);
        Integer[] Data80 = {-76, -38, -76, 0, -88, 0};
        outerArray.add(Data80);

        //Corridor - 3
        Integer[] Data81 = {0, -77, 0, -54, -52, -83};
        outerArray.add(Data81);
        Integer[] Data82 = {0, -78, 0, -58, -57, -79};
        outerArray.add(Data82);
        Integer[] Data83 = {0, -72, 0, -58, -51, -82};
        outerArray.add(Data83);
        Integer[] Data84 = {0, -72, 0, -58, -49, -85};
        outerArray.add(Data84);
        Integer[] Data85 = {0, -78, 0, -73, -53, -81};
        outerArray.add(Data85);
        Integer[] Data86 = {0, -84, 0, -65, -58, -75};
        outerArray.add(Data86);
        Integer[] Data87 = {0, -81, 0, -65, -61, -74};
        outerArray.add(Data87);
        Integer[] Data88 = {0, -76, 0, -58, -57, -80};
        outerArray.add(Data88);
        Integer[] Data89 = {0, -83, 0, -58, -58, -73};
        outerArray.add(Data89);
        Integer[] Data90 = {0, -80, 0, -62, -55, -75};
        outerArray.add(Data90);
        Integer[] Data91 = {0, -76, 0, -64, -61, -73};
        outerArray.add(Data91);
        Integer[] Data92 = {0, 0, 0, -69, -62, -80};
        outerArray.add(Data92);
        Integer[] Data93 = {0, -75, 0, -56, -62, -79};
        outerArray.add(Data93);
        Integer[] Data94 = {0, 0, 0, -71, -67, -77};
        outerArray.add(Data94);
        Integer[] Data95 = {0, -80, 0, -61, -53, -74};
        outerArray.add(Data95);
        Integer[] Data96 = {0, -75, 0, -65, -67, -75};
        outerArray.add(Data96);
        Integer[] Data97 = {0, -74, 0, -66, -59, -69};
        outerArray.add(Data97);
        Integer[] Data98 = {0, -71, 0, -71, -63, -80};
        outerArray.add(Data98);
        Integer[] Data99 = {0, -83, 0, -64, -66, -75};
        outerArray.add(Data99);
        Integer[] Data100 = {0, 0, 0, -74, -63, -75};
        outerArray.add(Data100);
        Integer[] Data101 = {0, -73, 0, -64, -62, -77};
        outerArray.add(Data101);
        Integer[] Data102 = {0, -79, 0, -74, -64, -70};
        outerArray.add(Data102);
        Integer[] Data103 = {0, -76, 0, -71, -65, -75};
        outerArray.add(Data103);
        Integer[] Data104 = {0, -73, 0, -74, -61, -72};
        outerArray.add(Data104);
        Integer[] Data105 = {0, -80, 0, -74, -57, -70};
        outerArray.add(Data105);
        Integer[] Data106 = {0, -71, 0, -69, -59, -80};
        outerArray.add(Data106);
        Integer[] Data107 = {0, -76, 0, -69, -68, -73};
        outerArray.add(Data107);
        Integer[] Data108 = {0, -73, 0, -64, -63, -69};
        outerArray.add(Data108);
        Integer[] Data109 = {0, -76, 0, -74, -70, -75};
        outerArray.add(Data109);
        Integer[] Data110 = {0, -75, 0, -79, -69, -75};
        outerArray.add(Data110);
        Integer[] Data111 = {0, -70, 0, -74, -57, -69};
        outerArray.add(Data111);
        Integer[] Data112 = {0, -76, 0, -80, -65, -63};
        outerArray.add(Data112);

        //Corridor - 4
        Integer[] Data113 = {0, -85, -82, 0, -80, -84};
        outerArray.add(Data113);
        Integer[] Data114 = {0, -85, -83, 0, -76, -72};
        outerArray.add(Data114);
        Integer[] Data115 = {0, 0, -83, 0, -81, -81};
        outerArray.add(Data115);
        Integer[] Data116 = {0, -78, -81, 0, -85, -76};
        outerArray.add(Data116);
        Integer[] Data117 = {0, -78, -88, 0, -83, -81};
        outerArray.add(Data117);
        Integer[] Data118 = {0, -84, -86, 0, -78, -86};
        outerArray.add(Data118);
        Integer[] Data119 = {0, -82, -83, 0, -77, -82};
        outerArray.add(Data119);
        Integer[] Data120 = {0, -79, -76, 0, -76, -73};
        outerArray.add(Data120);
        Integer[] Data121 = {0, -84, -85, 0, -76, -84};
        outerArray.add(Data121);
        Integer[] Data122 = {0, -78, -77, 0, -73, -75};
        outerArray.add(Data122);
        Integer[] Data123 = {0, -79, -78, 0, -73, -79};
        outerArray.add(Data123);
        Integer[] Data124 = {0, -81, -76, 0, -74, -73};
        outerArray.add(Data124);
        Integer[] Data125 = {0, -82, -71, 0, -85, -69};
        outerArray.add(Data125);
        Integer[] Data126 = {0, -78, -71, 0, -73, -70};
        outerArray.add(Data126);
        Integer[] Data127 = {0, -75, -71, 0, -70, -67};
        outerArray.add(Data127);
        Integer[] Data128 = {0, -76, -73, 0, -76, -73};
        outerArray.add(Data128);
        Integer[] Data129 = {0, -73, -78, 0, -75, -77};
        outerArray.add(Data129);
        Integer[] Data130 = {0, -82, -78, 0, -69, -74};
        outerArray.add(Data130);
        Integer[] Data131 = {0, -73, -76, 0, -73, -73};
        outerArray.add(Data131);
        Integer[] Data132 = {0, -69, 0, 0, -72, -73};
        outerArray.add(Data132);
        Integer[] Data133 = {0, -72, -76, 0, -73, -80};
        outerArray.add(Data133);
        Integer[] Data134 = {0, -63, 0, 0, -72, -76};
        outerArray.add(Data134);
        Integer[] Data135 = {0, -60, 0, 0, -74, -67};
        outerArray.add(Data135);
        Integer[] Data136 = {0, -67, -83, 0, -68, -66};
        outerArray.add(Data136);
        Integer[] Data137 = {0, -73, -80, 0, -70, -76};
        outerArray.add(Data137);

        //Corridor - 5
        Integer[] Data138 = {0, 0, 0, 0, -65, -51};
        outerArray.add(Data138);
        Integer[] Data139 = {0, 0, 0, 0, -66, -50};
        outerArray.add(Data139);
        Integer[] Data140 = {0, 0, 0, 0, -69, -59};
        outerArray.add(Data140);
        Integer[] Data141 = {0, 0, 0, 0, -71, -56};
        outerArray.add(Data141);
        Integer[] Data142 = {0, 0, 0, 0, -75, -60};
        outerArray.add(Data142);
        Integer[] Data143 = {0, 0, 0, 0, -65, -51};
        outerArray.add(Data143);
        Integer[] Data144 = {0, 0, 0, 0, -68, -47};
        outerArray.add(Data144);
        Integer[] Data145 = {0, 0, 0, 0, -63, -60};
        outerArray.add(Data145);
        Integer[] Data146 = {0, 0, 0, 0, -67, -46};
        outerArray.add(Data146);
        Integer[] Data147 = {0, 0, 0, 0, -73, -49};
        outerArray.add(Data147);
        Integer[] Data148 = {0, 0, 0, 0, -69, -51};
        outerArray.add(Data148);
        Integer[] Data149 = {0, 0, 0, 0, -73, -61};
        outerArray.add(Data149);
        Integer[] Data150 = {0, 0, 0, 0, -71, -58};
        outerArray.add(Data150);
        Integer[] Data151 = {0, 0, 0, 0, -76, -47};
        outerArray.add(Data151);
        Integer[] Data152 = {0, 0, 0, 0, -80, -54};
        outerArray.add(Data152);
        Integer[] Data153 = {0, 0, 0, 0, -63, -52};
        outerArray.add(Data153);
        Integer[] Data154 = {0, 0, 0, 0, -69, -61};
        outerArray.add(Data154);
        Integer[] Data155 = {0, 0, 0, -87, -71, -52};
        outerArray.add(Data155);
        Integer[] Data156 = {0, 0, 0, 0, -69, -59};
        outerArray.add(Data156);
        Integer[] Data157 = {0, 0, 0, 0, -76, -51};
        outerArray.add(Data157);
        Integer[] Data158 = {0, 0, 0, 0, -73, -55};
        outerArray.add(Data158);
        Integer[] Data159 = {0, 0, 0, 0, -63, -42};
        outerArray.add(Data159);
        Integer[] Data160 = {0, 0, 0, 0, -70, -52};
        outerArray.add(Data160);
        Integer[] Data161 = {0, 0, 0, 0, -71, -47};
        outerArray.add(Data161);
        Integer[] Data162 = {0, 0, 0, -84, -77, -46};
        outerArray.add(Data162);
        Integer[] Data163 = {0, 0, 0, 0, -76, -49};
        outerArray.add(Data163);
        Integer[] Data164 = {0, 0, 0, -86, -67, -56};
        outerArray.add(Data164);
        Integer[] Data165 = {0, 0, 0, 0, -67, -55};
        outerArray.add(Data165);
        Integer[] Data166 = {0, -85, 0, -86, -66, -41};
        outerArray.add(Data166);
        Integer[] Data167 = {0, -86, 0, -86, -69, -41};
        outerArray.add(Data167);
        Integer[] Data168 = {0, -87, 0, -89, -60, -39};
        outerArray.add(Data168);
        Integer[] Data169 = {0, 0, 0, -85, -67, -39};
        outerArray.add(Data169);
        Integer[] Data170 = {0, -78, 0, -87, -75, -31};
        outerArray.add(Data170);

        //Corridor - 6
        Integer[] Data171 = {0, 0, 0, -75, -42, -60};
        outerArray.add(Data171);
        Integer[] Data172 = {0, 0, 0, -72, -35, -49};
        outerArray.add(Data172);
        Integer[] Data173 = {0, 0, 0, -68, -42, -54};
        outerArray.add(Data173);
        Integer[] Data174 = {0, 0, 0, -79, -39, -60};
        outerArray.add(Data174);
        Integer[] Data175 = {0, 0, 0, -74, -41, -49};
        outerArray.add(Data175);
        Integer[] Data176 = {0, 0, 0, -83, -40, -48};
        outerArray.add(Data176);
        Integer[] Data177 = {0, 0, 0, -68, -41, -53};
        outerArray.add(Data177);
        Integer[] Data178 = {0, 0, 0, -63, -52, -52};
        outerArray.add(Data178);
        Integer[] Data179 = {0, 0, 0, -79, -53, -50};
        outerArray.add(Data179);
        Integer[] Data180 = {0, 0, 0, -69, -47, -54};
        outerArray.add(Data180);
        Integer[] Data181 = {0, 0, 0, -73, -48, -48};
        outerArray.add(Data181);
        Integer[] Data182 = {0, 0, 0, -72, -63, -48};
        outerArray.add(Data182);
        Integer[] Data183 = {0, 0, 0, -71, -55, -46};
        outerArray.add(Data183);
        Integer[] Data184 = {0, 0, 0, -77, -57, -57};
        outerArray.add(Data184);
        Integer[] Data185 = {0, -86, 0, -79, -59, -45};
        outerArray.add(Data185);
        Integer[] Data186 = {0, 0, 0, -78, -59, -47};
        outerArray.add(Data186);
        Integer[] Data187 = {0, 0, 0, -81, -59, -45};
        outerArray.add(Data187);
        Integer[] Data188 = {0, 0, 0, -75, -65, -44};
        outerArray.add(Data188);
        Integer[] Data189 = {0, 0, 0, -76, -52, -45};
        outerArray.add(Data189);
        Integer[] Data190 = {0, 0, 0, -78, -79, -51};
        outerArray.add(Data190);
        Integer[] Data191 = {0, 0, 0, -86, -60, -46};
        outerArray.add(Data191);
        Integer[] Data192 = {0, 0, 0, -80, -64, -54};
        outerArray.add(Data192);
        Integer[] Data193 = {0, -90, 0, -82, -69, -46};
        outerArray.add(Data193);
        Integer[] Data194 = {0, -90, 0, -80, -57, -47};
        outerArray.add(Data194);
        Integer[] Data195 = {0, -89, 0, -87, -74, -48};
        outerArray.add(Data195);
        Integer[] Data196 = {0, 0, 0, -83, -71, -55};
        outerArray.add(Data196);
        Integer[] Data197 = {0, -85, 0, -84, -73, -46};
        outerArray.add(Data197);
        Integer[] Data198 = {0, -85, 0, -84, -69, -45};
        outerArray.add(Data198);
        //Ashfak Sir's Room
        Integer[] Data199 = {0, -87, -88, 0, 0, -67};
        outerArray.add(Data199);
        Integer[] Data200 = {0, -88, 0, -89, 0, -73};
        outerArray.add(Data200);
        Integer[] Data201 = {0, -83, -84, 0, 0, -67};
        outerArray.add(Data201);
        Integer[] Data202 = {0, -85, -85, 0, 0, -69};
        outerArray.add(Data202);
        Integer[] Data203 = {0, -86, -85, 0, 0, -65};
        outerArray.add(Data203);
        Integer[] Data204 = {0, -83, -86, 0, 0, -71};
        outerArray.add(Data204);
        Integer[] Data205 = {0, -83, -86, 0, 0, -69};
        outerArray.add(Data205);
        Integer[] Data206 = {0, -85, -85, 0, 0, -67};
        outerArray.add(Data206);
        Integer[] Data207 = {0, -88, 0, 0, 0, -68};
        outerArray.add(Data207);
        Integer[] Data208 = {0, -86, -85, 0, 0, -65};
        outerArray.add(Data208);
        Integer[] Data209 = {0, -84, -88, 0, 0, -67};
        outerArray.add(Data209);
        Integer[] Data210 = {0, -84, 0, -87, 0, -65};
        outerArray.add(Data210);
        Integer[] Data211 = {0, -89, -89, 0, 0, -62};
        outerArray.add(Data211);
        Integer[] Data212 = {0, -87, -85, 0, 0, -69};
        outerArray.add(Data212);
        Integer[] Data213 = {0, -85, -85, 0, 0, -69};
        outerArray.add(Data213);
        Integer[] Data214 = {0, -88, -87, 0, 0, -63};
        outerArray.add(Data214);
        Integer[] Data215 = {0, -84, -87, 0, 0, -67};
        outerArray.add(Data215);
        Integer[] Data216 = {0, -89, 0, 0, 0, -74};
        outerArray.add(Data216);
        Integer[] Data217 = {0, -88, -86, 0, 0, -68};
        outerArray.add(Data217);
        Integer[] Data218 = {0, -88, 0, 0, 0, -73};
        outerArray.add(Data218);
        Integer[] Data219 = {0, -87, 0, 0, 0, -62};
        outerArray.add(Data219);
        Integer[] Data220 = {0, -86, 0, 0, 0, -68};
        outerArray.add(Data220);
        Integer[] Data221 = {0, -87, 0, 0, 0, -67};
        outerArray.add(Data221);



        buttonScan = (Button) findViewById(R.id.scanBtn);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //scanWifi();
                mHandler.postDelayed(mtr, 100);
            }
        });


        listView = (ListView) findViewById(R.id.wifiList);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        int i = 0;

        scanWifi();


        //For Log Out
        firebaseAuth = FirebaseAuth.getInstance();

        logout = (Button)findViewById(R.id.btnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
    }

    private void scanWifi() {
        arrayList.clear();

        registerReceiver(wifiReceiver, new IntentFilter(wifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        //Toast.makeText(this, "Scanning Wifi....", Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            results = wifiManager.getScanResults();
            unregisterReceiver(this);


            Count.put("Student's Room", 0);
            Count.put("Dining Room", 0);
            Count.put("Corridor - 1", 0);
            Count.put("Corridor - 2", 0);
            Count.put("Corridor - 3", 0);
            Count.put("Corridor - 4", 0);
            Count.put("Corridor - 5", 0);
            Count.put("Corridor - 6", 0);


            for (ScanResult scanResult : results) {
                if(scanResult.SSID.contentEquals("CSE Office-3")) { Now[0] = scanResult.level; }
                else if(scanResult.SSID.contentEquals("CSE Office-2")) {Now[1] = scanResult.level; }
                else if(scanResult.SSID.contentEquals("CSE Head Office")) { Now[2] = scanResult.level; }
                else if(scanResult.SSID.contentEquals("OSLAB")) {Now[3] = scanResult.level; }
                else if(scanResult.SSID.contentEquals("TP-LINK_80D4")) {Now[4] = scanResult.level; }
                else if(scanResult.SSID.contentEquals("CSE Networklab")) {Now[5] = scanResult.level; }
                //else if(scanResult.SSID.contentEquals("New_Hall")) {Now[4] = scanResult.level; }
                //strength.put(scanResult.SSID, scanResult.level);
                //int p = strength.get(scanResult.SSID);
                //String s = scanResult.SSID + String.valueOf(p);
                //double dis = calculateDistance((double)scanResult.level, (double)scanResult.frequency);
                //String ss = scanResult.SSID;
                //arrayList.add(ss);
                //adapter.notifyDataSetChanged();
            }
            for (int i = 0; i < outerArray.size(); i++) {
                Integer[] data = new Integer[6];
                data = outerArray.get(i);
                double dis = 0.0;
                for(int j = 0; j < data.length; j++) {
                    dis += (Now[j] - data[j]) * (Now[j] - data[j]);
                    //s +=  String.valueOf(data[j]) + " ";
                }
                dis = Math.sqrt(dis);
                DisIndx[i] = dis;
                Dis.add(dis);
            }
            Collections.sort(Dis);

            for(int i = 0; i < 6; i++) {
                double val = Dis.get(i);
                for(int j = 0; j < 198; j++) {
                    if (val == DisIndx[j] && vst[j] == false) {
                        vst[j] = true;

                        if(j < 14) Count.put("Student's Room", Count.get("Student's Room") + 1   );
                        else if(j >= 15 && j < 28) Count.put("Dining Room", Count.get("Dining Room") + 1);
                        else if(j >= 28 && j < 43) Count.put("Corridor - 1", Count.get("Corridor - 1") + 1);
                        else if(j >= 43 && j < 80) Count.put("Corridor - 2", Count.get("Corridor - 2") + 1);
                        else if(j >= 80 && j < 112) Count.put("Corridor - 3", Count.get("Corridor - 3") + 1);
                        else if(j >= 112 && j < 137) Count.put("Corridor - 4", Count.get("Corridor - 4") + 1);
                        else if(j >= 137 && j < 170) Count.put("Corridor - 5", Count.get("Corridor - 5") + 1);
                        else if(j >= 170 && j < 198) Count.put("Corridor - 6", Count.get("Corridor - 6") + 1);
                        else if(j >= 198 && j < 221) Count.put("Ashfak Sir's Room", Count.get("Ashfak Sir's Room") + 1);
                        break;
                    }
                }

            }


            int c = 0, loc = 0;
            for(int i = 0; i < LocationList.size(); i++) {
                if(Count.get(LocationList.get(i)) > c ) {
                    c = Count.get(LocationList.get(i));
                    loc = i;
                }
            }
            for(int i = 0; i <= 0; i++) {
                s = LocationList.get(loc);
                arrayList.add("5th Floor - Dept of CSE, CUET" + "(" + s + ")");
                adapter.notifyDataSetChanged();
            }
            updateLocation();
            /*for(int i = 0; i < 3; i++) {
                //s = String.valueOf(Dis.get(i));
                s = String.valueOf(Count.get(LocationList.get(i)));
                arrayList.add(s);
                adapter.notifyDataSetChanged();
            }*/

            //Clearing
            Dis.clear();
            for(int i = 0; i < 198; i++) {vst[i] = false;}
            for(int i = 0; i < 6; i++) {
                Now[i] = 0;
            }
            for (int i = 0; i < 8; i++) {
                Count.put(LocationList.get(i), 0);
            }


        }

    };

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }

    private  Runnable mtr = new Runnable() {
        @Override
        public void run() {
            scanWifi();
            mHandler.postDelayed(this, 1000);
        }
    };

    private void updateLocation(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
       /* myRef.child("Age").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    myRef.child("Age").setValue(s);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        Loc = "5th Floor - Dept of CSE : CUET(" + s + ")";
        myRef.child("userAge").setValue(Loc);
        //UserProfile userProfile = new UserProfile(age, email, name);
        //myRef.setValue(userProfile);
    }
}
