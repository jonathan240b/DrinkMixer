package com.example.jonathan.drinkmixer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static String[] name;
    private static int[] order;
    private static double[] price;

    public final static String EXTRA_MESSAGE = "com.TrollTek.DrinkMixer.MESSAGE";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = new String[4];
        order = new int[4];
        price = new double[4];
        setPrice();
        setName();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MenuFragment.newInstance(position + 1))
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, cartFragment.newInstance(position + 1))
                        .commit();
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class cartFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static cartFragment newInstance(int sectionNumber) {
            cartFragment fragment = new cartFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public cartFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.cart, container, false);

            for (int i = 0; i < order.length; i++)
                if (order[i] != 0)
                    addItemsToCart(rootView, i);

            updatePrice(rootView);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);


            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        /** Adds selected items to cart */
        public void addItemsToCart(View rootView, int id) {
            TableLayout table=(TableLayout)rootView.findViewById(R.id.cart_list);
            TableRow row = new TableRow(rootView.getContext());
            TextView textView_n = new TextView(rootView.getContext());
            TextView textView_q = new TextView(rootView.getContext());
            TextView textView_p = new TextView(rootView.getContext());

            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.setId(id);

            //Name
            textView_n.setText(name[id]);
            textView_n.setGravity(1);
            row.addView(textView_n);

            //Quantity
            textView_q.setText(Integer.toString(order[id]));
            textView_q.setGravity(1);
            row.addView(textView_q);

            //Price
            textView_p.setText(String.format("$ %.2f", price[id]*order[id]));
            textView_p.setGravity(1);
            row.addView(textView_p);

            table.addView(row);
        }

        /** Update Total Price */
        public void updatePrice(View rootView) {
            double total = 0;
            for (int i = 0; i < order.length; i++)
                total += order[i] * price[i];

            TextView textView = (TextView) rootView.findViewById(R.id.cart_price);
                textView.setText(String.format("$ %.2f", total));
        }
    }

    public static class MenuFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static MenuFragment newInstance(int sectionNumber) {
            MenuFragment fragment = new MenuFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public MenuFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.drink_menu, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    /** starts transmitting via nfc */
    public void testNfc(View view) {

        Intent intent = new Intent(this, BeamData.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /** Send Drink Order to NFC */
    public void sendToNfc(View view) {
        char drink = 'A';
        Intent intent = new Intent(this, BeamData.class);
        String message = "TrollTek";
        for (int i = 0; i < this.order.length; i++)
            message += " " + (drink+i) + this.order[i];
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /** Respond to add to cart */
    public void addToCart(View view) {
        switch (view.getId()) {
            case R.id.drink1:
                this.order[0]++;
                updateQuantity(R.id.drink1_num, 0);
                Toast.makeText(this, "Arnold Palmer added to cart", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drink2:
                this.order[1]++;
                updateQuantity(R.id.drink2_num, 1);
                Toast.makeText(this, "Whiskey added to cart", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drink3:
                this.order[2]++;
                updateQuantity(R.id.drink3_num, 2);
                Toast.makeText(this, "Tequila added to cart", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drink4:
                this.order[3]++;
                updateQuantity(R.id.drink4_num, 3);
                Toast.makeText(this, "Rum added to cart", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Undefined Add", Toast.LENGTH_SHORT).show();
        }
    }

    /** Respond to remove from cart */
    public void removeFromCart(View view) {
        switch (view.getId()) {
            case R.id.drink1_rm:
                if (this.order[0] != 0) {
                    this.order[0]--;
                    updateQuantity(R.id.drink1_num, 0);
                    Toast.makeText(this, "Arnold Palmer removed from cart", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.drink2_rm:
                if (this.order[1] != 0) {
                    this.order[1]--;
                    updateQuantity(R.id.drink2_num, 1);
                    Toast.makeText(this, "Whiskey removed from cart", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.drink3_rm:
                if (this.order[2] != 0) {
                    this.order[2]--;
                    updateQuantity(R.id.drink3_num, 2);
                    Toast.makeText(this, "Tequila removed from cart", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.drink4_rm:
                if (this.order[3] != 0) {
                    this.order[3]--;
                    updateQuantity(R.id.drink4_num, 3);
                    Toast.makeText(this, "Rum removed from cart", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, "Undefined Removal", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateQuantity(int id, int drink) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(Integer.toString(this.order[drink]));
    }
    /** Set price, Hardcoded for now*/
    public void setPrice() {
        this.price[0] = 2.99;
        this.price[1] = 3.99;
        this.price[2] = 4.99;
        this.price[3] = 5.99;
    }

    /** Set name, Hardcoded for now*/
    public void setName() {
        this.name[0] = "Arnold Palmer";
        this.name[1] = "Whiskey";
        this.name[2] = "Tequila";
        this.name[3] = "Rum";
    }
}
