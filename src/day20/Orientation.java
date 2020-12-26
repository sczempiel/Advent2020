package day20;

public enum Orientation {

	NORMAL(false, 0), NORM_ROTA_90(false, 1), NORM_ROTA_180(false, 2), NORM_ROTA_270(false, 3),

	FLIP_NORM(true, 0), FLIP_ROTA_90(true, 1), FLIP_ROTA_180(true, 2), FLIP_ROTA_270(true, 3);

	private final boolean flip;
	private final int rotations;

	private Orientation(boolean flip, int rotations) {
		this.flip = flip;
		this.rotations = rotations;
	}

	public boolean isFlip() {
		return this.flip;
	}

	public int getRotations() {
		return this.rotations;
	}

}