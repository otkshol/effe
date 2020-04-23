# 使われなくなったオブジェクト参照を取り除く

CやC++はメモリ管理を手作業で行う必要があった。。

ガベージコレクションの登場により、それを持つJavaなどの言語を扱うプログラマの仕事は楽になった。

しかしそれは、**プログラマはメモリ管理のことは考えなくて良いことは意味しない！**

下記の動作は問題ないが、**メモリリーク**を抱えている例で考えてみる

- **メモリリークとは**
    - 確保したメモリ領域を解放しないことで、メモリの空き容量が減っていくこと
---
### サンプルコード(StackはJava標準API)
(余談:Joshua BlochはJavaを作った人なのでJava標準のクラスを題材にしがち)
```
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    // コンストラクタで長さ16の配列を宣言している
    public Stack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    // 引数がObject型なので型安全ではない状態（ジェネリクスを使用した型安全を保証したバージョンは項目29で扱う）
    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    // このメソッドがメモリリークの原因になっている！
    public Object pop() {
        if (size == 0)
            throw new EnptyStackException();
        // 
        return elements[--size];
    }

    private void ensureCapacity(){
        if(elements.length == size)
            // size == 0 のときを考慮してcopyOfの第2引数の + 1 がある...がそのケースって実現されるっけ状態。（elements.length == 0　にならない説）
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }
}

```
メモリリークが起きると**ガベージコレクタの活動の増大、メモリ量の増大**によって性能劣化につながる。
極端に言うと、ディスク（物理メモリからスワップ領域へ）のページング(実記憶装置と補助記憶装置のやり取り)
や`OutOfMemoryError`が発生する（実際は滅多に起きない）

---
### メモリリークはどこで起きているのか？
**サイズが大きくなって、その後小さくなったスタック**

理由は、スタックは**使われなくなった参照（*obsolete reference*）**を保持していて、サイズが小さくなっても差分のオブジェクトがガベージコレクトされないから。

---

ガベージコレクトされる言語でメモリリークが起こると、そのオブジェクトが参照しているオブジェクトもガベージコレクションの対象外になる。

なので、メモリリークの量が少量でも雪だるま式にガベージコレクション対象外のオブジェクトが積み上がって性能劣化につながる可能性がある。

---
### 対策
一旦、使われなくなった参照には`null`を設定する。
```
TODO　popのコードかく
```
#### `null`を設定するメリット
- ガベージコレクションの対象にすることができる。
- 誤った参照の使われ方をした場合、`NullPointerException`でエラーにできる。（エラーはできるだけ早い段階で拾うのが良いJoshua Blochの思想）

**しかし**、使用したオブジェクトにnullをすぐに書くべきでもない。**オブジェクト参照にnullを設定するのは例外的であるべき**
使われなくなった参照を排除する最善の方法は、参照が含まれていた変数をスコープ外にすること。(項目57)

---

### え、結局いつnullを例外的に設定するべき？


---
### 余談
- **長さ(length)とサイズって違うっけ？**

やっぱ違いそう。サイズは`Collection`で使われていて、長さ（length）は`String`や`Array`で使われている。直列、まとまってるイメージのものは長さで、連続性はないがまとまっているものを表すのはサイズという直感的な理解をした。


--- 
### 参考
- ArraysクラスのcopyOfメソッド
    - https://docs.oracle.com/javase/jp/8/docs/api/java/util/Arrays.html#copyOf-T:A-int-
- ページング方式のざっくり理解
    - https://wa3.i-3-i.info/word13352.html
    - https://www.atmarkit.co.jp/ait/articles/0404/02/news079_2.html
- 物理メモリ
    - https://wa3.i-3-i.info/word1716.html
- スワップ領域
    - https://wa3.i-3-i.info/word1721.html
- メモリリークとは
    - https://wa3.i-3-i.info/word16066.html


--- 
### 備忘
- コンピュータサイエンスの本届いたらキャッチアップ
    - 抽象データ型（interface）
    - データ構造の話(メモリ上でどう実現されるか)
        - 抽象データ型＋データ構造で作られている型がある
        - 例：ArrayList、HashSet
