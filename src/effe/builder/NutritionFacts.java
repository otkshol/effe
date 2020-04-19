package effe.builder;

// ビルダーパターン
public class NutritionFacts {
    // テレスコーピング・コンストラクタ・パターンのメリットをここで享受（finalで不変性の1条件を満たしている）
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder {
        // 必須パラメータ
        private final int servingSize;
        private final int servings;
        // オプションパラメータ
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }
        public Builder fat(int val) {
            fat = val;
            return this;
        }
        public Builder sodium(int val) {
            sodium = val;
            return this;
        }
        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    // コンストラクタ
    private NutritionFacts(Builder builder){
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
                .calories(100).sodium(35).carbohydrate(27).build();

        System.out.println("servingSize: " + cocaCola.servingSize);
        System.out.println("servings: " + cocaCola.servings);
        System.out.println("calories: " + cocaCola.calories);
        System.out.println("sodium: " + cocaCola.sodium);
        System.out.println("carbohydrate: " + cocaCola.carbohydrate);
    }
}
