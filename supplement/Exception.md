# 例外

## スッキリわかるJava15章復習

### Javaにおける3種類の不具合

1. 文法エラー(systax error)

文法誤りによるコンパイル失敗

2. 実行時エラー（runtime error）

実行時に異常事態が発生して、動作継続ができなくなること。
例:範囲外の配列要素アクセス、0での割り算

3. 論理エラー(logic error)
文法問題もなく動作終了もしないが、動作が想定と


**例外処理は実行時エラーが発生したときの対応をする**

---

### 15.1.3 例外的状況

**プログラマがソースコードを作成する時点では例外発生を予防できない**

例外的状況の例
- PCのメモリ不足
- 存在するはずのファイルが見つからない
- nullが入っている変数を利用しようとした

---
## 例外処理の流れ

Java例外処理の基本パターン
```
try{
    通常実行される文
}catch(…){
    例外発生時に実行される文
}
```
### 15.3.2 例外の種類

- Error系例外（キャッチする必要はない）
    - 回復の見込みがない致命的な状況
        - OutOfMemoryError（メモリ不足）
        - ClassFormatError（クラスファイルが壊れている）
- Exception系例外（キャッチしないとコンパイルエラー）
    - 発生を想定して対処を考える必要がある例外的状況
        - IOException
        - ConnectExcetion
- RuntimeException系例外（キャッチは任意）
    - 必ずしも常に発生を想定すべきでない例外的状況
        - NullPointerException
        - ArrayIndexOutOfBoundsException

---
### 15.3.4 発生する例外の調べ方
APIレファレンスに発生可能性のある例外が書いてある
```
public FileWriter(String fileName)
           throws IOException
```

---
## 15.4 例外の発生と例外インスタンス

### 15.4.1 例外インスタンスの受け渡し

```
try{
    ︙
} catch (IOException e){
    ︙
    System.out.println("エラーです。終了します。");
    System.exit(1);
}
```
**例外的状況の詳細情報が詰め込まれているIOExceptionインスタンスをcatch文で指定された変数eに代入**


### 15.4.2 例外インスタンスの利用

例外インスタンスが必ず備えているメソッド

エラーメッセージを取得
```
String getMessage() 
```
スタックトレースの内容を画面に出力する
```
void printStackTrace() 
```

## 15.5 さまざまなcatch構文
