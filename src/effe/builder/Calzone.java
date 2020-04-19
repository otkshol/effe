package effe.builder;

public class Calzone extends Pizza {
    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder> {
        private boolean sauceInside = false;

        public Builder sauceInside(){
            sauceInside = true;
            return this;
        }

        @Override public Calzone build() {
            return new Calzone(this);
        }

        @Override protected Builder self() {
            return this;
        }
    }

    private Calzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }

    public static void main(String[] args) {
        //  Toppingのenumをstatic importするとTopping.を省略できる
        Calzone calzone = new Builder().addTopping(Topping.HAM).sauceInside().build();

        System.out.println("toppings: " + calzone.toppings);
        System.out.println("sauceInside: " + calzone.sauceInside);
    }
}
