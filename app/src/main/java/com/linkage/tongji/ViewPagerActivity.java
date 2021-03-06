/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.linkage.tongji;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.linkage.photoView.PhotoView;
import com.squareup.picasso.Picasso;



public class ViewPagerActivity extends BaseActivity {



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

		setSwipeBackEnable(false);
		ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);

		viewPager.setAdapter(new SamplePagerAdapter(ViewPagerActivity.this));
	}

	static class SamplePagerAdapter extends PagerAdapter {

		private Context m_context;
		public  SamplePagerAdapter(Context context)
		{
			 m_context = context;
		}

		private static final int[] sDrawables = { R.drawable.ic_icon,R.drawable.ic_icon};

		@Override
		public int getCount() {
			return sDrawables.length;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
//			photoView.setImageResource(sDrawables[position]);

			Picasso.with(m_context)
					.load("http://www.e-works.net.cn/fileupload/articleimage/201011/129349503810468750_new.jpg")
					.into(photoView);
			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}
}
