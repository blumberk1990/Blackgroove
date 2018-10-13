package pl.mgd.blackgroove.data;

import java.util.NoSuchElementException;
import java.util.Arrays;


public enum VinylSize {
	EMPTY(0, "-"),
	LP(12, "12\""),
	EP(10, "10\""),
	SINGLE(7, "7\"");
	
	private int size;
	private String description;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	VinylSize(int size, String desc) {
		this.setSize(size);
		this.setDescription(desc);
	}
	
	public static VinylSize createVinylSizeFromInt(int size) throws NoSuchElementException {
		VinylSize vinylSize = null;
		try {
		vinylSize = VinylSize.values()[size];
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new NoSuchElementException("Opcja nie dostpna w programie.");
		}
		return vinylSize;
	}
	
	public static VinylSize createVinylSizeFromString(String text) {
		return Arrays.stream(VinylSize.values())
		          .filter(bl -> bl.getDescription().equals(text))
		          .findFirst()
		          .orElse(null);
	}
	
	@Override
	public String toString() {
		return getDescription();
	}

}
