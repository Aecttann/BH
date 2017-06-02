package com.aectann.bh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

//
//        result = new DrawerBuilder()
//                .withActivity(this)
//                .withActionBarDrawerToggle(true)
//                .withHeader(R.layout.drawer_header)
//                .addDrawerItems(
//                        item1,
//                        item2,
//                        item3,
//                        item4,
//                        new DividerDrawerItem(),
//                        item5
//                )
//
//                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                        // do something with the clicked item :D
//                        if (item1.isSelected()){
//                            tvchoose.setVisibility(View.VISIBLE);
//                            //                           et1.setVisibility(1);
////                            spinner.setVisibility(1);
////                            Main.converter();
//                            result.closeDrawer();
//                        }
//                        if (item2.isSelected()){
//                            tvchoose.setVisibility(View.GONE);
////                            et1.setVisibility(0);
////                            spinner.setVisibility(0);
//                            result.closeDrawer();
//                        }
//                        if (item4.isSelected()){
//                            Intent intentHelp = new Intent(Help.this, Main.class);
//                            startActivity(intentHelp);
//                            result.closeDrawer();
////                            setContentView(R.layout.help);
//                        }
//                        if (item5.isSelected()){
//                            Intent intentContacts = new Intent(Help.this, Contacts.class);
//                            startActivity(intentContacts);
//                            result.closeDrawer();
////                            setContentView(R.layout.help);
//                        }
//                        return true;
//                    }
//                })
//                .build();
    }
}