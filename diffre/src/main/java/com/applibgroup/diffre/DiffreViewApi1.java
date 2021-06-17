package com.applibgroup.diffre;


import ohos.agp.components.AttrSet;
import ohos.agp.render.Path;
import ohos.agp.render.Region;
import ohos.agp.utils.Rect;
import ohos.agp.utils.RectFloat;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import org.jetbrains.annotations.Nullable;

/**
 * Works with any background. No anti-aliasing for this approach.
 * <p>
 * Heavily inspired from Romain Guy's Medium article on "How to underline text":
 * https://medium.com/google-developers/a-better-underline-for-android-90ba3a2e4fb#.hnv0zcm2h
 * <p>
 * Created by rakshakhegde on 16/02/17.
 */
public class DiffreViewApi1 extends DiffreView {

	final Region textRegion = new Region();
	final Region progressRegion = new Region();
	final Region region = new Region();

	public DiffreViewApi1(Context context) {
		this(context, null);
	}
	public DiffreViewApi1(Context context, @Nullable AttrSet attrs) {
		this(context, attrs, 0);
	}
	public DiffreViewApi1(Context context, @Nullable AttrSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void computeCroppedProgressPath() {

		region.setRect(new Rect(0,0,(int)(width*percent),height));

		// NOTE Below operations are not working as expected
		boolean result = progressRegion.setPath(progressStrokePath, region); // INTERSECT
		textRegion.setPath(textPath, region);

		progressRegion.op(textRegion, Region.Op.DIFFERENCE); // DIFFERENCE

		croppedProgressPath.rewind();

		progressRegion.getBoundaryPath(croppedProgressPath);
		HiLog.debug(logLabel, "progressRegion setPath called. Was successful? %{public}s", result);
	}

	@Override
	public void computeCroppedTextPath() {
		region.setRect(new Rect(0,0, (int) (width * percent), height));

		// NOTE Below operation is not working as expected
		textRegion.setPath(textPath, region); // INTERSECT

		croppedTextPath.rewind();
		boolean result = textRegion.getBoundaryPath(croppedTextPath);
		HiLog.debug(logLabel, "textRegion getBoundaryPath called. Was successful? %{public}s", result);
	}
}
