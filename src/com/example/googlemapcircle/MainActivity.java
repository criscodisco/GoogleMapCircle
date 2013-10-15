package com.example.googlemapcircle;


import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;



public class MainActivity extends MapActivity {
    MapView mapView;
    MapController mc;
    GeoPoint p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mapView = (MapView) findViewById(R.id.mapview);
    LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
    View zoomView = mapView.getZoomControls(); 

    zoomLayout.addView(zoomView, 
        new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, 
            LayoutParams.WRAP_CONTENT)); 
    mapView.displayZoomControls(true);

    mc = mapView.getController();        
    String coordinates[] = {"19.432608","-99.133208"};
    double lat = Double.parseDouble(coordinates[0]);
    double lng = Double.parseDouble(coordinates[1]);

    p = new GeoPoint(
        (int) (lat * 1E6), 
        (int) (lng * 1E6));

    mc.animateTo(p);
    mc.setZoom(7); 
    //---Add a location marker---
    MapOverlay mapOverlay = new MapOverlay();
    List<Overlay> listOfOverlays = mapView.getOverlays();
    listOfOverlays.clear();
    listOfOverlays.add(mapOverlay);        
    mapView.invalidate();
    }

    class MapOverlay extends com.google.android.maps.Overlay
{
    @Override
    public boolean draw(Canvas canvas, MapView mapView, 
    boolean shadow, long when) 
    {
        super.draw(canvas, mapView, shadow);                   

        //---translate the GeoPoint to screen pixels---
        Point screenPts = new Point();
        mapView.getProjection().toPixels(p, screenPts);
        //--------------draw circle----------------------            

        Point pt=mapView.getProjection().toPixels(p,screenPts);

        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(0x30000000);
        circlePaint.setStyle(Style.FILL_AND_STROKE);
        canvas.drawCircle(screenPts.x, screenPts.y, 300, circlePaint);           

        //---add the marker---
        Bitmap bmp = BitmapFactory.decodeResource(
            getResources(), R.drawable.beachflag);            
        canvas.drawBitmap(bmp, screenPts.x, screenPts.y-bmp.getHeight(), null);              
        super.draw(canvas,mapView,shadow);

        return true;

    }
}
@Override
protected boolean isRouteDisplayed() {
    // TODO Auto-generated method stub
    return false;
}
}