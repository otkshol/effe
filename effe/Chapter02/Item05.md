# 資源を直接結びつけるよりも依存性注入を選ぶ

スペルチェッカーが辞書に依存している例を考える。

- ２つのBad事例
    - staticなutilクラスとして実装
    - シングルトンとして実装
- Badな理由   
    - 使う辞書が１つしかないため、テストできない

- 直感的に思いつく対策: finalをなくしてしまえば良さそう?
これも良くない。理由は不変性が担保されず、マルチスレッドで機能しないので実行時エラーが発生しやすくなるから（できるだけコンパイラーで検知するべき思想）

## どうすれば良いのか？
**インスタンス生成時にコンストラクタに資源を渡す！依存性注入（dependency injection)と呼ばれる**

```
public class SpellChecker {
    // この行でSpellChekerの不変性が強まっている（dictionaryではない！）
    private final Lexicon dictionary;
    
    // ここで引数にdictionaryを入れることで依存性注入をしている
    public SpellCheker(Lexicon dictionary){
        this.dictionary = Objects.requireNonNull(dictionary);
    }
    
    public boolean isValid(Sting word) {...}
    public List<Sting> suggestions(String typo) {...}
}
```
上記パターンは任意の個数の資源と任意の依存性グラフをか使うことができる。
TODO 任意の依存性グラフの意味、具体例を調べる。
---

依存性注入パターンでは不変性が高めるので、（不変性があると同値ではない！）
複数クライアントが依存先のオブジェクトを共有できる。

---

有用な変形は、コンストラクタに資源ファクトリを渡すもの。
境界ワイルドカード型（bounded wildcard type）を用いた`Supplier<T>`メソッドの例（これはFactory Methodパターンを具体化している）

TODO 
Factory Methodパターンを理解する

```
Mosaic create(Supplier<? extends Tile> tileFactory) {...}
```

まとめ

クラス実装するときには引数に使用する資源を渡して、依存性注入パターンになるようにするのが望ましい

---
注意点
finalだけでは不変性が担保されるわけではでない。あくまで1条件で再代入不可という意味。

