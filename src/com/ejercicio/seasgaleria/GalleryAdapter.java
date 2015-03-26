/**
 * 
 */
package com.ejercicio.seasgaleria;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ejercicio.util.BitMapUtil;

/**
 * @author operador
 *
 */
public class GalleryAdapter extends BaseAdapter {

	Context context;
	Integer[] images;
	int background;
	private SparseArray<Bitmap> scalatedImages = new SparseArray<Bitmap>();
	
	public GalleryAdapter(Context context, Integer[] images) {
		
		super();
        this.images = images;
        this.context = context;
		
		TypedArray typedArray = context.obtainStyledAttributes(R.styleable.PicGallery);
		background = typedArray.getResourceId(R.styleable.PicGallery_android_galleryItemBackground, 0);
	    typedArray.recycle();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imagen = new ImageView(context);
		if (scalatedImages.get(position)==null){
			
			Bitmap bitmap = BitMapUtil.decodeSampledBitmapFromResource(context.getResources(), images[position], 45, 0);
			//bitmap.reconfigure(context.getResources()., height, config);
			scalatedImages.put(position, bitmap);			
		}
		
		imagen.setImageBitmap(scalatedImages.get(position));
		imagen.setBackgroundColor(background);
		//imagen.setScaleType(scaleType)
		return imagen;
	}

}
