package com.test.mateflick.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.test.mateflick.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Xtron Labs 05 on 3/25/2016.
 */
public class FragmentDashBoard extends BaseFragment implements  AdapterView.OnItemSelectedListener{

    private Context mContext;
    ArrayList<String> titleData = new ArrayList<String>();
    ArrayList<String> countryData = new ArrayList<String>();
    ArrayList<String> userID = new ArrayList<String>();
    ArrayList<String> eventID = new ArrayList<String>();
    ArrayList<String> eventID2 = new ArrayList<String>();
    ArrayList<String> eventID3 = new ArrayList<String>();
    ArrayList<String> eventID4 = new ArrayList<String>();
    ArrayList<String> manS = new ArrayList<String>();
    ArrayList<String> man1 = new ArrayList<String>();
    ArrayList<String> man2 = new ArrayList<String>();
    ArrayList<String> man3 = new ArrayList<String>();
    ArrayList<String> man4 = new ArrayList<String>();
    ArrayList<String> man5 = new ArrayList<String>();
    ArrayList<String> man6 = new ArrayList<String>();
    int dataLength = 0;
    int dataLength2 = 0;
    int dataLength3 = 0;
    String [] monthBday;
    int maxVal1,maxVal2,maxVal3,maxVal4;

    @BindView(R.id.dashBoardChart)
    CombinedChart dashBoardChart;
    @BindView(R.id.lblDashBoardPopulation)
    TextView lblDashBoardPopulation;
    @BindView(R.id.lblDashBoardUserCount)
    TextView lblDashBoardUserCount;
    @BindView(R.id.lblDashBoardBirthMatesInThis)
    TextView lblDashBoardBirthMatesInThis;
    @BindView(R.id.lblDashBoardBirthMatesCount)
    TextView lblDashBoardBirthMatesCount;
    @BindView(R.id.cboDashBoard)
    AppCompatSpinner cboDashBoard;
    @BindView(R.id.imgToggleNavigation)
    ImageButton imgtog;


    private final int itemcount = 5;

    private Handler mHandler = new Handler();

