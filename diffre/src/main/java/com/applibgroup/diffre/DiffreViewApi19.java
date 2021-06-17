package com.applibgroup.diffre;


import ohos.agp.components.AttrSet;
import ohos.agp.render.Path;
import ohos.app.Context;
import org.jetbrains.annotations.Nullable;


/**
 * Works with any background
 * <p>
 * Heavily inspired from Romain Guy's Medium article on "How to underline text":
 * https://medium.com/google-developers/a-better-underline-for-android-90ba3a2e4fb#.hnv0zcm2h
 * <p>
 * Created by rakshakhegde on 16/02/17.
 */
public class DiffreViewApi19 extends DiffreView {

	public DiffreViewApi19(Context context) {
		this(context, null);
	}

	public DiffreViewApi19(Context context, @Nullable AttrSet attrs) {
		this(context, attrs, 0);
	}

	public DiffreViewApi19(Context context, @Nullable AttrSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	final Path progressPath = new Path();

	@Override
	public void computeCroppedProgressPath() {
		setRectPath(progressPath, 0, 0, width * percent, height);
		// NOTE Below code is not implementable without an equivalence for performing Path-based boolean operations
		// TODO Find alternative to Path.op
		// croppedProgressPath.op(progressPath, textPath, Path.Op.DIFFERENCE);
		// croppedProgressPath.op(progressStrokePath, Path.Op.INTERSECT);
	}

	@Override
	public void computeCroppedTextPath() {
		// NOTE Below code is not implementable without an equivalence for performing Path-based boolean operations
		// TODO Find alternative to Path.op
		// croppedTextPath.op(textPath, progressPath, Path.Op.DIFFERENCE);
	}
}
