package nl.fourscouts.blog.validateddomain.domain;

public class BoxEvents {
	public static BoxBought boxBought() {
		return new BoxBought(BOX_ID, SIZE);
	}

	public static ItemsAdded itemsAddedToNewBox(int itemCount) {
		return itemsAdded(itemCount, SIZE - itemCount);
	}

	public static ItemsAdded itemsAdded(int itemCount, int availableRoom) {
		return new ItemsAdded(BOX_ID, itemCount, availableRoom);
	}

	public final static String BOX_ID = "boxId";
	public final static int SIZE = 3;
}
