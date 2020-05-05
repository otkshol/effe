# さまざまな種類のクラス (スッキリJava実践編第5章ざっくりまとめ)
## 型安全という価値
### 型を用いるメリット
型は制限するもの。制限することで、**変数の順番や、内容を間違えたときにコンパイルエラーで人間が気付けるメリット**がある。

型によって担保される安全性を **型安全(*type safe*)** という。
Javaは **静的型付け(*static typing*)** である。

---
## ジェネリクス
型安全のために< ~ > を使用する。
使用例は`ArrayList`や`HashMap`でそれらはジェネリクスという仕組みを用いて定義されている。

---
## 列挙型 (*enum type*)
指定した種類の値だけを入れることができる。

`enum`概要
```
アクセス修飾子 enum 列挙型名 {
    列挙子1, 列挙子2, 列挙子3 … 列挙子X;
}
```

```
enum AccountType {
    FUTSU, TOUZA, TEIKI;
}
```
上記のenumを呼び出すと```AccountType.FUTSU```となるが*staitc import*をすることで`FUTSU`で呼び出すことができる。

---
## インナークラス
クラス内クラスのイメージ。宣言場所で3種類に分けられる。
- メンバクラス
    - クラスのメンバとして定義するクラス
    - 外部からアクセス可能
- ローカルクラス
    - メソッド内部でのみ有効
    - 外部からアクセス不可能
- 匿名クラス
    - あるメソッドで1回しか使わないクラス
    - 宣言と利用を同時に行う

インナークラスはラムダ式の台頭で、使用頻度は下がっている。