# 多くのコンストラクタパラメータに直面したときにはビルダーを検討する

## staticファクトリメソッドとコンストラクタの課題
**大量のオプションパラメータにうまく対応できない**

- ２つの伝統的な対処方法
     - テレスコーピング・コンストラクタ
     - JavaBeansパターン

---
### テレスコーピング・コンストラクタ
- 内容
必須パラメータを受け取るコンストラクタを作成して、そこから１つのオプションパラメータを受け取るコンストラクタを作成して、
さらにそこから１つのオプションパラメータを受け取るコンストラクタを作成して...を繰り返す。
    - 呼び出し元の例
``NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0, 35, 27);``

- メリット
    - フィールドを`private final`で宣言しているので、5つある[不変性](https://github.com/otkshol/effe/blob/master/effe/Chapter08/Item50.md)の要件を２つ満たすので、不変性が高まる。
- デメリット
    - 変数が多い場合、クライアントのコードの可読性が落ちる
    - 引数それぞれの数字の意味が分からないので可読性が低い
    - コンパイルエラーにならず実行時エラーとなる（この本を通して避けるべきパターンとしてかかれている！）
        - `NutritionFacts`の例だと、`(240, 8, 100, 0, 35, 27)`の引数の数字が表す意味が呼び出し側だけではわからず、`fat`(脂質)を`0`とするつもりが、**引数の順番を間違って**`calories`(カロリー)を`0`としてしまうケース。しかも変数の型は`int`で揃っているので、コンパイラは検知してくれずバグとなる。

---
### JavaBeansパターン
- 呼び出し元の例
```
NutritionFacts cocaCola = new NutritionFacts();
cocaCola.setServingSize(240);
cocaCola.setServing(8);
cocaCola.setCalories(100);
cocaCola.setSodium(35);
cocaCola.setCarbohydrate(27);
```

- メリット
    - テレスコーピング・コンストラクタの欠点を解消している。
        - 変数が多くても引数が長くならないので可読性が落ちない
        - `cocaCola.setCalories(100)`のように呼び出し側で何の変数のどんな値をいれるか分かるので、実装者が誤って違う変数に値を代入する可能性が下がる。
    
- デメリット
    - 不変性が担保されなくなる(後からsetterで値を書き換えることができてしまう)

---
### Builderパターン
**テレスコーピング・コンストラクタとJavaBeansの良いとこ取りをしている！**
- フィールドの不変性が担保されるかつクライアント側のコードの可読性もあがる。
```
// ビルダーパターン
public class NutritionFacts {
    // テレスコーピング・コンストラクタ・パターンのメリットをここで享受（finalで不変性の2要件を満たしている）
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
            // ここで呼び出し元に返すことで流れるAPI（fluent API）を実現している(他の変数のreturn thisも同様)
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
        public Builder carhohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    // コンストラクタ (privateコンストラクタにすることでnew時点でのインスタンス生成を防いで、呼び出し側でbuildメソッドが実行されたときにインスタンス生成がされるようにしている)
    private NutritionFacts(Builder builder){
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }
}

```

- 呼び出し側は流れるAPI（fluent API）で書くことができる
```
NutritionFacts cocaCola = new NutritionFacts.Builder(240,8)
                .calories(100).sodium(35).carhohydrate(27).build();
```

- クラス階層にも適用可能
    -  例:  Pizza
    - Pizza.java, NyPizza.java, Calzone.java参照
    - サブクラスのメソッド（例でいうとNyPizzaとCalzoneのbuildメソッド）がスーパークラスで宣言された戻り値型のサブタイプを返す技法を**共変戻り値型付け**という
    
## Builderの長所
- 複数の可変長パラメータを持てる（例だとenum Toppingの中身に相当）
- 複数オブジェクトを構築するのに、１つのビルダーを使い回すことができる

## Builderの短所
- クラス宣言側(effeでいう`NutritionFacts`クラス)のコードが長くなってしまう
- インスタンス生成のコストが高くなる
## まとめ
- 引数が少ない場合はBuilderパターンはテレスコーピング・コンストラクタよりもコードが長くなるかつインスタンス生成コストがかかるが、
拡張性（今後引数を増やしたときの対応のしやすさ）を考えるとBuilderパターンが望ましい。


