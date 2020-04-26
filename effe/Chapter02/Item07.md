# 使われなくなったオブジェクト参照を取り除く

CやC++はメモリ管理を手作業で行う必要があった。。

ガベージコレクションの登場により、それを持つJavaなどの言語を扱うプログラマの仕事は楽になった。

しかしそれは、**プログラマはメモリ管理のことは考えなくて良いことは意味しない！**

下記の動作は問題ないが、**メモリリーク**を抱えている例で考えてみる

- **メモリリークとは**
    - 確保したメモリ領域を解放しないことで、メモリの空き容量が減っていくこと
---
### サンプルコード(StackはJava標準API)
(余談:Joshua BlochはJavaを作った人なのでJava標準のクラスを題材にしがち)[ArraysクラスのcopyOfメソッドのリファレンス](https://docs.oracle.com/javase/jp/8/docs/api/java/util/Arrays.html#copyOf-T:A-int-)
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
[メモリリーク](https://wa3.i-3-i.info/word16066.html)が起きると**ガベージコレクタの活動の増大、メモリ量の増大**によって性能劣化につながる。
極端に言うと、ディスク（[物理メモリ](https://wa3.i-3-i.info/word1716.html)から[スワップ領域](https://wa3.i-3-i.info/word1721.html)へ）の[ページング](https://wa3.i-3-i.info/word13352.html)([実記憶装置と補助記憶装置のやり取り](https://www.atmarkit.co.jp/ait/articles/0404/02/news079_2.html))
や`OutOfMemoryError`が発生する（実際は滅多に起きない）

---
### メモリリークはどこで起きているのか？
**サイズが大きくなって、その後小さくなったスタック**

理由は、スタックは使われなくなった参照（*obsolete reference*）を保持していて、サイズが小さくなっても差分のオブジェクトがガベージコレクトされないから。

---

ガベージコレクトされる言語でメモリリークが起こると、そのオブジェクトが参照しているオブジェクトもガベージコレクションの対象外になる。

なので、メモリリークの量が少量でも雪だるま式にガベージコレクション対象外のオブジェクトが積み上がって性能劣化につながる可能性がある。


具体例: 参照値を持つオブジェクトのメモリリークを考える
```
public class Dto {
    int id;
    string name;
}
```
呼び出し元
```
Stack stack = new Stack();
Dto dto1 = new Dto( 1, "hoge");
// dto2がガベージコレクションの対象外になるということは、"fuga"もガベージコレクションの対象外になる。
Dto dto2 = new Dto( 2, "fuga");
stack.push(dto1);
stack.push(dto2);
// ここでdto2は使われなくなるが参照値はもったまま
stack.pop();
```
---
### 対策
一旦、使われなくなった参照には`null`を設定する。
```
public Object pop() {
    // ガード節
    if (size == 0)
        // 例外翻訳をしている。これがなくてもArrayIndexOutOfBoundsExceptionが投げられるがこちらのほうがより親切
        throw new EmptyStackException;
    // ここで参照値をいったんtmp変数に格納する
    Object result = elements[--size];
    // ここで使用されなくなった参照を取り除く
    elements[size] = null;

    return result;
}
```
ガベージコレクションが実行されるのいいつ？
変数のスコープ

#### `null`を設定するメリット
- ガベージコレクションの対象にすることができる。
- 誤った参照の使われ方をした場合、`NullPointerException`でエラーにできる。（エラーはできるだけ早い段階で拾うのが良い `コンパイルエラー > 実行時エラー > 動作バグ `）


**しかし**、使用したオブジェクトにnullをすぐに書くべきでもない。**オブジェクト参照にnullを設定するのは例外的であるべき**
使われなくなった参照を排除する最善の方法は、参照が含まれていた変数をスコープ外にすること。(項目57)

---

### え、結局いつnullを例外的に設定するべき？
**一般的にクラスが独自のメモリを管理しているとき**
フィールド変数かつ仕様として使われない参照にnull

例: 上述のStackクラス。

この記憶プール(*storage pool*)は、element配列の要素からなる。
`割当(allocated) + 解放(free)`で成り立っているが、ガベージコレクタからしたらすべて有効なオブジェクト扱いになる。

---

### 参照を切る方法をいったん整理（ガベージコレクトを有効にする方法）
1. 変数をスコープアウトする
    - 一般的にこっちをやる。対象は仮引数とローカル変数
2. 参照値にnullを代入
    - 対象はフィールド変数。
    - 参照値にnullを代入する処理には意味があることを伝えるためにコメントをつけたほうが良い（Java標準APIの実装ですらやっている！例:Vector．class）
    ```
     elementData[elementCount] = null; /* to let gc do its work */
    ```

---
### 他のよくあるメモリリーク原因2選

- 原因1: 参照をキャッシュに入れたままにしてしまう
    - 解決方法
        - キャッシュを`WeakHashMap`で表現する（キャッシュのエントリに対するキーへの参照がキャッシュの外にある場合）
            - エントリは1組のkeyとvalueのことを指す(キャッシュがMapで表現されているので)
            - `WeakHashMap`が初見だったので[Javadoc](https://docs.oracle.com/javase/jp/8/docs/api/java/util/WeakHashMap.html)と[こちらの記事](https://qiita.com/Apacher-inf/items/fbcc00316a4085e45413)でキャッチアップ
            - `WeakHashMap`はキーが弱参照となっている。弱参照の使い所はマルチスレッドでインスタンスを再利用したいとき。参考記事は[こちら](https://qiita.com/ReijiHata/items/f1c1c580f0725b576f59)
                - （補足）参考記事の中で`syschronized`がわからなかったので、スッキリわかるJava入門実践編19.3.1,19.3.2と[悲観ロック・楽観ロックの記事](https://qiita.com/NagaokaKenichi/items/73040df85b7bd4e9ecfc)を参照
                -  (さらに補足)シングルスレッドでも`ConcurrentModificationException`が発生するケースを[コチラの記事](https://qiita.com/ukitiyan/items/adec43ea77cb78169e80)で考察
        - `WeakHashMap`ではエントリが無効になるとガベージコレクトの対象外になって破棄される。よってメモリリークを防ぐことができる
            -  上記の流れ
                1. キャッシュの外からの参照が切れる
                2. キャッシュエントリのキーが強参照でなくなる（弱参照になる）
                3. ガベージコレクションの対象となる
                4. キャッシュエントリのキーが消える
                5. キャッシュエントリのキーを持たない値も消える
                6. すなわちキャッシュエントリが消える
        - 一般的にはキャッシュエントリは時間経過に伴って意味をなさなくなるので適宜除去されるべき。これは[ScheduledThreadPoolExecutor](https://docs.oracle.com/javase/jp/8/docs/api/java/util/concurrent/ScheduledThreadPoolExecutor.html)で実現するか、キャッシュにエントリを追加するときに副作用として実行することもできる。
            - 前者の`ScheduledThreadPoolExecutor`の概要は[こちらの記事](https://qiita.com/amay077/items/b5f4e98b50d7fbcbaaec)を参照
                - 定期的にweakHashMapのキーをクリアしたり、ガベージコレクションを動作させる処理 を定期的にプログラムに実行してもらうイメージ
            - 後者の具体例は、[LinkedHashMapクラスのremoveEldestEntryメソッド](https://docs.oracle.com/javase/jp/8/docs/api/java/util/LinkedHashMap.html#removeEldestEntry-java.util.Map.Entry-)
                - キューのイメージで新しいキャッシュエントリが追加されたら古いキャッシュエントリが玉突きで削除されるイメージ
        - 自分でカスタマイズして、より高度な(というよりオーダーメイドに応えた？原著は`sophisticated`と書いてて`LinkedHashMap`の作者であるJoshua Blochの若干のドヤを感じる笑)キャッシュ[java.lang.ref](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/ref/package-summary.html])を直接使う場合もある


- 原因2:リスナーやコールバック
    - リスナー
    - コールバック
    - クライアントがコールバックを登録するが明示的に登録を解除しないAPIを実装するとき、コールバックが蓄積される。（例:グーグルマップAPIで地図見るたびにコールバック登録が蓄積される？）
    - 解決方法
        - `WeakHashMap`のキーとしてAPIのコールバックを保存する

---

メモリリークはエラーとしては現れないので発見しにくい...
コードインスペクションの結果やデバッグツールの**ヒーププロファイラ(*heap profiler*)** を使うと発見できる。
メモリリークもできるだけ早い段階で検知できることが望ましい。


---
### 余談
- **長さ(length)とサイズって違うっけ？**

やっぱ違いそう。サイズは`Collection`で使われていて、長さ（length）は`String`や`Array`で使われている。直列、まとまってるイメージのものは長さで、連続性はないがまとまっているものを表すのはサイズという直感的な理解をした。

--- 
### 備忘
- コンピュータサイエンスの本届いたらキャッチアップ
    - 抽象データ型（interface）
    - データ構造の話(メモリ上でどう実現されるか)
        - 抽象データ型＋データ構造で作られている型がある
        - 例：ArrayList、HashSet