package com.applibgroup.diffre;


import ohos.agp.components.AttrSet;
import ohos.agp.render.*;
import ohos.agp.components.Component;
import ohos.agp.utils.*;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import org.jetbrains.annotations.Nullable;

/**
 * Works with any background
 * <p>
 * Heavily inspired from Romain Guy's Medium article on "How to underline text":
 * https://medium.com/google-developers/a-better-underline-for-android-90ba3a2e4fb#.hnv0zcm2h
 * <p>
 */
public abstract class DiffreView extends Component implements Component.EstimateSizeListener, Component.DrawTask {

	int width;
	int height;
	float percent = 0.1F;
	Path progressStrokePath = new Path();

	final Path textPath = new Path();
	final Path fullTextPath = new Path();
	final Path croppedProgressPath = new Path();
	final Path croppedTextPath = new Path();

	// Logging parameters
	private static final int DOMAIN = 0x00101;
	private static final String TAG = "DIFFRE";
	protected static final HiLogLabel logLabel = new HiLogLabel(HiLog.LOG_APP, DOMAIN, TAG);


	private static RectFloat rectF = new RectFloat();

	// Temporary Constants, TODO remap to constants file
	final int paintTextSize = 18;
	final int paintTextPadding = 18;
	final int paintRadius = 10;

	private final int COLOR_ORANGE = 0xFFFD9727;
	private final String textString = "16:00 – 16:30";
	private final Paint paint = new Paint();
	private final float radius;
	private final int textPadding;
	private final Rect textBounds = new Rect();


	public DiffreView(Context context) {
		this(context, null);
	}

	public DiffreView(Context context, @Nullable AttrSet attrs) {
		this(context, attrs, 0);
	}

	public DiffreView(Context context, @Nullable AttrSet attrs, int defStyleId) {
		super(context, attrs, defStyleId);


		final int paintTextSize = this.paintTextSize;
		paint.setTextSize(paintTextSize);

		textPadding = paintTextPadding;

		radius = paintRadius;

		initPaint();

		setEstimateSizeListener(this);
		addDrawTask(this);
	}

	public static Path getRoundRectPath(float left, float top, float right, float bottom, float radius) {
		Path roundRectPath = new Path();
		rectF.modify(left, top, right, bottom);
		roundRectPath.addRoundRect(rectF, radius, radius, Path.Direction.CLOCK_WISE);
		return roundRectPath;
	}

	public static void setRectPath(Path path, float left, float top, float right, float bottom) {
		rectF.modify(left, top, right, bottom);
		path.rewind();
		path.addRect(rectF, Path.Direction.CLOCK_WISE);
	}

	private void initPaint()
	{
		paint.setAntiAlias(true);
		paint.setTextAlign(TextAlignment.CENTER);

		paint.setStrokeWidth(2f);
	}

	@Override
	public boolean onEstimateSize(int widthMeasureSpec, int heightMeasureSpec) {
		textBounds.modify(paint.getTextBounds(textString));

		float scale = 5f;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(scale, scale, textBounds.getCenterX(), textBounds.getCenterY());

		final int textWidth = (int) (textBounds.getWidth() * scale);
		final int textHeight = (int) (textBounds.getHeight() * scale);

		width = textWidth + textPadding;
		height = textHeight + textPadding;

		final int cx = width / 2;
		final int cy = (height + textHeight) / 2;

		paint.addTextToPath(textString, cx, cy, textPath);
		paint.addTextToPath(textString, cx, cy, fullTextPath);
		// textPath.transform(scaleMatrix);
		progressStrokePath = getRoundRectPath(0, 0, width, height, radius);

		computePaths();

		// Adding 1 to prevent artifacts
		setEstimatedSize(Component.EstimateSpec.getChildSizeWithMode(width+1, width+1, EstimateSpec.NOT_EXCEED),
				Component.EstimateSpec.getChildSizeWithMode(height+1, height+1, EstimateSpec.NOT_EXCEED));
		HiLog.debug(logLabel, "Estimate Size Called. Set measurements as %{public}d and %{public}d", width, height);
		return true;
	}

	@Override
	public void onDraw(Component component, Canvas canvas) {

		HiLog.debug(logLabel, "Draw Task Called at percent %{public}f.", percent);

		paint.setColor(new Color(COLOR_ORANGE));
		paint.setStyle(Paint.Style.STROKE_STYLE);
		canvas.drawPath(progressStrokePath, paint);

		paint.setStyle(Paint.Style.FILL_STYLE);
		canvas.drawPath(croppedProgressPath, paint);
		canvas.drawPath(croppedTextPath, paint);
	}

	public void setProgress(final float _percent) {
		assert _percent >= 0F && _percent <= 1F;
		percent = _percent;
		computePaths();
		invalidate();
	}

	private void computePaths() {
		computeCroppedProgressPath();
		computeCroppedTextPath();
	}

	public abstract void computeCroppedProgressPath();

	public abstract void computeCroppedTextPath();

}
