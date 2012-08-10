package org.expressme.examples.typecho.entity.constants;

public class Xcontents {
	public static enum Status {
		PUBLIC;
		public String db() {
			return this.name().toLowerCase();
		}
	}

	public static enum Type {
		POST, PAGE;
		public String db() {
			return this.name().toLowerCase();
		}
	}

	public static enum Allow {
		Allow(1), Deny(0);
		public int v;

		private Allow(int v) {
			this.v = v;
		}

		public int db() {
			return this.v;
		}
	}
}