    public FragmentDashBoard() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("WORLDS TOP BIRTHDAYS");
        spinnerItems.add("TOP BIRTHDAY MONTHS");
        spinnerItems.add("TOP BIRTHDAY YEARS");
        spinnerItems.add("TOP REGISTRATIONS BY COUNTRY");
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, spinnerItems);
        cboDashBoard.setAdapter(spinnerAdapter);
        cboDashBoard.setOnItemSelectedListener(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        GetExample example = new GetExample();
        try {
            example.run();

        } catch (Exception e) {
            e.printStackTrace();
        }

        GetExample3 example3 = new GetExample3();
        try {
          //  example3.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgtog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }/*

    //Default dropdown, This week
    public void updateinfo(){
        String [] titleData1 = titleData.toArray(new String[dataLength]);
        String recpop = titleData1[0];
        String cpop = recpop.substring(0,(recpop.length()-4));
        cpop = cpop.replace(".",",");
        lblDashBoardPopulation.setText(cpop);
        String [] eventData1 = eventID.toArray(new String[dataLength2]);
        lblDashBoardUserCount.setText(String.valueOf(eventData1.length));



        dashBoardChart.setDrawBarShadow(false);
        dashBoardChart.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        dashBoardChart.setDescription("");
        dashBoardChart.getLegend().setEnabled(false);
        dashBoardChart.setBorderColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        dashBoardChart.setDrawGridBackground(false);

        dashBoardChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR
        });

        YAxis rightAxis = dashBoardChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = dashBoardChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(getResources().getColor(R.color.com_facebook_button_text_color));
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = dashBoardChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.com_facebook_button_text_color));

        Calendar c = Calendar.getInstance();
        int month0 = c.get(Calendar.MONTH)+1;
        int day0 = c.get(Calendar.DAY_OF_MONTH);
        DecimalFormat mFormat= new DecimalFormat("00");
        mFormat.setRoundingMode(RoundingMode.DOWN);
        String month = mFormat.format(Double.valueOf(month0));
        String day = mFormat.format(Double.valueOf(day0));
        String monStr = "Jan";
        if(month.equals("01"))monStr="Jan";
        else if(month.equals("02"))monStr="Feb";
        else if(month.equals("03"))monStr="Mar";
        else if(month.equals("04"))monStr="Apr";
        else if(month.equals("05"))monStr="May";
        else if(month.equals("06"))monStr="Jun";
        else if(month.equals("07"))monStr="Jul";
        else if(month.equals("08"))monStr="Aug";
        else if(month.equals("09"))monStr="Sep";
        else if(month.equals("10"))monStr="Oct";
        else if(month.equals("11"))monStr="Nov";
        else if(month.equals("12"))monStr="Dec";

        //Log.e("TODAY IS", day+ " " +month);

        for (int i = 0; i < dataLength2; i++) {
            String tempData = eventData1[i];
            if(tempData!=null) {
                if(tempData.contains("/"+month)){
                    manS.add(tempData);
                    //Log.e("MAN_S IS ", tempData);
                }


            }
        }
        for (int i = 0; i < dataLength2; i++) {
            String tempData = eventData1[i];
            if(tempData!=null) {
                if(tempData.contains("A"+day+"/")){
                    man1.add(tempData);
                    //Log.e("MAN_1 ", tempData);
                }
                else if(tempData.contains("A"+(Integer.parseInt(day)+1)+"/"+month)){
                    man2.add(tempData);
                    //Log.e("MAN_2 ", tempData);
                }
                else if(tempData.contains("A"+(Integer.parseInt(day)+2)+"/"+month)){
                    man3.add(tempData);
                   // Log.e("MAN_3 ", tempData);
                }
                else if(tempData.contains("A"+(Integer.parseInt(day)+3)+"/"+month)){
                    man4.add(tempData);
                    //Log.e("MAN_4 ", tempData);
                }
                else if(tempData.contains("A"+(Integer.parseInt(day)+4)+"/"+month)){
                    man5.add(tempData);
                    //Log.e("MAN_5 ", tempData);
                }
               else if(tempData.contains("A"+(Integer.parseInt(day)+5)+"/"+month)){
                    man6.add(tempData);
                    //Log.e("MAN_6 ", tempData);
                }


            }
        }

        String[] mMonths = new String[]{
                monStr+day, ""+(Integer.parseInt(day)+1), ""+(Integer.parseInt(day)+2), ""+(Integer.parseInt(day)+3),
                ""+(Integer.parseInt(day)+4), ""+(Integer.parseInt(day)+5)//, ""+(Integer.parseInt(day)+6)
        };

        lblDashBoardBirthMatesCount.setText(String.valueOf(manS.size()));
        monthBday = new String[]{String.valueOf(man1.size()),String.valueOf(man2.size()),String.valueOf(man3.size()),
                String.valueOf(man4.size()),String.valueOf(man5.size()),String.valueOf(man6.size())};

        CombinedData data = new CombinedData(mMonths);
        data.setData(generateLineData());
        data.setData(generateBarData());

        dashBoardChart.setData(data);


    }
*/

//Main list - WORLD TOP BDAYS

