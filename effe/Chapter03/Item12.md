# toStringを常にオーバライドする

`Object.toString`が返す文字列は
```
getClass().getName() + '@' + Integer.toHexString(hashCode())
```
であり、人間には意味がわかりにくい。
（参考）[`Object.toString`のJavadoc](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Object.html#toString--)

---
## toStringの一般契約（２つの努力目標のイメージ）
- **返される文字列**
- **すべてのサブクラスがtoStringをオーバライドすることを推奨する**と

具体例
```
{Jenny=PhoneNumber@abdbd} より　{Jenny=707-867-5309}のほうが望ましい
```
---

## toStringが実行されるタイミング
- println
- printf
- 文字列結合演算子(+)
- asssertにオブジェクトが渡される
- デバッガでオブジェクトが表示される(例:EclipceのInspection)

---
## 優れたtoStringメソッドをPhoneNumberに提供した場合

```
System.out.println("Failed to connect: " + phoneNumber);
```
上記の場合
"+"演算子でtoStringが実行されて引数がStringのprintlnが実行されている

<補論>
- Systemは大文字始まりなのでクラス確定
- クラス名に.が続いた時点でstaticメソッドかstaticフィールドが確定
- ()の有無で判断（()があればstaticメソッド確定なければstaticフィールド確定）
- staticフィールドの次に.が続いた時点で基本データ型ではないことが確定。
- さらに()があるので、インスタンスメソッドであることが確定する
- まとめると、Systemクラスのstaticフィールドoutのインスタンスメソッドであるprintln(String string)が実行されている
- outの型はJavadocを確認すると、printStreamであることが分かる

---
## 注意点
toStringは意味のある情報を全て含む必要がある。例えば、３つのフィールドを用いたequals()なのに2つのフィールドのみの情報を出力すると下記のようなメンテナンスが難しい内容になってしまう。。。
```
Assertion failure: expected {abs, 123}, but was {abc, 123}.
```

---
## toString戻り値のドキュメンテーション
電話番号、行列などの値クラス(value class)に対しては明示推奨。
- メリット
    - 曖昧さがなく可読性が高まる。

形式を明示して、対応するスタティックファクトリメソッドやコンストラクタを提供すとオブジェクトと文字列表現の相互変換ができて良い。
- 例
    - BigInteger
    - BigDecimal
    - Integer
    - Double

- デメリット
    - 明示した形式を変更できない
        - 形式の意図をドキュメントに明確に書くべき。

