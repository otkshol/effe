# equalsをオーバーライドするときは一般契約に従う


`java.lang.Object`のequalsメソッドをオーバーライドするときに **一般契約(general contract)** に従っていないと、バグの温床になる。

---

### `equals`メソッドをオーバーライドしなくても良いケース
- クラスの個々のインスタンスが本質的に一意である
    - `Thread`のように値ではないものを表し一意であるインスタンス
- クラスが「論理的等価性(logical equality)」の検査を提供する必要がない
    - 例で`java.util.regex.Pattern`が挙がっいる。これは同じ内容を複数の正規表現を用いて表現できているかチェックすることは、プログラマからの需要が少ないかつ作成に工数がかかりメリットが薄いからオーバーロードしなかった説がある。
- スーパークラスがすでに`equals`メソッドをオーバーライドしており、スーパークラスの振る舞いが実装クラスに対して適切な場合
    - 例だと、Set,List,Mapに対するそれぞれのabstractクラスが挙がっている
- クラスが`private`あるいはパッケージプライベートであり、`equals`メソッドが呼び出されないことが確かである。
    - アクセス可能範囲が限られていることから、equalsメソッドの呼び出しが目視でも判断可能なケースを指していると想定している。
    - もし`equals`メソッドが呼ばれたら例外を投げるようにオーバーライドする対策方法もある
    ```
    @Override public boolean equals(Object o) {
        throw new AssertionError();
    } 
    ```
---
### そもそもqualsメソッドをオーバーライドしたいときっていつ？
**論理的等価性**を確認したいとき(対となる概念は物理的等価性？）つまり、参照先の値を比較したいときにオーバーライドする必要がある。
Objectクラスのequalsメソッドは参照値比較で等価判定を行うので、参照先の値で比較をするためにオーバーライドをする。

---
 # 一般契約
 - 反射律 (*reflexive*)
    - nullでない任意の参照値*x*に対して、`x.equals(x)`は`true`を返す
 - 対象律 (*symmetric*)
    - nullでない任意の参照値*x、y*に対して、`y.equals(x)`が`true`を返すときのみ`x.equals(y)`は`true`を返す
 - 推移律 (*transitive*)
    - nullでない任意の参照値*x,y,z*に対して、`x.equals(y)`と`y.equals(z)`が`true`を返すとき、`x.equals(z)`は`true`を返す
 - 整合律 (*consistent*)
    - nullでない任意の参照値*x,y*に対して、`x.equals(y)`を複数回呼び出した際に結果が一貫して`true`か`false`を返す
 - null性
    - nullでない任意の参照値*x*に対して、`x.equals(null)`は`false`を返す

 ---   

## 反射律 (*reflexive*)


## 対象律 (*symmetric*)
    

    
## 推移律 (*transitive*)
    
## 整合律 (*consistent*)
   
## null性
    