# privateのコンストラクタかenum型でシングルトン特性を強制する

シングルトン(singleton)は、厳密に一度のみインスタンス生成されるクラスのこと。
シングルトンにすると、呼び出し元のテストが難しくなる（モック実装ができないため）

- シングルトン実装の2つの方法
    - public finalフィールドによるシングルトン
    ```
    public class Elvis {
        public static final Elvis INSTANCE = new Elvis();
  
        // リフレクション対策で２つ目のインスタンスが生成されたら例外スローする
        // 具体的にAccessibleObject.setAccessibleでリフレクションできる
        private Elvis() {
            throw new IllegalArgumentException("シングルトンに対して2つ目のインスタンス生成例外")
        }
   
        public void leaveTheBuilding(){...}     
    }
    ```
    - staticファクトリメソッドによるシングルトン
    ```
    public class Elvis {
        private static final Elvis INSTANCE = new Elvis();
        private Elvis() { ... }
        public static Elvis getInstance() { return INSTANCE; }
  
        public void leaveTheBuilding() {...}
    }
    ```
  
  - public finalフィールドによるシングルトンの長所
    - クラスがシングルトンであることがAPIから明らか
    - 単純である
  
  - staticファクトリメソッドによるシングルトンの長所
    - APIを変更せずにシングルトンかどうかを決めることができる(シングルトンかどうかを呼び出し元は意識しないでOK)
    - ジェネリックのシングルトンファクトリを書ける
    - メソッド参照を使うことができる（例: Elvis::getInstanceをSupplier<Elvis>として使える） 
  
  - 上記２つの短所
    - シリアライズ可能にするために`implements Serializable`を追加するだけでは不十分。
        - readResolve()メソッドを追加する必要がある...
  
  ## シングルトン実装の3つ目の方法(単一要素を持つenumを宣言)
  ```
  public enum Elvis {
    INSTANCE;
  
    public void leaveTheBuilding() { ... }
  }
  ```
  
  - enumのメリット
    - なにもしなくてもシリアライズ可能
    - シリアライズ攻撃やリフレクション攻撃も大丈夫
  - enumのデメリット
    - Enum以外のスーパークラスの拡張の場合使えない
    
  ## まとめ
  シングルトンを実装するときにはenumが良い！  
  
  
    
    
  