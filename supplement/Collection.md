# データ構造と抽象データ型からのJavaコレクションフレームワークの学習

参考
- みんなの　第3章コレクション
- スッキリわかるJava入門実践編 第3章

---
- 抽象データ型


- データ構造


---

- 抽象データ型(Abstract Data Type)
    - 代表例: List, Map, Set
- データ構造
    - 代表例: Array, Linked, DoubleLiked, Hash, Tree

Javaでは`java.util`パッケージにコレクションフレームワークとして提供されていて
**データ構造 + 抽象データ型**の名称でクラスが提供されている（例:ArrayList, HashMap）

---

### ArrayListでコレクションに触れてみる
#### ArrayListと配列[]の違い３つ
- ArrayListにはimport文を記述
    - ```import java.util.ArrayList;```
- ArrayListは不等号の括弧を使って、格納するオブジェクトの型を指定する（ジェネリクスの使用）
    - ```ArrayList<String>```
- ArrayListは宣言時にサイズ指定の必要がなく、要素が適宜変更可能


メモリ効率は配列のほうが良いが、要素数や要素の中身を適宜変更できる利便性からArrayListはよく使用されている。
しかし、コレクションにはインスタンスでないものは格納できない。すなわち、基本データ型は格納できない。

例：ArrayListの要素にはInteger型は格納できるが、int型は格納できない。

int型をいれようとすると、オートボクシングが実行されてInteger型として格納される。（意図しないオートボクシングはメモリ効率を悪くするので注意！）

#### ArrayListの宣言


- Java6以前
    ```
    List<Integer> points = new ArrayList<Integer>();
    ```

- Java7以降
    ```
    List<Integer> points = new ArrayList<>();
    ```

用意されているメソッドは[ArrayListのJavadoc](https://docs.oracle.com/javase/jp/8/docs/api/java/util/ArrayList.html)参照。
Listインタフェースを実装していることを理解してると、代表的なメソッドは想像できるかも... 

---

### ArrayList以外のリスト
#### LinkedList
同じListを実装しているので、使用できるメソッドや使いかたもほぼ同じだが、クラスの内部実装が異なる。

具体的な差分はArrayとLinked

|   |  ArrayList |  LinkedList  | 
| ---- | ---- | ---- | 
|  内部構造 |  配列  |  連結リスト  |
|  要素の挿入・削除<br>```add() ,remove()``` |  ✗<br>(遅い)  |  ○<br>(高速) | 
|  指定位置要素の取得<br>```get()``` |  ○<br>(高速)  |  ✗<br>(遅い) | 

---
## List以外の主要なコレクションクラス

### Set
よく使われるのは`java.util.HashSet`クラス
抽象データ型`Set`でデータ構造が`Hash`である。

#### Setの特性
 - 要素の重複がない
    - 同じ要素を追加しても実行時エラーにはならず無視して実行される 
 - 要素間に順序関係がない
    - よってインデックスを指定する`set(),get()`が定義されていない
    - 要素を取り出す処理は`Iterator`で可能だが、順序が保証されていないことに注意
    - 順序を保証したいときは`Linkedhash　TreeSet`を使用する

---
### Map

**キー(key)と値(value)のペアとして格納する**
よく使われるのは`java.util.HashSet`クラス
