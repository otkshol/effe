# コンストラクトの代わりにstaticファクトリメソッドを検討する

## クラスのインスタンスをクライアントが得る方法

- publicのコンストラクタを提供する
    - 例
    ```
    public class Hoge{
        // publicコンストラクタの定義
        public Hoge(){
        }
    }
    ```
  
  （復習）
  - コンストラクタってなんだっけ？
    - インスタンス生成時に自動実行されるメソッドで主に変数の初期化などが実行される
  - コンストラクタの条件
    - メソッド名とクラス名が等しい
    - メソッド宣言に戻り値が記述されていない
    
- publicの **staticファクトリメソッド(static factory method)**
    - 例
    ```
     public static Boolean ValueOf(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }
    ```
#### 補足
- staticファクトリメソッドはデザインパターンのFactory Methodパターンと同じではない
- staticファクトリメソッドの例となってる`BigInteger.probablePrime`はJava4で追加された
---

## publicコンストラクタの代わりにstaticファクトリメソッドの５つの長所
 1.**コンストラクタと異なり、名前を持つこと**
 何を呼び出しているかひと目で分かるようになるので、呼び出し元（クライアント）のコードが読みやすくなる。

 （例）確率的素数（*probable prime*）のBigIntegerを返す場面
 - コンストラクタの場合
 `BigInteger(int,int,random)`
 - staticファクトリメソッドの場合
`BigInteger.probablePrime`
---
 2.**コンストラクタと異なり、呼び出しごとに新たなオブジェクトを生成する必要がないこと**
 - システム起動時にキャッシュされたインスタンスを繰り返し使うことで、メモリ効率が良くなる。
 - 不変(immutable)な値クラスのインスタンスを複数存在させないことを意味する
    -  「値が等しい」ことと「参照値が等しい」ことが等価になる！
 ---
 3.**コンストラクタと異なり、メソッドの戻り値型の任意のサブタイプのオブジェクトを返せること**
 - コンストラクタが返すオブジェクトは実装クラスの型に依存するが、staticファクトリメソッドは柔軟に返すオブジェクトを決めることができる。
 - 役に立ってる具体例: [Collectionsフレームワーク](
 https://docs.oracle.com/javase/jp/8/docs/api/java/util/Collections.html)
 ---
 4.**返されるオブジェクトのクラスは、入力パラメータの値に応じて呼び出しごとに変えられること**
 - 役に立ってる具体例: EnumSetクラス
    - enum型の要素が64個以下の場合、RegularEumSetインスタンスを返し、
    enum型の要素が65個以上の場合、JumboEnumSetインスタンスを返す。
    入力によって呼び出しが変わっているが、呼び出し元は上記の違いを特に気にせずEnumSetのサブクラスを呼び出すことができている。
 
 ---
 5.**返されるオブジェクトのクラスは、そのstaticファクトリメソッドを含むクラスが書かれた時点で存在する必要がない**
 - 役に立っている具体例: JDBC(Java Database Connectivity API)などのサービスプロバイダフレームワーク(SPI)の基盤になっている。
    -  サービスプロバイダフレームワークってなんだ？
        - 特定のインターフェースに一致する実装をJavaが検出して暗黙的にロードするための機能。[参考サイト](https://hazm.at/mox/lang/java/spi/index.html)
        
    -  サービスプロバイダフレームワークの３つのコンポーネント
        - サービスインターフェース (実装を表している)
            - JDBCの場合、Connectionが相当。
        - プロバイダ登録API (プロバイダが実装を登録するために使う)
            - JDBCの場合、DriverManager.registerDriverが相当。
        - サービスアクセスAPI （こいつがstaticファクトリになっている）
            - JDBCの場合、DriverManager.getConnectionが相当。
            
    -  サービスプロバイダフレームワークの4つめのコンポーネント
        - サービスプロバイダインターフェース(サービスインターフェースのインスタンスを生成するファクトリオブジェクトを定義する)
            - これがないと実装はリフレクションでインスタンス化する必要がある。
            - JDBCの場合、Driverが相当。
    - TODO: JDBCの具体例を書いてもう少し深く理解する        

 ---
## staticファクトリメソッドの2つの短所
 1.**publicあるいはprotectedのコンストラクタを持たないクラスのサブクラスは作れない**
 - いわゆる継承ができない。しかしJoshua Bloch的には欠点ではない。（継承はイケてなくてコンポジションを使った方が良い主張）
---
 2.**プログラマがstaticファクトリメソッドを見つけるのが難しい**
 - クライアント側からは実装の中身はひと目では分からない。
    - （対策） 命名規則を遵守することで何をやっているか分かるようにする
        - `from`
            - 単一のパラメータを受け取り、対応する型のインスタンスを返す型変換メソッド(type-convention method)
            - 例 ```Date d = Date.from(instant);```
        - `of`
            - 複数のパラメータを受け取り、それらを含んだ対応する型のインスタンスを返す集約メソッド(aggregation method)
            -  例 ```Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);```            
        - `valueOf`
            - fromやofの代わりとなる、冗長な名前のメソッド
            - 例 ```BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE)```
        - `instaceあるいはgetInstance`
            - パラメータで表しているインスタンスの参照値を返す。
            - 例 ```StackWalker luke = StaclWalker.getInstance(option);```
        - `crateあるいはnewInstance`
            - 呼び出しごとに新たなインスタンスを返す
            - 例 ```Object newArray = Array.newinstance(classObject, arrayLen);```
        - `getType`
            - getInstanceに似ているが、ファクトリメソッドが対象のクラスとは異なるクラスがある場合に使われる。
            - 例 ```FileStore fs = Files.getFileStore(path);```
        - `newType`
            - newInstanceに似ているが、ファクトリメソッドが対象のクラスとは異なるクラスがある場合に使われる。
            - 例 ```BufferedReader br = Files.newBufferedReader(path);```
        - `type`  
            - getTypeやnewTypeの代わりとなる、簡潔な名前のメソッド
            - 例 ```List<Comlpaint> litany = Collections.list(legacyLitany)```
 ---
 ### まとめ
 staticファクトリメソッドがコンストラクタより望ましいので、まずはstaticファクトリメソッドを検討する

