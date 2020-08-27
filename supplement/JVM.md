# JVM制御とリフレクション(スッキリJava実践編7章ざっくりまとめ)
## JVM

## JVMの終了
`System.exit()`メソッドでプログラムを終了させる。
- 引数は0で正常終了で、それ以外は異常終了を意味する。バッチの異常終了時に原因切り分けでよく使うらしい

---
## 外部プログラムの実行
`java.lang.ProcessBuilder`クラスで、計算結果をメモ帳に出力することができる。

---
## システムプロパティの利用


---
## メモリに関する状態の取得
- `public long freeMemory()` :残りメモリ容量を返す
- `public long totalMemory()`　:現在のメモリ総容量を返す
- `public long maxMemory()`　:メモリ総容量の限界値を返す

---
## リフレクション
リフレクションAPIを利用すると、クラスやインターフェースに関するさまざまな情報を（フィールドやメソッドなど）をJVMに調べさせることができる。

### リフレクションの用途
- テストや解析のために`private`メンバを操作したい場合
- メンバ名を用いた特殊な処理を作り込みたい場合
- 利用するクラスを動的に追加・変更できるようにしたい場合（Strategyパターン）