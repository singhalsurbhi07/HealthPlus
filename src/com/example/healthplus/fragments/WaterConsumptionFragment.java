package com.example.healthplus.fragments;

import io.oauth.OAuthData;

import io.oauth.OAuthRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONObject;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.healthplus.MainActivity;
import com.example.healthplus.R;
import com.example.healthplus.datamodels.WaterConsumeModel;
import com.example.healthplus.oauth.SerializableOauthData;
import com.example.healthplus.utils.DateUtil;

public class WaterConsumptionFragment extends Fragment {
	OAuthData data;
	View view;
//	GraphicalView gv;
//    RelativeLayout rl;
    WaterConsumeModel waterComsumpt;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      view = inflater.inflate(R.layout.activity_water_consumption_fragment, container, false);
      getUserWaterData();
      float consumption = Float.parseFloat(waterComsumpt.getTotal_consumption());
      float remaining = Float.parseFloat(waterComsumpt.getRemAmount());
      PieChart mPieChart = (PieChart) view.findViewById(R.id.piechart);

      mPieChart.addPieSlice(new PieModel("Consumed", consumption, Color.parseColor("#FE6DA8")));
      mPieChart.addPieSlice(new PieModel("Remaining", remaining, Color.parseColor("#56B7F1")));
      //mPieChart.addPieSlice(new PieModel("Work", 35, Color.parseColor("#CDA67F")));
      //mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));

      mPieChart.startAnimation();


      //gv = createIntent(values1);

//      rl = (RelativeLayout) view.findViewById(R.id.rid);
//      rl.addView(gv);    
      return view;
    }
	
	public void getUserWaterData(){
		data = SerializableOauthData.getOauthData();
		data.http("/1/user/-/foods/log/water/date/"+DateUtil.getTodaysDate()+".json", new OAuthRequest() {
			private URL url;
			private URLConnection con;
			@Override
			public void onSetURL(String _url) {
				// This method is called once the final url is returned.
				System.out.println("_url-->"+_url);
				try {
					url = new URL(_url);
					con = url.openConnection();
				} catch (Exception e) { e.printStackTrace(); } 
			}

			@Override
			public void onSetHeader(String header, String value) {
				// This method is called for each header to add to the request.
				con.addRequestProperty(header, value);
			}

			@Override
			public void onReady() {
				// This method is called once url and headers are set.
				
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder total = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						total.append(line);
					}
					System.out.println(total.toString());
					JSONObject result = new JSONObject(total.toString());
					waterComsumpt  = new WaterConsumeModel(result);
					
					
					
					
				} catch (Exception e) { e.printStackTrace(); }
			}

			@Override
			public void onError(String message) {
				// This method is called if an error occured
				System.out.println("error-->>"+message);
			}
		});
	}
	
//	public GraphicalView createIntent(List<double[]> values1) {
//
//        List<String[]> titles = new ArrayList<String[]>();
//
//        titles.add(new String[] { " ", " " });
//        int[] colors = new int[] { Color.BLUE, Color.GREEN };
//
//        DefaultRenderer renderer = buildCategoryRenderer(colors);
//        renderer.setApplyBackgroundColor(true);
//        renderer.setShowLegend(false);
//
//        renderer.setShowLabels(false);
//        renderer.setStartAngle(270);
//        renderer.setBackgroundColor(Color.rgb(222, 222, 200));
//        renderer.setLabelsColor(Color.GRAY);
//
//
//
//        return ChartFactory.getDoughnutChartView(getActivity(),
//                buildMultipleCategoryDataset("Project budget", titles, values1),
//                renderer);
//    }
//
//    protected MultipleCategorySeries buildMultipleCategoryDataset(String title,
//            List<String[]> titles, List<double[]> values) {
//        MultipleCategorySeries series = new MultipleCategorySeries(title);
//        int k = 0;
//        for (double[] value : values) {
//            series.add(2007 + k + "", titles.get(k), value);
//            k++;
//        }
//        return series;
//    }
//
//    protected DefaultRenderer buildCategoryRenderer(int[] colors) {
//        DefaultRenderer renderer = new DefaultRenderer();
//        renderer.setLabelsTextSize(15);
//        renderer.setLegendTextSize(15);
//        renderer.setMargins(new int[] { 20, 30, 15, 0 });
//        for (int color : colors) {
//            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
//            r.setColor(color);
//            renderer.addSeriesRenderer(r);
//        }
//        return renderer;
//    }
}
