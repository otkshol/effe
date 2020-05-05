package effe.builder;

import java.util.Objects;

public class NyPizza extends Pizza {
	public enum Size {
		SMALL, MEDIUM, LARGE
	}

	private final Size size;

	public static class Builder extends Pizza.Builder<Builder> {
		private final Size size;

		public Builder(Size size) {
			this.size = Objects.requireNonNull(size);
		}

		// ここでPizzaの子クラスメソッドがPizzaの子クラス型を返すように実装している（共変戻り値片付けと呼ばれている）
		@Override
		public NyPizza build() {
			return new NyPizza(this);
		}

		@Override
		protected Builder self() {
			return this;
		}
	}

	private NyPizza(Builder builder) {
		super(builder);
		size = builder.size;
	}

	public Size getSize() {
		return size;
	}
}
