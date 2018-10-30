package com.zotcomm.zotcrush;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

   BoardView b;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);



    /*
        RelativeLayout relativeLayout_107 = new RelativeLayout(this);
        GridLayout.LayoutParams layout_490 = new GridLayout.LayoutParams();
        layout_490.width = GridLayout.LayoutParams.MATCH_PARENT;
        layout_490.height = GridLayout.LayoutParams.MATCH_PARENT;
        relativeLayout_107.setLayoutParams(layout_490);
*/

       b = new BoardView(this);
       setContentView(b);




    }
}