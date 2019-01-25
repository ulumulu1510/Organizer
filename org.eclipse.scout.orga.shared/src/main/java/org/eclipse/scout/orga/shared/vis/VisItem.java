package org.eclipse.scout.orga.shared.vis;

import java.util.Date;

public class VisItem {

	private Date x;
	private Number y;

	public VisItem(Date x, Number y) {
		this.x = x;
		this.y = y;
	}

	public Date getX() {
		return x;
	}

	public void setX(Date x) {
		this.x = x;
	}

	public Number getY() {
		return y;
	}

	public void setY(Number y) {
		this.y = y;
	}

}
