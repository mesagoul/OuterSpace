package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.fragments.reports.FragmentReportsDetail;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.Report;

/**
 * Created by Lucas on 25/03/2017.
 */

public class ReportDetailActivity extends AppCompatActivity {
    private Gson gson;
    private TextView report_from_username;
    private TextView report_to_username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        gson = new Gson();

        report_from_username = (TextView) findViewById(R.id.report_from_username);
        report_to_username = (TextView) findViewById(R.id.report_to_username);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Report aReport = gson.fromJson(getIntent().getStringExtra("report"), Report.class);

        FragmentReportsDetail fragment_general_detail = (FragmentReportsDetail)getSupportFragmentManager().findFragmentById(R.id.fragment_report_detail);
        if ( aReport != null){
            fragment_general_detail.setReport(aReport);
            report_from_username.setText(aReport.getFrom());
            report_to_username.setText(aReport.getTo());
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