public void updateinfo(){
    dashBoardChart.clear();
    String [] titleData1 = titleData.toArray(new String[dataLength]);
    String recpop = titleData1[0];
    String cpop = recpop.substring(0,(recpop.length()-4));
    cpop = cpop.replace(".",",");
    lblDashBoardPopulation.setText(cpop);
    String [] eventData1 = eventID.toArray(new String[dataLength2]);
    lblDashBoardUserCount.setText(String.valueOf(eventData1.length));

    Calendar cas = Calendar.getInstance();
    int month0 = cas.get(Calendar.MONTH)+1;
    int day0 = cas.get(Calendar.DAY_OF_MONTH);
    DecimalFormat mFormat= new DecimalFormat("00");
    mFormat.setRoundingMode(RoundingMode.DOWN);
    String month = mFormat.format(Double.valueOf(month0));
    for (int i = 0; i < dataLength2; i++) {
        String tempData = eventData1[i];
        if(tempData!=null) {
            if(tempData.contains("/"+month)){
                man1.add(tempData);
            }
        }
    }
    lblDashBoardBirthMatesCount.setText(String.valueOf(man1.size()));



    dashBoardChart.setDrawBarShadow(false);
    dashBoardChart.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
    dashBoardChart.setDescription("");
    dashBoardChart.getLegend().setEnabled(false);
    dashBoardChart.setBorderColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
    dashBoardChart.setDrawGridBackground(false);

    dashBoardChart.setDrawOrder(new CombinedChart.DrawOrder[]{
            CombinedChart.DrawOrder.BAR
    });

    YAxis rightAxis = dashBoardChart.getAxisRight();
    rightAxis.setDrawGridLines(false);
    rightAxis.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
    rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

    YAxis leftAxis = dashBoardChart.getAxisLeft();
    leftAxis.setDrawGridLines(false);
    leftAxis.setTextColor(getResources().getColor(R.color.com_facebook_button_text_color));
    leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

    XAxis xAxis = dashBoardChart.getXAxis();
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    xAxis.setDrawGridLines(false);
    xAxis.setTextColor(getResources().getColor(R.color.com_facebook_button_text_color));

    ArrayList<String> dateCount = new ArrayList<String>();
    ArrayList<String> dateInc = new ArrayList<String>();
    int max =1;int max2 =1;int max3 =1;int max4 =1;int max5 =1;


    for (int i = 0; i < eventData1.length; i++) {

        int occurrences = Collections.frequency(eventID, eventData1[i]);
        dateCount.add(String.valueOf(occurrences));
      //  Log.e(eventData1[i]," "+occurrences);
    }

    String [] dateCountArr = dateCount.toArray(new String[dateCount.size()]);
    int a = 0, b = 0, c = 0, d = 0, e = 0;

    for (int counter = 1; counter < dateCount.size(); counter++)
    {
        if (Integer.parseInt(dateCountArr[counter]) > max)
        {
            max = Integer.parseInt(dateCountArr[counter]);
            a=counter;
           // maxVal1 =max;

        }
    }
    for (int counter = 1; counter < dateCount.size(); counter++)
    {
        if (Integer.parseInt(dateCountArr[counter]) > max2 && Integer.parseInt(dateCountArr[counter])<max)
        {
            max2 = Integer.parseInt(dateCountArr[counter]);
            b=counter;

        }
    }
    for (int counter = 1; counter < dateCount.size(); counter++)
    {
        if (Integer.parseInt(dateCountArr[counter]) > max3 && Integer.parseInt(dateCountArr[counter])<max2)
        {
            max3 = Integer.parseInt(dateCountArr[counter]);
            c=counter;
        }
    }
    for (int counter = 1; counter < dateCount.size(); counter++)
    {
        if (Integer.parseInt(dateCountArr[counter]) > max4 && Integer.parseInt(dateCountArr[counter])<max3)
        {
            max4 = Integer.parseInt(dateCountArr[counter]);
            d=counter;

        }
    }
    for (int counter = 1; counter < dateCount.size(); counter++)
    {
        if (Integer.parseInt(dateCountArr[counter]) > max5 && Integer.parseInt(dateCountArr[counter])<max4)
        {
            max5 = Integer.parseInt(dateCountArr[counter]);
            e=counter;

        }
    }

    Log.e(a+" "+b,c+" "+d+" "+e);
    manS.add(String.valueOf(max));
    manS.add(String.valueOf(max2));
    manS.add(String.valueOf(max3));
    manS.add(String.valueOf(max4));
    manS.add(String.valueOf(max5));

    String[] mMonths = new String[]{
            eventData1[a].substring(0,eventData1[a].length()-5),eventData1[b].substring(0,eventData1[b].length()-5),
            eventData1[c].substring(0,eventData1[c].length()-5),eventData1[d].substring(0,eventData1[d].length()-5),
            eventData1[e].substring(0,eventData1[e].length()-5)
    };

    CombinedData data = new CombinedData(mMonths);
    data.setData(generateLineData());
    data.setData(generateBarData());
    dashBoardChart.setData(data);

}


    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < itemcount; index++)
            entries.add(new Entry(getRandom(maxVal1, 1), index));

        LineDataSet set = new LineDataSet(entries, "");
        set.disableDashedLine();
        set.setColor(Color.WHITE);
        set.setLineWidth(2.5f);
        set.setFillColor(Color.WHITE);
        set.setDrawCubic(false);
        set.setDrawValues(false);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);

        return d;
    }


    private BarData generateBarData() {

        BarData d = new BarData();

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        String [] ev = manS.toArray(new String[manS.size()]);

        for (int index = 0; index < itemcount; index++)
            entries.add(new BarEntry(Integer.parseInt(ev[index]), index));

        BarDataSet set = new BarDataSet(entries, "");
        set.setColor(Color.WHITE);
        set.setValueTextColor(Color.TRANSPARENT);
        set.setValueTextSize(10f);
        d.addDataSet(set);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return d;
    }


    //Main list - BDAY MONTHS

    public void updateinfo2(){

        dashBoardChart.clear();
        String [] eventData1 = eventID.toArray(new String[dataLength2]);
        for (int i = 0; i < eventData1.length; i++) {
            String [] mnb = eventData1[i].split("/");
            eventID2.add(mnb[1]);
        }
        String [] eventData2 = eventID2.toArray(new String[eventID2.size()]);

        dashBoardChart.setDrawBarShadow(false);
        dashBoardChart.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        dashBoardChart.setDescription("");
        dashBoardChart.getLegend().setEnabled(false);
        dashBoardChart.setBorderColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        dashBoardChart.setDrawGridBackground(false);

        dashBoardChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR
        });

        YAxis rightAxis = dashBoardChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = dashBoardChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(getResources().getColor(R.color.com_facebook_button_text_color));
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = dashBoardChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.com_facebook_button_text_color));

        ArrayList<String> dateCount = new ArrayList<String>();
        ArrayList<String> dateInc = new ArrayList<String>();
        int max =1;int max2 =1;int max3 =1;int max4 =1;int max5 =1;


        for (int i = 0; i < eventData2.length; i++) {

            int occurrences = Collections.frequency(eventID2, eventData2[i]);
            dateCount.add(String.valueOf(occurrences));
            //  Log.e(eventData2[i]," "+occurrences);
        }

        String [] dateCountArr = dateCount.toArray(new String[dateCount.size()]);
        int a = 0, b = 0, c = 0, d = 0, e = 0;

        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max)
            {
                max = Integer.parseInt(dateCountArr[counter]);
                a=counter;
               // maxVal2 = max;

            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max2 && Integer.parseInt(dateCountArr[counter])<max)
            {
                max2 = Integer.parseInt(dateCountArr[counter]);
                b=counter;

            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max3 && Integer.parseInt(dateCountArr[counter])<max2)
            {
                max3 = Integer.parseInt(dateCountArr[counter]);
                c=counter;
            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max4 && Integer.parseInt(dateCountArr[counter])<max3)
            {
                max4 = Integer.parseInt(dateCountArr[counter]);
                d=counter;

            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max5 && Integer.parseInt(dateCountArr[counter])<max4)
            {
                max5 = Integer.parseInt(dateCountArr[counter]);
                e=counter;

            }
        }

        Log.e(a+" "+b,c+" "+d+" "+e);
        man2.add(String.valueOf(max));
        man2.add(String.valueOf(max2));
        man2.add(String.valueOf(max3));
        man2.add(String.valueOf(max4));
        man2.add(String.valueOf(max5));

        String[] mMonths = new String[]{
                eventData2[a],eventData2[b],eventData2[c],eventData2[d],eventData2[e]
        };

        CombinedData data = new CombinedData(mMonths);
        data.setData(generateLineData2());
        data.setData(generateBarData2());
        dashBoardChart.setData(data);

    }


    private LineData generateLineData2() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < itemcount; index++)
            entries.add(new Entry(getRandom(maxVal2, 1), index));

        LineDataSet set = new LineDataSet(entries, "");
        set.disableDashedLine();
        set.setColor(Color.WHITE);
        set.setLineWidth(2.5f);
        set.setFillColor(Color.WHITE);
        set.setDrawCubic(false);
        set.setDrawValues(false);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);

        return d;
    }


    private BarData generateBarData2() {

        BarData d = new BarData();

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        String [] ev = man2.toArray(new String[man2.size()]);

        for (int index = 0; index < itemcount; index++)
            entries.add(new BarEntry(Integer.parseInt(ev[index]), index));

        BarDataSet set = new BarDataSet(entries, "");
        set.setColor(Color.WHITE);
        set.setValueTextColor(Color.TRANSPARENT);
        set.setValueTextSize(10f);
        d.addDataSet(set);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return d;
    }

    //Main list - BDAY COUNTRY

    public void updateinfo3(){

        dashBoardChart.clear();
        String [] eventData1 = eventID.toArray(new String[dataLength2]);
        for (int i = 0; i < eventData1.length; i++) {
            String [] mnb = eventData1[i].split("/");
            eventID3.add(mnb[2]);
        }
        String [] eventData2 = eventID3.toArray(new String[eventID3.size()]);

        dashBoardChart.setDrawBarShadow(false);
        dashBoardChart.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        dashBoardChart.setDescription("");
        dashBoardChart.getLegend().setEnabled(false);
        dashBoardChart.setBorderColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        dashBoardChart.setDrawGridBackground(false);

        dashBoardChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR
        });

        YAxis rightAxis = dashBoardChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = dashBoardChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(getResources().getColor(R.color.com_facebook_button_text_color));
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = dashBoardChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.com_facebook_button_text_color));

        ArrayList<String> dateCount = new ArrayList<String>();
        ArrayList<String> dateInc = new ArrayList<String>();
        int max =1;int max2 =1;int max3 =1;int max4 =1;int max5 =1;


        for (int i = 0; i < eventData2.length; i++) {

            int occurrences = Collections.frequency(eventID3, eventData2[i]);
            dateCount.add(String.valueOf(occurrences));
          //  Log.e(eventData2[i]," "+occurrences);
        }

        String [] dateCountArr = dateCount.toArray(new String[dateCount.size()]);
        int a = 0, b = 0, c = 0, d = 0, e = 0;

        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max)
            {
                max = Integer.parseInt(dateCountArr[counter]);
                a=counter;
                //maxVal3 = max;

            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max2 && Integer.parseInt(dateCountArr[counter])<max)
            {
                max2 = Integer.parseInt(dateCountArr[counter]);
                b=counter;

            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max3 && Integer.parseInt(dateCountArr[counter])<max2)
            {
                max3 = Integer.parseInt(dateCountArr[counter]);
                c=counter;
            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max4 && Integer.parseInt(dateCountArr[counter])<max3)
            {
                max4 = Integer.parseInt(dateCountArr[counter]);
                d=counter;

            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max5 && Integer.parseInt(dateCountArr[counter])<max4)
            {
                max5 = Integer.parseInt(dateCountArr[counter]);
                e=counter;

            }
        }

        Log.e(a+" "+b,c+" "+d+" "+e);
        man3.add(String.valueOf(max));
        man3.add(String.valueOf(max2));
        man3.add(String.valueOf(max3));
        man3.add(String.valueOf(max4));
        man3.add(String.valueOf(max5));

        String[] mMonths = new String[]{
                eventData2[a],eventData2[b],eventData2[c],eventData2[d],eventData2[e]
        };

        CombinedData data = new CombinedData(mMonths);
        data.setData(generateLineData3());
        data.setData(generateBarData3());
        dashBoardChart.setData(data);

    }


    private LineData generateLineData3() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < itemcount; index++)
            entries.add(new Entry(getRandom(maxVal3, 1), index));

        LineDataSet set = new LineDataSet(entries, "");
        set.disableDashedLine();
        set.setColor(Color.WHITE);
        set.setLineWidth(2.5f);
        set.setFillColor(Color.WHITE);
        set.setDrawCubic(false);
        set.setDrawValues(false);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);

        return d;
    }


    private BarData generateBarData3() {

        BarData d = new BarData();

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        String [] ev = man3.toArray(new String[man3.size()]);

        for (int index = 0; index < itemcount; index++)
            entries.add(new BarEntry(Integer.parseInt(ev[index]), index));

        BarDataSet set = new BarDataSet(entries, "");
        set.setColor(Color.WHITE);
        set.setValueTextColor(Color.TRANSPARENT);
        set.setValueTextSize(10f);
        d.addDataSet(set);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return d;
    }


    //Main list - BDAY YEAR

    public void updateinfo4(){

        dashBoardChart.clear();
        String [] eventData2 = countryData.toArray(new String[countryData.size()]);

        dashBoardChart.setDrawBarShadow(false);
        dashBoardChart.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        dashBoardChart.setDescription("");
        dashBoardChart.getLegend().setEnabled(false);
        dashBoardChart.setBorderColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        dashBoardChart.setDrawGridBackground(false);

        dashBoardChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR
        });

        YAxis rightAxis = dashBoardChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = dashBoardChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(getResources().getColor(R.color.com_facebook_button_text_color));
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = dashBoardChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.com_facebook_button_text_color));

        ArrayList<String> dateCount = new ArrayList<String>();
        ArrayList<String> dateInc = new ArrayList<String>();
        int max =1;int max2 =1;int max3 =1;int max4 =1;int max5 =1;


        for (int i = 0; i < eventData2.length; i++) {

            int occurrences = Collections.frequency(countryData, eventData2[i]);
            dateCount.add(String.valueOf(occurrences));
            //  Log.e(eventData2[i]," "+occurrences);
        }

        String [] dateCountArr = dateCount.toArray(new String[dateCount.size()]);
        int a = 0, b = 0, c = 0, d = 0, e = 0;

        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max)
            {
                max = Integer.parseInt(dateCountArr[counter]);
                a=counter;
                //maxVal3 = max;

            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max2 && Integer.parseInt(dateCountArr[counter])<max)
            {
                max2 = Integer.parseInt(dateCountArr[counter]);
                b=counter;

            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max3 && Integer.parseInt(dateCountArr[counter])<max2)
            {
                max3 = Integer.parseInt(dateCountArr[counter]);
                c=counter;
            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max4 && Integer.parseInt(dateCountArr[counter])<max3)
            {
                max4 = Integer.parseInt(dateCountArr[counter]);
                d=counter;

            }
        }
        for (int counter = 1; counter < dateCount.size(); counter++)
        {
            if (Integer.parseInt(dateCountArr[counter]) > max5 && Integer.parseInt(dateCountArr[counter])<max4)
            {
                max5 = Integer.parseInt(dateCountArr[counter]);
                e=counter;

            }
        }

        Log.e(a+" "+b,c+" "+d+" "+e);
        man4.add(String.valueOf(max));
        man4.add(String.valueOf(max2));
        man4.add(String.valueOf(max3));
        man4.add(String.valueOf(max4));
        man4.add(String.valueOf(max5));

        String[] mMonths = new String[]{
                eventData2[a].substring(0, Math.min(eventData2[a].length(), 3)),
                eventData2[b].substring(0, Math.min(eventData2[b].length(), 3)),
                eventData2[c].substring(0, Math.min(eventData2[c].length(), 3)),
                eventData2[d].substring(0, Math.min(eventData2[d].length(), 3)),
                eventData2[e].substring(0, Math.min(eventData2[e].length(), 3))
        };

        CombinedData data = new CombinedData(mMonths);
        data.setData(generateLineData4());
        data.setData(generateBarData4());
        dashBoardChart.setData(data);

    }


    private LineData generateLineData4() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < itemcount; index++)
            entries.add(new Entry(getRandom(maxVal3, 1), index));

        LineDataSet set = new LineDataSet(entries, "");
        set.disableDashedLine();
        set.setColor(Color.WHITE);
        set.setLineWidth(2.5f);
        set.setFillColor(Color.WHITE);
        set.setDrawCubic(false);
        set.setDrawValues(false);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);

        return d;
    }


    private BarData generateBarData4() {

        BarData d = new BarData();

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        String [] ev = man4.toArray(new String[man4.size()]);

        for (int index = 0; index < itemcount; index++)
            entries.add(new BarEntry(Integer.parseInt(ev[index]), index));

        BarDataSet set = new BarDataSet(entries, "");
        set.setColor(Color.WHITE);
        set.setValueTextColor(Color.TRANSPARENT);
        set.setValueTextSize(10f);
        d.addDataSet(set);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return d;
    }




    private float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            if(position==0){
                if (dataLength2!=0){
                updateinfo();
                System.out.println("00");}}

            else if(position==1){
                updateinfo2();
                System.out.println("01");}

            else if(position==2){
                updateinfo3();
                System.out.println("02");}

            else if(position==3){
                updateinfo4();
                System.out.println("03");}



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public class GetExample {
        private final OkHttpClient client = new OkHttpClient();


        public void run() throws Exception {
            Request request = new Request.Builder()
                    .url("http://realtimestatistics.net/rts/api/json.php?cid=nigeria&ckey=trw6871")
                    .header("Connection","close")
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    String jsonData = response.body().string();
                    try {
                        JSONObject Jobject = new JSONObject(jsonData);
                        JSONArray Jarray = Jobject.getJSONArray("current_population");

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                            if(object!=null) {
                                titleData.add((object.get("vs")).toString());
                                System.out.println((object.get("vs")).toString());
                                dataLength = Jarray.length();

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        GetExample example = new GetExample();
                        try {
                            example.run();

                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    GetExample2 example2 = new GetExample2();
                    try {
                        example2.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
    public class GetExample2 {
        private final OkHttpClient client = new OkHttpClient();


        public void run() throws Exception {
            Request request = new Request.Builder()
                    .url("http://bdc.mod.bz/user_sort_by_distance/57d439b62009985a6ce7e1c3/0/0")
                    .header("Connection","close")
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    String jsonData = response.body().string();
                    try {
                        JSONArray Jarray = new JSONArray(jsonData);

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                           // System.out.println("A"+object.get("dob"));
                            dataLength2 = Jarray.length();
                            if(object!=null) {
                                eventID.add((object.get("dob")).toString());
                                countryData.add((object.get("country")).toString());
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        GetExample2 example2 = new GetExample2();
                        try {
                            example2.run();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateinfo();
                        }
                    });

                }
            });
        }

    }
    public class GetExample3 {
        private final OkHttpClient client = new OkHttpClient();


        public void run() throws Exception {
            Request request = new Request.Builder()
                    .url("")
                    .header("Connection","close")
                    //57bed92bde2b1af100346acc
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    String jsonData = response.body().string();
                    try {
                        JSONObject Jobject = new JSONObject(jsonData);
                        JSONArray Jarray = Jobject.getJSONArray("Events");

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                            System.out.println(object.get("name"));
                            dataLength3 = Jarray.length();
                            if(object!=null) {
                                userID.add((object.get("user_id")).toString());
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }

}
