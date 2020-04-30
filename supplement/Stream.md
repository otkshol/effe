# ストリームの概念 （スッキリわかるJava入門実践編9.5~ざっくりまとめ）

### ストリームとは
データの読み書きを**小川**として抽象的に捉えて、応用・発展させる考え。

---
### フィルタとは
流れるデータに対して、多種多様な変換を行うことができる。
ストリームを導入するメリットになっている

- 特徴
    - Filter ~ クラスを継承している
        - `FilterReader`
        - `FilterWriter`
        - `FilterInputStream`
        - `FilterOutputStream`
    - 単独で存在できず、他に接続して生成する
        - フィルタ単独newできない
    - フィルタを複数連結可能
        - 例えば、文字をバイト型にしてから暗号化することが可能
    
#### バッファリングフィルタ
java.ioパッケージに準備されており、利用頻度が高い
- `BufferedReafer`
- `BufferedWriter`
- `BufferedInputStream`
- `BufferedOutputStream`

メリット
- 処理性能の向上
- まとまった単位でデータを読める


        