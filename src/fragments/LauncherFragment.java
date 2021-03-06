package fragments;

import java.util.ArrayList;
import java.util.List;

import adapters.LauncherAdapter;
import adapters.LauncherAdapter.LauncherItem;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.osramd.blue.iron.R;

/**
 ** Some lines may be off a few numbers Just be sure you're in the general area
 **/

public class LauncherFragment extends SherlockFragment {

	GridView gridView;
	final List<LauncherItem> launcherStuff = new ArrayList<LauncherItem>();

	// This is the background layout that gets inflated behind the list view
	public View onCreateView(LayoutInflater inflater, ViewGroup container_launcher,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.launcher_behind, null);
		gridView = (GridView) view.findViewById(R.id.grid);
		return view;
	}

	// Starts when the MainFragment is launched
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		launcherStuff.add(new LauncherItem("Apex", 0));
		launcherStuff.add(new LauncherItem("Nova", 1));
		launcherStuff.add(new LauncherItem("Holo", 2));
		launcherStuff.add(new LauncherItem("ADW", 3));
		launcherStuff.add(new LauncherItem("Action", 4));
		launcherStuff.add(new LauncherItem("Cancel", 5));

		LauncherAdapter adapter = new LauncherAdapter(getActivity(),
				launcherStuff);

		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				final String ACTION_APPLY_ICON_THEME = "com.teslacoilsw.launcher.APPLY_ICON_THEME";
				final String NOVA_PACKAGE = "com.teslacoilsw.launcher";
				final String EXTRA_ICON_THEME_PACKAGE = "com.teslacoilsw.launcher.extra.ICON_THEME_PACKAGE";
				final String EXTRA_ICON_THEME_TYPE = "com.teslacoilsw.launcher.extra.ICON_THEME_TYPE";
				final String ACTION_SET_THEME = "com.anddoes.launcher.SET_THEME";
				final String EXTRA_PACKAGE_NAME = "com.anddoes.launcher.THEME_PACKAGE_NAME";

				@SuppressWarnings("unused")
				MainFragment gridContentT = null;

				switch (position) {
				case 0:
					Intent apex = new Intent(ACTION_SET_THEME);
					apex.putExtra(EXTRA_PACKAGE_NAME, getSherlockActivity().getPackageName());
					apex.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					try {
						startActivity(apex);

						Toast applied = Toast.makeText(getSherlockActivity().getBaseContext(), 
								getResources().getString (R.string.finish_apply),
								Toast.LENGTH_LONG);
						applied.show();
					} catch (ActivityNotFoundException e) {
						Intent apexMarket = new Intent(Intent.ACTION_VIEW);
						apexMarket.setData(Uri
								.parse("market://details?id=com.anddoes.launcher"));
						startActivity(apexMarket);
						Toast failedApex = Toast
								.makeText(
										getSherlockActivity().getBaseContext(),
										getResources().getString (R.string.apex_market),
										Toast.LENGTH_SHORT);
						failedApex.show();
					}

					break;
				case 1:
					Intent nova = new Intent(ACTION_APPLY_ICON_THEME);
					nova.setPackage(NOVA_PACKAGE);
					nova.putExtra(EXTRA_ICON_THEME_TYPE, "GO");
					nova.putExtra(EXTRA_ICON_THEME_PACKAGE,
							"com.osramd.blue.iron");
					try {
						startActivity(nova);
					} catch (ActivityNotFoundException e) {
						Intent novaMarket = new Intent(Intent.ACTION_VIEW);
						novaMarket.setData(Uri
								.parse("market://details?id=com.teslacoilsw.launcher"));
						startActivity(novaMarket);
						Toast failedNova = Toast
								.makeText(
										getSherlockActivity().getBaseContext(),
										getResources().getString (R.string.nova_market),
										Toast.LENGTH_SHORT);
						failedNova.show();
					}
					break;
				case 2:
					Toast failedHolo = Toast.makeText(getSherlockActivity().getBaseContext(),
							getResources().getString (R.string.not_supported),
							Toast.LENGTH_LONG);
					failedHolo.show();
					break;
				case 3:
					Intent adw = new Intent("org.adw.launcher.SET_THEME");
					adw.putExtra("org.adw.launcher.theme.NAME",
							"com.osramd.blue.iron");
					try {
						startActivity(Intent.createChooser(adw,
								"activating theme..."));
					} catch (ActivityNotFoundException e) {						
						Intent adwMarket = new Intent(Intent.ACTION_VIEW);
						adwMarket.setData(Uri
								.parse("market://details?id=org.adw.launcher"));
						startActivity(adwMarket);
						Toast failedADW = Toast
								.makeText(
										getSherlockActivity().getBaseContext(),
										getResources().getString (R.string.adw_market),
										Toast.LENGTH_SHORT);
						failedADW.show();
					} 
					((Activity) getSherlockActivity()).finish();
					break;
				case 4:
					Intent al = getSherlockActivity().getPackageManager().getLaunchIntentForPackage(
							"com.chrislacy.actionlauncher.pro");
					if (al != null) {

						String packageName = "com.osramd.blue.iron";
						al.putExtra("apply_icon_pack", packageName);
						startActivity(al);
					} else {
						Intent alMarket = new Intent(Intent.ACTION_VIEW);
						alMarket.setData(Uri
								.parse("market://details?id=com.chrislacy.actionlauncher.pro"));
						startActivity(alMarket);
						Toast failedAL = Toast
								.makeText(
										getSherlockActivity().getBaseContext(), 
										getResources().getString (R.string.al_market),
										Toast.LENGTH_SHORT);
						failedAL.show();
					}
					break;
				
				/* This is your cancel button
				 * Always leave this as the last item
				 */
				case 5:
					((Activity) getSherlockActivity()).finish();
					break;
				}
			}
		});
	}
}