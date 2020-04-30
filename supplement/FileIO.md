# ファイルの操作 （スッキリわかるJava入門実践編9.1~9.4ざっくりまとめ）
## Javaの外部資源にアクセスする
### 外部資源の代表例としてファイルの基本操作を学習する

---

### ファイル操作の基本手順
1. 「最初に1回」ファイルを開く
2. 「必要な回数」ファイルのデータを読み込む or <br>ファイルにデータを書き込む
3. 「最後に1回」ファイルを閉じる
---
### 読み書きの方法
- ランダムアクセス
    - ファイルを自由に読み書きできるが、速度が遅く大きいファイルの扱いが苦手
- シーケンシャルアクセス（こちらのほうが一般的）
    - 読み書きの速度が速く、大きなファイルも扱えるがファイルの先頭からしか読み書きできない
---
## テキストファイルの読み書き
### 書き込み
[java.io.FileWriterクラス](https://docs.oracle.com/javase/jp/8/docs/api/java/io/FileWriter.html)を利用する
### 読み込み
[java.io.FileReaderクラス](https://docs.oracle.com/javase/jp/8/docs/api/java/io/FileReader.html)を利用する

---
## バイナリファイルの読み書き
ファイルの分類
- テキストファイル
    - 文字列データでJavaのソースファイル、メモ帳で作成したファイルが相当。これは`FileReader,FileWriter`を使用する
- バイナリファイル
    - 文字列データでは解釈できないデータ(バイト列)で、EXCELファイル、Javaのクラスファイル、画像ファイルが相当。これは[FileInputStream](https://docs.oracle.com/javase/jp/8/docs/api/java/io/FileInputStream.html),[FileOutputStream](https://docs.oracle.com/javase/jp/8/docs/api/java/io/FileOutputStream.html)を使用する

### 文字とバイトの関係

文字もバイト(0と1の組み合わせ)で表現されているので**実質的に同じ**しかし、**プログラマ的に「01000001を書き込む」より「'A'を書き込む」指定できたほうが便利**ばためFileWriterとFileOutputStreamを使用する

---
### 日本語と文字コード体系

日本語はひらがな、カタカナ、漢字にあるので**2バイトで1文字**を表現する。
- 文字コードの例
    - JIS
    - ShiftJIS
    - EUC
    - UTF-8

---
### close()処理を保証する
スッキリJavaでは`finallyブロック`に書くか、`try-with-resource`の書き方で保証する。

---


