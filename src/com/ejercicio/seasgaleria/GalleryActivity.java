package com.ejercicio.seasgaleria;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.ejercicio.util.BitMapUtil;

@SuppressWarnings("deprecation")
public class GalleryActivity extends Activity implements OnTouchListener{
	
	//To identify the log regiter
	private static final String TAG = "Touch";
	
	//Auxiliar matrix to calculate image zoom
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	
	//Image displayed
	ImageView selectedImage;
	
	//Gallery widget
	Gallery gallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		
		selectedImage = (ImageView)findViewById(R.id.image);
		
		selectedImage.setOnTouchListener(this);
		
		final Integer[] images = { R.drawable.burgos, R.drawable.palma, R.drawable.salamanca,R.drawable.segovia, R.drawable.sevilla, R.drawable.toledo};
		
		gallery = (Gallery)findViewById(R.id.gallery);
		
		gallery.setAdapter(new GalleryAdapter(this,images));
		
		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener()
		
		{
			
			
			
		public void onItemClick(AdapterView<?> paretn, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			selectedImage.setImageBitmap(BitMapUtil.decodeSampledBitmapFromResource(getResources(), images[position], 400, 0));
			
			/* ¿Con esto aplicaria un borde a la imagen seleccionada dentro de la galería?*/
			ImageView imageView = (ImageView) gallery.getSelectedView();
			imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_image_border));
        	imageView.setPadding(3, 3, 3, 3);
		}
			
			
			
		});
		
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		ImageView view = (ImageView) v;
		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {

        case MotionEvent.ACTION_DOWN:

            Log.d(TAG, "mode=DRAG" );
            mode = DRAG;
            break;

        case MotionEvent.ACTION_POINTER_DOWN:

            oldDist = spacing(event);
            Log.d(TAG, "oldDist=" + oldDist);
            if (oldDist > 10f) {

                savedMatrix.set(matrix);
                midPoint(mid, event);
                mode = ZOOM;
                Log.d(TAG, "mode=ZOOM" );
            }
            break;

        case MotionEvent.ACTION_MOVE:

            if (mode == DRAG) {

            	mode = NONE;
                Log.d(TAG, "mode=NONE" );
                break;
            }
            else if (mode == ZOOM) {

                float newDist = spacing(event);
                Log.d(TAG, "newDist=" + newDist);
                if (newDist > 10f) {

                    matrix.set(savedMatrix);
                    float scale = newDist / oldDist;
                    matrix.postScale(scale, scale, mid.x, mid.y);
                }
            }
            break;

        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:

            mode = NONE;
            Log.d(TAG, "mode=NONE" );
            break;
        }

        // Perform the transformation
        view.setImageMatrix(matrix);

        return true; // indicate event was handled
    }

	private float spacing(MotionEvent event) {
		
	    float x = event.getX(0) - event.getX(1);
	    float y = event.getY(0) - event.getY(1);
	    return (float) Math.sqrt(x * x + y * y);
	}

    private void midPoint(PointF point, MotionEvent event) {

        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

}

